package devatech.new_task;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;

import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.section_adapter.Item;
import devatech.section_adapter.ItemClickListener;
import devatech.section_adapter.Section;
import devatech.section_adapter.SectionedExpandableLayoutHelper;

/**
 * Created by Android on 9/13/2016.
 */
public class PinkConnection extends AppCompatActivity implements ItemClickListener {

    //**********************************************************************************************
    ImageView exit, back;
    Toolbar toolbar;
    private WebView webview;
    private static final String TAG = "Main";
    private ProgressDialog progressBar;


    RecyclerView mRecyclerView;
    SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper;

    ArrayList<Item> arrayList;


    //**********************************************************************************************

   /* public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.act_pinkconnection, container, false);

        try
        {
            GetInitialize(v);
            Controllisterners(v);


            Baseconfig.appendLog("Completion BBI Initialization");

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Baseconfig.appendLog("Benign Breast Issues");
            Baseconfig.appendLog(e.getMessage());

        }

        return v;

    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pinkconnection);

        try {
            GetInitialize();
            Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "PinkConnection", e.toString());
        }


    }

    //**********************************************************************************************

    public void GetInitialize() {

        // Baseconfig.LoadLanguage(PinkConnection.this,Baseconfig.Language,PinkConnection.this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
        back = (ImageView) toolbar.findViewById(R.id.ic_back);


        setSupportActionBar(toolbar);



        /*this.webview = (WebView)findViewById(R.id.webview);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        progressBar = ProgressDialog.show(getActivity(), "Pink connexion august-2016", "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " +url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);

                Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();

             *//*   alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();*//*
            }
        });

         webview.loadUrl("https://issuu.com/www.ubf.org.in/docs/pink_connexion__august_2016/1");
*/


        //setting the recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setNestedScrollingEnabled(false);

         sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(PinkConnection.this, mRecyclerView, (ItemClickListener) this, 2);


      /*  //random data
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList = new ArrayList<>();
        arrayList.add(new Item(getResources().getString(R.string.txt_mnth1), getResources().getString(R.string.txt_volume8), 1, getResources().getDrawable(R.drawable.pc5)));
        arrayList.add(new Item(getResources().getString(R.string.txt_mnth2), getResources().getString(R.string.txt_volume8), 2, getResources().getDrawable(R.drawable.cover_feb_april2017)));
        sectionedExpandableLayoutHelper.addSection(getResources().getString(R.string.year4), arrayList);


        arrayList = new ArrayList<>();
        arrayList.add(new Item(getResources().getString(R.string.txt_mnth2), getResources().getString(R.string.txt_volume1), 3, getResources().getDrawable(R.drawable.cover_feb_april2016)));
        arrayList.add(new Item(getResources().getString(R.string.txt_mnth3), getResources().getString(R.string.txt_volume2), 4, getResources().getDrawable(R.drawable.cover_may_july2016)));
        arrayList.add(new Item(getResources().getString(R.string.txt_mnth4), getResources().getString(R.string.txt_volume7), 5, getResources().getDrawable(R.drawable.cover_aug_oct2016)));
        sectionedExpandableLayoutHelper.addSection(getResources().getString(R.string.year3), arrayList);





        arrayList = new ArrayList<>();
        arrayList.add(new Item(getResources().getString(R.string.txt_mnth4), getResources().getString(R.string.txt_volume6), 10, getResources().getDrawable(R.drawable.cover_aug_oct2014)));
        sectionedExpandableLayoutHelper.addSection(getResources().getString(R.string.year1), arrayList);
*/


        //sectionedExpandableLayoutHelper.notifyDataSetChanged();
        //checking if adding single item works
        //sectionedExpandableLayoutHelper.addItem("Ice cream", new Item("Tutti frutti",5));



        /***********
         * @Ponnusamy
         * Date: 8.2.2017
         * Description: call Pinkconnection method
         * ************/
        viewPinkconnection();

    }

    //**********************************************************************************************

    @Override
    public void itemClicked(Item item) {



      /*  switch(item.getId())
        {

            case 1:


                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Nov - Jan2017.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;

            case 2:


                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Feb - April2017.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;

            case 3:


                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Feb - April2016.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 4:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/May - July2016.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 5:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Aug - Oct2016.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 6:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Feb - April2015.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 7:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/May - July2015.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 8:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Aug - Oct2015.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 9:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Nov - Jan2015.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;



            case 10:
                intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/English/Aug - Oct2014.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);

                break;*/

    }




    @Override
    public void itemClicked(Section section) {
        //  Toast.makeText(this, "Section: " + section.getName() + " clicked", Toast.LENGTH_SHORT).show();
    }

    //**********************************************************************************************

    public void Controllisterners() {

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(PinkConnection.this, null);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PinkConnection.this.finish();
                Intent about = new Intent(PinkConnection.this, Task_Navigation.class);
                startActivity(about);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

            }
        });


    }

    //**********************************************************************************************
    @Override
    public void onBackPressed() {

        PinkConnection.this.finish();
        Intent about = new Intent(PinkConnection.this, Task_Navigation.class);
        startActivity(about);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                this.finish();
                Intent taskselect = new Intent(PinkConnection.this, Task_Navigation.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

                return true;
        }

        return super.onOptionsItemSelected(item);

    }

    /***************
     * @Ponnusamy Date: 8.2.2017
     * Description:  Get Year in using DISTINCT Method
     *****************/

    public void viewPinkconnection()

    {

        SQLiteDatabase sqLiteDatabase = Baseconfig.GetDb(PinkConnection.this);

        String Query="SELECT DISTINCT YearPublished FROM Bind_Inspirational  where Language='"+Baseconfig.Language+"' order by Id Desc";

        Log.e("Query: ",Query);
        Log.e("Query: ",Query);
        Log.e("Query: ",Query);

        Cursor cursor = sqLiteDatabase.rawQuery(Query, null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    String years=cursor.getString(cursor.getColumnIndex("YearPublished"));

                    sectionedExpandableLayoutHelper.addSection(years, getYearVolumes(years));


                } while (cursor.moveToNext());

            }

        }
        sectionedExpandableLayoutHelper.notifyDataSetChanged();

        cursor.close();
        sqLiteDatabase.close();

    }

    public ArrayList<Item> getYearVolumes(String Yearpublised) {

        arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = Baseconfig.GetDb(PinkConnection.this);

        Cursor cursor = sqLiteDatabase.rawQuery("select VolumeIssue,MagazineName,PDF_URL,Id,Image_URL from Bind_Inspirational where YearPublished='"+Yearpublised+"' and Language='"+Baseconfig.Language+"'  order by Id Desc", null);

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    //String years=cursor.getString(cursor.getColumnIndex("YearPublished"));

                    arrayList.add(new Item(cursor.getString(cursor.getColumnIndex("VolumeIssue")),cursor.getString(cursor.getColumnIndex("MagazineName")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("Id"))), cursor.getString(cursor.getColumnIndex("Image_URL")),cursor.getString(cursor.getColumnIndex("PDF_URL"))));


                } while (cursor.moveToNext());

            }

        }

        cursor.close();
        sqLiteDatabase.close();

        return  arrayList;

       }



}
