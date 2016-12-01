using System.Collections.Generic;
using System.IO;

namespace LowFrequencyParser
{
    public class House
    {
        public House(string name)
        {
            Name = name;
            Channels = new Dictionary<string, Channel>();
        }

        public string Name { get; set; }
        public Dictionary<string,Channel> Channels { get; set; }
    }
}