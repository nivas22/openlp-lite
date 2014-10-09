package org.openlp.lite;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import org.openlp.lite.dao.SongDao;
import org.openlp.lite.domain.Song;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Madasamy on 8/10/2014.
 */
public class ListViewSongsActivity extends ListActivity
{
    private SongDao datasource;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_song);

        datasource = new SongDao(this);
        datasource.open();

        loadSongs();
        createSongs();
    }

    private void loadSongs()
    {
        List<Song> values = datasource.findAll();
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
            song = datasource.create(songName);
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
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        datasource.close();
        super.onPause();
    }
}
