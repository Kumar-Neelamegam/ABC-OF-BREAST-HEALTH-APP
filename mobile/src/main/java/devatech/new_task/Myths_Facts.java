package devatech.new_task;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.google.firebase.crash.FirebaseCrash;

import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 9/17/2016.
 */
public class Myths_Facts extends AppCompatActivity {


    WebView webvw;

    Bundle b;
    String Id;
    String Flag;

    private ImageView imageView;
    private ImageLoader imageLoader;


    //**********************************************************************************************

   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.act_myths, container, false);

        try {
            GetInitialize(v);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return v;

    }*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_myths);

        try {
           // GetInitialize();
           // Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myths&Facts", e.toString());
        }


    }

    //**********************************************************************************************

    public void GetInitialize(View v) {

        imageView = (ImageView) v.findViewById(R.id.imageView);
        webvw = (WebView) v.findViewById(R.id.webvw);

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

    public void LoadWebview(String Id) {

        try {

            webvw.getSettings().setJavaScriptEnabled(true);
            String data;// = getResources().getString(R.string.txt_about);
            data = GetHtmlSrc(Id);

            //webvw.loadDataWithBaseURL("", data, "text/html", "UTF-8", ""); //Loading from database

        /*    String style="<style type=\"text/css\">@font-face{font-family: MyFont1;src: url(\"file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/fonts/hindi.ttf\")}body{font-family: MyFont1;font-size: large;text-align: justify;}</style>";
            String start = "<html><head><meta http-equiv='Content-Type' content='text/html' charset='UTF-8' />  "+style+" </head><body>";
            String end = "</body></html>";
            String htmlStr=start + data + end;*/
            webvw.getSettings().setDefaultTextEncodingName("utf-8");

            webvw.setWebChromeClient(new WebChromeClient() {
            });

            switch (Baseconfig.Language) {


                case "বাঙালি"://Bengali   - বাঙালি
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-BENGALI/" + data + ".html"); //Loading from  assets
                    break;


                case "English"://English   - English
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-ENGLISH/" + data + ".html"); //Loading from  assets
                    break;


                case "ગુજરાતી"://Gujrathi  - ગુજરાતી
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-GUJRATHI/" + data + ".html"); //Loading from  assets

                    break;


                case "हिंदी"://Hindi     - हिंदी
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-HINDI/" + data + ".html"); //Loading from  assets

                    break;


                case "ಕನ್ನಡ"://Kannada   - ಕನ್ನಡ
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-KANNADA/" + data + ".html"); //Loading from  assets

                    break;


                case "മലയാളം"://Malayalam - മലയാളം
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-MALAYALAM/" + data + ".html"); //Loading from  assets

                    break;


                case "मराठी"://Marathi   - मराठी
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-MARATHI/" + data + ".html"); //Loading from  assets

                    break;


                case "ନୀୟ"://Oriya     - ନୀୟ
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-ORIYA/" + data + ".html"); //Loading from  assets

                    break;


                case "ਪੰਜਾਬੀ ਦੇ"://Punjabi   - ਪੰਜਾਬੀ ਦੇ
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-PUNJABI/" + data + ".html"); //Loading from  assets

                    break;


                case "தமிழ்"://Tamil     - தமிழ்
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-TAMIL/" + data + ".html"); //Loading from  assets

                    break;


                case "తెలుగు"://Telugu    - తెలుగు
                    webvw.loadUrl("file://"+ Environment.getExternalStorageDirectory()+"/.KIMS/BC-TELGU/" + data + ".html"); //Loading from  assets

                    break;


            }

            webvw.setBackgroundColor(0x00000000);
            webvw.setVerticalScrollBarEnabled(true);
            //  webvw.setHorizontalScrollBarEnabled(true);


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myths&Facts", e.toString());
        }

    }



    //**********************************************************************************************

    public String GetHtmlSrc(String Id) {
        try {
            String data = "", imgurl = "", FileName = "";


            SQLiteDatabase db = Baseconfig.GetDb(Myths_Facts.this);
            String Query = "select ImgURL,HtmlSrc from Bind_BC where Id='4'";
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
            FirebaseCrash.logcat(Log.ERROR, "Myths&Facts", e.toString());
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
                img.setImageDrawable(getResources().getDrawable(R.drawable.img_bc4));


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
