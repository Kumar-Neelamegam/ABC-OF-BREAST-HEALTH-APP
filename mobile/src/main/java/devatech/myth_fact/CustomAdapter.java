package devatech.myth_fact;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.StringEntity;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import cn.pedant.SweetAlert.SweetAlertDialog;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 2/2/2017.
 */

public class CustomAdapter extends BaseAdapter {


    //**********************************************************************************************

    Context context;

    // String[] fruitNames;
    LayoutInflater inflter;

    StringBuilder content;

    String Id;
    int count;

    ArrayList<FilperGetSet> arraylist;

    CardView Question_Layout;
    LinearLayout Answer_Layout;


    //Question Layout
    TextView Count;
    TextView Question;
    Button Btn_Myth, Btn_Fact;


    //Answer Layout
    TextView AnswerTitle;
    TextView Explanation;
    Button Btn_Done;

    AdapterViewFlipper simpleAdapterViewFlipper;

    //**********************************************************************************************


    public CustomAdapter(Context applicationContext, ArrayList<FilperGetSet> arrayofUsers, AdapterViewFlipper simpleAdapterViewFlipper)
    {
        this.context = applicationContext;

        arraylist = arrayofUsers;

        inflter = (LayoutInflater.from(applicationContext));

        this.simpleAdapterViewFlipper=simpleAdapterViewFlipper;

    }


    //**********************************************************************************************


