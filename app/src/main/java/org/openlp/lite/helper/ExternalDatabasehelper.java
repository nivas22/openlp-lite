package org.openlp.lite.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.apache.maven.shared.utils.io.FileUtils;
import org.apache.maven.shared.utils.io.IOUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class ExternalDatabaseHelper extends SQLiteOpenHelper
{
    public static String DB_PATH = "";

    // Data Base Version.
    private static final int DATABASE_VERSION = 3;
    // Table Names of Data Base.
    public static final String TABLE_NAME_AUTHOR = "authors";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_DISPLAY_NAME = "display_name";

    public Context context;
    static SQLiteDatabase sqliteDataBase;
    //The Android's default system path of your application database.
    public static String DB_NAME = "songs.sqlite";
    private SQLiteDatabase myDataBase;
    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public ExternalDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 3);
        this.myContext = context;
        ///data/data/YOUR_PACKAGE/databases/"
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
            inputStream = myContext.getAssets().open(DB_NAME);
            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;
            outputStream = new FileOutputStream(outFileName);
            IOUtil.copy(inputStream, outputStream);
            Log.i(this.getClass().getName(), "Copied successfully");
        } catch (Exception ex) {
            Log.e(this.getClass().getName(), "Error occurred while copying database" + ex);
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
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        return myDataBase;
    }

    @Override
    public synchronized void close()
    {
        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }

    public SQLiteDatabase getDatabase()
    {
        return myDataBase;
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
