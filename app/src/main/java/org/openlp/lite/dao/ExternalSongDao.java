package org.openlp.lite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.openlp.lite.domain.Author;
import org.openlp.lite.domain.Song;
import org.openlp.lite.helper.ExternalDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madasamy on 10/10/2014.
 */
public class ExternalSongDao
{
    private SQLiteDatabase database;
    private ExternalDatabaseHelper dbHelper;
    private String[] allColumns = {ExternalDatabaseHelper.COLUMN_ID, ExternalDatabaseHelper.COLUMN_FIRST_NAME,
            ExternalDatabaseHelper.COLUMN_LAST_NAME, ExternalDatabaseHelper.COLUMN_DISPLAY_NAME};

    public ExternalSongDao(Context context)
    {
        dbHelper = new ExternalDatabaseHelper(context);
    }

    public void open()
    {
        database = dbHelper.openDataBase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public void createDatabase() throws Exception
    {
        dbHelper.copyDataBase();
    }

    public List<Author> findAll()
    {
        List<Author> songs = new ArrayList<Author>();
        ArrayList<String> arrTblNames = new ArrayList<String>();
        Cursor c = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                arrTblNames.add(c.getString(c.getColumnIndex("name")));
                c.moveToNext();
            }
        }
        Log.w(this.getClass().getName(), "No.of table: " + arrTblNames.size());
        for (String table : arrTblNames) {
            Log.w(this.getClass().getName(), "Table name: " + table);
        }
        c.close();
        Cursor cursor = database.query(ExternalDatabaseHelper.TABLE_NAME_AUTHOR,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Author song = cursorToSong(cursor);
            songs.add(song);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return songs;
    }

    private Author cursorToSong(Cursor cursor)
    {
        Author author = new Author();
        author.setId(cursor.getInt(0));
        author.setFirstName(cursor.getString(1));
        author.setLastName(cursor.getString(2));
        author.setDisplayName(cursor.getString(3));
        return author;
    }
}
