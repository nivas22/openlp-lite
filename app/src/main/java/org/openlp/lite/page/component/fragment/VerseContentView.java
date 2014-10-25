package org.openlp.lite.page.component.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import org.openlp.lite.service.UserPreferenceSettingService;

import java.util.Arrays;

/**
 * Created by Seenivasan on 10/8/2014.
 */

public class VerseContentView extends Fragment {
    private UserPreferenceSettingService preferenceSettingService;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_content_view, container, false);
        if (getArguments() != null) {
            String text = getArguments().getString("verseData");
            String lines[] = text.split("\\r?\\n");
            Log.d(this.getClass().getName(),"Splitted String"+ Arrays.toString(lines));
            textView = ((TextView) rootView.findViewById(R.id.data));
            preferenceSettingService=new UserPreferenceSettingService();
            textView.setText(text);
            textView.setTypeface(preferenceSettingService.getTypeFace(), preferenceSettingService.getFontStyle());
            textView.setTextSize(preferenceSettingService.getFontSize());
            textView.setTextColor(preferenceSettingService.getColor());
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        textView.setTypeface(preferenceSettingService.getTypeFace(), preferenceSettingService.getFontStyle());
        textView.setTextSize(preferenceSettingService.getFontSize());
        textView.setTextColor(preferenceSettingService.getColor());
    }
}