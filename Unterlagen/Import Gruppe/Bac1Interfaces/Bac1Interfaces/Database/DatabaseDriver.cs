using Bac1Interfaces.Data;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Bac1Interfaces.Database
{
    class DatabaseDriver
    {
        System.Data.SqlClient.SqlConnection connection;

        public DatabaseDriver()
        {
        }

        /// <summary>
        /// Starts the connection
        /// </summary>
        public void ConnectToDB()
        {
            connection = new System.Data.SqlClient.SqlConnection();
            connection.ConnectionString = "Data Source =(LocalDB)\\MSSQLLocalDB; AttachDbFilename = C:\\Users\\Max-MSI\\Dropbox\\VergleichVerschiedenerDatenquellen\\Program\\Bac1Interfaces\\Bac1Interfaces\\Database\\MyDatabase.mdf; Integrated Security = True";
            try
            {
                connection.Open();
                //Console.WriteLine("Opened Connection successfully");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        public void CloseConnection()
        {
            connection.Close();
        }

        /// <summary>
        /// Prints all meter data Elements in Database
        /// </summary>
        /// <param name="verbosity">use string verbose for verbose output</param>
        public void PrintMeterData(String verbosity)
        {
            this.ConnectToDB();

            Console.WriteLine("Meter data found in database:");
            String sqlCommand = "SELECT * FROM meter_data";

            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            if (verbosity == "verbose")
                            {
                                Console.Write("Data Id: " + reader[0].ToString() + " | ");
                                Console.Write("Meter Id: " + reader[1].ToString() + " | ");
                                Console.Write("Timestamp: " + reader[2].ToString() + " | ");
                                Console.Write("Count Total: " + reader[3].ToString() + " | ");
                                Console.Write("Count Register 1: " + reader[4].ToString() + " | ");
                                Console.Write("Count Register 2: " + reader[5].ToString() + " | ");
                                Console.Write("Count Register 3: " + reader[6].ToString() + " | ");
                                Console.Write("Count Register 4: " + reader[7].ToString() + " | ");
                                Console.Write("Power P1: " + reader[8].ToString() + " | ");
                                Console.Write("Power P2: " + reader[9].ToString() + " | ");
                                Console.Write("Power P3: " + reader[10].ToString() + " | ");
                                Console.Write("Work P1: " + reader[11].ToString() + " | ");
                                Console.Write("Work P2: " + reader[12].ToString() + " | ");
                                Console.Write("Work P3: " + reader[13].ToString() + " | ");
                                Console.Write("Frequenzy P1: " + reader[14].ToString() + " | ");
                                Console.WriteLine("Voltage P1: " + reader[15].ToString());
                            }
                            else
                            {
                                Console.Write("Data Id: " + reader[0].ToString() + " | ");
                                Console.Write("Meter Id: " + reader[1].ToString() + " | ");
                                Console.Write("Timestamp: " + reader[2].ToString() + " | ");
                                Console.Write("Count Total: " + reader[3].ToString() + " | ");
                                Console.Write("Count Register 1: " + reader[4].ToString() + " | ");
                                Console.Write("Power P1: " + reader[8].ToString() + " | ");
                                Console.Write("Work P1: " + reader[11].ToString() + " | ");
                                Console.Write("Frequenzy P1: " + reader[14].ToString() + " | ");
                                Console.WriteLine("Voltage P1: " + reader[15].ToString());
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void PrintCustomerData(String verbosity)
        {
            this.ConnectToDB();

            Console.WriteLine("Customer data found in database:");
            String sqlCommand = "SELECT * FROM customer";

            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            if (verbosity == "verbose")
                            {
                                Console.Write("Id: " + reader[0].ToString() + " | ");
                                Console.Write("Lastname: " + reader[1].ToString() + " | ");
                                Console.Write("Firstname: " + reader[2].ToString() + " | ");
                                Console.Write("Street: " + reader[3].ToString() + " | ");
                                Console.Write("Postal code: " + reader[4].ToString() + " | ");
                                Console.Write("City: " + reader[5].ToString() + " | ");
                                Console.Write("Alias: " + reader[6].ToString() + " | ");
                                Console.Write("Company customer Id: " + reader[7].ToString() + " | ");
                                Console.Write("Signature: " + reader[8].ToString() + " | ");
                                Console.WriteLine("Key: " + reader[9].ToString());
                            }
                            else
                            {
                                Console.Write("Id: " + reader[0].ToString() + " | ");
                                Console.Write("Lastname: " + reader[1].ToString() + " | ");
                                Console.WriteLine("Alias: " + reader[6].ToString());
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void PrintMeterManagementData(String verbosity)
        {
            this.ConnectToDB();

            Console.WriteLine("Meter management data found in database:");
            String sqlCommand = "SELECT * FROM meter_management";

            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            if (verbosity == "verbose")
                            {
                                Console.Write("Id: " + reader[0].ToString() + " | ");
                                Console.Write("TypeId: " + reader[1].ToString() + " | ");
                                Console.Write("Protocol Id: " + reader[2].ToString() + " | ");
                                Console.Write("ManufactorId: " + reader[3].ToString() + " | ");
                                Console.Write("CustomerId: " + reader[4].ToString() + " | ");
                                Console.Write("Describtion: " + reader[5].ToString() + " | ");
                                Console.Write("Serial: " + reader[6].ToString() + " | ");
                                Console.Write("Key: " + reader[7].ToString() + " | ");
                                Console.Write("Period: " + reader[8].ToString() + " | ");
                                Console.Write("Is Active: " + reader[9].ToString() + " | ");
                                Console.WriteLine("Port: " + reader[10].ToString());
                            }
                            else
                            {
                                Console.Write("Id: " + reader[0].ToString() + " | ");
                                Console.Write("TypeId: " + reader[1].ToString() + " | ");
                                Console.Write("Protocol Id: " + reader[2].ToString() + " | ");
                                Console.Write("ManufactorId: " + reader[3].ToString() + " | ");
                                Console.Write("CustomerId: " + reader[4].ToString() + " | ");
                                Console.WriteLine("Description: " + reader[5].ToString() + " | ");
                            }
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void PrintMeterManufactorData()
        {
            this.ConnectToDB();

            Console.WriteLine("Meter management data found in database:");
            String sqlCommand = "SELECT * FROM meter_manufactor";

            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            Console.Write("Id: " + reader[0].ToString() + " | ");
                            Console.WriteLine("description: " + reader[1].ToString());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void PrintMeterProtocolData()
        {
            this.ConnectToDB();

            Console.WriteLine("Meter protocol data found in database:");
            String sqlCommand = "SELECT * FROM meter_protocol";

            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            Console.Write("Id: " + reader[0].ToString() + " | ");
                            Console.Write("Protocol: " + reader[1].ToString() + " | ");
                            Console.WriteLine("Data sceme: " + reader[2].ToString());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void PrintMeterTypeData()
        {
            this.ConnectToDB();

            Console.WriteLine("Meter type data found in database:");
            String sqlCommand = "SELECT * FROM meter_type";

            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            Console.Write("Id: " + reader[0].ToString() + " | ");
                            Console.WriteLine("description: " + reader[1].ToString());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        /// <summary>
        /// Get a unused meter data id
        /// </summary>
        /// <returns>Returns a unique data element identifier</returns>
        public int GetUniqueMeterDataId()
        {
            this.ConnectToDB();
            Randomizer rand = new Randomizer();

            String sqlCommand = @"SELECT data_id FROM meter_data;";

            bool uniqueIdFound = false;
            int id = -1;

            while (uniqueIdFound == false)
            {
                uniqueIdFound = true;
                id = rand.getRandomIntBoundries(1, 2147483646); //Maximum integer 2^32-1

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int compare = 0;
                            if (Int32.TryParse(reader[0].ToString(), out compare))
                            {
                                if (id == compare)
                                    uniqueIdFound = false;
                            }
                            else
                                uniqueIdFound = false;

                        }
                    }
                }
            }
            this.CloseConnection();
            return id;
        }

        /// <summary>
        /// Get a unique Meter Management Id
        /// </summary>
        /// <returns>Returns a random unique Id</returns>
        public int GetUniqueMeterManagementId()
        {
            this.ConnectToDB();
            Randomizer rand = new Randomizer();

            String sqlCommand = @"SELECT id_meter FROM meter_management;";

            bool uniqueIdFound = false;
            int id = -1;

            while (uniqueIdFound == false)
            {
                uniqueIdFound = true;
                id = rand.getRandomIntBoundries(1, 2147483646); //Maximum integer 2^32-1

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int compare = 0;
                            if (Int32.TryParse(reader[0].ToString(), out compare))
                            {
                                if (id == compare)
                                    uniqueIdFound = false;
                            }
                            else
                                uniqueIdFound = false;

                        }
                    }
                }
            }
            this.CloseConnection();
            return id;
        }

        /// <summary>
        /// Returns a random unique customer id
        /// </summary>
        /// <returns>returns unique id</returns>
        public int GetUniqueCustomerId()
        {
            this.ConnectToDB();
            Randomizer rand = new Randomizer();

            String sqlCommand = @"SELECT customer_id FROM customer;";

            bool uniqueIdFound = false;
            int id = -1;

            while (uniqueIdFound == false)
            {
                uniqueIdFound = true;
                id = rand.getRandomIntBoundries(1, 2147483646); //Maximum integer 2^32-1

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int compare = 0;
                            if (Int32.TryParse(reader[0].ToString(), out compare))
                            {
                                if (id == compare)
                                    uniqueIdFound = false;
                            }
                            else
                                uniqueIdFound = false;

                        }
                    }
                }
            }
            this.CloseConnection();
            return id;
        }

        /// <summary>
        /// Returns a random unique Meter Manufactor id
        /// </summary>
        /// <returns>returns a random id</returns>
        public int GetUniqueMeterManufactorId()
        {
            this.ConnectToDB();
            Randomizer rand = new Randomizer();

            String sqlCommand = @"SELECT manufactor_id FROM meter_manufactor;";

            bool uniqueIdFound = false;
            int id = -1;

            while (uniqueIdFound == false)
            {
                uniqueIdFound = true;
                id = rand.getRandomIntBoundries(1, 2147483646); //Maximum integer 2^32-1

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int compare = 0;
                            if (Int32.TryParse(reader[0].ToString(), out compare))
                            {
                                if (id == compare)
                                    uniqueIdFound = false;
                            }
                            else
                                uniqueIdFound = false;

                        }
                    }
                }
            }
            this.CloseConnection();
            return id;
        }

        /// <summary>
        /// Returns a random unique Meter Protocol id
        /// </summary>
        /// <returns>returns a random id</returns>
        public int GetUniqueMeterProtocolId()
        {
            this.ConnectToDB();
            Randomizer rand = new Randomizer();

            String sqlCommand = @"SELECT protocol_id FROM meter_protocol;";

            bool uniqueIdFound = false;
            int id = -1;

            while (uniqueIdFound == false)
            {
                uniqueIdFound = true;
                id = rand.getRandomIntBoundries(1, 2147483646); //Maximum integer 2^32-1

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int compare = 0;
                            if (Int32.TryParse(reader[0].ToString(), out compare))
                            {
                                if (id == compare)
                                    uniqueIdFound = false;
                            }
                            else
                                uniqueIdFound = false;

                        }
                    }
                }
            }
            this.CloseConnection();
            return id;
        }

        /// <summary>
        /// returns a random unique Meter Type id.
        /// </summary>
        /// <returns>returns a random id</returns>
        public int GetUniqueMeterTypeId()
        {
            this.ConnectToDB();
            Randomizer rand = new Randomizer();

            String sqlCommand = @"SELECT type_id FROM meter_type;";

            bool uniqueIdFound = false;
            int id = -1;

            while (uniqueIdFound == false)
            {
                uniqueIdFound = true;
                id = rand.getRandomIntBoundries(1, 2147483646); //Maximum integer 2^32-1

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            int compare = 0;
                            if (Int32.TryParse(reader[0].ToString(), out compare))
                            {
                                if (id == compare)
                                    uniqueIdFound = false;
                            }
                            else
                                uniqueIdFound = false;

                        }
                    }
                }
            }
            this.CloseConnection();
            return id;
        }

        public void InsertMeterDataIntoDb(MeterData newData)
        {
            this.ConnectToDB();
            try
            {
                String sqlCommand = @"INSERT INTO meter_data VALUES (@p1, @p2, @p3, @p4, @p5, @p6, @p7, @p8, @p9, @p10, @p11, @p12, @p13, @p14, @p15, @p16)";

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    String[] data = newData.GetData();

                    cmd.Parameters.AddWithValue("@p1", Int32.Parse(data[0]));
                    cmd.Parameters.AddWithValue("@p2", Int32.Parse(data[1]));
                    cmd.Parameters.AddWithValue("@p3", this.UnixTimeStampToDateTime(Convert.ToDouble(data[2])));
                    cmd.Parameters.AddWithValue("@p4", Convert.ToDouble(data[3]));
                    cmd.Parameters.AddWithValue("@p5", Convert.ToDouble(data[4]));
                    cmd.Parameters.AddWithValue("@p6", Convert.ToDouble(data[5]));
                    cmd.Parameters.AddWithValue("@p7", Convert.ToDouble(data[6]));
                    cmd.Parameters.AddWithValue("@p8", Convert.ToDouble(data[7]));
                    cmd.Parameters.AddWithValue("@p9", Convert.ToDouble(data[8]));
                    cmd.Parameters.AddWithValue("@p10", Convert.ToDouble(data[9]));
                    cmd.Parameters.AddWithValue("@p11", Convert.ToDouble(data[10]));
                    cmd.Parameters.AddWithValue("@p12", Convert.ToDouble(data[11]));
                    cmd.Parameters.AddWithValue("@p13", Convert.ToDouble(data[12]));
                    cmd.Parameters.AddWithValue("@p14", Convert.ToDouble(data[13]));
                    cmd.Parameters.AddWithValue("@p15", Convert.ToDouble(data[14]));
                    cmd.Parameters.AddWithValue("@p16", Convert.ToDouble(data[15]));

                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void InsertCustomerData(CustomerData newData)
        {
            this.ConnectToDB();
            try
            {
                String sqlCommand = @"INSERT INTO customer VALUES (@p1, @p2, @p3, @p4, @p5, @p6, @p7, @p8, @p9, @p10)";

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    
                    String[] data = newData.GetData();

                    cmd.Parameters.AddWithValue("@p1", Int32.Parse(data[0]));
                    cmd.Parameters.AddWithValue("@p2", data[1]);
                    cmd.Parameters.AddWithValue("@p3", data[2]);
                    cmd.Parameters.AddWithValue("@p4", data[3]);
                    cmd.Parameters.AddWithValue("@p5", data[4]);
                    cmd.Parameters.AddWithValue("@p6", data[5]);
                    cmd.Parameters.AddWithValue("@p7", data[6]);
                    cmd.Parameters.AddWithValue("@p8", data[7]);
                    cmd.Parameters.AddWithValue("@p9", data[8]);
                    cmd.Parameters.AddWithValue("@p10", data[9]);

                    cmd.ExecuteNonQuery();
                    
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                this.CloseConnection();
            }
            this.CloseConnection();
        }

        public void InsertMeterManagementData(MeterManagementData newData)
        {
            this.ConnectToDB();
            try
            {
                String sqlCommand = @"INSERT INTO meter_management VALUES (@p1, @p2, @p3, @p4, @p5, @p6, @p7, @p8, @p9, @p10, @p11)";

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    
                    String[] data = newData.GetData();

                    cmd.Parameters.AddWithValue("@p1", Int32.Parse(data[0]));
                    cmd.Parameters.AddWithValue("@p2", Int32.Parse(data[1]));
                    cmd.Parameters.AddWithValue("@p3", Int32.Parse(data[2]));
                    cmd.Parameters.AddWithValue("@p4", Int32.Parse(data[3]));
                    cmd.Parameters.AddWithValue("@p5", Int32.Parse(data[4]));
                    cmd.Parameters.AddWithValue("@p6", data[5]);
                    cmd.Parameters.AddWithValue("@p7", data[6]);
                    cmd.Parameters.AddWithValue("@p8", data[7]);
                    cmd.Parameters.AddWithValue("@p9", Int32.Parse(data[8]));
                    cmd.Parameters.AddWithValue("@p10", data[9]);
                    cmd.Parameters.AddWithValue("@p11", data[10]);

                    cmd.ExecuteNonQuery();
                    
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                this.CloseConnection();
            }
            this.CloseConnection();
        }

        public void InsertMeterManufactorData(MeterManufactorData newData)
        {
            this.ConnectToDB();
            try
            {
                String sqlCommand = @"INSERT INTO meter_manufactor VALUES (@p1, @p2)";

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    String[] data = newData.GetData();

                    cmd.Parameters.AddWithValue("@p1", Int32.Parse(data[0]));
                    cmd.Parameters.AddWithValue("@p2", data[1]);

                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                this.CloseConnection();
            }
            this.CloseConnection();
        }

        public void InsertMeterProtocolData(MeterProtocolData newData)
        {
            this.ConnectToDB();
            try
            {
                String sqlCommand = @"INSERT INTO meter_protocol VALUES (@p1, @p2, @p3)";

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    String[] data = newData.GetData();

                    cmd.Parameters.AddWithValue("@p1", Int32.Parse(data[0]));
                    cmd.Parameters.AddWithValue("@p2", data[1]);
                    cmd.Parameters.AddWithValue("@p3", data[2]);

                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                this.CloseConnection();
            }
            this.CloseConnection();
        }

        public void InsertMeterTypeData(MeterTypeData newData)
        {
            this.ConnectToDB();
            try
            {
                String sqlCommand = @"INSERT INTO meter_type VALUES (@p1, @p2)";

                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    String[] data = newData.GetData();

                    cmd.Parameters.AddWithValue("@p1", Int32.Parse(data[0]));
                    cmd.Parameters.AddWithValue("@p2", data[1]);

                    cmd.ExecuteNonQuery();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                this.CloseConnection();
            }
            this.CloseConnection();
        }

        private DateTime UnixTimeStampToDateTime(double unixTimeStamp)
        {
            System.DateTime dtDateTime = new DateTime(1970, 1, 1, 0, 0, 0, 0, System.DateTimeKind.Utc);
            dtDateTime = dtDateTime.AddSeconds(unixTimeStamp);
            return dtDateTime;
        }

        public void DeleteAllMeterData()
        {
            this.ConnectToDB();
            try
            {
                using (SqlCommand cmd = new SqlCommand())
                {
                    cmd.Connection = this.connection;
                    cmd.CommandText = "DELETE FROM meter_data";

                    Console.WriteLine("Removed Lines: " + cmd.ExecuteNonQuery() + "from meter_data");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void DeleteAllCustomerData()
        {
            this.ConnectToDB();
            try
            {
                using (SqlCommand cmd = new SqlCommand())
                {
                    cmd.Connection = this.connection;
                    cmd.CommandText = "DELETE FROM customer";

                    Console.WriteLine("Removed Lines: " + cmd.ExecuteNonQuery() + "from customer");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void DeleteAllMeterManagementData()
        {
            this.ConnectToDB();
            try
            {
                using (SqlCommand cmd = new SqlCommand())
                {
                    cmd.Connection = this.connection;
                    cmd.CommandText = "DELETE FROM meter_management";

                    Console.WriteLine("Removed Lines: " + cmd.ExecuteNonQuery() + "from meter_management");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void DeleteAllMeterManufactorData()
        {
            this.ConnectToDB();
            try
            {
                using (SqlCommand cmd = new SqlCommand())
                {
                    cmd.Connection = this.connection;
                    cmd.CommandText = "DELETE FROM meter_manufactor";

                    Console.WriteLine("Removed Lines: " + cmd.ExecuteNonQuery() + "from meter_manufactor");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void DeleteAllMeterProtocolData()
        {
            this.ConnectToDB();
            try
            {
                using (SqlCommand cmd = new SqlCommand())
                {
                    cmd.Connection = this.connection;
                    cmd.CommandText = "DELETE FROM meter_protocol";

                    Console.WriteLine("Removed Lines: " + cmd.ExecuteNonQuery() + "from meter_protocol");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public void DeleteAllMeterTypeData()
        {
            this.ConnectToDB();
            try
            {
                using (SqlCommand cmd = new SqlCommand())
                {
                    cmd.Connection = this.connection;
                    cmd.CommandText = "DELETE FROM meter_type";

                    Console.WriteLine("Removed Lines: " + cmd.ExecuteNonQuery() + "from meter_type");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
        }

        public int GetCustomerIdByLastname(String lastname)
        {
            this.ConnectToDB();

            String sqlCommand = "SELECT * FROM customer WHERE lastname ='" + lastname +"'";
            int foundId = -1;
            
            try
            {
                using (SqlCommand cmd = new SqlCommand(sqlCommand, connection))
                {
                    using (SqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            foundId = Int32.Parse(reader[0].ToString());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
            this.CloseConnection();
            return foundId;
        }
    }
}
