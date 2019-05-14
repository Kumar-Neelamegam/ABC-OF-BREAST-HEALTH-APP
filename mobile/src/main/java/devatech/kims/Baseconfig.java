package devatech.kims;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import devatech.Utils.FontHelper;
import devatech.Utils.Webservices;

/**
 * Created by Android on 8/25/2016.
 */





public class Baseconfig {



    //******************************************************************************************

    public static int InternetFlag;

    // **************************************************************
    /**
     *
     * LOCAL DATABASE LOCATION
     */
    public static String DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/.KIMS/";// +
    public static String DATABASE_NAME = DATABASE_FILE_PATH + File.separator+ "KIMS.db";



    public static final String NAMESPACE = "http://tempuri.org/";
    public static String Webservice_URL="http://182.18.161.229/HealthFundooWS/Service.asmx";

    public static String LANG_8HINDI="BBI-HINDI";
    public static String LANG_TAMIL="BBI-TAMIL";
    public static String LANG_TELGU="BBI-TELGU";



    // **************************************************************

    public static String Lbl_ID="Id";
    public static String Lbl_LanguageID="LanguageID";
    public static String Lbl_File_Name="File_Name";
    public static String Lbl_File_Path="File_Path";
    public static String Lbl_File_Size="File_Size";
    public static String Lbl_FileTypeID="FileTypeID";
    public static String Lbl_FileCategoryID="FileCategoryID";
    public static String Lbl_IsUpdate="IsUpdate";
    public static String Lbl_IsActive="IsActive";
    public static String Lbl_ActDate="ActDate";


    public static String Lbl_FileCategory="FileCategory";
    public static String Lbl_FileType="FileType";
    public static String Lbl_LanguageCode="Language_Code";





    public static final String MSTR_ALLERGY_NAME="Allergy";

    public static String TABLE_MSTR_Files="Bind_ImportFiles";
    public static String TABLE_MSTR_FILECATEGORY="Mstr_FileCategory";
    public static String TABLE_MSTR_FILETYPE="Mstr_FileType";
    public static String TABLE_MSTR_LANGUAGE="Mstr_Language";

    // **************************************************************



    //Language settings
    public static String Language="";

    public static Locale myLocale;




    public static String Layout_Font_Assamese="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/assamese.TTF";
    public static String Layout_Font_Benagli="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/bengali.ttf";
    public static String Layout_Font_English="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/english.ttf";
    public static String Layout_Font_Hindi="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/hindi.ttf";
    public static String Layout_Font_Tamil="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/tamil.ttf";
    public static String Layout_Font_Telugu="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/telugu.ttf";
    public static String Layout_Font_Kannada="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/kannada1.ttf";
    public static String Layout_Font_Malayalam="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/malayalam.ttf";
    public static String Layout_Font_Marathi="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/marathi.ttf";
    public static String Layout_Font_Punjabi="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/punjabi.ttf";
    public static String Layout_Font_Oriya="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/oriya.ttf";
    public static String Layout_Font_Gujrathi="file://"+Environment.getExternalStorageDirectory()+"/.KIMS/fonts/gujrathi.ttf";
    public static String TodayScore="0";
    public static String QuestionCount;



    // **************************************************************
    public static String ASSEST_ZIP_NAME = DATABASE_FILE_PATH  + "META_DATA.zip";
    public static String ASSEST_ZIP_NAME_OUT = DATABASE_FILE_PATH;


    public SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    static Calendar currentDate = Calendar.getInstance();
    static SimpleDateFormat formatterr = new SimpleDateFormat("dd");
    static String daynum = formatterr.format(currentDate.getTime());
    static SimpleDateFormat formatterm = new SimpleDateFormat("yyyy");
    static String yrnum = formatterm.format(currentDate.getTime());
    static String dayname = (String) android.text.format.DateFormat.format(
            "EEEE", currentDate);
    static String Monthname = (String) android.text.format.DateFormat.format(
            "MMMM", currentDate);
    static String finaldt = dayname + ", " + Monthname + " " + daynum + ", "
            + yrnum;// +"  , "+tm;

