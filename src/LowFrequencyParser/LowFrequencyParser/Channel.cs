using System.Collections.Generic;

namespace LowFrequencyParser
{
    public class Channel
    {
        public Channel(string id, string name)
        {
            Id = id;
            Name = name;
            Data = new Dictionary<string, double>();
        }

        public string Id { get; set; }  
        public string Name { get; set; }
        public Dictionary<string,double> Data { get; set; }
    }
}