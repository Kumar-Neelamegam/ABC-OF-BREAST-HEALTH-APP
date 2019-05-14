package devatech.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.crash.FirebaseCrash;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.new_task.BBI_ReadMore;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;


    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView, itemList, context);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {


        try {
            holder.countryName.setText(itemList.get(position).getName());
            SetImageFromDrawable(itemList.get(position).getFileName(), holder.imageView);

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((Activity) context).finish();
                    Intent intent4 = new Intent(context, BBI_ReadMore.class);
                    intent4.putExtra("Id", String.valueOf(itemList.get(position).getId()));
                    intent4.putExtra("Lang", "English");
                    view.getContext().startActivity(intent4);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Recycler", e.toString());
           // Baseconfig.appendLog("BBI Recycler Log");
           // Baseconfig.appendLog(e.getMessage());

        }


    }

    @Override
    public int getItemCount() {


        try {

            return this.itemList.size();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Recycler", e.toString());
            //Baseconfig.appendLog("BBI Recycler Log");
           // Baseconfig.appendLog(e.getMessage());

            return 0;
        }

    }


    //**********************************************************************************************


    public void SetImageFromDrawable(String FileName, ImageView img) {

        //-----------------
        //niversalImageLoader - 620 x405.png ";
        // UNIVERSAL IMAGE LOADER SETUP
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        // END - UNIVERSAL IMAGE LOADER SETUP


        switch (FileName) {
            case "BREAST HEALTH, 1":

                //img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi1));

                imageLoader.displayImage("drawable://" + R.drawable.img_bbi1, img, defaultOptions);


                break;

            case "BREAST HEALTH, 2":

               // img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bc2));

                imageLoader.displayImage("drawable://" + R.drawable.img_bc2, img, defaultOptions);

                break;


            case "BREAST HEALTH, 3":
               // img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bc4));

                switch (Baseconfig.Language)
                {


                    case "অসমিয়া"://Assamese
                        imageLoader.displayImage("drawable://" +R.drawable.d3_assamese, img, defaultOptions);

                        break;

                    case "বাঙালি"://Bengali*
                        imageLoader.displayImage("drawable://" +R.drawable.d3_bengali, img, defaultOptions);

                        break;

                    case "English"://English*
                        imageLoader.displayImage("drawable://" +R.drawable.d3, img, defaultOptions);

                        break;

                    case "ગુજરાતી"://Gujrathi
                        imageLoader.displayImage("drawable://" +R.drawable.d3_gujrathi, img, defaultOptions);

                        break;

                    case "हिंदी"://Hindi*
                        imageLoader.displayImage("drawable://" +R.drawable.d3_hindi, img, defaultOptions);

                        break;

                    case "ಕನ್ನಡ"://Kannada
                        imageLoader.displayImage("drawable://" +R.drawable.d3_kannada, img, defaultOptions);

                        break;

                    case "മലയാളം"://Malayalam
                        imageLoader.displayImage("drawable://" +R.drawable.d3_malayalam, img, defaultOptions);

                        break;

                    case "मराठी"://Marathi*
                        imageLoader.displayImage("drawable://" +R.drawable.d3_marathi, img, defaultOptions);

                        break;

                    case "ଓଡ଼ିଆ"://Oriya
                        imageLoader.displayImage("drawable://" +R.drawable.d3_oriya, img, defaultOptions);

                        break;

                    case "ਪੰਜਾਬੀ ਦੇ"://Punjabi
                        imageLoader.displayImage("drawable://" +R.drawable.d3_punjabi, img, defaultOptions);

                        break;

                    case "தமிழ்"://Tamil
                        imageLoader.displayImage("drawable://" +R.drawable.d3_tamil, img, defaultOptions);

                        break;

                    case "తెలుగు"://Telgu*
                        imageLoader.displayImage("drawable://" +R.drawable.d3_telugu, img, defaultOptions);

                        break;



                    default:
                        imageLoader.displayImage("drawable://" +R.drawable.img_bc4, img, defaultOptions);
                        break;

                }


                break;

            case "BREAST HEALTH, 4":

              //  img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi2));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi2, img, defaultOptions);

                break;

            case "BREAST HEALTH, 5":

                //img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi3));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi3, img, defaultOptions);

                break;

            case "BREAST HEALTH, 6":

               // img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi4));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi4, img, defaultOptions);

                break;

            case "BREAST HEALTH, 7":

               // img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi5));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi5, img, defaultOptions);

                break;

            case "BREAST HEALTH, 8":

              //  img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi6));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi6, img, defaultOptions);

                break;

            case "BREAST HEALTH, 9":

              //  img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi7));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi7, img, defaultOptions);

                break;

            case "BREAST HEALTH, 10":

            //    img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi8));
                imageLoader.displayImage("drawable://" + R.drawable.img_bbi8, img, defaultOptions);


                break;

            case "BREAST HEALTH, 11":

                //    img.setImageDrawable(context.getResources().getDrawable(R.drawable.img_bbi8));
                imageLoader.displayImage("drawable://" + R.drawable.maligant, img, defaultOptions);


                break;



        }

    }


    //**********************************************************************************************


}
