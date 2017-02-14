using Bac1Interfaces.Data;
using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Bac1Interfaces.DataHandler
{
    class UKdaleDataHandler : IDataHandler
    {
        DatabaseDriver myDatabase = new DatabaseDriver();

        Boolean filterByTimeEnabled = false;

        int currentTime = 0;

        List<Tuple<int[], ApplicationEnum>> house1ApplicationList = new List<Tuple<int[], ApplicationEnum>>();
        List<Tuple<int[], ApplicationEnum>> house2ApplicationList = new List<Tuple<int[], ApplicationEnum>>();
        List<Tuple<int[], ApplicationEnum>> house3ApplicationList = new List<Tuple<int[], ApplicationEnum>>();
        List<Tuple<int[], ApplicationEnum>> house4ApplicationList = new List<Tuple<int[], ApplicationEnum>>();
        List<Tuple<int[], ApplicationEnum>> house5ApplicationList = new List<Tuple<int[], ApplicationEnum>>();

        List<Tuple<int, ApplicationEnum>> housesList = new List<Tuple<int, ApplicationEnum>>();

        public UKdaleDataHandler(DatabaseDriver _myDatabase, DateTime? _starttime = null, DateTime? _endtime = null, List<ApplicationEnum> _application = null, List<TypeEnum> _types = null, double _frequenzy = 0)
        {
            this.myDatabase = _myDatabase;

            this.types = _types;
            this.application = _application;
            this.frequenzy = _frequenzy;

            if (_starttime != null)
                this.starttime = (DateTime)_starttime; //set starttime if one is given
            else
                this.starttime = new DateTime(2000, 1, 1); //otherwise set a unusable Datetime

            if (_endtime != null)
                this.endtime = (DateTime)_endtime; //set endtime if one is given
            else
                this.endtime = new DateTime(2000, 1, 1); //otherwise set a unusable Datetime

            this.fillDBWithMeters();
        }

        /// <summary>
        /// Check if given Path bellongs to a directory or to a file
        /// </summary>
        public override void ParseDirectory()
        {
            ///Create a compareable time
            DateTime comparetime = new DateTime(2000, 1, 1);

            if (this.starttime == comparetime && this.endtime == comparetime)
            {
                Console.WriteLine("No time filter used");
                this.filterByTimeEnabled = false;
            }
            else
            {
                Console.WriteLine("Time filter used");
                this.filterByTimeEnabled = true;
            }

            if (File.Exists(this.Path))
            {
                // This path is a file
                this.processFile(this.Path);
            }
            else if (Directory.Exists(this.Path))
            {
                // This path is a directory
                this.processDirectory(this.Path);
            }
            else
            {
                Console.WriteLine("{0} is not a valid file or directory.", this.Path);
            }

            Console.WriteLine("Succesfully loaded Files into DB <UK-DALE>");
        }

        /// <summary>
        /// Processes the directory. If directory has subdirectory, recurse. Else work all the containing files.
        /// </summary>
        /// <param name="targetDirectory"></param>
        private void processDirectory(String targetDirectory)
        {
            // Process the list of files found in the directory.
            string[] fileEntries = Directory.GetFiles(targetDirectory);
            foreach (string fileName in fileEntries)
            {
                this.processFile(fileName);
            }


            // Recurse into subdirectories of this directory.
            string[] subdirectoryEntries = Directory.GetDirectories(targetDirectory);
            foreach (string subdirectory in subdirectoryEntries)
            {
                processDirectory(subdirectory);
            }
        }

        /// <summary>
        /// Check if the file has voltage in it. if it has, continue
        /// </summary>
        /// <param name="path"></param>
        protected override void processFile(String path)
        {
            if (this.types == null)
            {
                Console.WriteLine("No Type Filtering");
                Match matchDisagregation = Regex.Match(path, "channel");
                if (matchDisagregation.Success)
                {
                    this.workDisagregationFile(path);
                }

                //If filename contains dataset, start function
                Match matchFast = Regex.Match(path, "voltage");
                if (matchFast.Success)
                {
                    this.workFile(path);
                }
            }
            else
            {
                int disagregated = 0;
                int aggregated = 0;
                foreach (TypeEnum currentType in this.types)
                {
                    if (currentType == TypeEnum.Current || currentType == TypeEnum.Voltage && aggregated == 0)
                    {
                        //If filename contains dataset, start function
                        Match matchFast = Regex.Match(path, "voltage");
                        if (matchFast.Success)
                        {
                            this.workFile(path);
                        }
                        aggregated++;
                    }
                    else if (currentType == TypeEnum.Power && disagregated == 0)
                    {
                        Match matchDisagregation = Regex.Match(path, "channel");
                        if (matchDisagregation.Success)
                        {
                            matchDisagregation = Regex.Match(path, "button");
                            if (matchDisagregation.Success)
                            {
                            }
                            else
                            {
                                this.workDisagregationFile(path);
                            }
                        }
                        disagregated++;
                    }
                }
            }
        }

        private void workDisagregationFile(String path)
        {
            String[] paths = path.Split('\\');

            ///For later usage to find the household
            String household = paths[paths.Count() - 2];
            int householdNumber = (int)Char.GetNumericValue(household[household.Count() - 1]);

            ///Device identifier
            String device = paths[paths.Count() - 1];
            String[] deviceParts = device.Split('.');
            String[] furtherDeviceParts = deviceParts[0].Split('_');
            int deviceNumber = Int32.Parse(furtherDeviceParts[1]);

            bool filterOk = false;

            ///Filter for appliance
            if (this.application != null)
            {
                foreach (ApplicationEnum currentApp in this.application)
                {
                    filterOk |= this.compareApplicationEnumWithCurrentHouseholdApp(currentApp, householdNumber, deviceNumber);
                }
            }
            else
                filterOk = true;

            if (filterOk == true)
            {
                using (StreamReader file = new StreamReader(path))
                {
                    if (file != null)
                    {
                        String line = "";
                        while ((line = file.ReadLine()) != null)
                        {
                            if (line != null)
                            {

                                String[] values = line.Split(' ');

                                String[] PowerValues = new String[(values.Count())];


                                if (!swapTimestampifSecondsPassed(values[0]))
                                    continue;

                                ///Only store data to database if the current timestamp is in the given boundries
                                if (Int32.Parse(values[0]) < this.getUnixTimestampFromDate(this.starttime) || Int32.Parse(values[0]) > this.getUnixTimestampFromDate(this.endtime) && this.filterByTimeEnabled)
                                {
                                }
                                else
                                {

                                    for (int i = 0; i < values.Count(); i++)
                                    {
                                        PowerValues[i] = values[i];
                                    }

                                    ///CHANGE TO REAL METERS
                                    MeterData temp = new MeterData(this.housesList[0].Item1);
                                    temp.SetTimestamp(values[0]);
                                    temp.SetPowerP1(Convert.ToDouble(values[1]));

                                    myDatabase.InsertMeterDataIntoDb(temp);
                                }
                            }
                        }
                    }
                }
                Console.WriteLine("Loaded " + household + " " + device + " sucessfully!");
            }
        }

        private bool compareApplicationEnumWithCurrentHouseholdApp(ApplicationEnum currentApp, int household, int device)
        {
            switch (household)
            {
                case 1:
                    {
                        foreach (Tuple<int[], ApplicationEnum> currentTupel in this.house1ApplicationList)
                        {
                            if (currentTupel.Item2 == currentApp && currentTupel.Item1[1] == device)
                            {
                                return true;
                            }
                        }
                        break;
                    }
                case 2:
                    {
                        foreach (Tuple<int[], ApplicationEnum> currentTupel in this.house2ApplicationList)
                        {
                            if (currentTupel.Item2 == currentApp && currentTupel.Item1[1] == device)
                            {
                                return true;
                            }
                        }
                        break;
                    }
                case 3:
                    {
                        foreach (Tuple<int[], ApplicationEnum> currentTupel in this.house3ApplicationList)
                        {
                            if (currentTupel.Item2 == currentApp && currentTupel.Item1[1] == device)
                            {
                                return true;
                            }
                        }
                        break;
                    }
                case 4:
                    {
                        foreach (Tuple<int[], ApplicationEnum> currentTupel in this.house4ApplicationList)
                        {
                            if (currentTupel.Item2 == currentApp && currentTupel.Item1[1] == device)
                            {
                                return true;
                            }
                        }
                        break;
                    }
                case 5:
                    {
                        foreach (Tuple<int[], ApplicationEnum> currentTupel in this.house5ApplicationList)
                        {
                            if (currentTupel.Item2 == currentApp && currentTupel.Item1[1] == device)
                            {
                                return true;
                            }
                        }
                        break;
                    }
            }
            return false;
        }

        private void workFile(String path)
        {
            String pathToVoltageFile = path;
            String pathToCurrentFile = "";
            String[] paths = pathToVoltageFile.Split('\\');

            //Gets the household number
            String household = paths[paths.Count() - 2];
            int householdNumber = (int)Char.GetNumericValue(household[household.Count() - 1]);

            for (int i = 0; i < (paths.Count() - 1); i++)
            {
                pathToCurrentFile += paths[i] + "\\";
            }
            String[] timestamp = paths[paths.Count() - 1].Split('-');

            String curentTimestamp = this.removeMilisFromTimestamp(timestamp[0]);

            pathToCurrentFile += timestamp[0] + "-current";

            int chunkSize = 1024;

            using (FileStream fsVoltage = new FileStream(pathToVoltageFile, FileMode.Open, FileAccess.Read))
            {
                using (BinaryReader binReaderVoltage = new BinaryReader(fsVoltage, new ASCIIEncoding()))
                {
                    using (FileStream fsCurrent = new FileStream(pathToCurrentFile, FileMode.Open, FileAccess.Read))
                    {
                        using (BinaryReader binReaderCurrent = new BinaryReader(fsCurrent, new ASCIIEncoding()))
                        {
                            byte[] chunkVolts;
                            byte[] chunkAmps;

                            int k = 0;
                            int l = 0;

                            while (binReaderVoltage.BaseStream.Position != binReaderVoltage.BaseStream.Length)
                            {
                                int realTimestamp = Int32.Parse(curentTimestamp) + l;

                                if (!swapTimestampifSecondsPassed(realTimestamp.ToString()))
                                {
                                    if (k == (16000 / chunkSize))
                                    {
                                        k = 0;
                                        l++;
                                    }
                                    k++;

                                    continue;
                                }

                                ///If value timestamp is out of timestampvalue / Filter by time
                                if (realTimestamp < this.getUnixTimestampFromDate(this.starttime) || realTimestamp > this.getUnixTimestampFromDate(this.endtime) && this.filterByTimeEnabled)
                                {
                                }
                                else
                                {
                                    if (this.frequenzy == 0)
                                    {
                                        chunkVolts = binReaderVoltage.ReadBytes(chunkSize);
                                        chunkAmps = binReaderCurrent.ReadBytes(chunkSize);

                                        double[] voltValues = new double[chunkVolts.Length / 8];
                                        double[] ampValues = new double[chunkAmps.Length / 8];
                                        for (int j = 0; j < voltValues.Length; j++)
                                        {
                                            voltValues[j] = BitConverter.ToDouble(chunkVolts, j * 8);
                                            voltValues[j] *= Math.Pow(2, 31); //conversion factor
                                            voltValues[j] *= (1.90101491444 * Math.Pow(10, -7));
                                        }

                                        for (int j = 0; j < ampValues.Length; j++)
                                        {
                                            ampValues[j] = BitConverter.ToDouble(chunkAmps, j * 8);
                                            ampValues[j] *= Math.Pow(2, 31); //conversion factor
                                            ampValues[j] *= (4.9224284384 * Math.Pow(10, -8));
                                        }

                                        double[] powerValue = new double[ampValues.Length];
                                        for (int i = 0; i < powerValue.Count(); i++)
                                        {
                                            powerValue[i] = voltValues[i] * ampValues[i];

                                            ///Filtering whole houses does not work yet
                                            MeterData temp = new MeterData(this.housesList[householdNumber - 1].Item1);

                                            ///Frequenzy = 16kHz
                                            ///all 16 tousand times, one second passes
                                            temp.SetTimestamp(realTimestamp.ToString());
                                            temp.SetPowerP1(powerValue[i]);
                                            temp.SetVoltage(voltValues[i]);

                                            myDatabase.InsertMeterDataIntoDb(temp);
                                        }
                                    }
                                    else
                                    {
                                        chunkVolts = binReaderVoltage.ReadBytes(chunkSize);
                                        chunkAmps = binReaderCurrent.ReadBytes(chunkSize);

                                        double[] voltValues = new double[chunkVolts.Length / 8];
                                        double[] ampValues = new double[chunkAmps.Length / 8];
                                        for (int j = 0; j < voltValues.Length; j++)
                                        {
                                            voltValues[j] = BitConverter.ToDouble(chunkVolts, j * 8);
                                            voltValues[j] *= Math.Pow(2, 31); //conversion factor
                                            voltValues[j] *= (1.90101491444 * Math.Pow(10, -7));
                                        }

                                        for (int j = 0; j < ampValues.Length; j++)
                                        {
                                            ampValues[j] = BitConverter.ToDouble(chunkAmps, j * 8);
                                            ampValues[j] *= Math.Pow(2, 31); //conversion factor
                                            ampValues[j] *= (4.9224284384 * Math.Pow(10, -8));
                                        }

                                        double[] powerValue = new double[ampValues.Length];
                                        for (int i = 0; i < 1; i++)
                                        {
                                            powerValue[i] = voltValues[i] * ampValues[i];

                                            MeterData temp = new MeterData(this.housesList[householdNumber - 1].Item1);

                                            ///Frequenzy = 16kHz
                                            ///all 16 tousand times, one second passes
                                            temp.SetTimestamp(realTimestamp.ToString());
                                            temp.SetPowerP1(powerValue[i]);
                                            temp.SetVoltage(voltValues[i]);

                                            myDatabase.InsertMeterDataIntoDb(temp);
                                        }
                                    }
                                }

                                if (k == (16000 / chunkSize))
                                {
                                    k = 0;
                                    l++;
                                }
                                k++;
                            }
                        }
                    }
                }
            }
        }

        private bool swapTimestampifSecondsPassed(String newTime)
        {
            ///if we don't specify the accurancy every value gets taken
            if (this.frequenzy == 0)
                return true;

            //Console.WriteLine("using Accuracy");

            int newTimeConverted = Int32.Parse(this.removeMilisFromTimestamp(newTime));

            if (this.currentTime == 0 || newTimeConverted >= (currentTime + this.frequenzy))
            {
                this.currentTime = newTimeConverted;
                return true;
            }
            else
            {
                return false;
            }
        }

        protected int getUnixTimestampFromDate(DateTime dateToConvert)
        {
            return (Int32)(dateToConvert.Subtract(new DateTime(1970, 1, 1))).TotalSeconds;
        }

        private String removeMilisFromTimestamp(String timestampWithMillis)
        {
            String[] values = timestampWithMillis.Split('.');
            return values[0];
        }

        protected override void filterByApplication()
        {
            throw new NotImplementedException();
        }

        protected override void filterByTime(DateTime minDT, DateTime maxDT)
        {
            throw new NotImplementedException();
        }

        protected override void filterByType()
        {
            throw new NotImplementedException();
        }

        protected override void fillDBWithMeters()
        {
            CustomerData tempCustomer = new CustomerData();
            int customerId = tempCustomer.FillEmptyCustomer();
            myDatabase.InsertCustomerData(tempCustomer);

            MeterManufactorData tempMeterManufactor = new MeterManufactorData();
            int meterManufactorId = tempMeterManufactor.FillEmptyMeterManufactor();
            myDatabase.InsertMeterManufactorData(tempMeterManufactor);

            MeterProtocolData tempMeterProtocol = new MeterProtocolData();
            int meterProtocolId = tempMeterProtocol.FillEmptyMeterProtocol();
            myDatabase.InsertMeterProtocolData(tempMeterProtocol);

            MeterTypeData tempMeterType = new MeterTypeData();
            int meterTypeId = tempMeterType.FillEmptyMeterType();
            myDatabase.InsertMeterTypeData(tempMeterType);

            ///Create whole houses meter
            for (int i = 0; i < 4; i++)
            {
                MeterManagementData UKDALEMeter = new MeterManagementData();
                UKDALEMeter.SetCustomerId(customerId);
                UKDALEMeter.SetManufactorId(meterManufactorId);
                UKDALEMeter.SetProtolId(meterProtocolId);
                UKDALEMeter.SetTypeId(meterTypeId);
                UKDALEMeter.SetDescribtion("UKDALE-" + i + "-ALL");
                UKDALEMeter.SetKey("not availiable");
                UKDALEMeter.SetPeriod(1);
                UKDALEMeter.SetPort("not availiable");
                UKDALEMeter.SetSerial("not availiable");
                UKDALEMeter.SetActive(true);
                myDatabase.InsertMeterManagementData(UKDALEMeter);

                int tempMeterId = UKDALEMeter.GetMeterId();

                Tuple<int, ApplicationEnum> temp;
                switch (i)
                {
                    case 0:
                        {
                            temp = new Tuple<int, ApplicationEnum>(tempMeterId, ApplicationEnum.UKDALEwholeHouse1);
                            break;
                        }
                    case 1:
                        {
                            temp = new Tuple<int, ApplicationEnum>(tempMeterId, ApplicationEnum.UKDALEwholeHouse2);
                            break;
                        }
                    case 2:
                        {
                            temp = new Tuple<int, ApplicationEnum>(tempMeterId, ApplicationEnum.UKDALEwholeHouse3);
                            break;
                        }
                    case 3:
                        {
                            temp = new Tuple<int, ApplicationEnum>(tempMeterId, ApplicationEnum.UKDALEwholeHouse4);
                            break;
                        }
                    case 4:
                        {
                            temp = new Tuple<int, ApplicationEnum>(tempMeterId, ApplicationEnum.UKDALEwholeHouse5);
                            break;
                        }
                    default:
                        {
                            temp = new Tuple<int, ApplicationEnum>(tempMeterId, ApplicationEnum.None);
                            break;
                        }
                }
                this.housesList.Add(temp);
            }

            String pathToUKDALEAplliances = "UKDaleAppliances\\";
            for (int i = 1; i <= 5; i++)
            {
                Console.WriteLine("working File: " + i);
                using (StreamReader file = new StreamReader(pathToUKDALEAplliances + "UKDALEdat_" + i + ".txt"))
                {
                    if (file != null)
                    {
                        String line = "";
                        while ((line = file.ReadLine()) != null)
                        {
                            MeterManagementData UKDALEMeter = new MeterManagementData();
                            UKDALEMeter.SetCustomerId(customerId);
                            UKDALEMeter.SetManufactorId(meterManufactorId);
                            UKDALEMeter.SetProtolId(meterProtocolId);
                            UKDALEMeter.SetTypeId(meterTypeId);

                            String[] applicationParts = line.Split(' ');
                            int applicationNumber = Int32.Parse(applicationParts[0]);
                            ApplicationEnum foundApplicationEnum = this.FindApplicationEnumByString(applicationParts[1]);

                            UKDALEMeter.SetDescribtion("UKDALE-" + (i - 1) + "-" + applicationNumber);
                            UKDALEMeter.SetKey("not availiable");
                            UKDALEMeter.SetPeriod(1);
                            UKDALEMeter.SetPort("not availiable");
                            UKDALEMeter.SetSerial("not availiable");
                            UKDALEMeter.SetActive(true);
                            myDatabase.InsertMeterManagementData(UKDALEMeter);

                            int tempMeterId = UKDALEMeter.GetMeterId();

                            int[] tempTupel = new int[2];
                            tempTupel[0] = tempMeterId;
                            tempTupel[1] = applicationNumber;

                            Tuple<int[], ApplicationEnum> temp = new Tuple<int[], ApplicationEnum>(tempTupel, foundApplicationEnum);

                            switch (i)
                            {
                                case 1:
                                    {
                                        this.house1ApplicationList.Add(temp);
                                        break;
                                    }
                                case 2:
                                    {
                                        this.house2ApplicationList.Add(temp);
                                        break;
                                    }
                                case 3:
                                    {
                                        this.house3ApplicationList.Add(temp);
                                        break;
                                    }
                                case 4:
                                    {
                                        this.house4ApplicationList.Add(temp);
                                        break;
                                    }
                                case 5:
                                    {
                                        this.house5ApplicationList.Add(temp);
                                        break;
                                    }
                            }
                        }
                    }
                }
            }
        }

        private ApplicationEnum FindApplicationEnumByString(string enumToFind)
        {
            Array values = Enum.GetValues(typeof(ApplicationEnum));

            foreach (ApplicationEnum val in values)
            {
                if (val.ToString() == enumToFind)
                    return val;
            }
            return ApplicationEnum.None;
        }

        private DateTime UnixTimeStampToDateTime(double unixTimeStamp)
        {
            System.DateTime dtDateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp);
            return dtDateTime;
        }
    }
}
