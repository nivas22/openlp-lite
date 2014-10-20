package org.openlp.lite.activity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.openlp.lite.R;
import org.openlp.lite.dao.SongDao;
import org.openlp.lite.domain.Song;
import org.openlp.lite.domain.Verse;
import org.openlp.lite.parser.VerseParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seenivasan on 10/1/2014.
 */

public class SongsListActivity extends Activity
{

    ListView list_view;
    ArrayAdapter<String> myAdapter;
    private VerseParser parser;

    private SongDao songDao;
    List<Song> songs;
    List<Verse> verse;
    ArrayAdapter<Song> adapter;
    String[] dataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.songs_list_activity);
        list_view = (ListView) findViewById(R.id.list_view);
        songDao = new SongDao(this);
        parser = new VerseParser();
        try {
            songDao.copyDatabase("", false);
        } catch (Exception ex) {
            Log.w(this.getClass().getName(), "Error occurred while creating database", ex);
        }
        songDao.open();
        loadSongs();
        dataArray = new String[songs.size()];
        for (int i = 0; i < songs.size(); i++) {
            dataArray[i] = songs.get(i).toString();
        }

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                String lyrics = songs.get(position).getLyrics();
                getVerse(lyrics);
                List<String> verseName = new ArrayList<String>();
                List<String> verseContent = new ArrayList<String>();
                for (Verse verses : verse) {
                    verseName.add(verses.getType() + verses.getLabel());
                    verseContent.add(verses.getContent());
                }
                Intent intent = new Intent(SongsListActivity.this, SongsViewActivity.class);
                intent.putStringArrayListExtra("verseName", (ArrayList<String>) verseName);
                intent.putStringArrayListExtra("verseContent", (ArrayList<String>) verseContent);
                startActivity(intent);
            }
        });
    }

    private void getVerse(String lyrics)
    {
        verse = new ArrayList<Verse>();
        verse = parser.parseVerseDom(this, lyrics);
    }

    private void loadSongs()
    {
        songs = songDao.findTitles();
        adapter = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, songs);
        list_view.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_action_bar_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                adapter.getFilter().filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SongsListActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
