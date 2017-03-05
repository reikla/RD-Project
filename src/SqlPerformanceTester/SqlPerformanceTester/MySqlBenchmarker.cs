using System;
using System.Collections.Generic;
using System.Data;
using System.Diagnostics;
using System.IO;
using System.Text;
using MySql.Data.MySqlClient;

namespace SqlPerformanceTester
{
  public static class MySqlBenchmarker
  {
    private static readonly string ConnectionString = "server=localhost;user=root;database=load_profiles";
    private static readonly string CalculateCommandString = "select SQL_NO_CACHE avg(power) as power, day, month, meterId from redd group by meterId asc, month asc, day asc";

    public static PerformanceResult BenchmarkDataSet(ReddDataSet dataSet, int numOfRepetitions = 5)
    {

      var performanceResult = new PerformanceResult { DataSet = dataSet };


      ProcessAndUploadDataSet(dataSet, performanceResult, 100000);

      BenchmarkServerCalculation(performanceResult, numOfRepetitions);

      return performanceResult;

    }

    private static void ProcessAndUploadDataSet(ReddDataSet dataSet, PerformanceResult performanceResult, int bulkSize)
    {
      using (MySqlConnection insertConnection = new MySqlConnection(ConnectionString))
      {
        insertConnection.Open();
        var sw = Stopwatch.StartNew();

        var rows = new List<string>();
        int pos = 0;
        foreach (var line in File.ReadLines(dataSet.FilePath))
        {
          var split = line.Split(' ');
          var date = FromUnixTime(long.Parse(split[0]));
          rows.Add($"({split[1]}, {dataSet.Id},{date.Day}, {date.Month})");
          if (++pos % bulkSize == 0)
          {
            StoreDataOnServer(rows, insertConnection);
          }
        }

        //Store the remaining rows
        StoreDataOnServer(rows, insertConnection);
        performanceResult.InsertTimeMs = sw.ElapsedMilliseconds;
        performanceResult.NumberOfNewRows = dataSet.LineCount;
      }
    }

    private static void StoreDataOnServer(List<string> rows, MySqlConnection insertConnection)
    {
      StringBuilder command = new StringBuilder("INSERT INTO redd (power, meterId, day, month) values");

      command.Append(string.Join(",", rows));
      command.Append(";");
      using (var insertCommand = new MySqlCommand(command.ToString(), insertConnection))
      {
        insertCommand.CommandType = CommandType.Text;
        insertCommand.CommandTimeout = 60 * 60;
        insertCommand.ExecuteNonQuery();
      }
      rows.Clear();
    }

    private static void BenchmarkServerCalculation(PerformanceResult performanceResult, int numOfRepetitions = 5)
    {
      for (var i = 0; i < numOfRepetitions; i++)
      {
        var sw = Stopwatch.StartNew();
        using (var calculationConnection = new MySqlConnection(ConnectionString))
        {
          calculationConnection.Open();
          LogCommand(CalculateCommandString);
          var calculateCommand = new MySqlCommand(CalculateCommandString, calculationConnection);
          SetupCommand(calculateCommand);
          var reader = calculateCommand.ExecuteReader();
          DataTable dt = new DataTable();
          dt.Load(reader);
          performanceResult.AddCalculationResult(dt.Rows.Count, sw.ElapsedMilliseconds);
        }

      }
    }

    private static DateTime FromUnixTime(long unixTime)
    {
      var epoch = new DateTime(1970, 1, 1, 0, 0, 0, DateTimeKind.Utc);
      return epoch.AddSeconds(unixTime);
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
