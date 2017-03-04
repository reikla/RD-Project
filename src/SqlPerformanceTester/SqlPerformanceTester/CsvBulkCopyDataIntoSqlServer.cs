using System.Data;
using System.Data.SqlClient;
using Microsoft.VisualBasic.FileIO;

namespace SqlPerformanceTester
{
  public class CsvBulkCopyDataIntoSqlServer
  {
    static int _batchSize = 100000;
    private static string _tableName = "redd";

    public static void LoadCsvDataIntoSqlServer(ReddDataSet dataSet)
    {
      // This should be the full path

      var createdCount = 0;

      using (var textFieldParser = new TextFieldParser(dataSet.FilePath))
      {
        textFieldParser.TextFieldType = FieldType.Delimited;
        textFieldParser.Delimiters = new[] { " " };

        var connectionString = "Server=localhost;Database=load_profiles";

        var dataTable = new DataTable(_tableName);

        // Add the columns in the temp table
        dataTable.Columns.Add("meterId");
        dataTable.Columns.Add("timestamp");
        dataTable.Columns.Add("power");

        using (var sqlConnection = new SqlConnection(connectionString))
        {
          sqlConnection.Open();

         

          // Create the bulk copy object
          var sqlBulkCopy = new SqlBulkCopy(sqlConnection)
          {
            DestinationTableName = _tableName
          };

          // Setup the column mappings, anything ommitted is skipped
          sqlBulkCopy.ColumnMappings.Add("meterId", "meterId");
          sqlBulkCopy.ColumnMappings.Add("timestamp", "timestamp");
          sqlBulkCopy.ColumnMappings.Add("power", "power");


          // Loop through the CSV and load each set of 100,000 records into a DataTable
          // Then send it to the LiveTable
          while (!textFieldParser.EndOfData)
          {
            dataTable.Rows.Add(dataSet.Id, textFieldParser.ReadFields());

            createdCount++;

            if (createdCount % _batchSize == 0)
            {
              InsertDataTable(sqlBulkCopy, sqlConnection, dataTable);

              break;
            }
          }

          // Don't forget to send the last batch under 100,000
          InsertDataTable(sqlBulkCopy, sqlConnection, dataTable);

          sqlConnection.Close();
        }
      }
    }

    protected static void InsertDataTable(SqlBulkCopy sqlBulkCopy, SqlConnection sqlConnection, DataTable dataTable)
    {
      sqlBulkCopy.WriteToServer(dataTable);

      dataTable.Rows.Clear();
    }
  }
}