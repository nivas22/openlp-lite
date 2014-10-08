package org.openlp.lite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.openlp.lite.domain.Song;
import org.openlp.lite.helper.OpenlpliteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madasamy on 8/10/2014.
 */
public class SongDao {

    private SQLiteDatabase database;
    private OpenlpliteHelper dbHelper;
    private String[] allColumns = { OpenlpliteHelper.COLUMN_ID,
            OpenlpliteHelper.COLUMN_COMMENT };

    public SongDao(Context context) {
        dbHelper = new OpenlpliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Song createComment(String song) {
        ContentValues values = new ContentValues();
        values.put(OpenlpliteHelper.COLUMN_COMMENT, song);
        long insertId = database.insert(OpenlpliteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(OpenlpliteHelper.TABLE_COMMENTS,
                allColumns, OpenlpliteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Song newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Song song) {
        long id = song.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(OpenlpliteHelper.TABLE_COMMENTS, OpenlpliteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Song> getAllComments() {
        List<Song> songs = new ArrayList<Song>();

        Cursor cursor = database.query(OpenlpliteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Song song = cursorToComment(cursor);
            songs.add(song);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return songs;
    }

    private Song cursorToComment(Cursor cursor) {
        Song comment = new Song();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}