    // **************************************************************
    public static SimpleDateFormat dateformt = new SimpleDateFormat(
            "dd/MM/yyyy HH:mm:ss");
    public static Date date1 = new Date();
    public static String dttm1 = dateformt.format(date1).toString();


     //**********************************************************
     public static boolean CheckNW(Context ctx) {
         ConnectivityManager cn = (ConnectivityManager) ctx
                 .getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo nf = cn.getActiveNetworkInfo();
         if (nf != null && nf.isConnected() == true) {

             Baseconfig.InternetFlag = 1;
             return true;
         } else {

             Baseconfig.InternetFlag = 0;
             return false;
         }
     }

    /*
	 * Baseconfig.java,  E-SchoolSystem
	 * Author: AndroidGeek
	 * Developer name: N.Muthukumar
	 * Email: muthukumar.n@devatechinfosystems.com
	 * Created on: Feb 6, 2016 3:45:05 PM
	 */
    public static void animateImageView(View v)
    {

        // Create an animation instance
        Animation an;// = new RotateAnimation(0.0f, 360.0f, 1, 1);
        an = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // Set the animation's parameters
        an.setDuration(500);               // duration in ms
        an.setRepeatCount(0);                // -1 = infinite repeated
        an.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an.setFillAfter(true);               // keep rotation after animation

        // Aply animation to image view
        v.setAnimation(an);
    }

// **************************************************************


    public static void animation1(ImageView mLogo)
    {

        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();


    }

    public static void animation1(CardView mLogo)
    {

        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();


    }

// **************************************************************

    Context ctx;
    // **************************************************************
    public Baseconfig(Context ctx)
    {
        this.ctx=ctx;

    }


