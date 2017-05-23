package at.ac.fh.salzburg.smartmeter.rest;

import at.ac.fh.salzburg.smartmeter.access.SqlDatabaseAccess;

/**
 * Created by reimarklammer on 23.05.17.
 */
public abstract class CustomQueryControllerBase {

    protected static SqlDatabaseAccess databaseAccess = new SqlDatabaseAccess();

}
