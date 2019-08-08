package com.rocca.umrah.kafala.request;

import com.google.gson.annotations.SerializedName;

public class UpdatePostDate {

    @SerializedName("postId")
    private int postId;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
