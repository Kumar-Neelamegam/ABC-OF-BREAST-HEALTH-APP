package devatech.new_task;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;

import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 9/13/2016.
 */
public class About extends AppCompatActivity {


    WebView webvw;
    ImageView exit, back;
    Toolbar toolbar;
    //**********************************************************************************************

   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.act_aboutmore1, container, false);

        try {

            GetInitialize(v);
            Controllisterners(v);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;

    }*/



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aboutmore1);

        try {
            GetInitialize();
            Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "About", e.toString());
        }


    }


    //**********************************************************************************************

    public void GetInitialize() {

        //Baseconfig.LoadLanguage(About.this, Baseconfig.Language, About.this);

        webvw = (WebView) findViewById(R.id.webvw);
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

        LoadWebview();

        //setHasOptionsMenu(true);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
        back = (ImageView) toolbar.findViewById(R.id.ic_back);

        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    //**********************************************************************************************

    public void Controllisterners() {

         exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(About.this, null);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                About.this.finish();
                Intent taskselect = new Intent(About.this, Task_Navigation.class);
                startActivity(taskselect);
                About.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


            }
        });

    }

    //**********************************************************************************************

    public void LoadWebview() {


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

                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Assamese.html"); //Loading from  assets

                break;

            case "বাঙালি"://Bengali*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Bengali.html"); //Loading from  assets

                break;

            case "English"://English*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_English.html"); //Loading from  assets

                break;

            case "ગુજરાતી"://Gujrathi
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Gujrathi.html"); //Loading from  assets

                break;

            case "हिंदी"://Hindi*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Hindi.html"); //Loading from  assets

                break;

            case "ಕನ್ನಡ"://Kannada
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Kannada.html"); //Loading from  assets

                break;

            case "മലയാളം"://Malayalam
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Malayalam.html"); //Loading from  assets

                break;

            case "मराठी"://Marathi*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Marathi.html"); //Loading from  assets

                break;

            case "ଓଡ଼ିଆ"://Oriya
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Oriya.html"); //Loading from  assets

                break;

            case "ਪੰਜਾਬੀ ਦੇ"://Punjabi
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Punjabi.html"); //Loading from  assets

                break;

            case "தமிழ்"://Tamil
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Tamil.html"); //Loading from  assets

                break;

            case "తెలుగు"://Telgu*
                webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/About/About_Telugu.html"); //Loading from  assets

                break;


        }

        // webvw.setBackgroundColor(0x00000000);
        webvw.setVerticalScrollBarEnabled(true);
        // webvw.setHorizontalScrollBarEnabled(true);

    }

    @Override
    public void onBackPressed() {

        About.this.finish();
        Intent about = new Intent(About.this, Task_Navigation.class);
        startActivity(about);
        About.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }




//**********************************************************************************************


  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                this.finish();
                Intent taskselect = new Intent(About.this, Task_Navigation.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


                return true;
        }

        return super.onOptionsItemSelected(item);

    }

*/
    /**********************************************************************************************
     */

}//Ends
