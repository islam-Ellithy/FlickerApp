package victorylink.com.flickerapp.Models;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


class DownloadFile extends AsyncTask<String, Integer, Integer> {

    ProgressDialog mProgressDialog;// Change Mainactivity.this with your activity name.
    Context context;

    public DownloadFile(Context newContext) {
        mProgressDialog = new ProgressDialog(newContext);
        context = newContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    @Override
    protected Integer doInBackground(String... aurl) {
        int count;
        Integer result = 0;// if 1 mean is downloaded else if 0 isn't
        final String downloadFolder = "FlickerImages";
        try {
            URL url = new URL((String) aurl[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            String targetFileName = aurl[1] + ".jpg";//Change name and subname
            int lenghtOfFile = conexion.getContentLength();
            String PATH = Environment.getExternalStorageDirectory() + "/" + downloadFolder;
            File folder = new File(PATH);
            if (!folder.exists()) {
                folder.mkdir();//If there is no folder it will be created.
                Log.v("Info", "FOLDER has created");
            }
            InputStream input = new BufferedInputStream(url.openStream());

            String fullPath = PATH + "/" + targetFileName;

            File f = new File(fullPath);


            if (!f.exists()) {
                OutputStream output = new FileOutputStream(fullPath);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

                MediaScannerConnection.scanFile(context, new String[]{fullPath}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("SCAN", "Scanned " + path);
                            }
                        });

                result = 1;
            } else {
                mProgressDialog.dismiss();
                Log.v("IMAGE", "Image is already exists");
                result = 0;
            }
        } catch (Exception e) {
        }
        return result;
    }

    protected void onProgressUpdate(Integer... progress) {
        mProgressDialog.setProgress(progress[0]);
        if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
            mProgressDialog.dismiss();
            Toast.makeText(context, "File Downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPostExecute(Integer result) {
        if (result == 0)
            Toast.makeText(context, "Image is already exists", Toast.LENGTH_LONG).show();
    }
}
