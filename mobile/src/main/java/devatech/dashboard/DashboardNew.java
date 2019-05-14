package devatech.dashboard;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.crash.FirebaseCrash;

import java.util.ArrayList;
import java.util.List;

import devatech.adapter.ItemObject;
import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.myth_fact.Myths_Facts_new;
import devatech.new_task.About;
import devatech.new_task.BBI;
import devatech.new_task.BC;
import devatech.new_task.PinkConnection;
import devatech.new_task.Video;

/**
 * Created by Android on 11/30/2016.
 */

public class DashboardNew extends Fragment {


    //*********************************************************************************************

    public GridLayoutManager lLayout;
    CardView card1, card2, card3, card4, card5, card6;
    TextView tv1,tv2,tv3,tv4,tv5,tv6;
    ImageView myth_facts;

    //*********************************************************************************************

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dashboardnew2, container, false);

        try
        {

            GetInitialize(v);

            Controllisterners(v);


            if(Baseconfig.Language.length()>0)
            {
                Baseconfig.setLocale(Baseconfig.Language, getActivity(), null);

            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Dashboard New", e.toString());
        }

        return v;
    }
    //*********************************************************************************************

    public void GetInitialize(View v) {

   /*     List<ItemObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(getActivity(), 2);

        RecyclerView rView = (RecyclerView) v.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        rView.setNestedScrollingEnabled(false);
        RecyclerViewAdapter5 rcAdapter = new RecyclerViewAdapter5(getActivity(), rowListItem);
        rView.setAdapter(rcAdapter);
*/

        card1 = (CardView) v.findViewById(R.id.card_view1);
        card2 = (CardView) v.findViewById(R.id.card_view2);
        card3 = (CardView) v.findViewById(R.id.card_view3);
        card4 = (CardView) v.findViewById(R.id.card_view4);
        card5 = (CardView) v.findViewById(R.id.card_view5);
        card6 = (CardView) v.findViewById(R.id.card_view6);

        myth_facts =(ImageView)v.findViewById(R.id.myth_facts);

        YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .playOn(v.findViewById(R.id.card_view1));

        YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .playOn(v.findViewById(R.id.card_view2));

        YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .playOn(v.findViewById(R.id.card_view3));

        YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .playOn(v.findViewById(R.id.card_view4));

        YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .playOn(v.findViewById(R.id.card_view5));


        YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .playOn(v.findViewById(R.id.card_view6));

        tv1=(TextView)v.findViewById(R.id.txt_1);
        tv2=(TextView)v.findViewById(R.id.txt_2);
        tv3=(TextView)v.findViewById(R.id.txt_3);
        tv4=(TextView)v.findViewById(R.id.txt_4);
        tv5=(TextView)v.findViewById(R.id.txt_5);
        tv6=(TextView)v.findViewById(R.id.txt_6);


        switch (Baseconfig.Language)
        {

            case "অসমিয়া"://Assamese
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_assamese));

                break;

            case "বাঙালি"://Bengali*
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_bengali));

                break;

            case "English"://English*
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3));

                break;

            case "ગુજરાતી"://Gujrathi
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_gujrathi));

                break;

            case "हिंदी"://Hindi*
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_hindi));

                break;

            case "ಕನ್ನಡ"://Kannada
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_kannada));//Need to chng this img

                break;

            case "മലയാളം"://Malayalam
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_malayalam));//Need to chng this img

                break;

            case "मराठी"://Marathi*
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_marathi));

                break;

            case "ଓଡ଼ିଆ"://Oriya
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_oriya));

                break;

            case "ਪੰਜਾਬੀ ਦੇ"://Punjabi
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_punjabi));

                break;

            case "தமிழ்"://Tamil
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_tamil));

                break;

            case "తెలుగు"://Telgu*
                myth_facts.setImageDrawable(getResources().getDrawable(R.drawable.d3_telugu));

                break;



        }


        if(Baseconfig.Language.toString().equalsIgnoreCase("অসমিয়া"))//Assamese
        {
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(),Baseconfig.Layout_Font_Assamese);
            tv1.setTypeface(type);
            tv2.setTypeface(type);
            tv3.setTypeface(type);
            tv4.setTypeface(type);
            tv5.setTypeface(type);
            tv6.setTypeface(type);

            tv1.setText("বিষয়");
            tv2.setText("স্তনৰ কৰ্কটৰোগ");
            tv3.setText("স্তনৰ নিৰোগী কোষকলা");
            tv4.setText("কল্পিত ধাৰণা আৰু সত্যতা");
            tv5.setText("গুলপীয়া সংযোগ");
            tv6.setText("জীৱন ব্যাপী সজাগতা");

        }
        else if(Baseconfig.Language.toString().equalsIgnoreCase("ଓଡ଼ିଆ"))//Oriya
        {
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(),Baseconfig.Layout_Font_Oriya);
            tv1.setTypeface(type);
            tv2.setTypeface(type);
            tv3.setTypeface(type);
            tv4.setTypeface(type);
            tv5.setTypeface(type);
            tv6.setTypeface(type);

            tv1.setText("ବିଷୟରେ");
            tv2.setText("ସ୍ତନ କର୍କଟ");
            tv3.setText("ବେନିନ ସ୍ତନ ସମସ୍ୟା");
            tv4.setText("କଳ୍ପନା ଓ ବାସ୍ତବ");
            tv5.setText("ଗୋଲାପୀ ଯୋଗାଯୋଗ");
            tv6.setText("ଜୀବନ ପ୍ରତି ସଚେତନତା");
        }

    }


    //*********************************************************************************************
    Fragment fragment = null;
    Class fragmentClass;

    public void Controllisterners(View v) {

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), About.class);
                getActivity().startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

              /*  fragmentClass=About.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();*/


            }
        });


        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*    fragmentClass=BC.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
