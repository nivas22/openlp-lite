package org.openlp.lite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.openlp.lite.helper.DatabaseHelper;

import java.io.IOException;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class AbstractDao
{
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;
    private Context context;

    public AbstractDao(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
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
