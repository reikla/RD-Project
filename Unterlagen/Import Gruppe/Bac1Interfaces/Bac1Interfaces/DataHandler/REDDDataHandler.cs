using Bac1Interfaces.Data;
using Bac1Interfaces.Database;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.DataHandler
{
    class REDDDataHandler : IDataHandler
    {
        MeterData temp;
        private DatabaseDriver myDatabase = new DatabaseDriver();
        MeterManagementData REDDMeter;
        private bool fitsTimeFilter = false;
        private bool fitsTypeFilter = false;
        private bool fitsApplicationFilter = false;

        public REDDDataHandler(DatabaseDriver _myDatabase, DateTime? _starttime = null, DateTime? _endtime = null, List<ApplicationEnum> _application = null, List<TypeEnum> _types = null, double _frequenzy = 0)
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

            //this.fillDBWithMeters();
        }


        public override void ParseDirectory()
        {
            //low_freq
            string low_freq = this.Path + "\\00_original Data\\low_freq";
            string[] houseEntries = Directory.GetFileSystemEntries(low_freq);
            foreach (string house in houseEntries)
            {
                Console.WriteLine("House: " + house[house.Length - 1]);

                string[] fileEntries = Directory.GetFileSystemEntries(house);

                //read the labels file
                StreamReader reader = new StreamReader(house + "\\labels.dat");
                string labelFile = reader.ReadToEnd().Replace("\r\n", "\n").Replace("\n\r", "\n");
                //create a seperate string for each label
                string[] labels = labelFile.Split(new char[] { '\n' });

                for (int i = 0; i < labels.Length; i++)
                {
                    //split label name and number (tmp[0] = number, tmp[1] = name)
                    string[] tmp = labels[i].Split();
                    for (int j = 0; j < fileEntries.Length; j++)
                    {
                        //search through the files untill the file that belongs to the label is found
                        if ((fileEntries[j].Replace((house + "\\"), "")).Equals("channel_" + tmp[0] + ".dat"))
                        {
                            REDDMeter = new MeterManagementData();
                            REDDMeter.SetDescribtion("REDD-0" + house[house.Length - 1]);
                            processFile_LowFreq(tmp[1], fileEntries[j]);
                        }
                    }
                }
            }
        }

        protected override void filterByApplication()
        {
            foreach (ApplicationEnum application in this.application)
            {
                string app = REDDMeter.GetDescription().Split('-').Last(); //get the number of the TypeApplication Enum
                if (Int32.Parse(app) == (int)application)
                    fitsApplicationFilter = true;
            }
        }

        protected override void filterByTime(DateTime minDT, DateTime maxDT)
        {
            DateTime dt = new DateTime(1970, 1, 1, 0, 0, 0, 0, DateTimeKind.Utc);
            dt = dt.AddSeconds(double.Parse(temp.GetTimestamp())).ToUniversalTime();
            if (dt >= minDT && dt <= maxDT)
            {
                fitsTimeFilter = true;
            }
        }

        protected override void filterByType()
        {
            foreach (TypeEnum type in this.types)
            {
                if (TypeEnum.Power == type)
                    fitsTypeFilter = true;
            }
        }

        protected override void processFile(string file)
        {
            StreamReader reader = new StreamReader(file);

            string line;
            int counter = 0;
            double sec = (double)1 / this.frequenzy;
            string application = REDDMeter.GetDescription().Split('-').Last(); //get the number of the TypeApplication Enum

            //read the whole file line by line
            while ((line = reader.ReadLine()) != null)
            {
                if (this.frequenzy == 0) //unse every line if no frequenzy is specified
                {
                    filterAndSave(line);
                }
                else if (Int32.Parse(application) == (int)ApplicationEnum.Mains && this.frequenzy != 0) //differentiate between Mains (every sec) and others (every 3sec)
                {
                    if (counter % sec == 0 && this.frequenzy < 1) //only use every other line to fit the specified frequenzy
                    {
                        filterAndSave(line);
                        counter = 0;
                    }
                    counter++;
                }
                else if (Int32.Parse(application) != (int)ApplicationEnum.Mains && (double)1 / 3 % this.frequenzy == 0) //check if frequenzy fits for the application accuracy
                {
                    if (frequenzy == (double)1 / 3) //only use every other line to fit the specified frequenzy
                    {
                        filterAndSave(line);
                        counter = 0;
                    }
                    else if (this.frequenzy < (double)1 / 3)
                    {
                        if (counter % sec == 0)
                        {
                            filterAndSave(line);
                            counter = 0;
                        }
                        counter += 3;
                    }
                }
            }
        }



        private void filterAndSave(string line)
        {
            //split timestamp and value
            string[] substrings = line.Split(' ');
            if (substrings.Length > 1)
            {
                if (this.starttime != new DateTime(2000, 1, 1) && this.endtime != new DateTime(2000, 1, 1)) //check if time filter is set
                    filterByTime(this.starttime, this.endtime); //check if timestamp fits between start and endtime
                else
                    fitsTimeFilter = true;

                if (this.types != null && fitsTimeFilter) //check if any type filters are set and if time is right
                    filterByType();
                else
                    fitsTypeFilter = true;

                if (this.application != null && fitsTimeFilter && fitsTypeFilter) //check if any application filters are set and if time and types are right
                    filterByApplication();
                else
                    fitsApplicationFilter = true;

                if (fitsTimeFilter && fitsTypeFilter && fitsApplicationFilter) //check if data fits all filters
                {
                    temp = new MeterData(REDDMeter.GetMeterId());
                    temp.SetTimestamp(substrings[0]);
                    temp.SetPowerP1(Convert.ToDouble(substrings[1], CultureInfo.InvariantCulture));
                    myDatabase.InsertMeterDataIntoDb(temp);
                }
                fitsTimeFilter = false;
                fitsTypeFilter = false;
                fitsApplicationFilter = false;
            }
        }


        private void processFile_LowFreq(string label, string file)
        {
            ApplicationEnum application = getApplication(label);

            if ((int)application < 10)
                REDDMeter.SetDescribtion(REDDMeter.GetDescription() + "-0" + (int)application);
            else
                REDDMeter.SetDescribtion(REDDMeter.GetDescription() + "-" + (int)application);

            fillDBWithMeters();
            processFile(file);

            Console.WriteLine("Done");
        }

        private ApplicationEnum getApplication(string label)
        {
            ApplicationEnum application;
            switch (label)
            {
                case "mains":
                    application = ApplicationEnum.Mains;
                    break;
                case "oven":
                    application = ApplicationEnum.Oven;
                    break;
                case "refrigerator":
                    application = ApplicationEnum.Refrigerator;
                    break;
                case "microwave":
                    application = ApplicationEnum.Microwave;
                    break;
                case "stove":
                    application = ApplicationEnum.Stove;
                    break;
                case "dishwaser":
                    application = ApplicationEnum.Dishwasher;
                    break;
                case "disposal":
                    application = ApplicationEnum.Disposal;
                    break;
                case "kitchen_outlets":
                    application = ApplicationEnum.KitchenOutlets;
                    break;
                case "furance":
                    application = ApplicationEnum.Furnance;
                    break;
                case "washer_dryer":
                    application = ApplicationEnum.WasherDryer;
                    break;
                case "bathroom_gfi":
                    application = ApplicationEnum.BathroomGFI;
                    break;
                case "lighting":
                    application = ApplicationEnum.Lighting;
                    break;
                case "electric_heat":
                    application = ApplicationEnum.ElectricHeat;
                    break;
                case "smoke_alarms":
                    application = ApplicationEnum.SmokeAlarms;
                    break;
                case "air_conditioning":
                    application = ApplicationEnum.AirConditioning;
                    break;
                case "subpanel":
                    application = ApplicationEnum.Subpanel;
                    break;
                case "electronics":
                    application = ApplicationEnum.Electronics;
                    break;
                case "outdoor_outlets":
                    application = ApplicationEnum.OutdoorOutlets;
                    break;
                case "miscellaeneous":
                    application = ApplicationEnum.Miscellaeneous;
                    break;
                case "outlets_unknown":
                    application = ApplicationEnum.OutletsUnknown;
                    break;
                default:
                    application = ApplicationEnum.OutletsUnknown;
                    break;
            }

            return application;
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


            REDDMeter.SetCustomerId(customerId);
            REDDMeter.SetManufactorId(meterManufactorId);
            REDDMeter.SetProtolId(meterProtocolId);
            REDDMeter.SetTypeId(meterTypeId);
            REDDMeter.SetKey("not availiable");
            REDDMeter.SetPeriod(1);
            REDDMeter.SetPort("not availiable");
            REDDMeter.SetSerial("not availiable");
            REDDMeter.SetActive(true);
            myDatabase.InsertMeterManagementData(REDDMeter);

        }
    }
}
