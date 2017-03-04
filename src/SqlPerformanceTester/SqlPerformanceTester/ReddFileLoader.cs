using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace SqlPerformanceTester
{
  class ReddFileLoader
  {
    private string _rootPath;

    public ReddFileLoader(string rootPath)
    {
      _rootPath = rootPath;
    }

    public List<ReddDataSet> GetDataSets()
    {
      List<ReddDataSet> dataSets = new List<ReddDataSet>();

      var rootDir = new DirectoryInfo(_rootPath);

      foreach (var houseDirectory in rootDir.EnumerateDirectories())
      {
        var houseName = houseDirectory.Name;
        var lines = ReadLabels(houseDirectory.FullName);

        foreach (var line in lines)
        {
          var split = line.Split(' ');
          dataSets.Add(new ReddDataSet
          {
            House = houseName,
            Label = split[1],
            FilePath = Path.Combine(houseDirectory.FullName, $"channel_{split[0]}.dat")
          });
        }
      }

      return dataSets;
    }

    private IEnumerable<string> ReadLabels(string houseDirectory)
    {
      return
          File.ReadLines(Path.Combine(houseDirectory, "labels.dat"), Encoding.UTF8)
              .Where(x => x.Length > 0);
    }
  }

}
