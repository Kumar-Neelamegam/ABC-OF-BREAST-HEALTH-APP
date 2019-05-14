package devatech.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.StringEntity;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import cn.pedant.SweetAlert.SweetAlertDialog;
import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.myth_fact.Myths_Facts_new;
import devatech.new_task.About;
import devatech.new_task.BBI;
import devatech.new_task.BC;
import devatech.new_task.PinkConnection;
import devatech.new_task.Video;
import devatech.notification.AlarmManagerCustom;

import static devatech.kims.Baseconfig.GetIdFromString;

public class Task_Navigation extends AppCompatActivity {

    //*********************************************************************************************

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    ImageView exit;

    Spinner spn;

    Fragment fragment = null;
    Class fragmentClass;


    Bundle b;
    String Id = "";

    String GAME = "";

    Dialog overlayInfo;

    //*********************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        /*******
         * @Nabeel Ahamed
         * Date: 15.2.2017
         * Description: Daily Repeating math fact notification
         * *******/

        try {
            new AlarmManagerCustom().setRepeatedNotification(Task_Navigation.this);
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Task_Navigation", e.toString());
        }


        try
        {


            loadSavedPreferences1();

            LoadCurrentLanguage();

            GetInitialize(savedInstanceState);

            Controllisteners();


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Task_Navigation", e.toString());
        }

    }

    //**********************************************************************************************

    public void LoadCurrentLanguage() {
        String Lang = Baseconfig.GetIdFromString("select Language as dvalue from Bind_Settings", Task_Navigation.this);
        Baseconfig.Language = Lang;
        Log.e("Current Language**********: ", Baseconfig.Language);
        Log.e("Current Language**********: ", Baseconfig.Language);
        Log.e("Current Language**********: ", Baseconfig.Language);
    }

    //**********************************************************************************************
    AlertDialog.Builder builder;// = new AlertDialog.Builder(Task_Navigation.this);

    public void ShowLanguages() {

        builder = new AlertDialog.Builder(Task_Navigation.this);

        final String[] items = getResources().getStringArray(R.array.languages1);

        builder.setCancelable(false);
        builder.setTitle("Choose Your Preferred Language");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                //Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();

                dialog.dismiss();


                if (!items[item].toString().equalsIgnoreCase(Baseconfig.Language)) {

                    String Lan = items[item].toString();
                    String LanId = GetIdFromString("select ServerId as dvalue from Mstr_Language where Language='" + Lan + "'", Task_Navigation.this);

                    boolean q = Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_Settings", Task_Navigation.this);

                    SQLiteDatabase db = Baseconfig.GetDb(Task_Navigation.this);

                    /*if (q)
                    {
                        String UpdateQuery = "update Bind_Settings set Language='" + Lan + "', LanguageID='" + LanId + "'";
                        db.execSQL(UpdateQuery);
                        Log.e("Settings Update", UpdateQuery);

                    } else {
*/

                    String InsertQuery = "Insert into Bind_Settings (Language,LanguageID,IsUpdate,IsActive) values ('" + Lan + "','" + LanId + "',1,1)";
                    db.execSQL(InsertQuery);
                    Log.e("Settings Insert", InsertQuery);
                    Log.e("Settings Insert", InsertQuery);
                    Log.e("Settings Insert", InsertQuery);
                    // }


                    db.close();


                    //Toast.makeText(Task_Navigation.this, "Language Updated Successfully..", Toast.LENGTH_SHORT).show();

                    SaveLaguagePreference(Lan);
                    Baseconfig.Language = Lan;

                    ShowDialog();//about 2 page

                    savePreferences("CheckBox_Value1", true);
                }

            }
        });


    }
    //**********************************************************************************************

    public void ShowDialog() {

        /**
         * Developer name: N.Muthukumar Overlay first time screen for
         * registration
         */
        overlayInfo = new Dialog(Task_Navigation.this);
        overlayInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overlayInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        overlayInfo.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        overlayInfo.setContentView(R.layout.showcase_dialog);
        overlayInfo.setCancelable(false);

        WebView webvw;
        Button okbutton = (Button) overlayInfo.findViewById(R.id.button);

        webvw = (WebView) overlayInfo.findViewById(R.id.webvw);
        webvw.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webvw.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        webvw.setLongClickable(false);

        LoadWebview(webvw);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                overlayInfo.cancel();


                // check if you are connected or not
                if (Baseconfig.isConnected(Task_Navigation.this)) {


                    savePreferences("CheckBox_Value", true);

                    GetUserId();//Avantari - API

                } else {
                    //Baseconfig.SweetDialgos(4, Task_Navigation.this, "Information", "No Internet Connection Available\nTry Again Later", "OK");

                    new SweetAlertDialog(Task_Navigation.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(Task_Navigation.this.getString(R.string.info))
                            .setContentText("No Internet Connection Available\nGet connected with internet to stay updated with latest Myth / Fact")
                            .setConfirmText(Task_Navigation.this.getString(R.string.ok))
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();

                                    Task_Navigation.this.finish();
                                    Intent intent = new Intent(Task_Navigation.this, Task_Navigation.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    Baseconfig.setLocale(Baseconfig.Language, Task_Navigation.this, null);
                                }
                            })
                            .show();

                }


            }
        });


        loadSavedPreferences();

    }
    //**********************************************************************************************

    /**
     * CONTACTING BHI - SERVER
     * TO GET THE NEW USER ID FOR THE USER
     * MUTHUKUMAR N
     * 01/02/2017
     */

    public void GetUserId() {
        try {

            new HttpAsyncTask2().execute(Baseconfig.URL_USERID);
        } catch (Exception e) {
            Log.e("ERROR: ", "GETTING USERID FROM URL");
            e.printStackTrace();
        }

    }

    //**********************************************************************************************


    /**
     * POST DATA TO BHI - SERVER
     * Inserting New User
     */
    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {

        Dialog builderDialog;


        @Override
        protected String doInBackground(String... urls) {


            return POST(urls[0]);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builderDialog = Baseconfig.showCustomDialog(Task_Navigation.this.getString(R.string.please_wait), "Loading..", Task_Navigation.this);

            builderDialog.show();
        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            // Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();


            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }

            //Toast.makeText(getBaseContext(), "Received!"+result, Toast.LENGTH_LONG).show();

            Log.e("BHI - Server: ", result);


            InsertUserInfo(result);


        }
    }
    //**********************************************************************************************

