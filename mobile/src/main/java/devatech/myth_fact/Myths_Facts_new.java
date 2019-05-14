package devatech.myth_fact;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.crash.FirebaseCrash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import cn.pedant.SweetAlert.SweetAlertDialog;
import devatech.dashboard.Task_Navigation;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 1/20/2017.
 */

public class Myths_Facts_new extends Fragment {

    //**********************************************************************************************

    //Declaration
    TextView tv_Question, tv_Count, tv_Content;
    Button btn_myths, btn_facts;
    // CircleProgressView cpv;
    String UserId;

    HttpAsyncTask1 Task1;
    HttpAsyncTask2 Task2;
    //**********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.act_myths_new, container, false);

        /**
         * GET THE LIVE QUESTIONS
         * MUTHUKUMAR
         * 01/02/2017
         */
        try {

            Toast.makeText(getActivity(), getString(R.string.please_wait), Toast.LENGTH_LONG).show();

            UserId = Baseconfig.GetUserId(getActivity());//TO get the userId from the local database

            if (Baseconfig.isConnected(getActivity())) {
                if (UserId.toString().length() > 0) {
                    LoadQuestions(v);
                }

            } else {

                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(getString(R.string.info))
                        .setContentText(getString(R.string.no_internet))
                        .setConfirmText(getString(R.string.str_ok))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismiss();

                                getActivity().finish();
                                Intent intent = new Intent(getActivity(), Task_Navigation.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                Baseconfig.setLocale(Baseconfig.Language, getActivity(), null);
                            }
                        })
                        .show();
            }


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
        }

        //**********************************************************************************************


        return v;
    }

    //**********************************************************************************************


    public void LoadQuestions(View v) {


        // check if you are connected or not
        if (Baseconfig.isConnected(getActivity())) {
            GetQuestionCount(v);


        } else {
            //Baseconfig.SweetDialgos(4, Task_Navigation.this, "Information", "No Internet Connection Available\nTry Again Later", "OK");

            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.info))
                    .setContentText(getString(R.string.no_internet))
                    .setConfirmText(getString(R.string.str_ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), Task_Navigation.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Baseconfig.setLocale(Baseconfig.Language, getActivity(), null);
                        }
                    })
                    .show();

        }
    }
    //**********************************************************************************************


    /**
     * To get the total question count
     * @param v
     */
    public void GetQuestionCount(View v) {

        try {
            // call AsynTask to perform network operation on separate thread
            //new HttpAsyncTask3(v).execute("http://bhi-server.us-east-1.elasticbeanstalk.com/v1/api/users/" + UserId + "/available");

            Task2 = new HttpAsyncTask2(v);
            Task2.execute("http://bhi-server.us-east-1.elasticbeanstalk.com/v1/api/users/" + UserId + "/available");

            Log.e("UserId: ", UserId);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
        }

    }


    //**********************************************************************************************

    /**
     * To get the questions
     * @param v
     */
    public void GetQuestion(View v) {

        try {
            // call AsynTask to perform network operation on separate thread
            //new HttpAsyncTask1(v).execute("http://bhi-server.us-east-1.elasticbeanstalk.com/v1/api/users/" + UserId + "/questions");

            Task1 = new HttpAsyncTask1(v);
            Task1.execute("http://bhi-server.us-east-1.elasticbeanstalk.com/v1/api/users/" + UserId + "/questions");

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
        }

    }



    //**********************************************************************************************


    /**
     * GET DATA FROM BHI - SERVER
     * MUTHUKUMAR N
     * 01/02/2017
     */
    //*********************************************************************************
    public Dialog showCustomDialog(String title, String message, Context ctx) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View inflatedLayout = inflater.inflate(R.layout.popup_layout, null, false);
        Dialog builderDialog = new Dialog(ctx);
        builderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        TextView messageView = (TextView) inflatedLayout.findViewById(R.id.message);
        TextView titleView = (TextView) inflatedLayout.findViewById(R.id.title);
        ImageView image = (ImageView) inflatedLayout.findViewById(R.id.load_image);
        // Create an animation instance
        Animation an;// = new RotateAnimation(0.0f, 360.0f, 1, 1);
        an = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // Set the animation's parameters
        an.setDuration(300);               // duration in ms
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


    public class HttpAsyncTask1 extends AsyncTask<String, Void, String> {

        View v;
        Dialog builderDialog;

        public HttpAsyncTask1(View v) {
            this.v = v;
            builderDialog = showCustomDialog(getString(R.string.please_wait), getString(R.string.getting_question), getActivity());

        }

        @Override
        protected String doInBackground(String... urls) {

            //Toast.makeText(getActivity(), "Link Questions:"+urls[0], Toast.LENGTH_LONG).show();

            Log.e("BHI - Server: ", urls[0]);

            return GET(urls[0]);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            builderDialog.show();

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }


            Task1.cancel(true);

            Task1 = new HttpAsyncTask1(v);

            //Toast.makeText(getActivity(), "Received Questions:" + result, Toast.LENGTH_LONG).show();

            //Log.e("BHI - Server: ", result);

            if (result.toString().equalsIgnoreCase("[]"))//No question available Today
            {
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
                            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                    }
                }, 500);
                //-----------------------
            } else // Question Available Today
            {
                LoadResource(result, v);

            }

        }
    }


    /**
     * To get question count
     */
    String Count = "0";

    public class HttpAsyncTask2 extends AsyncTask<String, Void, String> {

        View v;

        public HttpAsyncTask2(View v) {
            this.v = v;
        }

        @Override
        protected String doInBackground(String... urls) {

            //Toast.makeText(getActivity(), "Link Questions:"+urls[0], Toast.LENGTH_LONG).show();

            Log.e("BHI - Server: ", urls[0]);

            return GET(urls[0]);

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {

                Task2.cancel(true);

                Task2 = new HttpAsyncTask2(v);


                JSONObject obj = new JSONObject(result.toString());

                Count = obj.getString("count");


                if (Count.toString().equalsIgnoreCase("0"))//No question available Today
                {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                //Do something after 100ms
                                fragmentClass = Myths_Facts_new3.class;
                                try {
                                    fragment = (Fragment) fragmentClass.newInstance();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
                                }
                                // Insert the fragment by replacing any existing fragment

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                            } catch (Exception e) {
                                e.printStackTrace();
                                FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
                            }
                        }
                    }, 500);

                } else // Question Available Today
                {
                    Log.e("Result Question Count", result);

                    Baseconfig.QuestionCount = Count;

                    GetQuestion(v);//Avantari - API

                    //LoadResource(result, v);

                }
            } catch (Exception e) {
                e.printStackTrace();
                FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
            }

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


    //*******************

    /**
     * Loading question and explantion live
     * Muthukumar N
     * 02/02/2017
     *
     * @param result
     */

    //Declaration
    AdapterViewFlipper simpleAdapterViewFlipper;


    public void LoadResource(String result, View v) {
        try {

        /*[
        {
            "id": "ciymcms5q000033lj51jgsj3p",
                "question": {
            "english": "Most breast lumps are cancers."
        },
            "serial": 1
        },
        {
            "id": "ciymcms8b000133lj0f26kxcy",
                "question": {
            "english": "Breast cancer can occur at any age."
        },
            "serial": 2
        }
        ]*/


            simpleAdapterViewFlipper = (AdapterViewFlipper) v.findViewById(R.id.simpleAdapterViewFlipper1); // get the reference of AdapterViewFlipper

            // Custom Adapter for setting the data in Views
            CustomAdapter customAdapter;

            ArrayList<FilperGetSet> arrayofUsers;// = new ArrayList<>();


            arrayofUsers = new ArrayList<>();

            //JSONObject mainJson = new JSONObject(result.toString());
            JSONArray jsonArray = new JSONArray(result.toString());
            JSONObject objJson = null;

            String QuestionId = "", Question_Serial = "", Question = "";
            FilperGetSet fliper;

            for (int i = 0; i < jsonArray.length(); i++) {
                objJson = jsonArray.getJSONObject(i);

                QuestionId = objJson.getString("id");
                Question_Serial = objJson.getString("serial");
                //Question = objJson.getJSONObject("question").getString("english");
                Question = objJson.getJSONObject("question").getString(Baseconfig.GetLanguageString());
                fliper = new FilperGetSet();

                fliper.setId(QuestionId);
                fliper.setQuestion(Question);
                //fliper.setCount(Question_Serial+"/"+jsonArray.length());
                fliper.setCount(Question_Serial);

                arrayofUsers.add(fliper);
            }

            customAdapter = new CustomAdapter(getActivity(), arrayofUsers, simpleAdapterViewFlipper);
            simpleAdapterViewFlipper.setAdapter(customAdapter); // set adapter for AdapterViewFlipper


        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
        }

    }
    //**********************************************************************************************



    //**********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;

    private int progress;

    private int progressStatus = 0;
    private Handler handler = new Handler();
    Thread brthread;


    public void GetInitialize(View v) {

        tv_Question = (TextView) v.findViewById(R.id.ques_tv);
        tv_Count = (TextView) v.findViewById(R.id.count_tv);
        tv_Content = (TextView) v.findViewById(R.id.content_tv);

        btn_myths = (Button) v.findViewById(R.id.myths_btn);
        btn_facts = (Button) v.findViewById(R.id.facts_btn);


        // cpv = (CircleProgressView) v.findViewById(R.id.circleView);
    }

    public void Controllisteners(final View v) {


        btn_myths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //-----------------------
                AnimateFadeOut(v);


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        fragmentClass = Myths_Facts_new2.class;
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                    }
                }, 3000);
                //-----------------------


            }
        });


        // LoadProgress();

    }


    public void LoadProgress() {
        {
            new Thread(new Runnable() {
                public void run() {

                    while (progressStatus < 66) {
                        progressStatus = doSomeWork();

                        handler.post(new Runnable() {
                            public void run() {
                                //progressBar.setProgress(progressStatus);
                                //progressBar.setText(String.valueOf(progressStatus));
                                // cpv.setValue(Float.parseFloat(String.valueOf(progressStatus)));
                            }
                        });
                    }

                    handler.post(new Runnable() {
                        public void run() {
                            // ---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                            //cpv.setVisibility(View.GONE);

                        }
                    });
                }

                private int doSomeWork() {
                    try {
                        // ---simulate doing some work---
                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new", e.toString());
                    }
                    return
                            ++progress;
                }
            }).start();

        }
    }

    //**********************************************************************************************

    public void AnimateFadeIn(View v) {

        YoYo.with(Techniques.FadeIn).duration(500).playOn(v.findViewById(R.id.ques_tv));
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(v.findViewById(R.id.count_tv));
        YoYo.with(Techniques.FadeIn).duration(1500).playOn(v.findViewById(R.id.content_tv));

        YoYo.with(Techniques.FadeIn).duration(2000).playOn(v.findViewById(R.id.myths_btn));
        YoYo.with(Techniques.FadeIn).duration(2500).playOn(v.findViewById(R.id.facts_btn));


        //YoYo.with(Techniques.FadeIn).duration(3000).playOn(v.findViewById(R.id.progrs));

    }

    //**********************************************************************************************


    public void AnimateFadeOut(View v) {

        YoYo.with(Techniques.FadeOut).duration(1000).playOn(v.findViewById(R.id.ques_tv));
        YoYo.with(Techniques.FadeOut).duration(1500).playOn(v.findViewById(R.id.count_tv));
        YoYo.with(Techniques.FadeOut).duration(2000).playOn(v.findViewById(R.id.content_tv));

        YoYo.with(Techniques.FadeOut).duration(2500).playOn(v.findViewById(R.id.myths_btn));
        YoYo.with(Techniques.FadeOut).duration(3000).playOn(v.findViewById(R.id.facts_btn));


        //YoYo.with(Techniques.FadeOut).duration(3500).playOn(v.findViewById(R.id.progrs));

    }

    //**********************************************************************************************


}
