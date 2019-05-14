package devatech.new_task;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 9/17/2016.
 */
public class Inspirational_ReadMore extends AppCompatActivity {


    WebView webvw;
    ImageView exit, back;
    Toolbar toolbar;

    Bundle b;
    int  Id;

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
        }


    }

    //**********************************************************************************************

    public void GetInitialize() {


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
        back = (ImageView) toolbar.findViewById(R.id.ic_back);

        setSupportActionBar(toolbar);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Baseconfig.LoadLanguage(Inspirational_ReadMore.this, Baseconfig.Language, Inspirational_ReadMore.this);

        try {

            b = getIntent().getExtras();

            if (b == null) {
                Id = 0;
            } else {
                Id = b.getInt("Id");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        imageView = (ImageView) findViewById(R.id.imageView);
        webvw = (WebView) findViewById(R.id.webvw);

        webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        LoadWebview(Id);

    }


    //**********************************************************************************************

    public void Controllisterners() {

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(Inspirational_ReadMore.this, null);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Inspirational_ReadMore.this.finish();
                Intent taskselect = new Intent(Inspirational_ReadMore.this, Inspirational.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


            }
        });

    }

    //**********************************************************************************************

    public void LoadWebview(int Id)
    {

        try {

            webvw.getSettings().setJavaScriptEnabled(true);
            String data;// = getResources().getString(R.string.txt_about);
            data = GetHtmlSrc(Id);

            webvw.getSettings().setDefaultTextEncodingName("utf-8");

            webvw.setWebChromeClient(new WebChromeClient() {
            });

            switch (Baseconfig.Language) {


                case "বাঙালি"://Bengali   - বাঙালি
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Bengali/" + data + ".html"); //Loading from  assets
                    break;


                case "English"://English   - English
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/English/" + data + ".html"); //Loading from  assets
                    break;


                case "ગુજરાતી"://Gujrathi  - ગુજરાતી
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Gujrathi/" + data + ".html"); //Loading from  assets

                    break;


                case "हिंदी"://Hindi     - हिंदी
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Hindi/" + data + ".html"); //Loading from  assets

                    break;


                case "ಕನ್ನಡ"://Kannada   - ಕನ್ನಡ
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Kannada/" + data + ".html"); //Loading from  assets

                    break;


                case "മലയാളം"://Malayalam - മലയാളം
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Malayalam/" + data + ".html"); //Loading from  assets

                    break;


                case "मराठी"://Marathi   - मराठी
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Marathi/" + data + ".html"); //Loading from  assets

                    break;


           /*     case "ନୀୟ"://Oriya     - ନୀୟ
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-ORIYA/" + data + ".html"); //Loading from  assets

                    break;*/


                case "ਪੰਜਾਬੀ ਦੇ"://Punjabi   - ਪੰਜਾਬੀ ਦੇ
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Punjabi/" + data + ".html"); //Loading from  assets

                    break;


                case "தமிழ்"://Tamil     - தமிழ்
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Tamil/" + data + ".html"); //Loading from  assets

                    break;


                case "తెలుగు"://Telugu    - తెలుగు
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/Inspirational/Telgu/" + data + ".html"); //Loading from  assets

                    break;

            }

            webvw.setBackgroundColor(0x00000000);
            webvw.setVerticalScrollBarEnabled(true);
            //webvw.setHorizontalScrollBarEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

        Inspirational_ReadMore.this.finish();
        Intent about = new Intent(Inspirational_ReadMore.this, Inspirational.class);
        startActivity(about);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************


    public String GetHtmlSrc(int Id)
    {
        try {
            String data = "";


            SQLiteDatabase db = Baseconfig.GetDb(Inspirational_ReadMore.this);
            String Query = "select FileName from Bind_Inspirational where Id='" + Id + "'";
            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {

                    do {

                        data = c.getString(c.getColumnIndex("FileName"));

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


//**********************************************************************************************


}
