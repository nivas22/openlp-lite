package org.openlp.lite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.openlp.lite.helper.ExternalDatabaseHelper;

import java.io.IOException;

/**
 * Created by Madasamy on 11/10/2014.
 */
public class AbstractDao
{
    private SQLiteDatabase database;
    private ExternalDatabaseHelper databaseHelper;
    private Context context;

    public AbstractDao(Context context)
    {
        databaseHelper = new ExternalDatabaseHelper(context);
    }

    public void copyDatabase() throws IOException
    {
        databaseHelper.copyDataBase();
    }

    public void open()
    {
        database = databaseHelper.openDataBase();
    }

    public void close()
    {
        databaseHelper.close();
    }

    protected SQLiteDatabase getDatabase()
    {
        if (database == null) {
            database = databaseHelper.openDataBase();
        }
        return database;
    }
}
