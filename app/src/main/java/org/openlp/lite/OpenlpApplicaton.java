package org.openlp.lite;

import android.app.Application;
import android.content.Context;

/**
 * Created by Seenivasan on 10/19/2014.
 */
public class OpenlpApplicaton extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

    }

}
