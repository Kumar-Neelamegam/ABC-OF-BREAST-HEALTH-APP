package devatech.myth_fact;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.crash.FirebaseCrash;

import at.grabner.circleprogress.CircleProgressView;
import devatech.dashboard.DashboardNew;
import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 1/20/2017.
 */

public class Myths_Facts_new3 extends Fragment {

    //**********************************************************************************************

    //Declaration
    TextView tv_Title;
    LinearLayout layout_progress;
    Button btn_explore;
    CircleProgressView cpv;

    //**********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.act_myths_new3, container, false);

        try
        {
            Baseconfig.setLocale(Baseconfig.Language, getActivity(), null);

            GetInitialize(v);

            Controllisteners(v);

            AnimateFadeIn(v);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new3", e.toString());
        }

        return v;
    }

    //**********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;


    public void GetInitialize(View v)
    {

        tv_Title = (TextView) v.findViewById(R.id.title_txt);
        layout_progress = (LinearLayout)v.findViewById(R.id.progress_layout);
        btn_explore = (Button) v.findViewById(R.id.explore_btn);

        cpv = (CircleProgressView) v.findViewById(R.id.circleView);


        loadSavedPreferences();

    }

    //**********************************************************************************************
    SharedPreferences sharedPreferences;
    String Score;

    void loadSavedPreferences()
    {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Score = sharedPreferences.getString("SCORE","0");

       // Toast.makeText(getActivity(), "Present Score: "+ Score, Toast.LENGTH_SHORT).show();

    }
    //**********************************************************************************************

    public void Controllisteners(final View v) {

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AnimateFadeOut(v);

           /*     SQLiteDatabase db=Baseconfig.GetDb(getActivity());
                db.execSQL("Delete from Bind_DeviceInfo");
                db.close();
*/


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        //Do something after 100ms
                        fragmentClass=DashboardNew.class;
                        try
                        {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new3", e.toString());
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                    }
                }, 1000);
                //-----------------------

            }
        });


        LoadProgress();

    }


    private int progress;

    private int progressStatus = 0;
    private Handler handler = new Handler();
    Thread brthread;

    public void LoadProgress()
    {
        {
            new Thread(new Runnable() {
                public void run() {

                    while (progressStatus < Integer.parseInt(Score))
                    {
                        progressStatus = doSomeWork();

                        handler.post(new Runnable() {
                            public void run() {
                                //progressBar.setProgress(progressStatus);
                                //progressBar.setText(String.valueOf(progressStatus));
                                cpv.setValue(Float.parseFloat(String.valueOf(progressStatus)));
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
                        FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new3", e.toString());
                    }
                    return
                            ++progress;
                }
            }).start();

        }
    }
    //**********************************************************************************************

    public void AnimateFadeIn(View v)
    {

        YoYo.with(Techniques.FadeIn).duration(500).playOn(v.findViewById(R.id.title_txt));
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(v.findViewById(R.id.progress_layout));
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(v.findViewById(R.id.explore_layout));

    }

    //**********************************************************************************************


    public void AnimateFadeOut(View v) {

        YoYo.with(Techniques.FadeOut).duration(500).playOn(v.findViewById(R.id.title_txt));
        YoYo.with(Techniques.FadeOut).duration(1000).playOn(v.findViewById(R.id.progress_layout));
        YoYo.with(Techniques.FadeOut).duration(2000).playOn(v.findViewById(R.id.explore_layout));

    }

    //**********************************************************************************************

}
