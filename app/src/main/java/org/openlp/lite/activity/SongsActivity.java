package org.openlp.lite.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import org.openlp.lite.R;
import org.openlp.lite.page.component.fragment.TabContentView;
import org.openlp.lite.page.component.tab.TabListener;

/**
 * Created by Seenivasan on 10/8/2014.
 */
public class SongsActivity extends Activity {

    ActionBar.Tab Tab1, Tab2, Tab3;
    TextView textView;
    String[] dataArray;
    Fragment fragmentTab1 = new TabContentView();
    Fragment fragmentTab2 = new TabContentView();
    Fragment fragmentTab3 = new TabContentView();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabactivity);
        Intent intent = getIntent();
        dataArray = intent.getStringArrayExtra("data");
        int position = intent.getExtras().getInt("position");
        System.out.print("Position"+position);
        Log.d("Pos",Integer.toString(position));
        textView = (TextView) findViewById(R.id.data);
        textView.setText(dataArray[1]);

        //textView.setText("hi...");

        ActionBar actionBar = getActionBar();

        // Hide Actionbar Icon
        actionBar.setDisplayShowHomeEnabled(true);

        // Hide Actionbar Title
        actionBar.setDisplayShowTitleEnabled(true);

        // Create Actionbar Tabs
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set Tab Icon and Titles
        Tab1 = actionBar.newTab().setText("Tab1");
        Tab2 = actionBar.newTab().setText("Tab2");
        Tab3 = actionBar.newTab().setText("Tab3");

        // Set Tab Listeners
        Tab1.setTabListener(new TabListener(fragmentTab1));
        Tab2.setTabListener(new TabListener(fragmentTab2));
        Tab3.setTabListener(new TabListener(fragmentTab3));

        // Add tabs to actionbar
        actionBar.addTab(Tab1);
        actionBar.addTab(Tab2);
        actionBar.addTab(Tab3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
}
