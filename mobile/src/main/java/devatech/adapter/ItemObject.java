package devatech.adapter;


public class ItemObject {

    private String name;
    public String desc;
    private String photo;
    public int Id;
    public String FileName;

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String videoId;

    public ItemObject(int Id,String name, String desc, String photo,String FileName)
    {
        this.name = name;
        this.desc = desc;
        this.photo = photo;
        this.Id=Id;
        this.FileName= FileName;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}
