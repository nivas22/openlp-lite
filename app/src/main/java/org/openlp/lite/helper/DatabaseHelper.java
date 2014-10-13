package org.openlp.lite.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.maven.shared.utils.io.IOUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class DatabaseHelper extends SQLiteOpenHelper
{
    public static String DB_PATH = "";
    private static final int DATABASE_VERSION = 3;
    public static String DB_NAME = "songs.sqlite";
    private SQLiteDatabase database;
    private final Context context;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.context = context;
        this.DB_PATH = "/data/data/" + context.getApplicationContext().getPackageName() + "/databases/";
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException
    {
        Log.w(this.getClass().getName(), "Preparing to create database");
        boolean dbExist = checkDataBase();
        if (dbExist) {
            //do nothing - database already exist
            Log.w(this.getClass().getName(), "DB already exists");
        } else {
            Log.w(this.getClass().getName(), "DB is not exists");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (Exception ex) {
                Log.e(this.getClass().getName(), "Error occurred while copy database " + ex);
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            Log.w(this.getClass().getName(), "DB path" + myPath);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error occurred while checking database" + ex);
            //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     */
    public void copyDataBase() throws IOException
    {
        Log.i(this.getClass().getName(), "Preparing to copy database");
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            Log.i(this.getClass().getName(), "Db path: "+ outFileName);
            Log.i(this.getClass().getName(), "InputStream : "+ inputStream);
            outputStream = new FileOutputStream(outFileName);

            Log.i(this.getClass().getName(), "Output stream: "+ outputStream);
            IOUtil.copy(inputStream, outputStream);
            Log.i(this.getClass().getName(), "Copied successfully");
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error occurred while copying database " + ex);
        } finally {
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException
    {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return database;
    }

    @Override
    public synchronized void close()
    {
        if (database != null)
            database.close();

        super.close();
    }

    public SQLiteDatabase getDatabase()
    {
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
}
