package devatech.new_task;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
public class Inspirational extends AppCompatActivity implements ItemClickListener {

    //**********************************************************************************************
    ImageView exit,back;
    Toolbar toolbar;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inspirational);

        try {
            GetInitialize();
            Controllisterners();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //**********************************************************************************************

    public void GetInitialize() {

        Baseconfig.LoadLanguage(Inspirational.this, Baseconfig.Language, Inspirational.this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        exit = (ImageView) toolbar.findViewById(R.id.ic_exit);

        back=(ImageView)toolbar.findViewById(R.id.ic_back);


        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setNestedScrollingEnabled(false);

        LoadInspirational();

       /* //random data
        ArrayList<Item> arrayList = new ArrayList<>();
        arrayList.add(new Item("Feb - April", "Volume 5 Issue 3",1,getResources().getDrawable(R.drawable.cover_feb_april2015)));
        sectionedExpandableLayoutHelper.addSection("Year 2015", arrayList);

        arrayList = new ArrayList<>();
        arrayList.add(new Item("Aug - Oct", "Volume 1 Issue 1",2,getResources().getDrawable(R.drawable.cover_aug_oct2014)));
        sectionedExpandableLayoutHelper.addSection("Year 2014", arrayList);
*/


    }

    //**********************************************************************************************

    public void LoadInspirational() {

        SectionedExpandableLayoutHelper sectionedExpandableLayoutHelper = new SectionedExpandableLayoutHelper(Inspirational.this, mRecyclerView, (ItemClickListener) this, 2);

        String MagazineName = "";
        String VolumeIssue = "";
        int Img = 0;
        String Year = "";
        int Id;

        ArrayList<Item> arrayList;

        SQLiteDatabase db = Baseconfig.GetDb(Inspirational.this);


        String Query1="select Id as dstatus1 from Bind_Inspirational where IsActive=1 and Language='" + Baseconfig.Language +"'";
        boolean q=Baseconfig.LoadReportsBooleanStatus(Query1,Inspirational.this);

        if(q)
        {
            String Query = "select Id,MagazineName,VolumeIssue,Img,YearPublished from Bind_Inspirational where IsActive=1 and Language='" + Baseconfig.Language + "'";
            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        MagazineName = c.getString(c.getColumnIndex("MagazineName"));
                        VolumeIssue = c.getString(c.getColumnIndex("VolumeIssue"));
                        Img = Integer.parseInt(c.getString(c.getColumnIndex("Img")).toString());
                        Year = c.getString(c.getColumnIndex("YearPublished"));
                        Id = c.getInt(c.getColumnIndex("Id"));

                        arrayList = new ArrayList<>();
                      //  arrayList.add(new Item(MagazineName, VolumeIssue, Id, GetImage(Img)));
                        sectionedExpandableLayoutHelper.addSection(Year, arrayList);

                    } while (c.moveToNext());
                }

            }

            c.close();
            db.close();

            sectionedExpandableLayoutHelper.notifyDataSetChanged();
        } else{

            Baseconfig.SweetDialgos(4,Inspirational.this,Inspirational.this.getResources().getString(R.string.information),Inspirational.this.getResources().getString(R.string.nodata_available)+" "+Baseconfig.Language+" " + Inspirational.this.getResources().getString(R.string.language),Inspirational.this.getResources().getString(R.string.ok));

        }





    }

    //**********************************************************************************************

    public Drawable GetImage(int Id)
    {
        Drawable img = null;

        if (Id == 1)
        {
        //    img = getResources().getDrawable(R.drawable.cover_feb_april2015);

        }
        else if (Id == 2)
        {
           // img = getResources().getDrawable(R.drawable.cover_aug_oct2014);
        }

        return img;

    }

    //**********************************************************************************************

    @Override
    public void itemClicked(Item item) {

       // Toast.makeText(this, "Item: " + item.getId() + " clicked" + item.getId(), Toast.LENGTH_SHORT).show();

        Inspirational.this.finish();
        Intent intent4=new Intent(Inspirational.this, Inspirational_ReadMore.class);
        intent4.putExtra("Id",item.getId());
        startActivity(intent4);

     /*   switch (item.getId()) {


            case 1:


                *//*intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/Inspirational/English/Inspirational2.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);*//*

                break;


            case 2:
               *//* intent4.putExtra("FilePath",Baseconfig.DATABASE_FILE_PATH+"/Inspirational/English/Inspirational1.pdf");
                intent4.putExtra("FileName",item.getName());
                intent4.putExtra("Title",item.getName()+" - "+item.getSeries());
                startActivity(intent4);*//*

                break;


        }*/


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
                Baseconfig.ExitSweetDialog(Inspirational.this, null);


            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Inspirational.this.finish();
                Intent about = new Intent(Inspirational.this, Task_Navigation.class);
                startActivity(about);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


            }
        });

    }

    //**********************************************************************************************
    @Override
    public void onBackPressed() {

        Inspirational.this.finish();
        Intent about = new Intent(Inspirational.this, Task_Navigation.class);
        startActivity(about);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


    }

    //**********************************************************************************************

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                this.finish();
                Intent taskselect = new Intent(Inspirational.this, Task_Navigation.class);
                startActivity(taskselect);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

                return true;
        }

        return super.onOptionsItemSelected(item);

    }
    //**********************************************************************************************

}
