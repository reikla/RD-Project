using System.IO;

namespace SqlPerformanceTester
{
  public class ReddDataSet
  {
    private static int id = 0;

    public ReddDataSet()
    {
      Id = ++id;
    }

    public int Id { get; }
    public string House { get; set; }
    public string FilePath { get; set; }
    public string Label { get; set; }

    public int LineCount
    {
      get
      {
        using (var r = new StreamReader(FilePath))
        {
          var i = 0;
          while (r.ReadLine() != null) { i++; }
          return i;
        }
      }
    }
  }
}
