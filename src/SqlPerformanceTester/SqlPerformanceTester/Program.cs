using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Serialization;

namespace SqlPerformanceTester
{
  class Program
  {
    static void Main(string[] args)
    {
      ReddFileLoader rfl = new ReddFileLoader(@"F:\data\low_freq");

      var results = new List<PerformanceResult>();
      int totalNumberOfRows = 0;

      foreach (var reddDataSet in rfl.GetDataSets().Where(x=>x.LineCount < 50000))
      {
        var lines = reddDataSet.LineCount;
        
        Console.WriteLine($"House: '{reddDataSet.House}' Label: '{reddDataSet.Label}' Lines: {lines}");
        var result = MySqlBenchmarker.BenchmarkDataSet(reddDataSet);
        totalNumberOfRows += lines;
        result.TotalNumberOfRows = totalNumberOfRows;

        results.Add(result);
        XmlSerializer serializer = new XmlSerializer(typeof(List<PerformanceResult>));
        using (TextWriter tw = new StreamWriter("Q:\\performance.xml"))
        {
          serializer.Serialize(tw, results);
        }
      }
      Console.ReadKey();
    }
  }
}