    // **************************************************************
    //Common sweet Dialog
    public static void setSweetDialog(final Context ctx,String Title,String strMessage,final Class<?> className)
    {
        new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(Title)
                .setContentText(strMessage)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {

                        sweetAlertDialog.dismiss();

                        ((Activity) ctx).finish();
                        Intent intent = new Intent();
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setClass(ctx, className);
                        ctx.startActivity(intent);

                    }
                })
                .show();

    }

    //Common sweet Dialog
    public static void setSweetDialog(final Context ctx,String Title,String strMessage)
    {
        new SweetAlertDialog(ctx, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(Title)
                .setContentText(strMessage)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {

                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog)
                    {

                        sweetAlertDialog.dismiss();

                    }
                })
                .show();

    }


    public static void SweetDialgos(int Id, Context ctx, String Title, String Message1, String Message2)
    {

        switch (Id)
        {


            //A basic message
            case 1:

                new SweetAlertDialog(ctx)
                        .setTitleText(Title)
                        .show();

                break;

            //A title with a text under
            case 2:

                new SweetAlertDialog(ctx)
                        .setTitleText(Title)
                        .setContentText(Message1)
                        .show();

                break;

            //A error message
            case 3:

                new SweetAlertDialog(ctx, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(Title)
                        .setContentText(Message1)
                        .show();

                break;
            //A warning message
            case 4:

                new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(Title)
                        .setContentText(Message1)
                        .setConfirmText(Message2)
                        .show();

                break;

            case 5:

                new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("No,cancel plx!")
                        .setConfirmText("Yes,delete it!")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                // reuse previous dialog instance, keep widget user state, reset them if you need
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.ERROR_TYPE);


                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                break;

        }


    }
    // **************************************************************

    public static void ExitSweetDialog(final Context ctx,final Class<?> className)
    {

        new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(ctx.getResources().getString(R.string.information))
                .setContentText(ctx.getResources().getString(R.string.message))
                .setCancelText(ctx.getResources().getString(R.string.no))
                .setConfirmText(ctx.getResources().getString(R.string.yes))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {


                        sDialog.dismiss();

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismiss();
                        //Baseconfig.StopwebService();
                        ((Activity) ctx).finish();


                    }
                })
                .show();

    }


    public static void ConfirmSweetDialog(final Context ctx,String title,String message)
    {

        new SweetAlertDialog(ctx, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCancelText(ctx.getResources().getString(R.string.no))
                .setConfirmText(ctx.getResources().getString(R.string.yes))
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {


                        sDialog.dismiss();

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismiss();


                    }
                })
                .show();

    }


   //******************************************************************************************

    public static void initCustomSpinnerDB(Spinner spn, Context ctx, String Query, String lstadd)
    {

        SQLiteDatabase db = Baseconfig.GetDb(ctx);
        Cursor c = db.rawQuery(Query, null);
        List<String> list = new ArrayList<String>();

        //list.add(lstadd);

        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {

                    String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                    list.add(counrtyname);

                } while (c.moveToNext());
            }
        }

        ArrayList<String> listOfStrings = new ArrayList<String>(list.size());

        listOfStrings.addAll(list);

        Baseconfig common=new Baseconfig(ctx);
        CustomSpinnerAdapter customSpinnerAdapter=common.new CustomSpinnerAdapter(ctx,listOfStrings);
        spn.setAdapter(customSpinnerAdapter);

    }


    public static void initCustomSpinnerDB(int resource, Spinner spn, Context ctx)
    {

        List<String> myArrayList = Arrays.asList(ctx.getResources().getStringArray(resource));
        ArrayList<String> listOfStrings = new ArrayList<String>(myArrayList.size());
        //Collections.sort(listOfStrings);
        listOfStrings.addAll(myArrayList);

        Baseconfig common=new Baseconfig(ctx);
        CustomSpinnerAdapter customSpinnerAdapter=common.new CustomSpinnerAdapter(ctx,listOfStrings);
        spn.setAdapter(customSpinnerAdapter);

    }

    //* ******************************************************************************************************

    public static void initCustomSpinner(int resource, Spinner spn, Context ctx) {

        List<String> myArrayList = Arrays.asList(ctx.getResources().getStringArray(resource));
        ArrayList<String> listOfStrings = new ArrayList<String>(myArrayList.size());
        //Collections.sort(listOfStrings);
        listOfStrings.addAll(myArrayList);

        Baseconfig common=new Baseconfig(ctx);
        CustomSpinnerAdapter customSpinnerAdapter=common.new CustomSpinnerAdapter(ctx,listOfStrings);
        spn.setAdapter(customSpinnerAdapter);

    }

    public static void initCustomSpinnerList(List<String> myArrayList,Spinner spn,Context ctx) {

        // List<String> myArrayList = Arrays.asList(ctx.getResources().getStringArray(resource));
        ArrayList<String> listOfStrings = new ArrayList<String>(myArrayList.size());
        //Collections.sort(listOfStrings);
        listOfStrings.addAll(myArrayList);

        Baseconfig common=new Baseconfig(ctx);
        CustomSpinnerAdapter customSpinnerAdapter=common.new CustomSpinnerAdapter(ctx,listOfStrings);
        spn.setAdapter(customSpinnerAdapter);

    }

    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(activity);
            txt.setPadding(10, 10, 10, 10);
            //txt.setTextSize(18);

            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#ffffff"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(activity);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(10, 10, 10, 10);
            //txt.setTextSize(16);
           // txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

    }
    //**********************************************************************************************

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    //**********************************************************************************************
    //@Database Operations
    //Open Database
    public static SQLiteDatabase GetDb(Context ctx)
    {
        SQLiteDatabase db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null, null);
        return db;
    }

    //Insert values to database
    public static void insertValueToDb(Context ctx , ContentValues values, String tableName)
    {
        SQLiteDatabase db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null, null);
        db.insert(tableName,null,values);
        db.close();
    }

    //Update values to database
    public static void updateValuesToDb(Context ctx ,ContentValues values,String tableName,String whereClause)
    {
        SQLiteDatabase db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null, null);
        db.update(tableName, values, whereClause, null);
        db.close();
    }

    //**********************************************************************************************



    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    //**********************************************************************************************







    //**********************************************************************************************

    public static ScheduledExecutorService scheduler;
    public static void StartWebservice(final Context ctx)
    {
        Log.e("Webservices", "Started");
        scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.e("Webservices", "Started Thread...");
                try {

                    Webservices service = new Webservices(ctx);
                    service.ExecuteAll();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.gc();
            }
        }, 1, 10, TimeUnit.SECONDS);

    }

    public static void StopwebService()
    {
        try {
            if (scheduler != null) {
                Log.e("Webservices", "Stopped Thread...");
                scheduler.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //**********************************************************************************************

    public static String CheckString(String edt, Context ctx)
    {

        String ret="N/A";

        if(edt.toString().length()>0)
        {

            ret=edt.toString();

        }
        return ret;
    }



    // **************************************************************
    // Boolen Return Status
    static String professionalcharges;

    String pharm_diagcntrcount;
    public static boolean LoadReportsBooleanStatus(String Query,Context ctx)
    {
        try
        {
            int havcount = 0;

            SQLiteDatabase db=GetDb(ctx);

            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do
                    {

                        professionalcharges = c.getString(c.getColumnIndex("dstatus1"));

                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

            if (havcount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // **************************************************************

    public static boolean LoadRegisterBooleanStatus(String Query,Context ctx)
    {
        try
        {
            int havcount = 0;

            SQLiteDatabase db=GetDb(ctx);

            Cursor c = db.rawQuery(Query, null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        havcount = Integer.parseInt(c.getString(c.getColumnIndex("dstatus1")));

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();

            if (havcount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "LoadBoolean", e.toString());
            return false;
        }
    }

//*****************************************************************************


    public static String GetIdFromString(String Query,Context ctx)
    {
        String Id="";

        SQLiteDatabase db =  Baseconfig.GetDb(ctx);

        String query = Query;
        Cursor c = db.rawQuery(query, null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    Id=c.getString(c.getColumnIndex("dvalue"));

                }while (c.moveToNext());

            }

        }
        c.close();



        db.close();


        return Id;


    }
    //*****************************************************************************
    public static boolean GetStatusFromString(String Query,Context ctx)
    {
        boolean status=false;

        SQLiteDatabase db =  Baseconfig.GetDb(ctx);

        String query = Query;
        Cursor c = db.rawQuery(query, null);
        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {
                    if(c.getString(c.getColumnIndex("status")).toString().equalsIgnoreCase("0"))
                    {
                        return status;//No data
                    }
                    else
                    {
                        return true;//Data available
                    }


                }while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        return status;


    }
    //*****************************************************************************

    public static String CheckEditText(EditText edt)
    {
        String value="";
        if(edt.getText().toString().length()>0)
        {
            value=edt.getText().toString();
        }

        return value;

    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* Loading Autocomplete values */
    public static void LoadValues(AutoCompleteTextView autotxt, Context cntxt,
                                  String Query) {
        try {

            SQLiteDatabase database = Baseconfig.GetDb(cntxt);
            Cursor c = database.rawQuery(Query, null);
            List<String> list = new ArrayList<String>();

            if (c != null)
            {
                if (c.moveToFirst())
                {
                    do
                    {
                        String counrtyname = c.getString(c
                                .getColumnIndex("dvalue"));
                        list.add(counrtyname);
                        //	Log.e("Country", counrtyname);

                    }
                    while (c.moveToNext());
                }
            }

            // spinner2meth(cntxt, list, autotxt);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(cntxt,
                    android.R.layout.simple_spinner_dropdown_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            autotxt.setAdapter(adapter);

            c.close();
            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //*********************************************************************************

    /**
     * Avantari
     * BHI Myth & Fact Implementation
     * 01/2/2017
     * Muthukumar N
     * @param ctx
     * @return
     */

    public static String USER_ID="";
    public static String URL_USERID="http://bhi-server.us-east-1.elasticbeanstalk.com/v1/api/auth/new";
    public static String URL_GET_QUESTIONS="";

    public static String GetUserId(Context ctx)
    {
        String str="";

        SQLiteDatabase db=Baseconfig.GetDb(ctx);
        String Query="select UserId from Bind_DeviceInfo limit 1";
        Cursor c = db.rawQuery(Query,null);
        if(c!=null)
        {
            if(c.moveToFirst())
            {
                do
                {


                    str=c.getString(c.getColumnIndex("UserId"));

                }
                while(c.moveToNext());
            }
        }

        db.close();

        USER_ID=str;

        Log.e("Getting UserId: ",str);

        return str;

    }


    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    //*********************************************************************************
    public static Dialog showCustomDialog(String title, String message, Activity ctx)
    {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View inflatedLayout= inflater.inflate(R.layout.popup_layout, null, false);
        Dialog builderDialog = new Dialog(ctx);
        builderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView messageView = (TextView) inflatedLayout.findViewById(R.id.message);
        TextView titleView = (TextView) inflatedLayout.findViewById(R.id.title);
        ImageView image  = (ImageView) inflatedLayout.findViewById(R.id.load_image);
        // Create an animation instance
        Animation an;// = new RotateAnimation(0.0f, 360.0f, 1, 1);
        an = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // Set the animation's parameters
        an.setDuration(700);               // duration in ms
        an.setRepeatCount(Animation.INFINITE);                // -1 = infinite repeated
        //an.setRepeatMode(Animation.RESTART); // reverses each repeat
        an.setFillAfter(true);               // keep rotation after animation

        // Aply animation to image view
        image.setAnimation(an);

        messageView.setText(message);
        titleView.setText(title);
        builderDialog.setContentView(inflatedLayout);
        builderDialog.setCancelable(false);
        builderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation2;


        //builderDialog.show();
        return builderDialog;

    }

    //******************************************************************************************
    public static void setLocale(String lang,Context ctx,final Class<?> className)
    {
        switch (Language)
        {

            case "অসমীয়া":

                lang="as";
                break;


            case "বাঙালি":

                lang="bn";
                break;


            case "English":

                lang="US";
                break;

            case "ગુજરાતી"://Gujrathi  - ગુજરાતી

                lang="gu";
                break;



            case "हिंदी"://Hindi     - हिंदी

                lang="hi";
                break;


            case "ಕನ್ನಡ"://Kannada   - ಕನ್ನಡ

                lang="kn";

                break;


            case "മലയാളം"://Malayalam - മലയാളം

                lang="ml";
                break;



            case "मराठी"://Marathi   - मराठी

                lang="mr";
                break;


            case "ନୀୟ"://Oriya     - ନୀୟ

                lang="or";
                break;


            case "ਪੰਜਾਬੀ ਦੇ"://Punjabi   - ਪੰਜਾਬੀ ਦੇ

                lang="pa";
                break;


            case "தமிழ்"://Tamil     - தமிழ்

                lang="ta";
                break;


            case "తెలుగు"://Telugu    - తెలుగు

                lang="te";
                break;




        }

        myLocale = new Locale(lang);
        Resources res = ctx.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);


    }

    //*******************************************************************************

   public static void LoadLanguage(Context ctx,String Language,Activity activity)
    {


        switch (Language)
        {
            case "বাঙালি":
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Benagli);

                break;

            case "English":
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_English);

                break;

            case "ગુજરાતી"://Gujrathi  - ગુજરાતી
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Gujrathi);

                break;



            case "हिंदी"://Hindi     - हिंदी
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Hindi);

                break;


            case "ಕನ್ನಡ"://Kannada   - ಕನ್ನಡ

                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Kannada);

                break;


            case "മലയാളം"://Malayalam - മലയാളം
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Malayalam);

                break;



            case "मराठी"://Marathi   - मराठी
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Marathi);

                break;


            case "ନୀୟ"://Oriya     - ନୀୟ
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Oriya);

                break;


            case "ਪੰਜਾਬੀ ਦੇ"://Punjabi   - ਪੰਜਾਬੀ ਦੇ
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Punjabi);

                break;


            case "தமிழ்"://Tamil     - தமிழ்
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Tamil);

                break;


            case "తెలుగు"://Telugu    - తెలుగు
                //applying fontsS
                FontHelper.applyFont(ctx, activity.findViewById(R.id.parent_layout), Baseconfig.Layout_Font_Telugu);

                break;

        }

    }
    //*******************************************************************************


    public static void appendLog(String text)
    {
        File logFile = new File(Environment.getExternalStorageDirectory().getPath() + "/.KIMS/KIMLOG.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();


            }
        }
        try {

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text + "\n");
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    //**********************************************************************************************
    public static boolean isConnected(Context ctx)
    {
        ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    //**********************************************************************************************

    public void deleteFile(String value)
    {
        File file = new File(value);
        file.delete();
    }
    //**********************************************************************************************

    /*****
     * @Ponnusamy
     * Date: 11.2.2017
     * Description: gendrate image
     *
     * *****/

/*
    public static void generateImageFromPdf(Uri pdfUri, Context context, String filename) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(context);
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = context.getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp,filename);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "GenerateImageFromPDF", e.toString());
        }
    }
*/

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/.KIMS/_Image/";
    private static void saveImage(Bitmap bmp,String filename) {
        FileOutputStream out = null;
        try {
            File folder = new File(FOLDER);
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, filename+".png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
                e.printStackTrace();
            }
        }
    }
    //**********************************************************************************************


    //**********************************************************************************************

    /*************
     * @Ponnusamy
     * Date: 15.2.2017
     * Description: insert table
     * **************/

    public void insertMathFact()
    {

        /***********
         * CREATE TABLE `MathFact` (
         `Id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
         `Date`	TEXT,
         `IsOpen`	TEXT
         );
         * ***********/

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        SQLiteDatabase db=Baseconfig.GetDb(ctx);
        String Query="INSERT INTO MathFact (Date,IsOpen)" +
                " VALUES ('"+thisDate+"','0');";
        db.execSQL(Query);db.close();
    }


    public boolean check3DayNotPlayMathFact()
    {

        /***********
         * CREATE TABLE `MathFact` (
         `Id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
         `Date`	TEXT,
         `IsOpen`	TEXT
         );
         * ***********/

        try {
            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            Date todayDate = new Date();
            String thisDate = currentDate.format(todayDate);

            String previousdate1=getPreviousDate(thisDate,-1);
            String previousdate2=getPreviousDate(thisDate,-2);
            String previousdate3=getPreviousDate(thisDate,-3);

            SQLiteDatabase db=Baseconfig.GetDb(ctx);
            String Query="SELECT IFNULL(SUM(IsOpen),0) as Count_value FROM MathFact WHERE Date IN ('"+previousdate1+"','"+previousdate2+"','"+previousdate3+"');";
            Cursor c=db.rawQuery(Query,null);

            if (c != null)
            {
                if (c.moveToFirst())
                {
                    do
                    {
                        int counrtyname = Integer.parseInt(c.getString(c
                                .getColumnIndex("Count_value")));

                        if(counrtyname==0)
                        {
                            return true;
                        }else if(counrtyname>0)
                        {
                            return false;
                        }

                        //	Log.e("Country", counrtyname);

                    }
                    while (c.moveToNext());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Check3Days", e.toString());
            return false;
        }


        return false;
    }

    private String getPreviousDate(String inputDate,int previousdatevalue){
        // inputDate = "15-12-2015"; // for example
        SimpleDateFormat  format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(inputDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            c.add(Calendar.DATE, previousdatevalue);
            inputDate = format.format(c.getTime());
            Log.d("asd", "selected date : "+inputDate);

            System.out.println(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "GetPreviousDate", e.toString());
            inputDate ="";
        }
        return inputDate;
    }


    //**********************************************************************************************

    public static String GetLanguageString()
    {
        String str="";


        switch(Baseconfig.Language)
        {
            case "অসমিয়া"://Assamese

                str="assamese";

                break;

            case "বাঙালি"://Bengali*

                str="bengali";

                break;

            case "English"://English*

                str="english";

                break;

            case "ગુજરાતી"://Gujrathi

                str="gujarathi";

                break;

            case "हिंदी"://Hindi*

                str="hindi";
                break;

            case "ಕನ್ನಡ"://Kannada
                str="kannada";
                break;

            case "മലയാളം"://Malayalam
                str="malayalam";
                break;

            case "मराठी"://Marathi*
                str="marathi";
                break;

            case "ଓଡ଼ିଆ"://Oriya
                str="oriya";
                break;

            case "ਪੰਜਾਬੀ ਦੇ"://Punjabi
                str="punjabi";
                break;

            case "தமிழ்"://Tamil
                str="tamil";
                break;

            case "తెలుగు"://Telgu*
                str="telugu";
                break;
        }


        return str;

    }
    //**********************************************************************************************


}


