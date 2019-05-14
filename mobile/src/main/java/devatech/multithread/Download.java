package devatech.multithread;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.new_task.PdfViewActivity;
import devatech.new_task.PinkConnection;
import devatech.section_adapter.Item;
import devatech.section_adapter.SectionedExpandableGridAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by Ponnusamy M on 2/9/2017.
 */

public class Download {
    private NotificationManager mNotifyManager;
    private Builder build;
    String Url = "";
    SectionedExpandableGridAdapter.ViewHolder viewHolder;
    Item item;
    String Destination = "";

    public Download(String url, SectionedExpandableGridAdapter.ViewHolder viewHolder, String Destination, Item item) {
        this.Url = url;
        this.item = item;
        this.viewHolder = viewHolder;
        this.Destination = Destination;

    }

    public void download() {

        DownloadFileFromURLTask downloadFileFromURLTask = new DownloadFileFromURLTask(Url, viewHolder, Destination, item);
        downloadFileFromURLTask.execute();


    }

    class DownloadFileFromURLTask extends
            AsyncTask<String, String, String> {

        boolean fulldownload = false;
        String fileid = "";
        String Pdf_url = "";
        SectionedExpandableGridAdapter.ViewHolder viewHolder;
        Item item;
        String Destinationpath = "";
        public int id = 1;

        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            mNotifyManager = (NotificationManager) ((Activity) viewHolder.view.getContext()).getSystemService(Context.NOTIFICATION_SERVICE);
            build = new NotificationCompat.Builder(viewHolder.view.getContext());
            build.setContentTitle("Download  " + item.getName())
                    .setContentText(item.getSeries() + "  Download in progress")
                    .setSmallIcon(R.mipmap.ic_launcher);
            // Displays the progress bar for the first time.
            build.setProgress(100, 0, false);
            mNotifyManager.notify(id, build.build());

            ((Activity) viewHolder.view.getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewHolder.downloadProgress.setVisibility(View.VISIBLE);
                    viewHolder.progresstext.setVisibility(View.VISIBLE);
                }
            });


        }

        /**
         * Downloading file in background thread
         */

        @Override
        protected String doInBackground(String... f_url) {
            Log.i(TAG, "do in background");


            int count;
            //Code to update the UI
            try {
                URL url = new URL(Pdf_url.replaceAll(" ", "%20"));
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);


                File file = new File(Destinationpath);
                // Output stream to write file
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[104857600];

                long total = 0;


                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));


                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                fulldownload = true;

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
                fulldownload = false;

            }


            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(final String... progress) {
            // setting progress percentage

            ((Activity) viewHolder.view.getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    viewHolder.downloadProgress.setProgress(Integer.parseInt(progress[0]));
                    viewHolder.downloadProgress.setProgress(Integer.parseInt(progress[0]));
                    viewHolder.progresstext.setText(progress[0] + "%");
                    viewHolder.progresstext.setText(progress[0] + "%");
                }
            });

            build.setProgress(100, Integer.parseInt(progress[0]), false);
            mNotifyManager.notify(id, build.build());
            super.onProgressUpdate(progress[0]);

//            viewHolder.downloadProgress.setProgress(Integer.parseInt(progress[0]));
//            viewHolder.downloadProgress.setProgress(Integer.parseInt(progress[0]));
//            viewHolder.progresstext.setText(progress[0] + "%");
//            viewHolder.progresstext.setText(progress[0] + "%");


        }

        /**
         * After completing background task Dismiss the progress dialog
         **/

        @Override
        protected void onPostExecute(String file_url) {
            Log.i(TAG, "on post excute!");

            // dismiss progressbars after the file was downloaded

            if (fulldownload) {
                ((Activity) viewHolder.view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.progresstext.setVisibility(View.GONE);
                        viewHolder.downloadProgress.setVisibility(View.GONE);
                        viewHolder.pdf_available.setImageResource(R.drawable.downloaded);


                    }
                });

                build.setContentText("Download complete");
                // Removes the progress bar
                build.setProgress(0, 0, false);

                Context context = ((Activity) viewHolder.view.getContext()).getApplicationContext();


                Intent resultIntent = new Intent(context, PinkConnection.class);
                /*resultIntent.putExtra("Id", item.getId());
                resultIntent.putExtra("FilePath", Destinationpath.toString());
                resultIntent.putExtra("FileName", item.getName());
                resultIntent.putExtra("Title", item.getName() + " - " + item.getSeries());
                */

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(PinkConnection.class);

                build.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
                build.setAutoCancel(true);

// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                build.setContentIntent(resultPendingIntent);


                mNotifyManager.notify(id, build.build());


                Toast.makeText(viewHolder.view.getContext(), "Download Successfully...", Toast.LENGTH_SHORT).show();
            } else {

                ((Activity) viewHolder.view.getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.progresstext.setVisibility(View.GONE);
                        viewHolder.downloadProgress.setVisibility(View.GONE);
                        viewHolder.pdf_available.setImageResource(R.drawable.notdownload);


                    }
                });

                build.setContentText("Download is error..try later..");
                // Removes the progress bar
                build.setProgress(0, 0, false);

                Context context = ((Activity) viewHolder.view.getContext()).getApplicationContext();

                Intent resultIntent = new Intent(context, PinkConnection.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addParentStack(PdfViewActivity.class);

                build.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
                build.setAutoCancel(true);

// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                build.setContentIntent(resultPendingIntent);


                mNotifyManager.notify(id, build.build());


                Toast.makeText(viewHolder.view.getContext(), "Try to Download again...", Toast.LENGTH_SHORT).show();

                new Baseconfig(viewHolder.view.getContext()).deleteFile(Destinationpath);

            }

//            ((Activity) viewHolder.view.getContext()).finish();
//            Intent intent4 = new Intent(viewHolder.view.getContext(), PdfViewActivity.class);
//            intent4.putExtra("Id", item.getId());
//            intent4.putExtra("FilePath", Destinationpath.toString());
//            intent4.putExtra("FileName", item.getName());
//            intent4.putExtra("Title", item.getName() + " - " + item.getSeries());
//            viewHolder.view.getContext().startActivity(intent4);


            /******
             * @Ponnusamy
             * Date: 13.2.2017
             * Description : if pdf is downloaded write image in sd
             * ******/
            File pdfimage = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_Image/" + String.valueOf(item.getId()) + ".png");
            File pdffilecheck = new File(Environment.getExternalStorageDirectory().toString() + "/.KIMS/_PDF/" + String.valueOf(item.getId()) + ".pdf");

            if (pdffilecheck.exists()) {
                if (!pdfimage.exists()) {
                    Uri uri = null;
                    try {

                        uri = Uri.fromFile(pdffilecheck);
                    } catch (Exception e) {
                        e.printStackTrace();
                        FirebaseCrash.logcat(Log.ERROR, "Download", e.toString());
                    }

                    // Baseconfig.generateImageFromPdf(uri, viewHolder.view.getContext(), String.valueOf(item.getId()));
                }

            }


        }

        public DownloadFileFromURLTask(String pdf_url, SectionedExpandableGridAdapter.ViewHolder holder, String Destination_path, Item item) {


            this.Pdf_url = pdf_url;
            this.viewHolder = holder;
            this.Destinationpath = Destination_path;
            this.item = item;

        }
    }
}
