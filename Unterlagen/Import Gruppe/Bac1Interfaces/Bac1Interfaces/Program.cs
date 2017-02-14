using Bac1Interfaces.Data;
using Bac1Interfaces.Database;
using Bac1Interfaces.DataHandler;
using System;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces
{
    class Program
    {
        static DatabaseDriver myDatabase = new DatabaseDriver();

        static void Main(string[] args)
        {
            List<IDataHandler> dataHandlerList = new List<IDataHandler>();

            while (true)
            {
                int choice = 0;

                if (userInputDataset(dataHandlerList) == 1)
                    continue;

                Console.WriteLine("Do you want to load another dataset <1 Yes | 2 No> ?");
                choice = getIntFromConsole();
                if (choice == 1)
                {
                    Console.WriteLine("Next Order ...");
                    continue;
                }
                else
                {
                    Console.WriteLine("Starting to prcess files ...");
                    break;
                }
            }

            foreach (IDataHandler currentHandler in dataHandlerList)
            {
                try
                {
                    currentHandler.ParseDirectory();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.Message);
                }
            }
            
            Console.Read();
        }

        private static int getIntFromConsole()
        {
            String inputValue = Console.ReadLine();

            int integerValue = 0;

            if (Int32.TryParse(inputValue, out integerValue))
            {
                return integerValue;
            }

            return 0;
        }

        private static int userInputDataset(List<IDataHandler> dataHandlerList)
        {
            int choice = 0;
            Console.WriteLine("Which Dataset do you want to load data from <1 ADRES | 2 GREEND | 3 REDD | 4 UKdale> ?");
            Console.WriteLine("Or do you want to <5 print meter_data | 6 Delete meter_data | 7 Delete all data | 8 Create completly new data");
            Console.WriteLine("Or do you want to <9 print customer data | 10 print meter management data | 11 print meter manufactor data");
            Console.WriteLine("Or do you want to <12 print meter protocol data | 13 print meter type data>");
            choice = getIntFromConsole();
            if (choice == 0)
                return 1;

            switch (choice)
            {
                case 1:
                    {
                        List<TypeEnum> tempTypeEnums = new List<TypeEnum>();
                        //tempTypeEnums.Add(TypeEnum.Power);
                        tempTypeEnums.Add(TypeEnum.Voltage);
                        IDataHandler handler = new ADRESDataHandler(myDatabase,null, null, null, tempTypeEnums,5);
                        handler.setPath(@"E:\Anna\Dokumente\Studium\Jahr3\Semester5\BAC1\Lastprofile\ADRES\00originalData\umgewandelt");
                        
                        dataHandlerList.Add(handler);
                        break;
                    }
                case 2:
                    {
                        //IDataHandler handler = new GREENDDataHandler(myDatabase);
                        //IDataHandler handler = new GREENDDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00));
                        //IDataHandler handler = new GREENDDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00), null, null, 1);

                        /*
                        List<TypeEnum> tempEnumList = new List<TypeEnum>();
                        tempEnumList.Add(TypeEnum.Current);
                        tempEnumList.Add(TypeEnum.Power);
                        IDataHandler handler = new GREENDDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00), null, tempEnumList, 1);
                        */

                        /*
                        List<TypeEnum> tempEnumList = new List<TypeEnum>();
                        tempEnumList.Add(TypeEnum.Current);
                        tempEnumList.Add(TypeEnum.Voltage);
                        IDataHandler handler = new GREENDDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00), null, tempEnumList, 1);
                        */

                        /*
                        List<ApplicationEnum> tempApplicationList = new List<ApplicationEnum>();
                        tempApplicationList.Add(ApplicationEnum.TV);
                        tempApplicationList.Add(ApplicationEnum.WaterKettle);
                        tempApplicationList.Add(ApplicationEnum.Radio);
                        IDataHandler handler = new GREENDDataHandler(myDatabase, null, null, tempApplicationList, null, 1);
                        */
                        
                        List<ApplicationEnum> tempApplicationList = new List<ApplicationEnum>();
                        tempApplicationList.Add(ApplicationEnum.TV);
                        tempApplicationList.Add(ApplicationEnum.WaterKettle);
                        tempApplicationList.Add(ApplicationEnum.Radio);
                        IDataHandler handler = new GREENDDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 50, 00), new DateTime(2013, 12, 06, 23, 59, 59), tempApplicationList, null, 1);

                        handler.setPath(@"E:\Anna\Dokumente\Studium\Jahr3\Semester5\BAC1\Lastprofile\GREEND_0-1_311014");

                        dataHandlerList.Add(handler);
                        break;
                    }
                case 3:
                    {
                        IDataHandler handler = new REDDDataHandler(myDatabase);
                        List<ApplicationEnum> tempApplication = new List<ApplicationEnum>();
                        tempApplication.Add(ApplicationEnum.Dishwasher);
                        handler.setPath(@"E:\Anna\Dokumente\Studium\Jahr3\Semester5\BAC1\Lastprofile\REDD");

                        dataHandlerList.Add(handler);
                        break;
                    }
                case 4:
                    {
                        //IDataHandler handler = new UKdaleDataHandler(myDatabase);

                        //IDataHandler handler = new UKdaleDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00));

                        /*
                        List<TypeEnum> tempEnumList = new List<TypeEnum>();
                        tempEnumList.Add(TypeEnum.Current);
                        tempEnumList.Add(TypeEnum.Voltage);
                        IDataHandler handler = new UKdaleDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00), null, tempEnumList);
                        */

                        /*
                        List<TypeEnum> tempEnumList = new List<TypeEnum>();
                        tempEnumList.Add(TypeEnum.Power);
                        IDataHandler handler = new UKdaleDataHandler(myDatabase, new DateTime(2012, 11, 09, 23, 02, 21), new DateTime(2012, 11, 09, 23, 04, 21), null, tempEnumList);
                        */

                        //IDataHandler handler = new UKdaleDataHandler(myDatabase, new DateTime(2013, 12, 06, 23, 55, 00), new DateTime(2013, 12, 06, 23, 56, 00), null, null, 1);

                        /*
                        List<TypeEnum> tempEnumList = new List<TypeEnum>();
                        tempEnumList.Add(TypeEnum.Power);
                        IDataHandler handler = new UKdaleDataHandler(myDatabase, new DateTime(2012, 11, 09, 23, 02, 21), new DateTime(2012, 11, 09, 23, 04, 21), null, tempEnumList, 1);
                        */

                        List<ApplicationEnum> tempAppList = new List<ApplicationEnum>();
                        tempAppList.Add(ApplicationEnum.WaterKettle);
                        tempAppList.Add(ApplicationEnum.Laptop);
                        List<TypeEnum> tempEnumList = new List<TypeEnum>();
                        tempEnumList.Add(TypeEnum.Power);
                        IDataHandler handler = new UKdaleDataHandler(myDatabase, new DateTime(2012, 11, 09, 23, 02, 21), new DateTime(2012, 11, 09, 23, 04, 21), tempAppList, tempEnumList, 1);

                        handler.setPath(@"E:\Datasets\UK-DaleSample");


                        dataHandlerList.Add(handler);
                        break;
                    }
                case 5:
                    {
                        myDatabase.PrintMeterData("");
                        break;
                    }
                case 6:
                    {
                        myDatabase.DeleteAllMeterData();
                        break;
                    }
                case 7:
                    {
                        myDatabase.DeleteAllMeterManagementData();
                        myDatabase.DeleteAllMeterData();
                        myDatabase.DeleteAllCustomerData();
                        myDatabase.DeleteAllMeterManufactorData();
                        myDatabase.DeleteAllMeterProtocolData();
                        myDatabase.DeleteAllMeterTypeData();
                        break;
                    }
                case 8:
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

                        MeterManagementData tempMeterManagement = new MeterManagementData();
                        tempMeterManagement.FillEmptyManagementData(meterTypeId, meterProtocolId, meterManufactorId, customerId);
                        myDatabase.InsertMeterManagementData(tempMeterManagement);
                        break;
                    }
                case 9:
                    {
                        myDatabase.PrintCustomerData("");
                        break;
                    }
                case 10:
                    {
                        myDatabase.PrintMeterManagementData("");
                        break;
                    }
                case 11:
                    {
                        myDatabase.PrintMeterManufactorData();
                        break;
                    }
                case 12:
                    {
                        myDatabase.PrintMeterProtocolData();
                        break;
                    }
                case 13:
                    {
                        myDatabase.PrintMeterTypeData();
                        break;
                    }
            }
            return 0;
        }
    }
}
