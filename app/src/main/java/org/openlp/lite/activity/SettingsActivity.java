package org.openlp.lite.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.openlp.lite.R;
import org.openlp.lite.service.FilePickerService;


import java.io.File;


/**
 * Created by Seenivasan on 10/11/2014.
 */
public class SettingsActivity extends Activity {

    private static final int REQUEST_PICK_FILE = 1;
    private File selectedFile;
    final Context context = this;
    ListView settingsMenuList;
    String settingsMenuValues[];
    AlertDialog levelDialog;

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        ActionBar ab = getActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        settingsMenuValues = getResources().getStringArray(R.array.settings_menu);
        settingsMenuList = (ListView) findViewById(R.id.listView1);
        settingsMenuList.setAdapter(new ListAdapter(this));

        settingsMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                AlertDialogView();
            }

        });
    }

    private void AlertDialogView() {
        // Strings to Show In Dialog with Radio Buttons
        final CharSequence[] syncDatabaseOption = getResources().getStringArray(R.array.sync_database_options);
        // Creating and Building the Dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sync Database");
        builder.setSingleChoiceItems(syncDatabaseOption, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        Intent intent = new Intent(SettingsActivity.this, FilePickerService.class);
                        intent.setType("storage/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_PICK_FILE);
                        break;

                    case 1:
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

    public boolean onOptionsItemSelected(MenuItem item)
    {
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            switch(requestCode) {

                case REQUEST_PICK_FILE:

                    if(data.hasExtra(FilePickerService.EXTRA_FILE_PATH)) {

                        levelDialog.dismiss();
                        selectedFile = new File
                                (data.getStringExtra(FilePickerService.EXTRA_FILE_PATH));

                        Toast.makeText(SettingsActivity.this, "File Path is" + selectedFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                       // filePath.setText(selectedFile.getPath());
                    }
                    break;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}