    @Override
    public int getCount() {
        // return 0;
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //**********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;
    FilperGetSet users;

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        view = inflter.inflate(R.layout.item_myth_fact, null);


        Question_Layout = (CardView) view.findViewById(R.id.question_layout);
        Answer_Layout = (LinearLayout) view.findViewById(R.id.answer_layout);


        //Question Layout
        Count = (TextView) view.findViewById(R.id.count_tv);
        Question = (TextView) view.findViewById(R.id.content_tv);
        Btn_Myth = (Button) view.findViewById(R.id.myths_btn);
        Btn_Fact = (Button) view.findViewById(R.id.facts_btn);


        //Answer Layout
        AnswerTitle = (TextView) view.findViewById(R.id.title_txt);
        Explanation = (TextView) view.findViewById(R.id.tv_content);
        Btn_Done = (Button) view.findViewById(R.id.done_btn);


        users = arraylist.get(position);

        Log.e("User Count : arraylist.get(position)=",String.valueOf(users.getCount()));
        Log.e("User Count : arraylist.get(position)=",String.valueOf(users.getCount()));
        Log.e("User Count : arraylist.get(position)=",String.valueOf(users.getCount()));

        /*if(arraylist.size()!=0)
        {
            Count.setText(String.valueOf(arraylist.size()));

        }*/

        AnimateFadeIn1(view);//Fading In the Question Content

        //Question Part
        Question.setText(users.getQuestion());// What is?
       // Count.setText(users.getCount());//+" / "+arraylist.size());//Baseconfig.QuestionCount);// 3/5

        Count.setText((position+1) +" / "+arraylist.size());

        Log.e("Q Count : arraylist.get(position)=",String.valueOf((position+1)) +" / "+String.valueOf(arraylist.size()));
        Log.e("Q Count : arraylist.get(position)=",String.valueOf((position+1)) +" / "+String.valueOf(arraylist.size()));
        Log.e("Q Count : arraylist.get(position)=",String.valueOf((position+1)) +" / "+String.valueOf(arraylist.size()));

        //Answer Part
        AnswerTitle.setText(users.getAnswer());//
        Explanation.setText(users.getExplanation());

        final View finalView = view;
        Btn_Myth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (Baseconfig.isConnected(context)) {


                        AnimateFadeOut1(finalView);//Fading our the question content

                        LoadAnswer(finalView, users.getId(), 1);//Avantari - API

                    } else {
                        //Baseconfig.SweetDialgos(4, Task_Navigation.this, "Information", "No Internet Connection Available\nTry Again Later", "OK");

                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Information")
                                .setContentText("No Internet Connection Available\nGet connected with internet to stay updated with latest Myth / Fact")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        CallExplore(finalView);

                                    }
                                })
                                .show();

                    }

                   // Toast.makeText(context,"Total ArraySize: Btn_Myth"+ String.valueOf(arraylist.size()),Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                    FirebaseCrash.logcat(Log.ERROR, "Customer_Adapter", e.toString());
                }


            }
        });


        final View finalView1 = view;
        Btn_Fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {


                    if (Baseconfig.isConnected(context)) {

                        AnimateFadeOut1(finalView1);//Fading our the question content

                        LoadAnswer(finalView1, users.getId(), 2);//Avantari - API

                    } else {
                        //Baseconfig.SweetDialgos(4, Task_Navigation.this, "Information", "No Internet Connection Available\nTry Again Later", "OK");

                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Information")
                                .setContentText("No Internet Connection Available\nGet connected with internet to stay updated with latest Myth / Fact")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        CallExplore(finalView1);
                                    }
                                })
                                .show();

                    }

                    //Toast.makeText(context,"Total ArraySize: Btn_Fact"+ String.valueOf(arraylist.size()),Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


        //Toast.makeText(context,"Total ArraySize:"+ String.valueOf(arraylist.size()),Toast.LENGTH_LONG).show();

        final View finalView2 = view;
        Btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /**********
                 *@Ponnusamy
                 * Date: 15.2.2017
                 * Description: insert current date open 1
                 *
                 * *********/
                updateOpenMathFact();

                Log.e("Arraylist Size 1: ", String.valueOf(arraylist.size()));
                Log.e("Arraylist Size 1: ", String.valueOf(arraylist.size()));
                Log.e("Arraylist Size 1: ", String.valueOf(arraylist.size()));

                Log.e("Question COunt 1: ", String.valueOf(users.getCount()));
                Log.e("Question COunt 1: ", String.valueOf(users.getCount()));
                Log.e("Question COunt 1: ", String.valueOf(users.getCount()));

                Log.e("Q Count : arraylist.get(position)=",String.valueOf((position+1)) +" / "+String.valueOf(arraylist.size()));

                //if(arraylist.size()== Integer.parseInt(users.getCount()))
                if(arraylist.size()== (position+1))
                {

                    CallExplore(finalView2);

                }
                else
                {


                    //simpleAdapterViewFlipper.getSelectedItemPosition();

                    Answer_Layout.setVisibility(View.GONE);

                    simpleAdapterViewFlipper.showNext();

                  ///  arraylist.remove(0);//Hardcoded to remove 0th of array
                    notifyDataSetChanged();

                    Log.e("Arraylist Size 2: ", String.valueOf(arraylist.size()));
                    Log.e("Arraylist Size 2: ", String.valueOf(arraylist.size()));
                    Log.e("Arraylist Size 2: ", String.valueOf(arraylist.size()));

                    Log.e("Question COunt 2: ", String.valueOf(users.getCount()));
                    Log.e("Question COunt 2: ", String.valueOf(users.getCount()));
                    Log.e("Question COunt 2: ", String.valueOf(users.getCount()));

                    //Toast.makeText(context,"Array Position: "+ arraylist.indexOf(position), Toast.LENGTH_LONG).show();

                    //Toast.makeText(context,"Current Position: "+ String.valueOf(arraylist.get(position)), Toast.LENGTH_LONG).show();


                    //Toast.makeText(context,"Total ArraySize Clicked: Btn_Done"+ String.valueOf(arraylist.size()+"\nPosition: "+position),Toast.LENGTH_LONG).show();

                }

               // Toast.makeText(context,"Adapter position: "+ simpleAdapterViewFlipper.getSelectedItemPosition(), Toast.LENGTH_LONG).show();



            }
        });


        return view;
    }
    //**********************************************************************************************


    private void updateOpenMathFact() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        SQLiteDatabase db= Baseconfig.GetDb(context);
        String Query="UPDATE MathFact " +
                "SET IsOpen = '1' " +
                "WHERE Date='"+thisDate+"' ;";
        db.execSQL(Query);
        db.close();
    }
    //**********************************************************************************************







    public void CallExplore(View view)
    {
        //Go to explore page
        //-----------------------
        AnimateFadeOut(view);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                fragmentClass = Myths_Facts_new3.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    FirebaseCrash.logcat(Log.ERROR, "Custom Adapter", e.toString());
                }
                // Insert the fragment by replacing any existing fragment
                //Activity activity = (Activity) context;
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

            }
        }, 2000);
        //-----------------------

    }























    public void LoadAnswer(View v, String QuestionId, int Id) {

        try {
            String UserId = Baseconfig.GetUserId(context);//TO get the userId from the local database

            // call AsynTask to perform network operation on separate thread
            new HttpAsyncTask1(v, QuestionId, UserId, Id).execute("http://bhi-server.us-east-1.elasticbeanstalk.com/v1/api/users/user_id/questions/question_id/answer");

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "CustomAdapter", e.toString());
        }

    }
    //**********************************************************************************************


    /**
     * GET DATA FROM BHI - SERVER
     * MUTHUKUMAR N
     * 01/02/2017
     */

    Dialog builderDialog;

    public class HttpAsyncTask1 extends AsyncTask<String, Void, String> {

        View v;
        String QuestionId;
        String UserId;
        int Id;

        public HttpAsyncTask1(View v, String QuestionId, String UserId, int Id) {
            this.v = v;
            this.QuestionId = QuestionId;
            this.UserId = UserId;
            this.Id = Id;
        }

        @Override
        protected String doInBackground(String... urls) {

            //Toast.makeText(getActivity(), "Link Questions:"+urls[0], Toast.LENGTH_LONG).show();

            Log.e("BHI - Server: ", urls[0]);

            return POST(urls[0], QuestionId, UserId, Id);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builderDialog = Baseconfig.showCustomDialog(context.getString(R.string.please_wait), context.getString(R.string.str_verify_ans), ((Activity) context));

            builderDialog.show();
        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }

            //Toast.makeText(context, "Received Questions:" + result, Toast.LENGTH_LONG).show();

            Log.e("BHI - Server: ", result);

            LoadResource(result, v);

        }
    }

    //*******************

    public void LoadResource(String result, View v) {

        try {

/*
        {
            "score": 100,
                "explanation": {
            "english": "9 out of 10 breast lumps are not cancers. However, it is vitally important to investigate the breast lump by way of Triple assessment (Clinical Breast Examination by a Specialist, Bilateral Mammogram and Ultrasound guided core needle biopsy) in order to obtain a definitive diagnosis"
        },
            "correct": true
        }*/

            AnimateFadeIn(v);

            Question_Layout.setVisibility(View.GONE);
            Answer_Layout.setVisibility(View.VISIBLE);

            JSONObject obj = new JSONObject(result);

            String Score = obj.getString("score");
            String Str_Explanation = obj.getJSONObject("explanation").getString(Baseconfig.GetLanguageString());
            String Status = obj.getString("correct");

            Baseconfig.TodayScore = Score;


            SaveScore(Score);

            if (Status.toString().equalsIgnoreCase("true")) {
                Status = context.getString(R.string.ans_correct);//"You're Right";
            } else {
                Status = context.getString(R.string.ans_not_quite);//"Not Quite!";
            }


            AnswerTitle.setText(Status);
            Explanation.setText(Str_Explanation);


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "CustomAdapter", e.toString());
        }


    }

    //*******************
    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = Baseconfig.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }


    /**
     * HTTP Avantari
     * GET & POST METHOD
     *
     * @param url
     * @return
     * @Muthukumar 01/2/2017
     */
    public static String POST(String url, String QuestionId, String UserId, int Id) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("question_id", QuestionId);

            if (Id == 1)//Myth
            {
                jsonObject.accumulate("answer", "myth");


            } else if (Id == 2)//Fact
            {

                jsonObject.accumulate("answer", "fact");

            }


            jsonObject.accumulate("user_id", UserId);

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)
                result = Baseconfig.convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;


    }


    //**********************************************************************************************

    public void SaveScore(String Score)
    {
        String language_code = Score.toString();

        savePreferences("SCORE", language_code.toString());

    }

    //**********************************************************************************************

    public void savePreferences(String key, String value)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    //**********************************************************************************************
    //Answer Page FadeIn - FadeOut

    public void AnimateFadeIn(View v) {

        YoYo.with(Techniques.FadeIn).duration(500).playOn(v.findViewById(R.id.title_txt));
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(v.findViewById(R.id.tv_content));
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(v.findViewById(R.id.done_btn));

    }

    //**********************************************************************************************


    public void AnimateFadeOut(View v) {

        YoYo.with(Techniques.FadeOut).duration(500).playOn(v.findViewById(R.id.title_txt));
        YoYo.with(Techniques.FadeOut).duration(1000).playOn(v.findViewById(R.id.tv_content));
        YoYo.with(Techniques.FadeOut).duration(2000).playOn(v.findViewById(R.id.done_btn));

    }

    //**********************************************************************************************
    //Question Page FadeIn - FadeOut

    public void AnimateFadeIn1(View v) {

        YoYo.with(Techniques.FadeIn).duration(500).playOn(v.findViewById(R.id.ques_tv));
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(v.findViewById(R.id.count_tv));
        YoYo.with(Techniques.FadeIn).duration(1500).playOn(v.findViewById(R.id.content_tv));

        YoYo.with(Techniques.FadeIn).duration(2000).playOn(v.findViewById(R.id.myths_btn));
        YoYo.with(Techniques.FadeIn).duration(2500).playOn(v.findViewById(R.id.facts_btn));


    }

    //**********************************************************************************************


    public void AnimateFadeOut1(View v) {

        YoYo.with(Techniques.FadeOut).duration(1000).playOn(v.findViewById(R.id.ques_tv));
        YoYo.with(Techniques.FadeOut).duration(1500).playOn(v.findViewById(R.id.count_tv));
        YoYo.with(Techniques.FadeOut).duration(2000).playOn(v.findViewById(R.id.content_tv));

        YoYo.with(Techniques.FadeOut).duration(2500).playOn(v.findViewById(R.id.myths_btn));
        YoYo.with(Techniques.FadeOut).duration(3000).playOn(v.findViewById(R.id.facts_btn));

    }

    //**********************************************************************************************


}