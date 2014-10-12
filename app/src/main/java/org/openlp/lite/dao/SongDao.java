package org.openlp.lite.dao;

import android.content.Context;
import android.database.Cursor;

import org.openlp.lite.domain.Author;
import org.openlp.lite.domain.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class SongDao extends AbstractDao
{
    public static final String TABLE_NAME_AUTHOR = "songs";
    public static final String[] allColumns = {"id", "song_book_id", "title", "alternate_title",
            "lyrics", "verse_order", "copyright", "comments", "ccli_number", "song_number", "theme_name",
            "search_title", "search_lyrics", "create_date", "last_modified", "temporary"};

    public SongDao(Context context)
    {
        super(context);
    }

    public List<Song> findTitles()
    {
        List<Song> songs = new ArrayList<Song>();
        Cursor cursor = getDatabase().query(TABLE_NAME_AUTHOR,
                new String[]{"title"}, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Song song = cursorToSong(cursor);
            songs.add(song);
            cursor.moveToNext();
        }
        cursor.close();
        return songs;
    }

    private Song cursorToSong(Cursor cursor)
    {
        Song song = new Song();
        song.setTitle(cursor.getString(0));
        return song;
    }
}
