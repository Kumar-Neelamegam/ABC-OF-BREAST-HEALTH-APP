/*
 * Copyright (C) 2016 Olmo Gallegos Hernández.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package devatech.new_task;

import android.support.v7.app.AppCompatActivity;

//import com.github.barteksc.pdfviewer.PDFView;
//import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;


public class PdfViewActivity extends AppCompatActivity //implements OnPageChangeListener
{
   /* /*//**********************************************************************************************

  //  PDFView pdfView;
    TextView PageNo;
    Integer pageNumber = 0;
    //String pdfName = "sample.pdf";

    Bundle b;
    String FilePath,FileName,Title;
    int Id;

    Dialog overlayInfo;
    private ImageView mOverLayImage, mOverLayImage1;
    CheckBox mOverLayChecbox;

    ImageView zoom,swipe;
    /*//**********************************************************************************************
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdf);


        try {
            GetInitialize();

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    /*//**********************************************************************************************

    public void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(PdfViewActivity.this);

        boolean checkBoxValue = sharedPreferences.getBoolean("CheckBox_Value3",
                false);

        if (!checkBoxValue) {
            overlayInfo.show();
        } else {
            overlayInfo.cancel();
        }

    }

    public void savePreferences(String key, boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PdfViewActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    // ********************************************************************************************



    public void GetInitialize()
    {
        try {

            b = getIntent().getExtras();

            if (b == null) {
                FilePath = null;
                FileName = null;
                Title = null;
                Id=0;
            } else {
                FilePath = b.getString("FilePath");
                FileName = b.getString("FileName");
                Title = b.getString("Title");
                Id = b.getInt("Id");
            }


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TextView tv=(TextView)toolbar.findViewById(R.id.tool_text);
            tv.setText("Pink Connection: "+ Title);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


          //  pdfView = (PDFView) findViewById(R.id.pdfView);
            PageNo = (TextView) findViewById(R.id.textView);

            zoom=(ImageView)findViewById(R.id.img_zoom);
            swipe=(ImageView)findViewById(R.id.img_swipe);

            Controllisteners();

            ShowOverlayLayout();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void ShowOverlayLayout() {
        *//**
         * Developer name: N.Muthukumar Overlay first time screen for
         * registration
         *//*
        overlayInfo = new Dialog(PdfViewActivity.this);
        overlayInfo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overlayInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        overlayInfo.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        overlayInfo.setContentView(R.layout.showcase_inbox);

        mOverLayImage = (ImageView) overlayInfo
                .findViewById(R.id.over_lay_image);

        Button okbutton=(Button)overlayInfo.findViewById(R.id.button);

        okbutton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                overlayInfo.cancel();

            }
        });

        mOverLayImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                overlayInfo.cancel();

            }
        });

        mOverLayChecbox = (CheckBox) overlayInfo.findViewById(R.id.checkBox1);

        mOverLayChecbox
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {

                        savePreferences("CheckBox_Value3",mOverLayChecbox.isChecked());

                    }
                });



        loadSavedPreferences();

    }

    /*//**********************************************************************************************
    public void Controllisteners()
    {

        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PdfViewActivity.this,PdfViewActivity.this.getResources().getString(R.string.zoom),Toast.LENGTH_LONG).show();
            }
        });

        swipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PdfViewActivity.this,PdfViewActivity.this.getResources().getString(R.string.swipe),Toast.LENGTH_LONG).show();
            }
        });

       *//* pdfView.fromAsset(pdfName)
                .pages(0, 2, 1, 3, 3, 3)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .showMinimap(false)
                .enableSwipe(true)
                .load();*//*


       // pdfView.loadPages();

        try
        {
            File f = new File(FilePath);

          //  pdfView.fromFile(f).defaultPage(pageNumber).onPageChange(this).showMinimap(false).enableSwipe(true).load();
          //  pdfView.fromFile(f).pages(pdfView.getPageCount());

           *//* pdfView.fromFile(f) // all pages are displayed by default
                    .enableSwipe(true)
                    .swipeHorizontal(true)
                    .enableDoubletap(true)
                    .defaultPage(pageNumber)
                    .enableAnnotationRendering(false)
                    .onPageChange(this)
                    .scrollHandle(null)
                    .load();
            pdfView.useBestQuality(true);
            pdfView.fromFile(f).pages(pdfView.getPageCount());*//*

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "PdfView", e.toString());
        }



        //String pdfName="KIMS/English/BREAST HEALTH, 1.pdf";

        try
        {
           // AssetManager am = getAssets();
            //InputStream inputStream = am.open(FilePath.toString());
           //File file = createFileFromInputStream(inputStream);

           *//* pdfView.fromAsset("KIMS/English/BREASTHEALTH,1.pdf")
                    .pages(7)
                    .defaultPage(1)
                    .onPageChange(this)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();*//*
        }
        catch(Exception e)
        {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "PdfView", e.toString());
        }


    }



    /*//**********************************************************************************************

    int page_count=0;
    @Override
    public void onPageChanged(int page, int pageCount)
    {
        try {

            PageNo.setText(format("( %s )   %s / %s", FileName, page, pageCount-1));
            pageNumber = page;
            page_count=pageCount;

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "PdfView", e.toString());
        }
    }

    /*//**********************************************************************************************

    @Override
    public void onBackPressed() {

            this.finish();
            Intent taskselect = new Intent(PdfViewActivity.this, Task_Navigation.class);
            taskselect.putExtra("Id", "PINK");
            taskselect.putExtra("GAME","");
            startActivity(taskselect);

    }


    /*//***********************************************************************************************

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {

            // Respond to the action bar's Up/Home button
            case android.R.id.home:

                //if(Id==1)
                //{
                    this.finish();
                    Intent taskselect = new Intent(PdfViewActivity.this, Task_Navigation.class);
                    taskselect.putExtra("Id", "PINK");
                    taskselect.putExtra("GAME","");
                    startActivity(taskselect);
               *//* }
                else if(Id==2){
                    this.finish();
                    Intent taskselect = new Intent(PdfViewActivity.this, Task_Navigation.class);
                   // taskselect.putExtra("Id", "PINK");
                    startActivity(taskselect);
                }
*//*

                return true;
        }

        return super.onOptionsItemSelected(item);

    }
    /*//**********************************************************************************************

*/
}
