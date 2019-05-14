package devatech.kims;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.crash.FirebaseCrash;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import devatech.Utils.RuntimePermissionsActivity;
import devatech.dashboard.Task_Navigation;

public class SplashActivity extends RuntimePermissionsActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = "Permission Check";
    //**********************************************************************************************
    ImageView Logo;

    // at.grabner.circleprogress.CircleProgressView progressBar;
    TextView progress_status;
    private int progress;

    private int progressStatus = 0;
    private Handler handler = new Handler();
    Thread brthread;

    Copydatabase copydb = new Copydatabase();

    // String [] languagelist={"Bengali","English","Gujrathi","Hindi","Kannada","Malayalam","Marathi","Punjabi","Tamil","Telugu"};

    String[] languagelist = {"English", "Inspirational"};

    private static final int REQUEST_PERMISSIONS = 20;

    ProgressBar progressBar1;

    //**********************************************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try
        {
            isStoragePermissionGranted();
//            throw new ArrayIndexOutOfBoundsException();
        } catch (Exception e) {
            e.printStackTrace();

            FirebaseCrash.logcat(Log.ERROR, "Splash Screen", e.toString());
        }



    }



    @Override
    public void onPermissionsGranted(final int requestCode) {

        // Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
        try {

            GetInitialize();
            Controllisteners();
            CreateFolder();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Splash_Screen", e.toString());
        }


    }

    public void isStoragePermissionGranted() {

         SplashActivity.super.requestAppPermissions(new
                                String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.CHANGE_WIFI_STATE,
                                Manifest.permission.WAKE_LOCK,
                                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                                Manifest.permission.VIBRATE,
                                Manifest.permission.SET_ALARM,
                                //Manifest.permission.WRITE_SECURE_SETTINGS
                        }, R.string
                                .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);


    }


    public void CreateFolder() {
        File f = new File(Baseconfig.DATABASE_FILE_PATH);
        if (!f.exists()) {
            File file = new File(Baseconfig.DATABASE_FILE_PATH);
            file.mkdirs();

        } else {


           /* SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);

            String IsFileDeleted = sp.getString("FileDeleted", "");

            if (!IsFileDeleted.toString().equalsIgnoreCase("1"))
            {

                File dir = new File(Baseconfig.DATABASE_FILE_PATH);
                if (dir.isDirectory())
                {
                    String[] children = dir.list();
                    for (int i = 0; i < children.length; i++)
                    {
                        new File(dir, children[i]).delete();
                        Log.e("Delete Files & Folders", new File(Baseconfig.DATABASE_NAME).getAbsolutePath());
                        savePreferences("FileDeleted", "1");
                    }
                }

            }*/

        }

        CopyDatabase();
    }

    /**
     *
     */


    //************************************************************************************
    /*
     * Remeber Me Using Shared Preference
	 */
    private void savePreferences(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //*******************************************************************************


    public void CopyDatabase() {


        // Check if the database exists before copying
        boolean initialiseDatabase = (new File(Baseconfig.DATABASE_NAME)).exists();
        Log.e("KIMS DB PATH", new File(Baseconfig.DATABASE_NAME).getAbsolutePath());

        if (initialiseDatabase == false) {
            copydb.execute();

        } else {

            LoadDashBoard();
        }

    }


    private class Copydatabase extends AsyncTask<String, String, String> {


        protected void onPostExecute(String v) {

            CopyDatabase();
            copydb.cancel(true);
            copydb = new Copydatabase();


        }


        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                shipdb();

                shipAssets();

              /*  for (int i = 0; i <= languagelist.length - 1; i++) {
                    copyFolder(languagelist[i].toString());
                }*/


            } catch (Exception e) {


                e.printStackTrace();
                FirebaseCrash.logcat(Log.ERROR, "Splash_Screen", e.toString());
            }


            return null;

        }

        @Override
        protected void onProgressUpdate(String... values) {


            super.onProgressUpdate(values);


        }

        //***************************************************************************************************
        private void shipAssets() throws IOException
        {

            // Check if the database exists before copying
            //boolean initialiseDatabase = (new File(Baseconfig.DATABASE_NAME)).exists();

            //if (initialiseDatabase == false)
            {
                Log.i("Processing...", "Copying Database");
                // Open the .db file in your assets directory
                InputStream is = getApplicationContext().getAssets().open("META_DATA.zip");

                // Copy the database into the destination
                OutputStream os = new FileOutputStream(Baseconfig.ASSEST_ZIP_NAME);
                byte[] buffer = new byte[1024];
                int length;
                int sizeOfInputStram = is.available();
                long total = 0;
                while ((length = is.read(buffer)) > 0) {
                    total += length;
                    publishProgress("" + (int) ((total * 100) / sizeOfInputStram));
                    os.write(buffer, 0, length);

                }
                os.flush();

                os.close();
                is.close();

            /*    //unpackZip(Baseconfig.DATABASE_FILE_PATH,"/META_DATA.zip");
                String zipFile = Baseconfig.ASSEST_ZIP_NAME;
                String unzipLocation = Environment.getExternalStorageDirectory() + "/unzipped/";

                Decompress d = new Decompress(zipFile, unzipLocation);
                d.unzip();*/

//                mUnpackZipFile(Baseconfig.ASSEST_ZIP_NAME,Environment.getExternalStorageDirectory().toString());
                unpackZip(Baseconfig.DATABASE_FILE_PATH,"META_DATA.zip");


            }

        }

        private boolean unpackZip(String path, String zipname)
        {
            InputStream is;
            ZipInputStream zis;
            try
            {
                String filename;
                is = new FileInputStream(path + zipname);
                Log.e("Zip Path",path+zipname);
                Log.e("Zip Path",path+zipname);
                Log.e("Zip Path",path+zipname);
                zis = new ZipInputStream(new BufferedInputStream(is));
                ZipEntry ze;
                byte[] buffer = new byte[1024];
                int count;

                while ((ze = zis.getNextEntry()) != null)
                {
                    // zapis do souboru
                    filename = ze.getName();

                    // Need to create directories if not exists, or
                    // it will generate an Exception...
                    if (ze.isDirectory()) {
                        File fmd = new File(path + filename);
                        fmd.mkdirs();
                        continue;
                    }

                    FileOutputStream fout = new FileOutputStream(path + filename);

                    // cteni zipu a zapis
                    while ((count = zis.read(buffer)) != -1)
                    {
                        fout.write(buffer, 0, count);
                    }

                    fout.close();
                    zis.closeEntry();
                }

                zis.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        private void shipdb() throws IOException {

            // Check if the database exists before copying
            boolean initialiseDatabase = (new File(Baseconfig.DATABASE_NAME)).exists();

            if (initialiseDatabase == false) {
                Log.i("Processing...", "Copying Database");
                // Open the .db file in your assets directory
                InputStream is = getApplicationContext().getAssets().open("kims.db");

                // Copy the database into the destination
                OutputStream os = new FileOutputStream(Baseconfig.DATABASE_NAME);
                byte[] buffer = new byte[1024];
                int length;
                int sizeOfInputStram = is.available();
                long total = 0;
                while ((length = is.read(buffer)) > 0) {
                    total += length;
                    publishProgress("" + (int) ((total * 100) / sizeOfInputStram));
                    os.write(buffer, 0, length);

                }
                os.flush();

                os.close();
                is.close();


            }

        }

    }
    //***************************************************************************************************

    public void GetInitialize() {

        Logo = (ImageView) findViewById(R.id.logo);

        //  progressBar = (CircleProgressView) findViewById(R.id.circleView);

        Baseconfig.animation1(Logo);
        //Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        //Logo.startAnimation(animation1);

        progressBar1 = (ProgressBar) findViewById(R.id.spin_kit);
        Wave doubleBounce = new Wave();
        progressBar1.setIndeterminateDrawable(doubleBounce);

        progress_status = (TextView) findViewById(R.id.textprgrs);


    }

    //**********************************************************************************************
    public void LoadDashBoard() {

        new Thread(new Runnable() {
            public void run() {

                while (progressStatus < 100) {
                    progressStatus = doSomeWork();

                    handler.post(new Runnable() {
                        public void run() {
                            //progressBar.setProgress(progressStatus);
                            //progressBar.setText(String.valueOf(progressStatus));
                            //progressBar.setValue();
                            progress_status.setText((String.valueOf(progressStatus)));
                        }
                    });
                }

                handler.post(new Runnable() {
                    public void run() {
                        // ---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                        // progressBar.setVisibility(View.GONE);
                        progress_status.setVisibility(View.GONE);
                        Baseconfig.StartWebservice(SplashActivity.this);
                        finish();
                        //Intent intent = new Intent(getApplicationContext(), DashBoard_Slide.class);
                        Intent intent = new Intent(getApplicationContext(), Task_Navigation.class);
                        intent.putExtra("GAME", "1");
                        intent.putExtra("Id", "");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                    }
                });
            }

            private int doSomeWork() {
                try {
                    // ---simulate doing some work---
                    Thread.sleep(30);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    FirebaseCrash.logcat(Log.ERROR, "Splash_Screen", e.toString());
                }
                return
                        ++progress;
            }
        }).start();

    }

    //**********************************************************************************************

    public void Controllisteners() {


    }

    //**********************************************************************************************

    @Override
    public void onBackPressed() {

    }


    private void copyFolder(String name) {

        try {
            // "Name" is the name of your folder!
            AssetManager assetManager = SplashActivity.this.getAssets();
            String[] files = null;

            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
                // We can read and write the media
                // Checking file on assets subfolder
                try {
                    files = assetManager.list(name);
                } catch (IOException e) {
                    Log.e("ERROR", "Failed to get asset file list.", e);
                }
                // Analyzing all file on assets subfolder
                for (String filename : files) {
                    InputStream in = null;
                    OutputStream out = null;
                    // First: checking if there is already a target folder
                    File folder = new File(Environment.getExternalStorageDirectory() + "/.KIMS/" + name);
                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdir();
                    }
                    if (success) {
                        // Moving all the files on external SD
                        try {
                            in = assetManager.open(name + "/" + filename);
                            out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/.KIMS/" + name + "/" + filename);
                            Log.i("WEBVIEW", Environment.getExternalStorageDirectory() + "/.KIMS/" + name + "/" + filename);
                            copyFile(in, out);
                            in.close();
                            in = null;
                            out.flush();
                            out.close();
                            out = null;
                        } catch (IOException e) {
                            Log.e("ERROR", "Failed to copy asset file: " + filename, e);
                        } /*finally {
                            // Edit 3 (after MMs comment)
                            in.close();
                            in = null;
                            out.flush();
                            out.close();
                            out = null;
                        }*/
                    } else {
                        // Do something else on failure
                    }
                }
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                // We can only read the media
            } else {
                // Something else is wrong. It may be one of many other states, but all we need
                // is to know is we can neither read nor write
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Splash_Screen", e.toString());
        }
    }


    // Method used by copyAssets() on purpose to copy a file.
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
    //**********************************************************************************************


}
//**********************************************************************************************




