package devatech.new_task;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.crash.FirebaseCrash;

import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 9/17/2016.
 */
public class BC_ReadMore extends AppCompatActivity {


    WebView webvw;
    ImageView exit, back;
    Toolbar toolbar;

    Bundle b;
    String Id;
    String Flag,File;

    private ImageView imageView;
    private ImageLoader imageLoader;

    //**********************************************************************************************

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_readmore);

        try
        {
            GetInitialize();
            Controllisterners();

          /*  if (savedInstanceState != null)
                ((WebView)findViewById(R.id.webvw)).restoreState(savedInstanceState);
*/

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC_ReadMore", e.toString());
        }


    }


    //**********************************************************************************************

    public void GetInitialize()
    {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
        back = (ImageView) toolbar.findViewById(R.id.ic_back);

        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Baseconfig.LoadLanguage(BC_ReadMore.this, Baseconfig.Language, BC_ReadMore.this);

        Flag="";

        try
        {

            b = getIntent().getExtras();

            if (b == null) {
                Id = null;
                Flag = "";
            } else {
                Id = b.getString("Id");
                Flag= b.getString("Flag");
                File= b.getString("File");

            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC_ReadMore", e.toString());
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        webvw = (WebView) findViewById(R.id.webvw);

        webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webvw.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webvw.setLongClickable(false);

        LoadWebview(Id,File);

    }


    //**********************************************************************************************

    public void Controllisterners() {

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(BC_ReadMore.this, null);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Flag==null || Flag.toString().equalsIgnoreCase(""))
                {
                    BC_ReadMore.this.finish();
                    Intent about = new Intent(BC_ReadMore.this, Task_Navigation.class);
                    about.putExtra("Id", "BC");
                    about.putExtra("GAME","");
                    startActivity(about);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

                }
                else if(Flag.toString().equalsIgnoreCase("FromMenu"))
                {

                    BC_ReadMore.this.finish();
                    Intent about = new Intent(BC_ReadMore.this, Task_Navigation.class);
                   // about.putExtra("Id", "BC");
                    startActivity(about);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

                }

            }
        });

    }

    //**********************************************************************************************

    public void LoadWebview(String Id,String File) {

        try
        {

            webvw.getSettings().setJavaScriptEnabled(true);
            String data;// = getResources().getString(R.string.txt_about);
            data = GetHtmlSrc(Id,File);

            //webvw.loadDataWithBaseURL("", data, "text/html", "UTF-8", ""); //Loading from database

        /*    String style="<style type=\"text/css\">@font-face{font-family: MyFont1;src: url(\"file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/fonts/hindi.ttf\")}body{font-family: MyFont1;font-size: large;text-align: justify;}</style>";
            String start = "<html><head><meta http-equiv='Content-Type' content='text/html' charset='UTF-8' />  "+style+" </head><body>";
            String end = "</body></html>";
            String htmlStr=start + data + end;*/
            webvw.getSettings().setDefaultTextEncodingName("utf-8");

            webvw.setWebChromeClient(new WebChromeClient() {
            });

            //("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-HINDI/" + data + ".html"); //Loading from  assets

            switch (Baseconfig.Language) {

                case "অসমিয়া"://Assamese
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-ASSAMESE/" + data + ".html"); //Loading from  assets

                    break;

                case "বাঙালি"://Bengali*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-BENGALI/" + data + ".html"); //Loading from  assets

                    break;

                case "English"://English*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-ENGLISH/" + data + ".html"); //Loading from  assets

                    break;

                case "ગુજરાતી"://Gujrathi
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-GUJRATHI/" + data + ".html"); //Loading from  assets

                    break;

                case "हिंदी"://Hindi*

                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-HINDI/" + data + ".html"); //Loading from  assets

                    break;

                case "ಕನ್ನಡ"://Kannada
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-KANNADA/" + data + ".html"); //Loading from  assets

                    break;

                case "മലയാളം"://Malayalam
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-MALAYALAM/" + data + ".html"); //Loading from  assets

                    break;

                case "मराठी"://Marathi*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-MARATHI/" + data + ".html"); //Loading from  assets

                    break;

                case "ଓଡ଼ିଆ"://Oriya
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-ORIYA/" + data + ".html"); //Loading from  assets

                    break;

                case "ਪੰਜਾਬੀ ਦੇ"://Punjabi

                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-PUNJABI/" + data + ".html"); //Loading from  assets

                    break;

                case "தமிழ்"://Tamil
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-TAMIL/" + data + ".html"); //Loading from  assets

                    break;

                case "తెలుగు"://Telgu*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-TELUGU/" + data + ".html"); //Loading from  assets

                    break;


            }

            webvw.setBackgroundColor(0x00000000);
            webvw.setVerticalScrollBarEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC_ReadMore", e.toString());
        }

    }

    @Override
    public void onBackPressed() {

        if(Flag==null || Flag.toString().equalsIgnoreCase(""))
        {
            BC_ReadMore.this.finish();
            Intent about = new Intent(BC_ReadMore.this, Task_Navigation.class);
            about.putExtra("Id", "BC");
            about.putExtra("GAME","");
            startActivity(about);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

        }
        else if(Flag.toString().equalsIgnoreCase("FromMenu"))
        {

            BC_ReadMore.this.finish();
            Intent about = new Intent(BC_ReadMore.this, Task_Navigation.class);
           // about.putExtra("Id", "BC");
            startActivity(about);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

        }


    }

    //**********************************************************************************************

    public String GetHtmlSrc(String Id,String HtmlFile) {
        try {
            String data = "", imgurl = "", FileName = "";


            SQLiteDatabase db = Baseconfig.GetDb(BC_ReadMore.this);
            String Query = "select ImgURL,HtmlSrc from Bind_BC where Id='" + Id + "' or HtmlSrc='"+HtmlFile+"' and Language='"+Baseconfig.Language+"'";
            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {

                    do {

                        data = c.getString(c.getColumnIndex("HtmlSrc"));

                        //imgurl = c.getString(c.getColumnIndex("ImgURL"));

                       /* imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
                        imageLoader.get(imgurl, ImageLoader.getImageListener(imageView, R.drawable.ic_loadinglogo, R.drawable.ic_action_report_problem));
                        imageView.setImageUrl(imgurl, imageLoader);
*/

                        SetImageFromDrawable(data, imageView);

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return data;


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC_ReadMore", e.toString());
        }
        return null;
    }


//**********************************************************************************************

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                this.finish();
                Intent taskselect = new Intent(BC_ReadMore.this, BC.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


                return true;
        }

        return super.onOptionsItemSelected(item);

    }*/
    //**********************************************************************************************

    public void SetImageFromDrawable(String FileName, ImageView img) {

        switch (FileName) {


            case "BREAST HEALTH, 1":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc1));

                break;

            case "BREAST HEALTH, 2":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc2));

                break;

            case "BREAST HEALTH, 3":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc3));

                break;

            case "BREAST HEALTH, 4":

                switch (Baseconfig.Language)
                {


                    case "অসমিয়া"://Assamese
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_assamese));

                        break;

                    case "বাঙালি"://Bengali*
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_bengali));

                        break;

                    case "English"://English*
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3));

                        break;

                    case "ગુજરાતી"://Gujrathi
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_gujrathi));

                        break;

                    case "हिंदी"://Hindi*
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_hindi));

                        break;

                    case "ಕನ್ನಡ"://Kannada
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_kannada));

                        break;

                    case "മലയാളം"://Malayalam
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_malayalam));

                        break;

                    case "मराठी"://Marathi*
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_marathi));

                        break;

                    case "ଓଡ଼ିଆ"://Oriya
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_oriya));

                        break;

                    case "ਪੰਜਾਬੀ ਦੇ"://Punjabi
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_punjabi));

                        break;

                    case "தமிழ்"://Tamil
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_tamil));

                        break;

                    case "తెలుగు"://Telgu*
                        img.setImageDrawable(getResources().getDrawable(R.drawable.d3_telugu));

                        break;



                    default:
                        img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc4));
                        break;

                }



                break;

            case "BREAST HEALTH, 5":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc5));

                break;

            case "BREAST HEALTH, 6":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc6));

                break;

            case "BREAST HEALTH, 7":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc7));

                break;

            case "BREAST HEALTH, 8":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc8));

                break;

            case "BREAST HEALTH, 9":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc9));

                break;

            case "BREAST HEALTH, 10":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc10));

                break;

            case "BREAST HEALTH, 11":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc11));


                break;

            case "BREAST HEALTH, 12":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc12));


                break;

            case "BREAST HEALTH, 13":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc13));


                break;

            case "BREAST HEALTH, 14":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc14));


                break;

            case "BREAST HEALTH, 15":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc15));


                break;

            case "BREAST HEALTH, 16":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc16));


                break;

            case "BREAST HEALTH, 17":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc17));


                break;

            case "BREAST HEALTH, 18":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc18));


                break;

            case "BREAST HEALTH, 19":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc19));


                break;

            case "BREAST HEALTH, 20":

                img.setImageDrawable(getResources().getDrawable(R.drawable.maligant));


                break;

        }

    }


    @Override
    public void onPause() {
        super.onPause();
        webvw.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        webvw.onResume();
    }
    //**********************************************************************************************


}
