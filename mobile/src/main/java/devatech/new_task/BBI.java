package devatech.new_task;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import devatech.adapter.ItemObject;
import devatech.adapter.RecyclerViewAdapter;
import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 9/13/2016.
 */
public class BBI extends AppCompatActivity {

    //**********************************************************************************************
    ImageView exit, back;
    Toolbar toolbar;

    private GridLayoutManager lLayout;

    //**********************************************************************************************

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.act_benigl, container, false);

        try {
            GetInitialize(v);
            Controllisterners(v);


            Baseconfig.appendLog("Completion BBI Initialization");

        } catch (Exception e) {
            e.printStackTrace();
            Baseconfig.appendLog("Benign Breast Issues");
            Baseconfig.appendLog(e.getMessage());

        }

        return v;

    }*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_benigl);

        try
        {
            GetInitialize();
            Controllisterners();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI", e.toString());
        }


    }

    //**********************************************************************************************

    public void GetInitialize()
    {

        try
        {
            //Baseconfig.LoadLanguage(BBI.this, Baseconfig.Language, BBI.this);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            exit = (ImageView) toolbar.findViewById(R.id.ic_exit);
            back = (ImageView) toolbar.findViewById(R.id.ic_back);


            setSupportActionBar(toolbar);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            List<ItemObject> rowListItem = getAllItemList();
            lLayout = new GridLayoutManager(BBI.this, 2);

            RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(lLayout);
            rView.setNestedScrollingEnabled(false);
            RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(BBI.this, rowListItem);
            rView.setAdapter(rcAdapter);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI", e.toString());
        }

    }


    //**********************************************************************************************


    public void Controllisterners()
    {


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(BBI.this, null);


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BBI.this.finish();
                Intent about = new Intent(BBI.this, Task_Navigation.class);
                startActivity(about);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

            }
        });

    }

    //**********************************************************************************************

    private List<ItemObject> getAllItemList() {

        try {
            List<ItemObject> allItems = new ArrayList<ItemObject>();

            SQLiteDatabase db = Baseconfig.GetDb(BBI.this);

            String Query1 = "select Id as dstatus1 from Bind_BBI where Language='" + Baseconfig.Language + "' and IsActive=1";
            boolean q = Baseconfig.LoadReportsBooleanStatus(Query1, BBI.this);

            if (q) {
                String Query = "select Id,Title,HtmlSrc from Bind_BBI where Language='" + Baseconfig.Language + "' and IsActive=1";
                Cursor c = db.rawQuery(Query, null);
                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            int Id = c.getInt(c.getColumnIndex("Id"));
                            String Title = c.getString(c.getColumnIndex("Title"));
                            String FileName = c.getString(c.getColumnIndex("HtmlSrc"));

                            allItems.add(new ItemObject(Id, Title, "", "", FileName));

                        } while (c.moveToNext());
                    }
                }

                c.close();

            } else {

                Baseconfig.SweetDialgos(4, BBI.this, BBI.this.getResources().getString(R.string.information), BBI.this.getResources().getString(R.string.nodata_available) + " " + Baseconfig.Language + " " + BBI.this.getResources().getString(R.string.language), BBI.this.getResources().getString(R.string.ok));

            }


            // String Query="select * from Bind_BreastCancer where Language='"+Baseconfig.Language+"'";

            db.close();


      /*
        allItems.add(new ItemObject("All you need to know about Breast awareness, Mammogram & other modalities of Breast imaging", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed vel erat et elit gravida pretium. Nulla id tortor sed est imperdiet commodo. Nullam feugiat, elit id tincidunt imperdiet, tellus dui rutrum quam, sit amet porta diam lectus eget purus. Maecenas aliquam magna sed metus porta euismod. In egestas quam nec placerat facilisis. Integer eget facilisis justo, ac rutrum nibh. Integer lacinia semper orci. Donec imperdiet tellus eu viverra commodo.", 0));
     */


            return allItems;

        } catch (Exception e) {

            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BBI", e.toString());

            return null;
        }

    }

    //**********************************************************************************************
    @Override
    public void onBackPressed() {

        BBI.this.finish();
        Intent about = new Intent(BBI.this, Task_Navigation.class);
        startActivity(about);
        BBI.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************

}
