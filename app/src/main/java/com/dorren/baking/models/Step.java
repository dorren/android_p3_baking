package com.dorren.baking.models;

import java.net.URL;

/**
 * Created by dorrenchen on 6/18/17.
 * id: 0,
 shortDescription: "Recipe Introduction",
 description: "Recipe Introduction",
 videoURL: "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
 thumbnailURL: ""
 */

public class Step {
    public static final String STEP_INDEX_KEY = "step_index_key";

    private int mId;
    private String mShortDescription, mDescription, mThumbnailURL;
    private URL mVideoURL;

    public Step(int id, String shortDesc, String desc, URL url, String thumbURL){
        this.mId = id;
        this.mShortDescription = shortDesc;
        this.mDescription = desc;
        this.mVideoURL = url;
        this.mThumbnailURL = thumbURL;
    }

    public int getId() { return mId; }
    public String getShortDescription() { return mShortDescription; }
    public String getDescription() { return  mDescription; }
    public URL getVideoURL() { return mVideoURL; }
    public String getmThumbnailURL() {return mThumbnailURL;}

    public String toString() {
        return "step " + mId + " " + mShortDescription;
    }
}
