package org.openlp.lite.page.component.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.openlp.lite.OpenlpApplicaton;
import org.openlp.lite.R;

/**
 * Created by Seenivasan on 10/8/2014.
 */

public class VerseContentView extends Fragment
{

    Context context = OpenlpApplicaton.getContext();
    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

    private Typeface typeFace;
    private Typeface fontFaceStyle;
    private float fontSize;
    TextView textView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_content_view, container, false);


        if (getArguments() != null) {
            String text = getArguments().getString("verseData");
            textView = ((TextView) rootView.findViewById(R.id.data));
            textView.setText(text);
            textView.setTypeface(getTypeFace());
            textView.setTextSize(getFontSize());

        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        textView.setTypeface(getTypeFace());
        textView.setTextSize(getFontSize());
    }

    public Typeface getTypeFace() {
        String sharedTypeFace = sharedPrefs.getString("prefSetFontFace","NULL");
        Log.d(this.getClass().getName(),"Font Face:"+sharedTypeFace);
        if(sharedTypeFace.equals("DEFAULT"))
            typeFace=Typeface.DEFAULT;
        if(sharedTypeFace.equals("DEFAULT_BOLD"))
            typeFace=Typeface.DEFAULT_BOLD;
        if(sharedTypeFace.equals("MONOSPACE"))
            typeFace=Typeface.MONOSPACE;
        if(sharedTypeFace.equals("SANS_SERIF"))
            typeFace=Typeface.SANS_SERIF;
        if(sharedTypeFace.equals("SERIF"))
            typeFace=Typeface.SERIF;
        return typeFace;
    }


    public float getFontSize() {
        String sharedFontSize = sharedPrefs.getString("prefSetFont","NULL");

        if(sharedFontSize.equals("SMALL"))
            fontSize=10;
        if(sharedFontSize.equals("MEDIUM"))
            fontSize=15;
        if(sharedFontSize.equals("NORMAL"))
            fontSize=20;
        if(sharedFontSize.equals("HIGH"))
            fontSize=30;
        return fontSize;
    }
}