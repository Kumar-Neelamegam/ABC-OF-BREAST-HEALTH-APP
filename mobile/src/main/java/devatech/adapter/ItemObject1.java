package devatech.adapter;


public class ItemObject1 {

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String videoId;
    public String Title;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ItemObject1(String VideoID,String Title) {

        this.videoId=VideoID;
        this.Title=Title;
    }


}
