package devatech.new_task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import devatech.adapter.ItemObject1;
import devatech.adapter.RecyclerViewAdapter3;
import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 10/13/2016.
 */

public class Video extends AppCompatActivity {


    private GridLayoutManager lLayout;
    ImageView exit, back;
    Toolbar toolbar;
    //**********************************************************************************************

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_video);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount=0.0f;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        try
        {
            GetInitialize();
            Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Video", e.toString());
        }


    }

    //**********************************************************************************************

    public void GetInitialize() {
        //Baseconfig.LoadLanguage(Video.this, Baseconfig.Language, Video.this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);

        back = (ImageView) toolbar.findViewById(R.id.ic_back);


        setSupportActionBar(toolbar);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(DashBoard_Slide.this,"Exit",Toast.LENGTH_LONG).show();
                Baseconfig.ExitSweetDialog(Video.this, null);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Video.this.finish();
                Intent taskselect = new Intent(Video.this, Task_Navigation.class);
                startActivity(taskselect);
                Video.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


            }
        });


        List<ItemObject1> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(Video.this, 1);


        if (Baseconfig.CheckNW(Video.this))
        {
            RecyclerView rView = (RecyclerView) findViewById(R.id.recycler_view);
            rView.setHasFixedSize(true);
            rView.setLayoutManager(lLayout);
            rView.setNestedScrollingEnabled(false);


            RecyclerViewAdapter3 rcAdapter = new RecyclerViewAdapter3(Video.this, rowListItem);
            rView.setAdapter(rcAdapter);

        } else {

            new SweetAlertDialog(Video.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.information))
                    .setContentText(getResources().getString(R.string.nointernet))
                    .setConfirmText(getResources().getString(R.string.ok))
                    .show();


        }


    }

    //**********************************************************************************************

    private List<ItemObject1> getAllItemList() {


        List<ItemObject1> allItems = new ArrayList<ItemObject1>();

        /*SQLiteDatabase db = Baseconfig.GetDb(BC.this);

        String Query1 = "select Id as dstatus1 from Bind_BreastCancer where Language='" + Baseconfig.Language + "'";
        boolean q = Baseconfig.LoadReportsBooleanStatus(Query1, BC.this);

        if (q) {
            String Query = "select * from Bind_BreastCancer where Language='" + Baseconfig.Language + "'";

            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        int Id = c.getInt(c.getColumnIndex("Id"));
                        String Title = c.getString(c.getColumnIndex("Title"));
                        String Desc = c.getString(c.getColumnIndex("ShortDesc"));
                        String PhotoUrl = c.getString(c.getColumnIndex("ImgURL"));

                        allItems.add(new ItemObject(Id, Title, Desc, PhotoUrl));

                    } while (c.moveToNext());
                }
            }

            c.close();

        } else {

            Baseconfig.SweetDialgos(4, BC.this, BC.this.getResources().getString(R.string.information), BC.this.getResources().getString(R.string.nodata_available) + " " + Baseconfig.Language + " " + BC.this.getResources().getString(R.string.language), BC.this.getResources().getString(R.string.ok));

        }

        db.close();*/


        if (Baseconfig.Language.toString().equalsIgnoreCase("English"))//English
        {
            //allItems.add(new ItemObject1("1DwOPZOheDI", "Breast Cancer Awareness Film English"));

          // allItems.add(new ItemObject1("kSr8A4H7VLc", "Be Breast Aware"));

           //allItems.add(new ItemObject1("tHlamm6SHz8", "Breast Cancer Awareness Film"));


            // TODO: 3/11/2017 New Vedio URl Update
            /*****
             * @POnnusamy
             * ******/

            allItems.add(new ItemObject1("kSr8A4H7VLc", "Be Breast Aware"));
            allItems.add(new ItemObject1("tHlamm6SHz8", "Breast Cancer Awareness Film"));

        } else if (Baseconfig.Language.toString().equalsIgnoreCase("తెలుగు"))//Telgu
        {


            //allItems.add(new ItemObject1("Fw-mIzPQLKs", "రొమ్ము క్యాన్సర్ అవగాహన సినిమా తెలుగు"));

            //allItems.add(new ItemObject1("royR7qnIxKc`", "రొమ్ము క్యాన్సర్ అవగాహన సినిమా"));

            // TODO: 3/11/2017 New Vedio URl Update
            /*****
             * @POnnusamy
             * ******/

            allItems.add(new ItemObject1("BrY_NrQUC8c", "రొమ్ము క్యాన్సర్ అవగాహన సినిమా తెలుగు"));

            allItems.add(new ItemObject1("x9IpnJ_iVK4", "రొమ్ము క్యాన్సర్ అవగాహన సినిమా"));

        } else {


            allItems.add(new ItemObject1("kSr8A4H7VLc", "Be Breast Aware"));

            allItems.add(new ItemObject1("tHlamm6SHz8", "Breast Cancer Awareness Film"));


           /* new SweetAlertDialog(Video.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getResources().getString(R.string.information))
                    .setContentText(getResources().getString(R.string.novideo))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();
                            Video.this.finish();
                            Intent taskselect = new Intent(Video.this, Task_Navigation.class);
                            startActivity(taskselect);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


                        }
                    })
                    .show();*/


        }


        return allItems;

    }

    //**********************************************************************************************

    public void Controllisterners() {


    }

    //**********************************************************************************************

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                this.finish();
                Intent taskselect = new Intent(Video.this, DashBoard_Slide.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

                return true;
        }

        return super.onOptionsItemSelected(item);

    }*/

    //**********************************************************************************************
    @Override
    public void onBackPressed() {
        Video.this.finish();
        Intent about = new Intent(Video.this, Task_Navigation.class);
        startActivity(about);
        Video.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************


}