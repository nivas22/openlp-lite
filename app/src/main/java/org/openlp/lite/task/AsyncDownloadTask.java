package org.openlp.lite.task;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author : Madasamy
 * @Version : 1.0
 */
public class AsyncDownloadTask extends AsyncTask<String, Void, Boolean>
{
    @Override
    protected Boolean doInBackground(String... strings)
    {
        try {
            String remoteUrl = strings[0];
            String destinationPath = strings[1];
            String className = this.getClass().getSimpleName();
            Log.i(className, "Preparing to download " + destinationPath + "from " + remoteUrl);
            File destinationFile = new File(destinationPath);
            URL url = new URL(remoteUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            DataInputStream dataInputStream = new DataInputStream(urlConnection.getInputStream());
            int contentLength = urlConnection.getContentLength();
            Log.d(className, "Content length " + contentLength);
            byte[] buffer = new byte[contentLength];
            dataInputStream.readFully(buffer);
            dataInputStream.close();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(destinationFile));
            dataOutputStream.write(buffer);
            dataOutputStream.flush();
            dataOutputStream.close();
            Log.i(className, "Finished downloading file!");
            return true;
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "Error", ex);
            return false;
        }

    }
}
