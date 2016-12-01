using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using MongoDB.Bson;
using MongoDB.Bson.Serialization;
using MongoDB.Driver;

namespace LowFrequencyParser
{
    class Program
    {

        static void Main(string[] args)
        {

            var client = new MongoClient("mongodb://localhost:27017");

            var db = client.GetDatabase("db");
            var houses = db.GetCollection<BsonDocument>("houses");


            BsonClassMap.RegisterClassMap<House>();

            Console.WriteLine("Enter path to low frequency data");
            var path = Console.ReadLine();
            if (!Directory.Exists(path))
            {
                Console.WriteLine("Path does not exist!");
                Console.ReadKey();
                Environment.Exit(-1);
            }
            DirectoryInfo di = new DirectoryInfo(path);
            foreach (var dir in di.EnumerateDirectories())
            {
                var house = new House(dir.Name);
                Console.WriteLine($"Data for House: '{dir.Name}' found.");
                var labelsFile = dir.EnumerateFiles("labels.dat").Single();
                using (TextReader tr = new StreamReader(labelsFile.FullName))
                {
                    string line;
                    while ((line = tr.ReadLine()) != null)
                    {
                        var data = line.Split(' ');
                        var channel = new Channel(data[0], data[1]);
                        house.Channels.Add(channel.Id, channel);
                        Console.WriteLine($"Found label: '{data[0]}' name: '{data[1]}'");
                    }

                    foreach (var channelsKey in house.Channels.Keys)
                    {
                        var channelFileName = $"channel_{channelsKey}.dat";
                        var file = dir.EnumerateFiles(channelFileName).Single();

                        using (TextReader ctr = new StreamReader(file.FullName))
                        {
                            string cline;
                            while ((cline = ctr.ReadLine()) != null)
                            {
                                var cdata = cline.Split(' ');
                                house.Channels[channelsKey].Data.Add(cdata[0], double.Parse(cdata[1]));
                            }
                        }
                    }
                }

                foreach (var keyValuePair in house.Channels)
                {
                    var data = new List<BsonDocument>();
                    foreach (var entrySet in keyValuePair.Value.Data)
                    {
                        var channelData = new BsonDocument
                    {
                        {"house",house.Name},
                        {"name",keyValuePair.Key},
                        {"data",new BsonDocument
                        {
                            {entrySet.Key,entrySet.Value }
                        } }

                    };
                        data.Add(channelData);
                    }
                    houses.InsertMany(data);
                    Console.WriteLine("Done with channel" + keyValuePair.Key);
                }
                Console.WriteLine($"Done with {house.Name}");
            }


            Console.WriteLine("Press key to exit");
            Console.ReadKey();
        }
    }
}