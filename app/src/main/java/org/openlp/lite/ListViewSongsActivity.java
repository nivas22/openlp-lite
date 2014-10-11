package org.openlp.lite;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import org.openlp.lite.dao.AuthorDao;
import org.openlp.lite.dao.SongDao;
import org.openlp.lite.domain.Author;
import org.openlp.lite.domain.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Madasamy
 * @Version : 0.1
 */
public class ListViewSongsActivity extends ListActivity
{
    private AuthorDao authorDao;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_song);

        authorDao = new AuthorDao(this);
        try {
            authorDao.copyDatabase();
        } catch (Exception ex) {
            Log.w(ListViewSongsActivity.class.getName(), "Error occurred while creating database", ex);
        }
        authorDao.open();
        loadExternalSongs();
    }

    private void loadExternalSongs()
    {
        List<Author> values = authorDao.findAll();
        ArrayAdapter<Author> adapter = new ArrayAdapter<Author>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
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
        authorDao.open();
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        authorDao.close();
        super.onPause();
    }
}
