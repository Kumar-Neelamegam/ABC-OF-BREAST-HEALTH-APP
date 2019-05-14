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
import devatech.adapter.RecyclerViewAdapter2;
import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;

public class BC extends AppCompatActivity {

    ImageView exit, back;
    Toolbar toolbar;
        private GridLayoutManager lLayout;


   /* /*//**********************************************************************************************

     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View v = inflater.inflate(R.layout.act_breastcancer, container, false);

     try
     {
     GetInitialize(v);
     Controllisterners(v);

     Baseconfig.appendLog("Completion BC Initialization");

     }
     catch(Exception e)
     {
     e.printStackTrace();
     Baseconfig.appendLog("Breast Cancer Log");
     Baseconfig.appendLog(e.getMessage());

     }

     return v;

     }
     /*/
    /**********************************************************************************************
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_breastcancer);

        try {
            GetInitialize();
            Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC", e.toString());
        }


    }

    //**********************************************************************************************

    public void GetInitialize() {


        try {
            //Baseconfig.LoadLanguage(BC.this, Baseconfig.Language, BC.this);
            toolbar = (Toolbar)findViewById(R.id.toolbar);
            exit = (ImageView) toolbar.findViewById(R.id.ic_exit);

            back = (ImageView) toolbar.findViewById(R.id.ic_back);


            List<ItemObject> rowListItem = getAllItemList();
            lLayout = new GridLayoutManager(BC.this, 2);

            RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(lLayout);
            rView.setNestedScrollingEnabled(false);
            RecyclerViewAdapter2 rcAdapter = new RecyclerViewAdapter2(BC.this, rowListItem);
            rView.setAdapter(rcAdapter);

        } catch (Exception e) {
            e.printStackTrace();

            FirebaseCrash.logcat(Log.ERROR, "BC", e.toString());
        }

    }


    //**********************************************************************************************

    public void Controllisterners() {

        try {


            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                    Baseconfig.ExitSweetDialog(BC.this, null);


                }
            });


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    BC.this.finish();
                    Intent about = new Intent(BC.this, Task_Navigation.class);
                    startActivity(about);
                    BC.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC", e.toString());

        }

    }

    //**********************************************************************************************
     @Override
    public void onBackPressed()
    {

        BC.this.finish();
        Intent about = new Intent(BC.this, Task_Navigation.class);
        startActivity(about);
        BC.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************

    public List<ItemObject> getAllItemList() {

        try {
            List<ItemObject> allItems = new ArrayList<ItemObject>();

            SQLiteDatabase db = Baseconfig.GetDb(BC.this);

            String Query1 = "select Id as dstatus1 from Bind_BC where Language='" + Baseconfig.Language + "'  and IsActive=1";
            boolean q = Baseconfig.LoadReportsBooleanStatus(Query1, BC.this);

            if (q) {
                String Query = "select Id,Title,HtmlSrc from Bind_BC where Language='" + Baseconfig.Language + "'  and IsActive=1";

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

                Baseconfig.SweetDialgos(4, BC.this, BC.this.getResources().getString(R.string.information), BC.this.getResources().getString(R.string.nodata_available) + " " + Baseconfig.Language + " " + BC.this.getResources().getString(R.string.language), BC.this.getResources().getString(R.string.ok));

            }

            db.close();


            return allItems;
        } catch (Exception e) {

            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "BC", e.toString());

            return null;
        }

    }

//**********************************************************************************************
/*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                this.finish();
                Intent taskselect = new Intent(BC.this, Task_Navigation.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

                return true;
        }

        return super.onOptionsItemSelected(item);

    }
    /*/
/**********************************************************************************************
 */


}
