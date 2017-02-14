using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using Bac1Interfaces.Data;
using Bac1Interfaces.Database;

namespace Bac1Interfaces.DataHandler
{
    class ADRESDataHandler : IDataHandler
    {
        private bool isU = false;
        private bool sortU = false;
        private bool sortP = false;
        private bool sort = false;
        DatabaseDriver myDatabase = new DatabaseDriver();

        /// <summary>
        /// If dataset has more meters, convert to Array/List
        /// </summary>
        int ADRESMeterId = 0;

        public ADRESDataHandler(DatabaseDriver _myDatabase, DateTime? _starttime = null, DateTime? _endtime = null, List<ApplicationEnum> _application = null, List<TypeEnum> _types = null, double _frequenzy = 0)
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
            myDatabase.ConnectToDB();

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

            myDatabase.CloseConnection();
        }

        private void processDirectory(String directory)
        {
            this.filterByType();
            string[] fileEntries = Directory.GetFiles(directory);

            if (sort == false) {
                // Process the list of files found in the directory.
                foreach (string fileName in fileEntries)
                {
                    String[] cropName = fileName.Split('_');
                    Console.WriteLine(cropName[cropName.Count() - 1]);
                    String[] cropcropName = cropName[cropName.Count() - 1].Split('.');
                    Console.WriteLine(cropcropName[0]);
                    if (cropcropName.Equals("U"))
                        isU = true;

                    this.processFile(fileName);
                }
            }
            else {
                if (sortU) {
                    foreach (string fileName in fileEntries) {
                        String[] cropName = fileName.Split('_');
                        Console.WriteLine(cropName[cropName.Count() - 1]);
                        String[] cropcropName = cropName[cropName.Count() - 1].Split('.');
                        Console.WriteLine(cropcropName[0]);
                        if (cropcropName[0].Equals("U")) {
                            isU = true;
                            this.processFile(fileName);
                        }

                    }
                }

                if (sortP)
                {
                    foreach (string fileName in fileEntries)
                    {
                        String[] cropName = fileName.Split('_');
                        Console.WriteLine(cropName[cropName.Count() - 1]);
                        String[] cropcropName = cropName[cropName.Count() - 1].Split('.');
                        Console.WriteLine(cropcropName[0]);
                        if (cropcropName[0].Equals("PQ"))
                            this.processFile(fileName);
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

        protected override void filterByType()
        {
            sort = true;
            for (int i = 0; i < this.types.Count(); i++)
            {
                TypeEnum type = this.types.ElementAt(i);
                switch (type)
                {
                    case TypeEnum.Power:
                        sortP = true;
                        break;
                    case TypeEnum.Current: break;
                    case TypeEnum.Voltage:
                        sortU = true;
                        break;
                }
            }
        }

        protected override void processFile(String path)
        {
            //open file
            //get datastream
            //save data --> not implemented yet
            //close file
            StreamReader reader = new StreamReader(File.OpenRead(path));

            if (reader != null)
            {
                String line;
                while ((line = reader.ReadLine()) != null)
                {
                    Console.WriteLine(line);
                    Console.WriteLine();

                    String[] values = line.Split(',');

                    if (sort == false)
                    {
                        if (isU)
                        {
                            String[] voltage = new String[values.Count()];

                            for (int i = 0; i < values.Count(); i++)
                            {
                                voltage[i] = values[i];
                            }

                            int countFrequency = 0;
                            foreach (String value in voltage)
                            {
                                if ((countFrequency%frequenzy) == 0) {
                                    MeterData temp = new MeterData(ADRESMeterId);
                                    temp.SetVoltage(Convert.ToDouble(value, CultureInfo.InvariantCulture));

                                    myDatabase.InsertMeterDataIntoDb(temp);
                                }
                                countFrequency++;
                            }
                        }
                        else
                        {
                            String[] power = new String[values.Count()];

                            for (int i = 0; i < values.Count(); i++)
                            {
                                power[i] = values[i];
                            }

                            int counter = 0;
                            foreach (String value in power)
                            {
                                if (this.frequenzy == 0) {
                                    this.frequenzy = 1;
                                }

                                if ((counter % (2*frequenzy) == 0))
                                {
                                    MeterData temp = new MeterData(ADRESMeterId);
                                    temp.SetPowerP1(Convert.ToDouble(value, CultureInfo.InvariantCulture));
                                    myDatabase.InsertMeterDataIntoDb(temp);
                                }

                                counter++;  
                            }
                        }
                    }
                    else
                    {
                        if (sortU)
                        {
                            if (isU)
                            {
                                String[] voltage = new String[values.Count()];

                                for (int i = 0; i < values.Count(); i++)
                                {
                                    voltage[i] = values[i];
                                }

                                int countFrequency = 0;
                                foreach (String value in voltage)
                                {
                                    if (this.frequenzy == 0)
                                    {
                                        this.frequenzy = 1;
                                    }

                                    if ((countFrequency%frequenzy) == 0) {
                                        MeterData temp = new MeterData(ADRESMeterId);
                                        temp.SetVoltage(Convert.ToDouble(value, CultureInfo.InvariantCulture));
                                        myDatabase.InsertMeterDataIntoDb(temp);
                                    }
                                    countFrequency++;
                                }
                            }
                        }
                        if (sortP)
                        {
                            String[] power = new String[values.Count()];

                            for (int i = 0; i < values.Count(); i++)
                            {
                                power[i] = values[i];
                            }

                            int counter = 0;
                            foreach (String value in power)
                            {
                                if (this.frequenzy == 0)
                                {
                                    this.frequenzy = 1;
                                }
 
                                if ((counter % (2* frequenzy)) == 0)
                                {
                                    MeterData temp = new MeterData(ADRESMeterId);
                                    temp.SetPowerP1(Convert.ToDouble(value, CultureInfo.InvariantCulture));
                                    myDatabase.InsertMeterDataIntoDb(temp);
                                }

                                counter++;
                            }
                        }
                    }
                }
                reader.Close();
            }
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

            MeterManagementData ADRESMeter = new MeterManagementData();
            ADRESMeter.SetCustomerId(customerId);
            ADRESMeter.SetManufactorId(meterManufactorId);
            ADRESMeter.SetProtolId(meterProtocolId);
            ADRESMeter.SetTypeId(meterTypeId);
            ADRESMeter.SetDescribtion("ADRES-01-01");
            ADRESMeter.SetKey("not availiable");
            ADRESMeter.SetPeriod(1);
            ADRESMeter.SetPort("not availiable");
            ADRESMeter.SetSerial("not availiable");
            ADRESMeter.SetActive(true);
            myDatabase.InsertMeterManagementData(ADRESMeter);

            this.ADRESMeterId = ADRESMeter.GetMeterId();
        }
    }
}
