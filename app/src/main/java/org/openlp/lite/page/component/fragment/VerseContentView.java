package org.openlp.lite.page.component.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.openlp.lite.CommonConstants;
import org.openlp.lite.OpenlpApplicaton;
import org.openlp.lite.R;
import org.openlp.lite.service.CustomTagColorService;
import org.openlp.lite.service.UserPreferenceSettingService;
import org.openlp.lite.utils.PropertyUtils;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Seenivasan on 10/8/2014.
 */

public class VerseContentView extends Fragment {
    private UserPreferenceSettingService preferenceSettingService;
    private CustomTagColorService customTagColorService;
    Context context = OpenlpApplicaton.getContext();
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_content_view, container, false);
        textView = ((TextView) rootView.findViewById(R.id.data));
        preferenceSettingService = new UserPreferenceSettingService();
        if (getArguments() != null) {
            String text = getArguments().getString("verseData");
            customTagColorService = new CustomTagColorService();
            customTagColorService.setCustomTagTextView(context,text,textView);
            textView.setTypeface(preferenceSettingService.getTypeFace(), preferenceSettingService.getFontStyle());
            textView.setTextSize(preferenceSettingService.getFontSize());
            //textView.setTextColor(preferenceSettingService.getColor());
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