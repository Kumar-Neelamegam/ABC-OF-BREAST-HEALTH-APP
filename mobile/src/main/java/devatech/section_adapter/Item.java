package devatech.section_adapter;

import android.graphics.drawable.Drawable;

/**
 * Created by bpncool on 2/23/2016.
 */
public class Item {

    private final String name;
    private final int id;
    private String img;
    private String series;
    private String PDF_URL="";

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }




    public Item(String name,String series, int id, String image,String PDF_URL) {
        this.name = name;
        this.id = id;
        this.img=image;
        this.series=series;
        this.PDF_URL=PDF_URL;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getPDF_URL() {
        return PDF_URL;
    }

    public void setPDF_URL(String PDF_URL) {
        this.PDF_URL = PDF_URL;
    }

}
