package devatech.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

import java.io.ByteArrayOutputStream;
import java.util.List;

import devatech.kims.Baseconfig;
import devatech.kims.R;
import devatech.new_task.BC_ReadMore;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;


    ImageLoaderConfiguration config;
    DisplayImageOptions defaultOptions;
    ImageLoader imageLoader;

    public RecyclerViewAdapter2(Context context, List<ItemObject> itemList) {
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

            //-----------------
            //niversalImageLoader - 620 x405.png ";
            // UNIVERSAL IMAGE LOADER SETUP
            defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new FadeInBitmapDisplayer(300)).build();

            config = new ImageLoaderConfiguration.Builder(context)
                    .defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache())
                    .discCacheSize(100 * 1024 * 1024).build();

            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);


            SetImageFromDrawable(itemList.get(position).getFileName(), holder.imageView,holder);




            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((Activity) context).finish();
                    Intent intent4 = new Intent(context, BC_ReadMore.class);
                    intent4.putExtra("Id", String.valueOf(itemList.get(position).getId()));
                    intent4.putExtra("Lang", "English");
                    view.getContext().startActivity(intent4);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Recycler 2", e.toString());
            //Baseconfig.appendLog("Breast Cancer Recycler Log");
            //Baseconfig.appendLog(e.getMessage());

        }


    }


    @Override
    public int getItemCount() {

        try {

            return this.itemList.size();
        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Recycler 2", e.toString());
            //Baseconfig.appendLog("Breast Cancer Recycler Log");
           // Baseconfig.appendLog(e.getMessage());

            return 0;
        }


    }


    //**********************************************************************************************
    Drawable drawable;
    Bitmap bitmap1, bitmap2;
    ByteArrayOutputStream bytearrayoutputstream;
    byte[] BYTE;

    public void SetImageFromDrawable(String FileName, ImageView img,RecyclerViewHolders holder) {


        switch (FileName) {
            case "BREAST HEALTH, 1":


                //img.setImageBitmap(GetBitmap(R.drawable.img_bc1));

                imageLoader.displayImage("drawable://" + R.drawable.img_bc1, img, defaultOptions);


                break;

            case "BREAST HEALTH, 2":

                //img.setImageBitmap(GetBitmap(R.drawable.img_bc2));

                imageLoader.displayImage("drawable://" + R.drawable.img_bc2, img, defaultOptions);


                break;

            case "BREAST HEALTH, 3":

                //img.setImageBitmap(GetBitmap(R.drawable.img_bc3));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc3, img, defaultOptions);


                break;

            case "BREAST HEALTH, 4":
                //img.setImageBitmap(GetBitmap(R.drawable.img_bc4));
               // imageLoader.displayImage("drawable://" + R.drawable.img_bc4, img, defaultOptions);

                //
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

            case "BREAST HEALTH, 5":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc5));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc5, img, defaultOptions);

                break;

            case "BREAST HEALTH, 6":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc6));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc6, img, defaultOptions);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);


                break;

            case "BREAST HEALTH, 7":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc7));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc7, img, defaultOptions);
                img.setScaleType(ImageView.ScaleType.FIT_CENTER);

                break;

            case "BREAST HEALTH, 8":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc8));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc8, img, defaultOptions);

                break;

            case "BREAST HEALTH, 9":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc9));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc9, img, defaultOptions);

                break;

            case "BREAST HEALTH, 10":

              //  img.setImageBitmap(GetBitmap(R.drawable.img_bc10));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc10, img, defaultOptions);

                break;

            case "BREAST HEALTH, 11":

              //  img.setImageBitmap(GetBitmap(R.drawable.img_bc11));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc11, img, defaultOptions);

                break;

            case "BREAST HEALTH, 12":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc12));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc12, img, defaultOptions);

                break;

            case "BREAST HEALTH, 13":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc13));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc13, img, defaultOptions);

                break;

            case "BREAST HEALTH, 14":

             //   img.setImageBitmap(GetBitmap(R.drawable.img_bc14));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc14, img, defaultOptions);


                break;

            case "BREAST HEALTH, 15":

             //   img.setImageBitmap(GetBitmap(R.drawable.img_bc15));

                imageLoader.displayImage("drawable://" + R.drawable.img_bc15, img, defaultOptions);

                break;

            case "BREAST HEALTH, 16":

             //   img.setImageBitmap(GetBitmap(R.drawable.img_bc16));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc16, img, defaultOptions);

                break;

            case "BREAST HEALTH, 17":

             //   img.setImageBitmap(GetBitmap(R.drawable.img_bc17));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc17, img, defaultOptions);


                break;

            case "BREAST HEALTH, 18":

               // img.setImageBitmap(GetBitmap(R.drawable.img_bc18));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc18, img, defaultOptions);


                break;

            case "BREAST HEALTH, 19":

             //   img.setImageBitmap(GetBitmap(R.drawable.img_bc19));
                imageLoader.displayImage("drawable://" + R.drawable.img_bc19, img, defaultOptions);


                break;

            case "BREAST HEALTH, 20":

                //   img.setImageBitmap(GetBitmap(R.drawable.img_bc19));
                imageLoader.displayImage("drawable://" + R.drawable.maligant, img, defaultOptions);


                break;


        }

        System.gc();

    }

/*
    public Bitmap GetBitmap(int resrcs) {
        try {


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resrcs);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);

            byte[] byteArray = stream.toByteArray();
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            bitmap2 = compressedBitmap;




            //-----------------


            return bitmap2;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }*/


    //**********************************************************************************************

}
