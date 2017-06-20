package at.ac.fh.salzburg.smartmeter.controllers;

import at.ac.fh.salzburg.smartmeter.access.SqlDatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by reimarklammer on 23.05.17.
 */
public abstract class CustomQueryControllerBase {

    @Autowired
    protected SqlDatabaseAccess databaseAccess;

}
