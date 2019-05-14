package devatech.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.crash.FirebaseCrash;

import java.util.List;

import devatech.kims.R;
import devatech.new_task.BasicPlayerActivity;

import static android.provider.MediaStore.Video.Thumbnails.VIDEO_ID;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.VideoInfoHolder>  implements YouTubePlayer.OnInitializedListener{

    private List<ItemObject1> itemList;
    private Context context;

    private static final String YoutubeDeveloperKey = "AIzaSyAQf8fBXhxcYKXwGC685Lsps8wX4M6TVNQ";
    private static final int RECOVERY_DIALOG_REQUEST = 1;

    //**********************************************************************************************
    YouTubePlayer YPlayer;

    public RecyclerViewAdapter3(Context context,List<ItemObject1> itemList)
    {

        this.itemList = itemList;
        this.context = context;

    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_video_item, parent, false);
        return new VideoInfoHolder(itemView);
    }

    //**********************************************************************************************

    String YoutUbeId = "";

    @Override
    public void onBindViewHolder(final VideoInfoHolder  holder, final int position) {


        try {



            final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                @Override
                public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                }

                @Override
                public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                    youTubeThumbnailView.setVisibility(View.VISIBLE);
                    holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                }
            };

            holder.youTubeThumbnailView.initialize(YoutubeDeveloperKey, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                    youTubeThumbnailLoader.setVideo(itemList.get(position).getVideoId());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    //write something for failure
                }
            });

            holder.Title.setText(itemList.get(position).getTitle());

        } catch (Exception e) {
            e.printStackTrace();
            FirebaseCrash.logcat(Log.ERROR, "Recycler 3", e.toString());
        }


    }
    //**********************************************************************************************

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored)
    {
       /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        Toast.makeText(context, "Failured to Initialize!", Toast.LENGTH_LONG).show();


    }


    //**********************************************************************************************

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {

        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
        }

        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }

    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {

        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };
    //**********************************************************************************************


    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;
        TextView Title;

        public VideoInfoHolder(View itemView)
        {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            Title=(TextView)itemView.findViewById(R.id.txt_title);
        }

        @Override
        public void onClick(View v) {

           // Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, YoutubeDeveloperKey,  itemList.get(getPosition()).getVideoId());
      //context.startActivity(intent);

          /*  LayoutInflater inflater = LayoutInflater.from(context);
            View inflatedLayout= inflater.inflate(R.layout.video_youtube_layout, null, false);
            Dialog builderDialog = new Dialog(context);
            builderDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            TextView messageView = (TextView) inflatedLayout.findViewById(R.id.message);
            TextView titleView = (TextView) inflatedLayout.findViewById(R.id.title);
           // ImageView image  = (ImageView) inflatedLayout.findViewById(R.id.load_image);

            messageView.setText("Breast Awareness");
            titleView.setText("Video");
            builderDialog.setContentView(inflatedLayout);
            builderDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation2;


            builderDialog.show();*/

            Intent video=new Intent(context, BasicPlayerActivity.class);
            video.putExtra("VideoKey", YoutubeDeveloperKey);
            video.putExtra("VideoId",  itemList.get(getPosition()).getVideoId());
            context.startActivity(video);



        }
    }
    //**********************************************************************************************


}
