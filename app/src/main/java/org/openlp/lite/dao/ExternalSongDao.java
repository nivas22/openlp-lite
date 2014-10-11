package org.openlp.lite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.openlp.lite.domain.Author;
import org.openlp.lite.domain.Song;
import org.openlp.lite.helper.ExternalDatabaseHelper;

import java.io.IOException;
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

    public void copyDatabase() throws IOException
    {
        dbHelper.copyDataBase();
    }

    public void open()
    {
        database = dbHelper.openDataBase();
    }

    public void close()
    {
        dbHelper.close();
    }

    public List<Author> findAll()
    {
        List<Author> songs = new ArrayList<Author>();
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
