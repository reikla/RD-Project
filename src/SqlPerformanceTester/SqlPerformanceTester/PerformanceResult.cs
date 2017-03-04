using System.Collections.Generic;

namespace SqlPerformanceTester
{
  public class PerformanceResult
  {
    public void AddCalculationResult(int numOfAffectedRows, long time)
    {
      CalculationResults.Add(new CaluclationResult() {DurationMs = time, RowsAffected = numOfAffectedRows});
    }

    public PerformanceResult()
    {
      CalculationResults = new List<CaluclationResult>();
    }


    public ReddDataSet DataSet { get; set; }
    public int NumberOfNewRows { get; set; }

    public long InsertTimeMs { get; set; }

    public long UpdateTimeMs { get; set; }

    public List<CaluclationResult> CalculationResults { get; set; }
  }

  public class CaluclationResult
  {
    public CaluclationResult()
    {
      
    }
    public int RowsAffected { get; set; }
    public long DurationMs { get; set; }
  }
}