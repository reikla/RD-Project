using Bac1Interfaces.Data;
using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace Bac1Interfaces.DataHandler
{
    class GREENDDataHandler : IDataHandler
    {
        DatabaseDriver myDatabase = new DatabaseDriver();

        int currentHouseholdNumber = 0;

        /// <summary>
        /// If dataset has more meters, convert to Array/List
        /// </summary>
        List<Tuple<int, ApplicationEnum>> meterList = new List<Tuple<int, ApplicationEnum>>();

        Boolean filterByTimeEnabled = false;

        int currentTime = 0;

        public GREENDDataHandler(DatabaseDriver _myDatabase, DateTime? _starttime = null, DateTime? _endtime = null, List<ApplicationEnum> _application = null, List<TypeEnum> _types = null, double _frequenzy = 0)
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

        public override void ParseDirectory()
        {
            if (this.types == null)
            {
                Console.WriteLine("Loading GREEND power data into DB");
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

                Console.WriteLine("Succesfully loaded Files into DB <GREEND>");
            }
            else
            {
                foreach (TypeEnum currentType in this.types)
                {
                    if (currentType == TypeEnum.Current)
                    {
                        Console.WriteLine("GREEND doesn't contain current data - ABORTING GREEND current loading");
                    }
                    else if (currentType == TypeEnum.Voltage)
                    {
                        Console.WriteLine("GREEND doesn't contain voltage data - ABORTING GREEND voltage loading");
                    }
                    else if (currentType == TypeEnum.Power)
                    {
                        Console.WriteLine("Loading GREEND power data into DB");
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

                        Console.WriteLine("Succesfully loaded Files into DB <GREEND>");
                    }
                }
            }
        }

        protected override void filterByApplication()
        {
            throw new NotImplementedException();
        }

        protected override void filterByTime(DateTime minDT, DateTime maxDT)
        {
            throw new NotImplementedException();
        }

        // Process all files in the directory passed in, recurse on any directories 
        // that are found, and process the files they contain.
        private void processDirectory(string targetDirectory)
        {
            // Process the list of files found in the directory.
            string[] fileEntries = Directory.GetFiles(targetDirectory);
            foreach (string fileName in fileEntries)
            {
                ///Find out which building the current directory represents
                Match match = Regex.Match(targetDirectory, "building");
                if (match.Success)
                {
                    String[] pathParts = targetDirectory.Split('\\');
                    String currentHousehold = pathParts[pathParts.Count() - 1];
                    this.currentHouseholdNumber = (int)Char.GetNumericValue(currentHousehold[currentHousehold.Count() - 1]);
                }
                this.processFile(fileName);
            }


            // Recurse into subdirectories of this directory.
            string[] subdirectoryEntries = Directory.GetDirectories(targetDirectory);
            foreach (string subdirectory in subdirectoryEntries)
            {
                processDirectory(subdirectory);
            }
        }

        // Insert logic for processing found files here.
        protected override void processFile(string path)
        {
            //If filename contains dataset, start function
            Match match = Regex.Match(path, "dataset");
            if (match.Success)
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

                this.workFile(path);
            }
        }

        private void workFile(string path)
        {
            String[] pathParts = path.Split('\\');
            String household = pathParts[pathParts.Count() - 2];

            StreamReader file = new StreamReader(path);
            if (file != null)
            {
                //First line gets lost here on purpose
                String line = file.ReadLine();

                while ((line = file.ReadLine()) != null)
                {
                    if (line != null)
                    {
                        String[] values = line.Split(',');

                        ///The file reader can overflow. This protects that case
                        if (values[0] == "timestamp")
                            break;

                        String[] PowerValues = new String[(values.Count() - 1)];

                        for (int i = 1; i < values.Count(); i++)
                        {
                            PowerValues[i - 1] = values[i];
                        }

                        ///Aplication filter
                        List<int> foundIndicesWithDuplicates = new List<int>();
                        List<int> foundIndicesWithoutDuplicates = new List<int>();
                        if (this.application != null)
                        {
                            foreach (ApplicationEnum currentCheckApplication in this.application)
                            {
                                int startAplicationList = this.currentHouseholdNumber * 9;
                                for (int i = startAplicationList; i < startAplicationList + 9; i++)
                                {
                                    if (currentCheckApplication == this.meterList[i].Item2)
                                    {
                                        foundIndicesWithDuplicates.Add(i - (9 * startAplicationList));
                                    }
                                }
                            }
                            foundIndicesWithoutDuplicates = foundIndicesWithDuplicates.Distinct().ToList();
                        }
                        int j = 0;
                        foreach (String value in PowerValues)
                        {
                            if (j == 0)
                            {
                                ///If value timestamp is out of timestampvalue / Filter by time
                                if ((Int32.Parse(removeMilisFromTimestamp(values[0])) < this.getUnixTimestampFromDate(this.starttime) || Int32.Parse(removeMilisFromTimestamp(values[0])) > this.getUnixTimestampFromDate(this.endtime)) && this.filterByTimeEnabled)
                                    break;

                                ///Checks if the given accuracy time has already passed. if not, break out.
                                if (!swapTimestampifSecondsPassed(values[0]))
                                    break;
                            }

                            if (foundIndicesWithoutDuplicates.Count != 0)
                            {
                                foreach (int searchedIndex in foundIndicesWithoutDuplicates)
                                {
                                    if (j == searchedIndex)
                                    {
                                        MeterData temp = new MeterData(getMeterId(j));
                                        temp.SetTimestamp(removeMilisFromTimestamp(values[0]));

                                        if (value == "NULL")
                                            temp.SetPowerP1(Convert.ToDouble(0.0, CultureInfo.InvariantCulture));
                                        else
                                            temp.SetPowerP1(Convert.ToDouble(value, CultureInfo.InvariantCulture));

                                        myDatabase.InsertMeterDataIntoDb(temp);
                                    }
                                }
                            }
                            else
                            {
                                MeterData temp = new MeterData(getMeterId(j));
                                temp.SetTimestamp(removeMilisFromTimestamp(values[0]));

                                if (value == "NULL")
                                    temp.SetPowerP1(Convert.ToDouble(0.0, CultureInfo.InvariantCulture));
                                else
                                    temp.SetPowerP1(Convert.ToDouble(value, CultureInfo.InvariantCulture));

                                myDatabase.InsertMeterDataIntoDb(temp);
                            }
                            j++;
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

        protected int getMeterId(int applianceNumber)
        {
            return meterList[(this.currentHouseholdNumber * 9) + applianceNumber].Item1;
        }

        private String removeMilisFromTimestamp(String timestampWithMillis)
        {
            String[] values = timestampWithMillis.Split('.');
            return values[0];
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

            for (int i = 0; i < 81; i++)
            {
                int HomeNbr = i / 9;
                int ApplicationNbr = i % 9;

                MeterManagementData GREENDMeter = new MeterManagementData();
                GREENDMeter.SetCustomerId(customerId);
                GREENDMeter.SetManufactorId(meterManufactorId);
                GREENDMeter.SetProtolId(meterProtocolId);
                GREENDMeter.SetTypeId(meterTypeId);
                GREENDMeter.SetDescribtion("GREEND-" + HomeNbr + "-" + ApplicationNbr);
                GREENDMeter.SetKey("not availiable");
                GREENDMeter.SetPeriod(1);
                GREENDMeter.SetPort("not availiable");
                GREENDMeter.SetSerial("not availiable");
                GREENDMeter.SetActive(true);
                myDatabase.InsertMeterManagementData(GREENDMeter);

                Tuple<int, ApplicationEnum> temp = new Tuple<int, ApplicationEnum>(GREENDMeter.GetMeterId(), this.getEnumFromGREENDEnumId((HomeNbr * 9) + ApplicationNbr));
                this.meterList.Add(temp);
            }
        }

        protected int getUnixTimestampFromDate(DateTime dateToConvert)
        {
            return (Int32)(dateToConvert.Subtract(new DateTime(1970, 1, 1))).TotalSeconds;
        }

        protected ApplicationEnum getEnumFromGREENDEnumId(int id)
        {
            switch (id)
            {
                /// House 0
                case 0:
                    {
                        return ApplicationEnum.CoffeeMachine;
                    }
                case 1:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 2:
                    {
                        return ApplicationEnum.Radio;
                    }
                case 3:
                    {
                        return ApplicationEnum.WaterKettle;
                    }
                case 4:
                    {
                        return ApplicationEnum.FridgeWithFreezer;
                    }
                case 5:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 6:
                    {
                        return ApplicationEnum.KitchenLamp;
                    }
                case 7:
                    {
                        return ApplicationEnum.TV;
                    }
                case 8:
                    {
                        return ApplicationEnum.VacuumCleaner;
                    }
                ///House 1
                case 9:
                    {
                        return ApplicationEnum.Radio;
                    }
                case 10:
                    {
                        return ApplicationEnum.Freezer;
                    }
                case 11:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 12:
                    {
                        return ApplicationEnum.Refrigerator;
                    }
                case 13:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 14:
                    {
                        return ApplicationEnum.WaterKettle;
                    }
                case 15:
                    {
                        return ApplicationEnum.Blender;
                    }
                case 16:
                    {
                        return ApplicationEnum.NetworkRouter;
                    }
                case 17:
                    {
                        return ApplicationEnum.UnkknownGreendH1A9;
                    }
                ///House 2
                case 18:
                    {
                        return ApplicationEnum.Refrigerator;
                    }
                case 19:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 20:
                    {
                        return ApplicationEnum.Microwave;
                    }
                case 21:
                    {
                        return ApplicationEnum.WaterKettle;
                    }
                case 22:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 23:
                    {
                        return ApplicationEnum.RadioWithAmplifier;
                    }
                case 24:
                    {
                        return ApplicationEnum.Dryier;
                    }
                case 25:
                    {
                        return ApplicationEnum.Kitchenware;
                    }
                case 26:
                    {
                        return ApplicationEnum.BedsideLight;
                    }
                ///House 3
                case 27:
                    {
                        return ApplicationEnum.TV;
                    }
                case 28:
                    {
                        return ApplicationEnum.NAS;
                    }
                case 29:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 30:
                    {
                        return ApplicationEnum.Dryier;
                    }
                case 31:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 32:
                    {
                        return ApplicationEnum.Notebook;
                    }
                case 33:
                    {
                        return ApplicationEnum.Kitchenware;
                    }
                case 34:
                    {
                        return ApplicationEnum.CoffeeMachine;
                    }
                case 35:
                    {
                        return ApplicationEnum.BreadMachine;
                    }
                ///House 4
                case 36:
                    {
                        return ApplicationEnum.EntranceOutlet;
                    }
                case 37:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 38:
                    {
                        return ApplicationEnum.WaterKettle;
                    }
                case 39:
                    {
                        return ApplicationEnum.Refrigerator;
                    }
                case 40:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 41:
                    {
                        return ApplicationEnum.Hairdryer;
                    }
                case 42:
                    {
                        return ApplicationEnum.Computer;
                    }
                case 43:
                    {
                        return ApplicationEnum.CoffeeMachine;
                    }
                case 44:
                    {
                        return ApplicationEnum.TV;
                    }
                ///House 5
                case 45:
                    {
                        return ApplicationEnum.TotalOutlets;
                    }
                case 46:
                    {
                        return ApplicationEnum.TotalLights;
                    }
                case 47:
                    {
                        return ApplicationEnum.KitchenTV;
                    }
                case 48:
                    {
                        return ApplicationEnum.LivingRoomTV;
                    }
                case 49:
                    {
                        return ApplicationEnum.FridgeWithFreezer;
                    }
                case 50:
                    {
                        return ApplicationEnum.ElectricOven;
                    }
                case 51:
                    {
                        return ApplicationEnum.ComputerWithScannerAndPrinter;
                    }
                case 52:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 53:
                    {
                        return ApplicationEnum.FumeHood;
                    }
                ///House 6
                case 54:
                    {
                        return ApplicationEnum.PlasmaTV;
                    }
                case 55:
                    {
                        return ApplicationEnum.Lamp;
                    }
                case 56:
                    {
                        return ApplicationEnum.Toaster;
                    }
                case 57:
                    {
                        return ApplicationEnum.Hob;
                    }
                case 58:
                    {
                        return ApplicationEnum.Iron;
                    }
                case 59:
                    {
                        return ApplicationEnum.ComputerWithScannerAndPrinter;
                    }
                case 60:
                    {
                        return ApplicationEnum.LCDTV;
                    }
                case 61:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 62:
                    {
                        return ApplicationEnum.FridgeWithFreezer;
                    }
                ///House 7
                case 63:
                    {
                        return ApplicationEnum.Hairdryer;
                    }
                case 64:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 65:
                    {
                        return ApplicationEnum.VideogameConsoleAndRadio;
                    }
                case 66:
                    {
                        return ApplicationEnum.Dryier;
                    }
                case 67:
                    {
                        return ApplicationEnum.TVWithDecoderAndComputerInLivingRoom;
                    }
                case 68:
                    {
                        return ApplicationEnum.KitchenTV;
                    }
                case 69:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 70:
                    {
                        return ApplicationEnum.TotalOutlets;
                    }
                case 71:
                    {
                        return ApplicationEnum.TotalLights;
                    }
                ///House 8
                case 72:
                    {
                        return ApplicationEnum.KitchenTV;
                    }
                case 73:
                    {
                        return ApplicationEnum.Dishwasher;
                    }
                case 74:
                    {
                        return ApplicationEnum.LivingRoomTV;
                    }
                case 75:
                    {
                        return ApplicationEnum.DesktopComputerWithScreen;
                    }
                case 76:
                    {
                        return ApplicationEnum.WashingMachine;
                    }
                case 77:
                    {
                        return ApplicationEnum.BedroomTV;
                    }
                case 78:
                    {
                        return ApplicationEnum.TotalOutlets;
                    }
                case 79:
                    {
                        return ApplicationEnum.TotalLights;
                    }
                case 80:
                    {
                        return ApplicationEnum.UnkknownGreendH8A9;
                    }
            }
            return ApplicationEnum.None;
        }
    }
}
