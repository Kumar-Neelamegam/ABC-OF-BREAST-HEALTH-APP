package devatech.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import devatech.kims.R;

public class RecyclerViewHolders extends RecyclerView.ViewHolder {

    public TextView countryName;
    public TextView desc;
    // public ImageView countryPhoto;
    List<ItemObject> itemList;
    LinearLayout read_more;
    Context cntx;

    //public NetworkImageView imageView;
    public ImageView imageView;
    // public ImageLoader imageLoader;
    public ImageLoader imageLoader;

    CardView card;

    //**********************************************************************************************

    public RecyclerViewHolders(View itemView, final List<ItemObject> itemList, Context ctx) {
        super(itemView);
        //itemView.setOnClickListener(this);
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        //imageView=(ImageView)itemView.findViewById(R.id.country_photo);
        //imageView = (NetworkImageView) itemView.findViewById(R.id.country_photo);
        imageView = (ImageView) itemView.findViewById(R.id.country_photo);

        card=(CardView) itemView.findViewById(R.id.card_view);

        this.itemList = itemList;
        cntx = ctx;


        final int position = getPosition();

        //imageLoader=new ImageLoader(ctx);


    }
    //**********************************************************************************************

}