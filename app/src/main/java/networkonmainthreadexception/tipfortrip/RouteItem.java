package networkonmainthreadexception.tipfortrip;

import java.util.Date;

public class RouteItem {

    private final String TITLE;
    private final String IMAGE_URL;
    private final String PREVIEW_TEXT;
    private final String FULL_TEXT;
    private final String LOCATION;
    private final Date PUBLISH_DATE;

    public RouteItem(String title,
                     String imageUrl,
                     String previewText,
                     String fullText,
                     String location,
                     Date publishDate){
        this.FULL_TEXT = fullText;
        this.IMAGE_URL = imageUrl;
        this.LOCATION = location;
        this.PREVIEW_TEXT = previewText;
        this.PUBLISH_DATE = publishDate;
        this.TITLE = title;
    }

    public String getTitle(){ return TITLE; }
    public String getFullText(){ return FULL_TEXT; }
    public Date getPublishDate() { return PUBLISH_DATE; }
    public String getPreviewText() { return PREVIEW_TEXT; }
    public String getLocation() { return LOCATION; }
    public String getImageUrl() { return IMAGE_URL; }
}