/*
    private boolean validate()
    {
        if(etName.getText().toString().trim().equals(""))
            return false;
        else if(etCountry.getText().toString().trim().equals(""))
            return false;
        else if(etTwitter.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }
*/


    public void InsertUserInfo(String response) {

        try {


            // "user_id":"ciymvsj4a00020km9g4puwpu8"


            JSONObject obj = new JSONObject(response);

            String UserId = obj.getString("user_id");


            SQLiteDatabase db = Baseconfig.GetDb(Task_Navigation.this);

            db.execSQL("Insert into Bind_DeviceInfo(UserId,ActDate) values ('" + UserId.toString() + "','" + Baseconfig.getCurrentDateTime() + "');");

            db.close();


            Task_Navigation.this.finish();
            Intent intent = new Intent(Task_Navigation.this, Task_Navigation.class);
            intent.putExtra("GAME", "1");
            intent.putExtra("Id", "");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Baseconfig.setLocale(Baseconfig.Language, Task_Navigation.this, null);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Task_Navigation", e.toString());
        }
    }

    //**********************************************************************************************


    public void LoadWebview(WebView webvw) {


        webvw.getSettings().setJavaScriptEnabled(true);
        String data = getResources().getString(R.string.txt_about);
        //webvw.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
        //webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/about.html");


        webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webvw.getSettings().setDefaultTextEncodingName("utf-8");

        webvw.setWebChromeClient(new WebChromeClient() {

        });

        webvw.setBackgroundColor(0x00000000);
        webvw.setVerticalScrollBarEnabled(true);
        webvw.setHorizontalScrollBarEnabled(true);


        switch (Baseconfig.Language) {


            case "অসমিয়া"://Assamese

                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Assamese_2.html"); //Loading from  assets

                break;

            case "বাঙালি"://Bengali*

                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Bengali_2.html"); //Loading from  assets

                break;

            case "English"://English*

                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_English_2.html"); //Loading from  assets

                break;

            case "ગુજરાતી"://Gujrathi

                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Gujrathi_2.html"); //Loading from  assets

                break;

            case "हिंदी"://Hindi*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Hindi_2.html"); //Loading from  assets

                break;

            case "ಕನ್ನಡ"://Kannada
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Kannada_2.html"); //Loading from  assets

                break;

            case "മലയാളം"://Malayalam
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Malayalam_2.html"); //Loading from  assets

                break;

            case "मराठी"://Marathi*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Marathi_2.html"); //Loading from  assets

                break;

            case "ଓଡ଼ିଆ"://Oriya
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Oriya_2.html"); //Loading from  assets

                break;

            case "ਪੰਜਾਬੀ ਦੇ"://Punjabi
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Punjabi_2.html"); //Loading from  assets

                break;

            case "தமிழ்"://Tamil
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Tamil_2.html"); //Loading from  assets

                break;

            case "తెలుగు"://Telgu*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Telugu_2.html"); //Loading from  assets

                break;


        }

        // webvw.setBackgroundColor(0x00000000);
        webvw.setVerticalScrollBarEnabled(true);
        // webvw.setHorizontalScrollBarEnabled(true);

    }

    //**********************************************************************************************

    void loadSavedPreferences() {
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Task_Navigation.this);

        boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value",
                false);

        if (!checkBoxValue) {
            overlayInfo.show();
        } else {
            overlayInfo.cancel();
        }

    }

    SharedPreferences sharedPreferences;

    void loadSavedPreferences1() {
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(Task_Navigation.this);

        boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value1",
                false);

        if (!checkBoxValue) {

            ShowLanguages();

            AlertDialog alert = builder.create();
            alert.show();

        } else {


            boolean f = Baseconfig.LoadRegisterBooleanStatus("select Id as dstatus1 from Bind_DeviceInfo limit 1", Task_Navigation.this);

            Log.e("Bind_DeviceInfo Status: ", String.valueOf(f));
            Log.e("Bind_DeviceInfo Status: ", String.valueOf(f));

            if (!f) {
                // check if you are connected or not
                if (Baseconfig.isConnected(Task_Navigation.this)) {

                    GetUserId();//Avantari - API
                    Log.e("User Registration New: ", "Calling GetUserId");

                }
            }


        }

    }

    //**********************************************************************************************

    void savePreferences(String key, boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Task_Navigation.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }


    //*********************************************************************************************

    public void GetInitialize(Bundle savedInstanceState) {


        try {

            b = getIntent().getExtras();

            if (b == null) {
                Id = "";
                GAME = "";
            } else {
                Id = b.getString("Id");
                GAME = b.getString("GAME");

            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Task_Navigation", e.toString());
        }


        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        //View header = navigationView.getHeaderView(0);

        spn = (Spinner) toolbar.findViewById(R.id.spinner2);

        //spn.setEnabled(false);

        LoadLanguages();

        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);


        boolean q = Baseconfig.LoadReportsBooleanStatus("select Language as dstatus1 from Bind_Settings", Task_Navigation.this);

        if (q == true) {
            //spn.setSelection(2);
            for (int i = 0; i < spn.getCount(); i++) {
                if (GetIdFromString("select Language as dvalue from Bind_Settings", Task_Navigation.this).equals(spn.getItemAtPosition(i))) {
                    spn.setSelection(i);
                    break;
                } else {
                  //  spn.setSelection(1);
                }

            }


        } else {
            // spn.setSelection(1);
        }


        // Setup drawer view
        setupDrawerContent(navigationView);

        if(Baseconfig.Language.toString().equalsIgnoreCase("অসমিয়া"))//Assamese
        {
            UpdateMenuTitle_Assamese();

        }
        else if(Baseconfig.Language.toString().equalsIgnoreCase("ଓଡ଼ିଆ"))//Oriya
        {
            UpdateMenuTitle_Oriya();

        }


    }


    //*********************************************************************************************

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.


        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }




        return super.onOptionsItemSelected(item);
    }

    //*********************************************************************************************

    private void setupDrawerContent(NavigationView navigationView) {


        //navigationView.set

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        selectDrawerItem(menuItem);



                        return true;
                    }
                });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(Task_Navigation.this, drawerLayout, toolbar, 0, 0) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //  //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();



        // selectDrawerItem(navigationView.getMenu().getItem(0));//Old dashboard
        //******************************************************************************************


        //if (Baseconfig.Language.length() > 0) {
        if (Baseconfig.isConnected(Task_Navigation.this) && GAME.toString().equalsIgnoreCase("1")) {

            selectDrawerItem(navigationView.getMenu().getItem(4));//Myths & Facts New Intent
            navigationView.getMenu().getItem(4).setChecked(true);


        } else {

            selectDrawerItem(navigationView.getMenu().getItem(0));//Home Page
            navigationView.getMenu().getItem(0).setChecked(true);



        }
        //}

        //******************************************************************************************

        switch (Id) {
            case "BC":
                selectDrawerItem(navigationView.getMenu().getItem(2));

                break;


            case "BBI":
                selectDrawerItem(navigationView.getMenu().getItem(3));

                break;

            case "PINK":
                selectDrawerItem(navigationView.getMenu().getItem(5));

                break;

        }


    }
    //*********************************************************************************************
    public void UpdateMenuTitle_Assamese()
    {
        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_0 = menu.findItem(R.id.items0);
        MenuItem nav_1 = menu.findItem(R.id.items1);
        MenuItem nav_2 = menu.findItem(R.id.items2);
        MenuItem nav_3 = menu.findItem(R.id.items3);
        MenuItem nav_4 = menu.findItem(R.id.items4);
        MenuItem nav_5 = menu.findItem(R.id.items5);
        MenuItem nav_6 = menu.findItem(R.id.items6);

        // set new title to the MenuItem
        nav_0.setTitle("হোম");
        nav_1.setTitle("বিষয়");
        nav_2.setTitle("স্তনৰ কৰ্কটৰোগ");
        nav_3.setTitle("স্তনৰ নিৰোগী কোষকলা");
        nav_4.setTitle("কল্পিত ধাৰণা আৰু সত্যতা");
        nav_5.setTitle("গুলপীয়া সংযোগ");
        nav_6.setTitle("জীৱন ব্যাপী সজাগতা");

    }
    //*********************************************************************************************
    public void UpdateMenuTitle_Oriya()
    {

        // get menu from navigationView
        Menu menu = navigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_0 = menu.findItem(R.id.items0);
        MenuItem nav_1 = menu.findItem(R.id.items1);
        MenuItem nav_2 = menu.findItem(R.id.items2);
        MenuItem nav_3 = menu.findItem(R.id.items3);
        MenuItem nav_4 = menu.findItem(R.id.items4);
        MenuItem nav_5 = menu.findItem(R.id.items5);
        MenuItem nav_6 = menu.findItem(R.id.items6);

        // set new title to the MenuItem
        nav_0.setTitle("ହୋମ");
        nav_1.setTitle("ବିଷୟରେ");
        nav_2.setTitle("ସ୍ତନ କର୍କଟ");
        nav_3.setTitle("ବେନିନ ସ୍ତନ ସମସ୍ୟା");
        nav_4.setTitle("କଳ୍ପନା ଓ ବାସ୍ତବ");
        nav_5.setTitle("ଗୋଲାପୀ ଯୋଗାଯୋଗ");
        nav_6.setTitle("ଜୀବନ ପ୍ରତି ସଚେତନତା");


    }
    //*********************************************************************************************
    public void selectDrawerItem(MenuItem menuItem) {


        switch (menuItem.getItemId()) {

            case R.id.items0:

                // fragmentClass = DashboardNew.class;
                onSectionAttached(0, menuItem);


                break;

            case R.id.items1:

                // fragmentClass = About.class;
                onSectionAttached(1, menuItem);

                break;

            case R.id.items2:

                // fragmentClass = BC.class;
                onSectionAttached(2, menuItem);

                break;

            case R.id.items3:

                // fragmentClass = BC_ReadMore.class;
                onSectionAttached(3, menuItem);

                break;

            case R.id.items4:
                onSectionAttached(4, menuItem);

                //  fragmentClass = BBI.class;

                break;

            case R.id.items5:
                onSectionAttached(5, menuItem);

                // fragmentClass = PinkConnection.class;

                break;

            case R.id.items6:
                onSectionAttached(6, menuItem);

                // fragmentClass = PinkConnection.class;

                break;

        }


    }

    //*********************************************************************************************

    public void onSectionAttached(int number, MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        FragmentManager fragmentManager;

        switch (number) {
            case 0:


                fragmentClass = DashboardNew.class;
                try {


                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                    FirebaseCrash.logcat(Log.ERROR, "Task_Navigation", e.toString());
                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();


                break;
            case 1:
                // fragmentClass = About.class;

                Task_Navigation.this.finish();
                Intent intent1 = new Intent(Task_Navigation.this, About.class);
                Task_Navigation.this.startActivity(intent1);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                break;
            case 2:
                // fragmentClass = BC.class;
                Task_Navigation.this.finish();
                Intent intent2 = new Intent(Task_Navigation.this, BC.class);
                Task_Navigation.this.startActivity(intent2);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                break;
            case 3:

                // fragmentClass = BBI.class;
                Task_Navigation.this.finish();
                Intent intent4 = new Intent(Task_Navigation.this, BBI.class);
                Task_Navigation.this.startActivity(intent4);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);


                break;
            case 4:

               /* Task_Navigation.this.finish();
                Intent intent7 = new Intent(Task_Navigation.this, BC_ReadMore.class);
                intent7.putExtra("Id", "");
                intent7.putExtra("Lang", Baseconfig.Language);
                intent7.putExtra("Flag", "FromMenu");
                intent7.putExtra("File", "BREAST HEALTH, 4");
                Task_Navigation.this.startActivity(intent7);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
*/
                fragmentClass = Myths_Facts_new.class;

                try {


                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                    FirebaseCrash.logcat(Log.ERROR, "Task_Navigation", e.toString());
                }

                // Insert the fragment by replacing any existing fragment
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                //--Need to change

                break;
            case 5:
                // fragmentClass = PinkConnection.class;
                Task_Navigation.this.finish();
                Intent intent5 = new Intent(Task_Navigation.this, PinkConnection.class);
                Task_Navigation.this.startActivity(intent5);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);


                break;

            case 6:
                // fragmentClass = PinkConnection.class;
                Task_Navigation.this.finish();
                Intent intent6 = new Intent(Task_Navigation.this, Video.class);
                Task_Navigation.this.startActivity(intent6);
                Task_Navigation.this.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);


                break;


        }


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        //setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();


    }
    //*********************************************************************************************


    public void LoadLanguages() {


        List<String> myArrayList = Arrays.asList(getResources().getStringArray(R.array.languages));

        ArrayList listOfStrings = new ArrayList<>(myArrayList.size());

        listOfStrings.addAll(myArrayList);

        CustomSpinnerAdapter customSpinnerAdapter = new CustomSpinnerAdapter(Task_Navigation.this, listOfStrings);
        spn.setAdapter(customSpinnerAdapter);


    }
    //*********************************************************************************************

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr = asr;
            activity = context;
        }


        public int getCount() {
            return asr.size();
        }

        public Object getItem(int i) {
            return asr.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(activity);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);

            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#c7007f"));
            return txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(activity);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(3, 3, 3, 3);
            //txt.setTextSize(16);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#c7007f"));
            return txt;
        }

    }

    //* ******************************************************************************************************


    public void Controllisteners() {


        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String items = spn.getSelectedItem().toString();

                Log.e("Selected item : ", items);

                Log.e("Select Langauges: ", Baseconfig.Language.toString());

                //if (Baseconfig.Language.length() > 0)
                {
                    if (spn.getSelectedItemPosition()>0 && spn != null && !items.toString().equalsIgnoreCase(Baseconfig.Language)) {

                        String Lan = spn.getSelectedItem().toString();
                        String LanId = GetIdFromString("select ServerId as dvalue from Mstr_Language where Language='" + Lan + "'", Task_Navigation.this);

                        Log.e("Select Langauges: ", Baseconfig.Language.toString());
                        Log.e("Select Langauges: ", Baseconfig.Language.toString());

                        SQLiteDatabase db = Baseconfig.GetDb(Task_Navigation.this);

                        boolean q = Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_Settings", Task_Navigation.this);

                        if (!q) {

                            String InsertQuery = "Insert into Bind_Settings (Language,LanguageID,IsUpdate,IsActive) values ('" + Lan + "','" + LanId + "',1,1)";
                            db.execSQL(InsertQuery);

                        } else {
                            String Query = "update Bind_Settings set Language='" + Lan + "', LanguageID='" + LanId + "'";
                            db.execSQL(Query);
                            Log.e("Settings Update", Query);
                        }


                        db.close();


                        Toast.makeText(Task_Navigation.this, "Language Updated Successfully..", Toast.LENGTH_SHORT).show();

                        SaveLaguagePreference(Lan);
                        Baseconfig.Language = Lan;

                        Task_Navigation.this.finish();
                        Intent intent = new Intent(Task_Navigation.this, Task_Navigation.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        Baseconfig.setLocale(Baseconfig.Language, Task_Navigation.this, null);


                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText( getContext(),"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(Task_Navigation.this, Task_Navigation.class);


            }
        });

    }


    @Override
    public void onBackPressed() {


        selectDrawerItem(navigationView.getMenu().getItem(0));


        // super.onBackPressed();
        // Baseconfig.ExitSweetDialog(Task_Navigation.this, Task_Navigation.class);
   /*     fragmentClass=Task_Navigation.class;
        try
        {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = Task_Navigation.this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();*/

    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    public void SaveLaguagePreference(String LanguageCode) {
        String language_code = LanguageCode.toString();

        savePreferences("LANGUAGE_CODE", language_code.toString());

    }
    //************************************************************************************
    /*
     * Remeber Me Using Shared Preference
	 */

    private void savePreferences(String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Task_Navigation.this);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }


    //*******************************************************************************

    /**
     * HTTP Avantari
     * GET & POST METHOD
     *
     * @param url
     * @return
     * @Muthukumar 01/2/2017
     */
    public static String POST(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
           /* jsonObject.accumulate("name", person.getName());
            jsonObject.accumulate("country", person.getCountry());
            jsonObject.accumulate("twitter", person.getTwitter());
*/
            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            //httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = Baseconfig.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;


    }



    //*******************************************************************************


}
