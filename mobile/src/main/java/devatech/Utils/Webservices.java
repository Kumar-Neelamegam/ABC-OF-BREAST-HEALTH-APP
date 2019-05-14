package devatech.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import devatech.kims.Baseconfig;

/**
 * Created by AndroidGeek on 4/22/2016.
 * /*
 * Author: ${user}
 * Developer name: N.Muthukumar
 * Email: muthukumar.n@devatechinfosystems.com
 */

public class Webservices {


    //**************************************************************************************
    Context ctx;

    Baseconfig bc;


    ArrayList<String> TableName;//=new ArrayList<String>(Arrays.asList("Information", "Magazine", "True Story", "Settings"));

    String TABLE_BIND_FILEUPLOAD = "Bind_FileUpload";
    //GSON object
    Gson gson = new Gson();


    public Webservices(Context ctx) {
        this.ctx = ctx;
        bc = new Baseconfig(ctx);//
    }

    //**************************************************************************************

    public boolean CheckNW() {
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

    //**************************************************************************************

    /**
     * Created by AndroidGeek
     * Developer name: N.Muthukumar
     * Email: muthukumar.n@devatechinfosystems.com
     */

    public void ExecuteAll() {
        Log.e("Executing Webservices", "OnReady Execute All Called");

        if (CheckNW()) {

            try {

                //Import_Mstr_FileCategory();
                //Import_Mstr_FileType();
                //Import_Mstr_LanguageCode();
                //LoadFileCategory();
                //Import_BindFiles();
                Import_BindInspirational();

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }
    //**********************************************************************************************

    public void LoadFileCategory()
    {

        TableName=new ArrayList<String>();

        String Query = "select ServerId from Mstr_FileCategory";

        SQLiteDatabase db = Baseconfig.GetDb(ctx);

        Cursor c = db.rawQuery(Query, null);

        if (c != null)
        {
            if (c.moveToFirst())
            {
                do
                {

                    TableName.add(c.getString(c.getColumnIndex("ServerId")));

                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();

    }

    //**********************************************************************************************

    /**
     * Importing Files
     */
    public void Import_BindFiles() throws SoapFault {


        String str = "";

        String Query = "select IFNULL(max(LanguageID),'0') as LanguageID from Bind_Settings";

        SQLiteDatabase db = Baseconfig.GetDb(ctx);


        for(int j = 0; j<= TableName.size()-1; j++)
        {

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        if (c.getString(c.getColumnIndex("LanguageID")) != null) {
                            str = c.getString(c.getColumnIndex("LanguageID"));
                        } else {
                            str = "0";
                        }

                    } while (c.moveToNext());

                }
            }

            SoapObject request = new SoapObject(Baseconfig.NAMESPACE, "Get_MstrFiles");//change method name

            request.addProperty("LanguageID", str.toString());
            request.addProperty("FileCategoryID", TableName.get(j).toString());

            Log.e("Import Get_MstrFiles", str.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Baseconfig.Webservice_URL);


                androidHttpTransport.call("http://tempuri.org/Get_MstrFiles", envelope);//change method name
            } catch (Exception e) {
                e.printStackTrace();
            }


            SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

            ContentValues values1;

            try {

                if (!resultsRequestSOAP.toString().equalsIgnoreCase("[]")) {
                    JSONObject mainJson = new JSONObject(resultsRequestSOAP.toString());
                    JSONArray jsonArray = mainJson.getJSONArray(Baseconfig.MSTR_ALLERGY_NAME);
                    JSONObject objJson = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);


                        String ServerId = objJson.getString(Baseconfig.Lbl_ID);
                        String LanguageID = objJson.getString(Baseconfig.Lbl_LanguageID);
                        String FileName = objJson.getString(Baseconfig.Lbl_File_Name);
                        String FilePath = objJson.getString(Baseconfig.Lbl_File_Path);
                        String FileSize = objJson.getString(Baseconfig.Lbl_File_Size);
                        String FileTypeID = objJson.getString(Baseconfig.Lbl_FileTypeID);
                        String FileCategoryID = objJson.getString(Baseconfig.Lbl_FileCategoryID);
                        String IsActive = objJson.getString(Baseconfig.Lbl_IsActive);
                        String IsUpdate = objJson.getString(Baseconfig.Lbl_IsUpdate);
                        String ActDate = objJson.getString(Baseconfig.Lbl_ActDate);


                        values1 = new ContentValues();
                        values1.put("ServerId", ServerId);
                        values1.put("LanguageID", LanguageID);
                        values1.put("FileName", FileName);
                        values1.put("FilePath", FilePath);
                        values1.put("FileSize", FileSize);
                        values1.put("FileTypeID", FileTypeID);
                        values1.put("FileCategoryID", FileCategoryID);
                        //values1.put("IsDownloaded",0);
                        values1.put("IsUpdate",IsUpdate);
                        values1.put("IsActive",IsActive);
                        values1.put("ActDate", ActDate);

                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                        boolean GetStatus = Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_ImportFiles where ServerId='" + ServerId + "'", ctx);

                        if (!GetStatus) {
                            db.insert(Baseconfig.TABLE_MSTR_Files, null, values1);
                        } else {
                            db.update(Baseconfig.TABLE_MSTR_Files, values1, "ServerId='" + ServerId + "'", null);
                        }


                        Log.e("Insert Master Files: ", String.valueOf(values1));
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }



        }


       // c.close();
        db.close();

    }


    //**************************************************************************************

    /**
     * Import_Mstr_FileCategory
     */
    public void Import_Mstr_FileCategory() throws SoapFault {



        String str="";

        String Query="select IFNULL(max(IsUpdate),'0') as IsUpdateMax from Mstr_FileCategory";

        SQLiteDatabase db=Baseconfig.GetDb(ctx);

        Cursor c=db.rawQuery(Query,null);

        if(c!=null)
        {
            if(c.moveToFirst())
            {
                do{

                    if(c.getString(c.getColumnIndex("IsUpdateMax"))!=null)
                    {
                        str=c.getString(c.getColumnIndex("IsUpdateMax"));
                    }
                    else
                    {
                        str="0";
                    }

                }while (c.moveToNext());

            }
        }

        SoapObject request = new SoapObject(Baseconfig.NAMESPACE, "Get_MstrFilesCategory");//change method name

        request.addProperty("IsUpdateMax", str.toString());

        Log.e("Import Get_MstrFilesCategory", str.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        try
        {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Baseconfig.Webservice_URL);


            androidHttpTransport.call("http://tempuri.org/Get_MstrFilesCategory", envelope);//change method name
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

        ContentValues values1;

        try
        {

            if (!resultsRequestSOAP.toString().equalsIgnoreCase("[]"))
            {
                JSONObject mainJson = new JSONObject(resultsRequestSOAP.toString());
                JSONArray jsonArray = mainJson.getJSONArray(Baseconfig.MSTR_ALLERGY_NAME);
                JSONObject objJson = null;

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    objJson = jsonArray.getJSONObject(i);

                    String ServerId=objJson.getString(Baseconfig.Lbl_ID);
                    String FileCategory=objJson.getString(Baseconfig.Lbl_FileCategory);
                    String IsActive=objJson.getString(Baseconfig.Lbl_IsActive);
                    String IsUpdate=objJson.getString(Baseconfig.Lbl_IsUpdate);
                    String ActDate=objJson.getString(Baseconfig.Lbl_ActDate);


                    values1 = new ContentValues();
                    values1.put("ServerId",ServerId);
                    values1.put("FileCategory",FileCategory);
                    values1.put("IsActive",IsActive);
                    values1.put("IsUpdate",IsUpdate);
                    values1.put("ActDate",ActDate);

                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                    boolean GetStatus=Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_FileCategory where ServerId='"+ServerId+"'",ctx);

                    if(!GetStatus)
                    {
                        db.insert(Baseconfig.TABLE_MSTR_FILECATEGORY,null,values1);
                        Log.e("Insert TABLE_MSTR_FILECATEGORY: ",String.valueOf(values1));
                    }
                    else
                    {
                        db.update(Baseconfig.TABLE_MSTR_FILECATEGORY,values1,"ServerId='"+ServerId+"'",null);
                        Log.e("Update TABLE_MSTR_FILECATEGORY: ",String.valueOf(values1));
                    }



                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        db.close();

    }

    //**************************************************************************************

    /**
     * Mstr_FileType
     */
    public void Import_Mstr_FileType() throws SoapFault {



        String str="";

        String Query="select IFNULL(max(IsUpdate),'0') as IsUpdateMax from Mstr_FileType";

        SQLiteDatabase db=Baseconfig.GetDb(ctx);

        Cursor c=db.rawQuery(Query,null);

        if(c!=null)
        {
            if(c.moveToFirst())
            {
                do{

                    if(c.getString(c.getColumnIndex("IsUpdateMax"))!=null)
                    {
                        str=c.getString(c.getColumnIndex("IsUpdateMax"));
                    }
                    else
                    {
                        str="0";
                    }

                }while (c.moveToNext());

            }
        }

        SoapObject request = new SoapObject(Baseconfig.NAMESPACE, "Get_MstrFilesType");//change method name

        request.addProperty("IsUpdateMax", str.toString());

        Log.e("Import Get_MstrFilesType", str.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        try
        {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Baseconfig.Webservice_URL);


            androidHttpTransport.call("http://tempuri.org/Get_MstrFilesType", envelope);//change method name
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

        ContentValues values1;

        try
        {

            if (!resultsRequestSOAP.toString().equalsIgnoreCase("[]"))
            {
                JSONObject mainJson = new JSONObject(resultsRequestSOAP.toString());
                JSONArray jsonArray = mainJson.getJSONArray(Baseconfig.MSTR_ALLERGY_NAME);
                JSONObject objJson = null;

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    objJson = jsonArray.getJSONObject(i);

                    String ServerId=objJson.getString(Baseconfig.Lbl_ID);
                    String FileType=objJson.getString(Baseconfig.Lbl_FileType);
                    String IsActive=objJson.getString(Baseconfig.Lbl_IsActive);
                    String IsUpdate=objJson.getString(Baseconfig.Lbl_IsUpdate);
                    String ActDate=objJson.getString(Baseconfig.Lbl_ActDate);


                    values1 = new ContentValues();
                    values1.put("ServerId",ServerId);
                    values1.put("FileType",FileType);
                    values1.put("IsActive",IsActive);
                    values1.put("IsUpdate",IsUpdate);
                    values1.put("ActDate",ActDate);

                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                    boolean GetStatus=Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_FileType where ServerId='"+ServerId+"'",ctx);

                    if(!GetStatus)
                    {
                        db.insert(Baseconfig.TABLE_MSTR_FILETYPE,null,values1);
                    }
                    else
                    {
                        db.update(Baseconfig.TABLE_MSTR_FILETYPE,values1,"ServerId='"+ServerId+"'",null);
                    }


                    Log.e("Insert TABLE_MSTR_FILETYPE: ",String.valueOf(values1));
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        db.close();

    }

    //**************************************************************************************
    /**
     * Mstr_FileType
     */
    public void Import_Mstr_LanguageCode() throws SoapFault {



        String str="";

        String Query="select IFNULL(max(IsUpdate),'0') as IsUpdateMax from Mstr_Language";

        SQLiteDatabase db=Baseconfig.GetDb(ctx);

        Cursor c=db.rawQuery(Query,null);

        if(c!=null)
        {
            if(c.moveToFirst())
            {
                do{

                    if(c.getString(c.getColumnIndex("IsUpdateMax"))!=null)
                    {
                        str=c.getString(c.getColumnIndex("IsUpdateMax"));
                    }
                    else
                    {
                        str="0";
                    }

                }while (c.moveToNext());

            }
        }

        SoapObject request = new SoapObject(Baseconfig.NAMESPACE, "Get_MstrLanguageCode");//change method name

        request.addProperty("IsUpdateMax", str.toString());

        Log.e("Get_MstrLanguageCode", str.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        try
        {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Baseconfig.Webservice_URL);


            androidHttpTransport.call("http://tempuri.org/Get_MstrLanguageCode", envelope);//change method name
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

        ContentValues values1;

        try
        {

            if (!resultsRequestSOAP.toString().equalsIgnoreCase("[]"))
            {
                JSONObject mainJson = new JSONObject(resultsRequestSOAP.toString());
                JSONArray jsonArray = mainJson.getJSONArray(Baseconfig.MSTR_ALLERGY_NAME);
                JSONObject objJson = null;

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    objJson = jsonArray.getJSONObject(i);

                    String ServerId=objJson.getString(Baseconfig.Lbl_ID);
                    String LanguageCode=objJson.getString(Baseconfig.Lbl_LanguageCode);
                    String IsActive=objJson.getString(Baseconfig.Lbl_IsActive);
                    String IsUpdate=objJson.getString(Baseconfig.Lbl_IsUpdate);
                    String ActDate=objJson.getString(Baseconfig.Lbl_ActDate);


                    values1 = new ContentValues();
                    values1.put("ServerId",ServerId);
                    values1.put("Language",LanguageCode);
                    values1.put("IsActive",IsActive);
                    values1.put("IsUpdate",IsUpdate);
                    values1.put("ActDate",ActDate);

                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                    boolean GetStatus=Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Language where ServerId='"+ServerId+"'",ctx);

                    if(!GetStatus)
                    {
                        db.insert(Baseconfig.TABLE_MSTR_LANGUAGE,null,values1);
                    }
                    else
                    {
                        db.update(Baseconfig.TABLE_MSTR_LANGUAGE,values1,"ServerId='"+ServerId+"'",null);
                    }


                    Log.e("Insert TABLE_MSTR_LANGUAGE: ",String.valueOf(values1));
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        db.close();

    }

    //**************************************************************************************




    /****************************
     *
     * @Ponnusamy
     * Date : 8.2.2017
     * Description: Inspirational Table Data Insert Webservice
     *
     * ******************************/
    public void Import_BindInspirational() throws SoapFault {



        String str="";

        String Query="select IFNULL(max(IsUpdate),'0') as IsUpdateMax from Bind_Inspirational";

        SQLiteDatabase db=Baseconfig.GetDb(ctx);

        Cursor c=db.rawQuery(Query,null);

        if(c!=null)
        {
            if(c.moveToFirst())
            {
                do{

                    if(c.getString(c.getColumnIndex("IsUpdateMax"))!=null)
                    {
                        str=c.getString(c.getColumnIndex("IsUpdateMax"));
                    }
                    else
                    {
                        str="0";
                    }

                }while (c.moveToNext());

            }
        }

        SoapObject request = new SoapObject(Baseconfig.NAMESPACE, "getInspirational");//change method name

        request.addProperty("IsUpdateMax", str.toString());

        Log.e("getInspirational", str.toString());

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        try
        {
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Baseconfig.Webservice_URL);


            androidHttpTransport.call("http://tempuri.org/getInspirational", envelope);//change method name
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        SoapPrimitive resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();

        ContentValues values1;

        try
        {

            if (!resultsRequestSOAP.toString().equalsIgnoreCase("[]"))
            {
                JSONObject mainJson = new JSONObject(resultsRequestSOAP.toString());
                JSONArray jsonArray = mainJson.getJSONArray(Baseconfig.MSTR_ALLERGY_NAME);
                JSONObject objJson = null;

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    objJson = jsonArray.getJSONObject(i);

                   /*********
                    *
                    * "Id": 1,
                    "MagazineName": "Feb - April",
                    "VolumeIssue": "Volume 5 Issue 3",
                    "Language": "English",
                    "LanguageCode": "English",
                    "Img": "1",
                    "IsActive": 1,
                    "IsUpdate": 1,
                    "ServerId": 1,
                    "YearPublished": "Year 2015",
                    "FileName": "inspriation1",
                    "Imageurl": "http:\/\/182.18.161.229:8081\/.KIMS/\/assets\/img\/cover_feb_april2015.png",
                    "un_MagazineName": "Feb - April",
                    "un_VolumeIssue": "Volume 5 Issue 3",
                    "un_YearPublished": "Year 2015",
                    "PDF_URL": "http:\/\/www.adobe.com\/content\/dam\/Adobe\/en\/devnet\/acrobat\/pdfs\/pdf_open_parameters.pdf"*******/

                  String MagazineName= objJson.getString("MagazineName");
                  String VolumeIssue= objJson.getString("VolumeIssue");
                  String Language= objJson.getString("Language");
                  String LanguageCode= objJson.getString("LanguageCode");
                  String Img= "";
                  String IsUpdate= objJson.getString("IsUpdate");
                  String ServerId= objJson.getString("Id");
                  String YearPublished= objJson.getString("YearPublished");
                  String FileName= objJson.getString("FileName");
                  String Imageurl= objJson.getString("Imageurl");
                  String PDF_URL= objJson.getString("PDF_URL");




                    values1 = new ContentValues();

                    /*****
                     *
                     *
                     * 	`Id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                     `MagazineName`	TEXT,
                     `VolumeIssue`	TEXT,
                     `Language`	TEXT,
                     `LanguageCode`	TEXT,
                     `Img`	TEXT,
                     `IsActive`	INTEGER,
                     `IsUpdate`	INTEGER,
                     `ServerId`	INTEGER,
                     `YearPublished`	TEXT,
                     `FileName`	TEXT,
                     `Image_URL`	TEXT,
                     `PDF_URL`	TEXT********/

                    values1.put("MagazineName",MagazineName);
                    values1.put("VolumeIssue",VolumeIssue);
                    values1.put("Language",Language);
                    values1.put("LanguageCode",LanguageCode);
                    values1.put("Img",Img);
                    values1.put("IsActive",1);
                    values1.put("IsUpdate",IsUpdate);
                    values1.put("ServerId",ServerId);
                    values1.put("YearPublished",YearPublished);
                    values1.put("FileName",FileName);
                    values1.put("Image_URL",Imageurl);
                   // values1.put("",un_MagazineName);
                   // values1.put("",un_VolumeIssue);
                   // values1.put("",un_YearPublished);
                    values1.put("PDF_URL",PDF_URL);



                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                    boolean GetStatus=Baseconfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_Inspirational where ServerId='"+ServerId+"'",ctx);

                    if(!GetStatus)
                    {
                        db.insert("Bind_Inspirational",null,values1);
                    }
                    else
                    {
                        db.update("Bind_Inspirational",values1,"ServerId='"+ServerId+"'",null);
                    }


                    Log.e("Insert TABLE_MSTR_LANGUAGE: ",String.valueOf(values1));
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        db.close();

    }

}
//**************************************************************************************


