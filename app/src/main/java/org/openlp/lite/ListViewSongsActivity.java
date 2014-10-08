package org.openlp.lite;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import org.openlp.lite.dao.SongDao;
import org.openlp.lite.domain.Song;

import java.util.List;
import java.util.Random;

/**
 * Created by Madasamy on 8/10/2014.
 */
public class ListViewSongsActivity extends ListActivity {
    private SongDao datasource;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_song);

        datasource = new SongDao(this);
        datasource.open();

        List<Song> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Song> adapter = (ArrayAdapter<Song>) getListAdapter();
        Song song = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new song to the database
                song = datasource.createComment(comments[nextInt]);
                adapter.add(song);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    song = (Song) getListAdapter().getItem(0);
                    datasource.deleteComment(song);
                    adapter.remove(song);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
