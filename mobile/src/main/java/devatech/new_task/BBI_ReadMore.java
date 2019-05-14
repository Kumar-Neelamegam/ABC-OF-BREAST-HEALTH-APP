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
public class BBI_ReadMore extends AppCompatActivity {


    WebView webvw;
    ImageView exit,back;
    Toolbar toolbar;

    Bundle b;
    String Id;

    private ImageView imageView;
    private ImageLoader imageLoader;

    //**********************************************************************************************



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_readmore);

        try {
            GetInitialize();
            Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI_ReadMore", e.toString());
        }


    }

    //**********************************************************************************************

    public void GetInitialize() {



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
        back=(ImageView)toolbar.findViewById(R.id.ic_back);


        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Baseconfig.LoadLanguage(BBI_ReadMore.this, Baseconfig.Language, BBI_ReadMore.this);

        try {

            b = getIntent().getExtras();

            if (b == null) {
                Id = null;
            } else {
                Id = b.getString("Id");


            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI_ReadMore", e.toString());
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

        LoadWebview(Id);




    }


    //**********************************************************************************************

    public void Controllisterners() {

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(BBI_ReadMore.this, null);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BBI_ReadMore.this.finish();
                Intent taskselect = new Intent(BBI_ReadMore.this, Task_Navigation.class);
                taskselect.putExtra("Id", "BBI");
                taskselect.putExtra("GAME","");
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


            }
        });

    }

    //**********************************************************************************************

    public void LoadWebview(String Id) {

        try {

            webvw.getSettings().setJavaScriptEnabled(true);
            String data;// = getResources().getString(R.string.txt_about);
            data = GetHtmlSrc(Id);

            //webvw.loadDataWithBaseURL("", data, "text/html", "UTF-8", ""); //Loading from database

            webvw.getSettings().setDefaultTextEncodingName("utf-8");

            webvw.setWebChromeClient(new WebChromeClient() {
            });


        /*    String style="<style type=\"text/css\">@font-face{font-family: MyFont1;src: url(\"file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/fonts/hindi.ttf\")}body{font-family: MyFont1;font-size: large;text-align: justify;}</style>";
            String start = "<html><head><meta http-equiv='Content-Type' content='text/html' charset='UTF-8' />  "+style+" </head><body>";
            String end = "</body></html>";
            String htmlStr=start + data + end;*/


            switch (Baseconfig.Language)
            {


                case "অসমিয়া"://Assamese
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-ASSAMESE/" + data + ".html"); //Loading from  assets

                    break;

                case "বাঙালি"://Bengali*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-BENGALI/" + data + ".html"); //Loading from  assets

                    break;

                case "English"://English*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-ENGLISH/" + data + ".html"); //Loading from  assets

                    break;

                case "ગુજરાતી"://Gujrathi
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-GUJRATHI/" + data + ".html"); //Loading from  assets

                    break;

                case "हिंदी"://Hindi*

                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-HINDI/" + data + ".html"); //Loading from  assets

                    break;

                case "ಕನ್ನಡ"://Kannada
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-KANNADA/" + data + ".html"); //Loading from  assets

                    break;

                case "മലയാളം"://Malayalam
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-MALAYALAM/" + data + ".html"); //Loading from  assets

                    break;

                case "मराठी"://Marathi*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-MARATHI/" + data + ".html"); //Loading from  assets

                    break;

                case "ଓଡ଼ିଆ"://Oriya
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-ORIYA/" + data + ".html"); //Loading from  assets

                    break;

                case "ਪੰਜਾਬੀ ਦੇ"://Punjabi

                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-PUNJABI/" + data + ".html"); //Loading from  assets

                    break;

                case "தமிழ்"://Tamil
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-TAMIL/" + data + ".html"); //Loading from  assets

                    break;

                case "తెలుగు"://Telgu*
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BBI-TELUGU/" + data + ".html"); //Loading from  assets

                    break;



            }

            webvw.setBackgroundColor(0x00000000);
            webvw.setVerticalScrollBarEnabled(true);
            //webvw.setHorizontalScrollBarEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI_ReadMore", e.toString());
        }

    }

    @Override
    public void onBackPressed() {

        BBI_ReadMore.this.finish();
        Intent about = new Intent(BBI_ReadMore.this, Task_Navigation.class);
        about.putExtra("Id", "BBI");
        about.putExtra("GAME","");
        startActivity(about);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************

    public String GetHtmlSrc(String Id) {
        try {
            String data = "", imgurl = "";


            SQLiteDatabase db = Baseconfig.GetDb(BBI_ReadMore.this);
            String Query = "select ImgURL,HtmlSrc from Bind_BBI where Id='" + Id + "'";
            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {

                    do {

                        data = c.getString(c.getColumnIndex("HtmlSrc"));
                        imgurl = c.getString(c.getColumnIndex("ImgURL"));

                  /*      imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext()).getImageLoader();
                        imageLoader.get(imgurl, ImageLoader.getImageListener(imageView, R.drawable.ic_loadinglogo, R.drawable.ic_action_report_problem));
                        imageView.setImageUrl(imgurl, imageLoader);*/

                        SetImageFromDrawable(data,imageView);


                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return data;
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI_ReadMore", e.toString());
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
                Intent taskselect = new Intent(BBI_ReadMore.this, BBI.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


                return true;
        }

        return super.onOptionsItemSelected(item);

    }*/
    //**********************************************************************************************


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


    public void SetImageFromDrawable(String FileName, ImageView img)
    {

        switch (FileName)
        {
            case "BREAST HEALTH, 1":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi1));

                break;

            case "BREAST HEALTH, 2":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc2));

                break;



            case "BREAST HEALTH, 3":


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

            case "BREAST HEALTH, 4":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi2));

                break;

            case "BREAST HEALTH, 5":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi3));

                break;

            case "BREAST HEALTH, 6":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi4));

                break;

            case "BREAST HEALTH, 7":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi5));

                break;

            case "BREAST HEALTH, 8":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi6));

                break;

            case "BREAST HEALTH, 9":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi7));

                break;

            case "BREAST HEALTH, 10":

                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bbi8));


                break;

            case "BREAST HEALTH, 11":

                img.setImageDrawable(getResources().getDrawable(R.drawable.maligant));


                break;


        }

    }


    //**********************************************************************************************

}
