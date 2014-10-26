package org.openlp.lite.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.openlp.lite.R;
import org.openlp.lite.dao.SongDao;
import org.openlp.lite.picker.ColorPickerPreference;
import org.openlp.lite.task.AsyncDownloadTask;

import java.io.File;


/**
 * Created by Seenivasan on 10/11/2014.
 */
public class SettingsActivity extends Activity
{

    private static final int REQUEST_PICK_FILE = 1;
    private File selectedFile;
    private SongDao songDao;
    final Context context = this;
    ListView settingsMenuList;
    String settingsMenuValues[];
    AlertDialog levelDialog;

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        songDao = new SongDao(this);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        settingsMenuValues = getResources().getStringArray(R.array.settings_menu);
        settingsMenuList = (ListView) findViewById(R.id.listView1);
        settingsMenuList.setAdapter(new ListAdapter(this));

        settingsMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override

            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                AlertDialogView();
                if(position==0){
                    AlertDialogView();
                }
                else{
                    Intent  intent;
                    intent = new Intent(SettingsActivity.this, ColorPickerPreference.class);
                    startActivity(intent);
                }

            }

        });
    }

    private void AlertDialogView()
    {
        // Strings to Show In Dialog with Radio Buttons
        final CharSequence[] syncDatabaseOption = getResources().getStringArray(R.array.sync_database_options);
        // Creating and Building the Dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sync Database");
        builder.setSingleChoiceItems(syncDatabaseOption, -1, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                switch (item) {
                    case 0:
                        Intent intent = new Intent(SettingsActivity.this, FilePickerActivity.class);
                        intent.setType("storage/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_PICK_FILE);
                        break;

                    case 1:

                        //Download database file from remote url
                        downloadDialogBox();
                        // Your code when 2nd  option seletced

                        break;
                    case 2:
                        // Your code when 3rd option seletced
                        break;
                    case 3:
                        // Your code when 4th  option seletced
                        break;

                }


            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    private void downloadDialogBox()
    {
        final AsyncDownloadTask asyncDownloadTask = new AsyncDownloadTask();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getResources().getString(R.string.remoteUrlTitle));

        // Set an EditText view to get user remoteUrlEditText
        final EditText remoteUrlEditText = new EditText(this);
        remoteUrlEditText.setText(R.string.remoteUrl);
        alert.setView(remoteUrlEditText);

        alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                String remoteUrl = remoteUrlEditText.getText().toString();
                File externalCacheDir = context.getExternalCacheDir();
                File downloadSongFile = null;
                try {
                    downloadSongFile = File.createTempFile("downloadsongs", "sqlite", externalCacheDir);
                    if (asyncDownloadTask.execute(remoteUrl, downloadSongFile.getAbsolutePath()).get()) {
                        //do something after downloading
                        songDao.copyDatabase(downloadSongFile.getAbsolutePath(), true);
                    } else {
                        Log.w(this.getClass().getSimpleName(), "File is not downloaded from " + remoteUrl);
                    }
                } catch (Exception e) {
                    Log.e(this.getClass().getSimpleName(), "Error occurred while downloading file" + e);
                } finally {
                    downloadSongFile.deleteOnExit();
                }
            }
        });

        alert.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                // Canceled.
            }
        });
        alert.show();

// see http://androidsnippets.com/prompt-user-remoteUrlEditText-with-an-alertdialog
    }

    private class ListAdapter extends BaseAdapter
    {

        LayoutInflater inflater;

        public ListAdapter(Context context)
        {
            inflater = LayoutInflater.from(context);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            TextView txt_view1;
            convertView = inflater.inflate(R.layout.settings_list_textview, null);
            txt_view1 = (TextView) convertView.findViewById(R.id.textView1);
            txt_view1.setText(settingsMenuValues[position]);
            return convertView;
        }

        public int getCount()
        {
            return settingsMenuValues.length;
        }

        public Object getItem(int position)
        {
            return position;
        }

        public long getItemId(int position)
        {
            return position;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case REQUEST_PICK_FILE:

                    if (data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {

                        levelDialog.dismiss();
                        selectedFile = new File
                                (data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH));

                        Toast.makeText(SettingsActivity.this, "File Path is" + selectedFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        // filePath.setText(selectedFile.getPath());
                        try {
                            songDao.copyDatabase(selectedFile.getAbsolutePath(), true);
                            Toast.makeText(SettingsActivity.this, "Database is loaded from the file Path: " + selectedFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, SongsListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } catch (Exception e) {

                        }

                        // filePath.setText(selectedFile.getPath());

                    }
                    break;
            }
        }
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                intent = new Intent(this, SongsListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}