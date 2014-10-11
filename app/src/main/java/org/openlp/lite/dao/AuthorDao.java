package org.openlp.lite.dao;

import android.content.Context;
import android.database.Cursor;

import org.openlp.lite.domain.Author;
import org.openlp.lite.helper.ExternalDatabasehelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class AuthorDao extends AbstractDao
{
    private String[] allColumns = {ExternalDatabasehelper.COLUMN_ID, ExternalDatabasehelper.COLUMN_FIRST_NAME,
            ExternalDatabasehelper.COLUMN_LAST_NAME, ExternalDatabasehelper.COLUMN_DISPLAY_NAME};

    public AuthorDao(Context context)
    {
        super(context);
    }

    public List<Author> findAll()
    {
        List<Author> songs = new ArrayList<Author>();
        Cursor cursor = getDatabase().query(ExternalDatabasehelper.TABLE_NAME_AUTHOR,
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
