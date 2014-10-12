package org.openlp.lite.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import org.openlp.lite.R;


/**
 * Created by Seenivasan on 10/6/2014.
 */
public class Lyrics_Show extends Activity {

    // Declare Variables
    TextView textView;
    String[] dataArray;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics_view);
        Intent intent = getIntent();
        dataArray = intent.getStringArrayExtra("data");
        int position = intent.getExtras().getInt("position");
        textView = (TextView) findViewById(R.id.data);
        textView.setText(dataArray[position]);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}




