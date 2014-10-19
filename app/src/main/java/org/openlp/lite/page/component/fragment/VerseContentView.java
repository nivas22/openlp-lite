package org.openlp.lite.page.component.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.openlp.lite.R;

public class VerseContentView extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        View rootView = inflater.inflate(R.layout.tab_content_view, container, false);

        if(getArguments() != null)
        {
            String text = getArguments().getString("verseData");
            ((TextView)rootView.findViewById(R.id.data)).setText(text);
        }
        return rootView;
    }

}