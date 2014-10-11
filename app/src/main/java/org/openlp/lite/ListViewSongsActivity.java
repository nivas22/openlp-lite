package org.openlp.lite;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import org.openlp.lite.dao.ExternalSongDao;
import org.openlp.lite.dao.SongDao;
import org.openlp.lite.domain.Author;
import org.openlp.lite.domain.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madasamy on 8/10/2014.
 */
public class ListViewSongsActivity extends ListActivity
{
    private SongDao songDao;
    private ExternalSongDao externalSongDao;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_song);

        songDao = new SongDao(this);
        songDao.open();

        externalSongDao = new ExternalSongDao(this);
        //externalSongDao.open();
        try {
            externalSongDao.copyDatabase();
        } catch (Exception ex) {
            Log.w(ListViewSongsActivity.class.getName(), "Error occurred while creating database", ex);
        }
        externalSongDao.open();
        loadExternalSongs();
        //loadSongs();
        // createSongs();
    }

    private void loadExternalSongs()
    {
        List<Author> values = externalSongDao.findAll();
        ArrayAdapter<Author> adapter = new ArrayAdapter<Author>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    private void loadSongs()
    {
        List<Song> values = songDao.findAll();
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    private void createSongs()
    {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Song> adapter = (ArrayAdapter<Song>) getListAdapter();
        Song song = null;
        List<String> songList = new ArrayList<String>();
        songList.add("foo1");
        songList.add("foo2");
        songList.add("foo3");
        songList.add("foo4");
        for (String songName : songList) {
            song = songDao.create(songName);
            adapter.add(song);
            adapter.notifyDataSetChanged();
        }
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view)
    {
        //TODO:Remove commented line before release 1.0
//        @SuppressWarnings("unchecked")
//        ArrayAdapter<Song> adapter = (ArrayAdapter<Song>) getListAdapter();
//        Song song = null;
//        switch (view.getId()) {
//            case R.id.add:
//                String[] comments = new String[]{"Cool", "Very nice", "Hate it"};
//                int nextInt = new Random().nextInt(3);
//                // save the new song to the database
//                song = datasource.create(comments[nextInt]);
//                adapter.add(song);
//                break;
//            case R.id.delete:
//                if (getListAdapter().getCount() > 0) {
//                    song = (Song) getListAdapter().getItem(0);
//                    datasource.delete(song);
//                    adapter.remove(song);
//                }
//                break;
//        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume()
    {
        songDao.open();
        externalSongDao.open();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        songDao.close();
        externalSongDao.close();
        super.onPause();
    }
}
