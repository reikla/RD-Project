using System;
using System.Data;
using System.Diagnostics;
using MySql.Data.MySqlClient;

namespace SqlPerformanceTester
{
  public static class MySqlBenchmarker
  {
    public static PerformanceResult BenchmarkDataSet(ReddDataSet dataSet, int numOfRepetitions = 5)
    {

      var performanceResult = new PerformanceResult {DataSet = dataSet};

      var connectionString = "server=localhost;user=root;database=load_profiles";

      Insert(dataSet, connectionString, performanceResult);

      Update(dataSet, connectionString, performanceResult);

      var calculateCommandString =
        "select SQL_NO_CACHE avg(power) as power, day, month, meterId from redd group by meterId asc, month asc, day asc";

      Calculate(numOfRepetitions, connectionString, calculateCommandString, performanceResult);

      return performanceResult;

    }

    private static void Calculate(int numOfRepetitions, string connectionString, string calculateCommandString, PerformanceResult performanceResult)
    {
      for (var i = 0; i < numOfRepetitions; i++)
      {
        var sw = Stopwatch.StartNew();
        var calculationConnection = new MySqlConnection(connectionString);
        calculationConnection.Open();
        LogCommand(calculateCommandString);
        var calculateCommand = new MySqlCommand(calculateCommandString, calculationConnection);
        SetupCommand(calculateCommand);
        var reader = calculateCommand.ExecuteReader();
        DataTable dt = new DataTable();
        dt.Load(reader);
        performanceResult.AddCalculationResult(dt.Rows.Count,sw.ElapsedMilliseconds);
        
        calculationConnection.Close();
      }
    }

    private static void Update(ReddDataSet dataSet, string connectionString, PerformanceResult performanceResult)
    {
      var updateCommandString =
        $"update redd set datetime=FROM_UNIXTIME(timestamp), day=day(datetime), month=month(datetime), meterId = {dataSet.Id} where meterId is NULL";
      var sw = Stopwatch.StartNew();
      LogCommand(updateCommandString);

      var updateConnection = new MySqlConnection(connectionString);
      updateConnection.Open();

      MySqlCommand updateCommand = new MySqlCommand(updateCommandString, updateConnection);
      SetupCommand(updateCommand);
      updateCommand.ExecuteNonQuery();
      performanceResult.UpdateTimeMs = sw.ElapsedMilliseconds;
      updateConnection.Close();
    }

    private static void Insert(ReddDataSet dataSet, string connectionString, PerformanceResult performanceResult)
    {
      MySqlConnection insertConnection = new MySqlConnection(connectionString);
      insertConnection.Open();
      var sw = Stopwatch.StartNew();

      MySqlBulkLoader bl = new MySqlBulkLoader(insertConnection);
      Console.WriteLine($"Importing File: {dataSet.FilePath}");

      bl.TableName = "redd";
      bl.FieldTerminator = " ";
      bl.LineTerminator = "\n";
      bl.FileName = dataSet.FilePath;
      performanceResult.NumberOfNewRows = bl.Load();
      performanceResult.InsertTimeMs = sw.ElapsedMilliseconds;

      insertConnection.Close();
    }



    private static void SetupCommand(MySqlCommand command)
    {
      //5h
      command.CommandTimeout = 60 * 60 * 5;
    }

    private static void LogCommand(string command)
    {
      Console.WriteLine($"Executing command '{command}'");
    }
  }
}
