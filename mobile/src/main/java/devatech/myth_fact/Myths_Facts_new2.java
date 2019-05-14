package devatech.myth_fact;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.crash.FirebaseCrash;

import devatech.kims.Baseconfig;
import devatech.kims.R;

/**
 * Created by Android on 1/20/2017.
 */

public class Myths_Facts_new2 extends Fragment {

    //**********************************************************************************************

    //Declaration
    TextView tv_Title, tv_Content;
    Button btn_done;

    //**********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.act_myths_new2, container, false);

        try {
            Baseconfig.setLocale(Baseconfig.Language, getActivity(), null);

            GetInitialize(v);

            Controllisteners(v);

            AnimateFadeIn(v);

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new2", e.toString());
        }

        return v;
    }

    //**********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;


    public void GetInitialize(View v) {

        tv_Title = (TextView) v.findViewById(R.id.title_txt);
        tv_Content = (TextView) v.findViewById(R.id.tv_content);
        btn_done = (Button) v.findViewById(R.id.done_btn);

    }

    public void Controllisteners(final View v)
    {

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //-----------------------
                AnimateFadeOut(v);


                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        //Do something after 100ms
                        fragmentClass=Myths_Facts_new3.class;
                        try
                        {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                            FirebaseCrash.logcat(Log.ERROR, "Myhts_Facts_new2", e.toString());
                        }
                        // Insert the fragment by replacing any existing fragment
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();

                    }
                }, 2000);
                //-----------------------

            }
        });


    }

    //**********************************************************************************************

    public void AnimateFadeIn(View v)
    {

        YoYo.with(Techniques.FadeIn).duration(500).playOn(v.findViewById(R.id.title_txt));
        YoYo.with(Techniques.FadeIn).duration(1000).playOn(v.findViewById(R.id.tv_content));
        YoYo.with(Techniques.FadeIn).duration(2000).playOn(v.findViewById(R.id.done_btn));

    }

    //**********************************************************************************************


    public void AnimateFadeOut(View v)
    {

        YoYo.with(Techniques.FadeOut).duration(500).playOn(v.findViewById(R.id.title_txt));
        YoYo.with(Techniques.FadeOut).duration(1000).playOn(v.findViewById(R.id.tv_content));
        YoYo.with(Techniques.FadeOut).duration(2000).playOn(v.findViewById(R.id.done_btn));

    }

    //**********************************************************************************************

}