*/

                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), BC.class);
                getActivity().startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);


            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          /*      fragmentClass=Myths_Facts.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
*/

                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), BBI.class);
                getActivity().startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);


                //  getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

            }
        });

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             /*   getActivity().finish();
                Intent intent1 = new Intent(getActivity(), BC_ReadMore.class);
                intent1.putExtra("Id", "");
                intent1.putExtra("Lang", Baseconfig.Language);
                intent1.putExtra("Flag", "FromMenu");
                intent1.putExtra("File", "BREAST HEALTH, 4");
                getActivity().startActivity(intent1);*/



                fragmentClass=Myths_Facts_new.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();

                } catch (Exception e) {
                    e.printStackTrace();
                    FirebaseCrash.logcat(Log.ERROR, "Dashboard New", e.toString());
                }

                //Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();


            }
        });

        card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), PinkConnection.class);
                getActivity().startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                /*fragmentClass=PinkConnection.class;
                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();

                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();*/

            }
        });


        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent intent1 = new Intent(getActivity(), Video.class);
                getActivity().startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                /*fragmentClass=PinkConnection.class;




                try
                {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();*/

            }
        });


    }

    //*********************************************************************************************

    public List<ItemObject> getAllItemList()
    {

        try
        {
            List<ItemObject> allItems = new ArrayList<ItemObject>();


            allItems.add(new ItemObject(1, DashboardNew.this.getResources().getString(R.string.title_about), "", "", "1"));
            allItems.add(new ItemObject(2, DashboardNew.this.getResources().getString(R.string.title_breastcancer), "", "", "2"));
            allItems.add(new ItemObject(3, DashboardNew.this.getResources().getString(R.string.title_benign), "", "", "3"));
            allItems.add(new ItemObject(4, DashboardNew.this.getResources().getString(R.string.title_pink), "", "", "4"));
            allItems.add(new ItemObject(5, DashboardNew.this.getResources().getString(R.string.title_myths), "", "", "5"));


            return allItems;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Dashboard New", e.toString());
            return null;
        }

    }

//**********************************************************************************************


}