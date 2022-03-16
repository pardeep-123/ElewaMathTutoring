
package com.elewamathtutoring.Activity.Chat.chatModel.chatThreads;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class ListingModel {

    @SerializedName("body")
    private List<Body> mBody;
    @SerializedName("status")
    private Integer mStatus;
    @SerializedName("success")
    private String mSuccess;

    public List<Body> getBody() {
        return mBody;
    }

    public void setBody(List<Body> body) {
        mBody = body;
    }

    public Integer getStatus() {
        return mStatus;
    }

    public void setStatus(Integer status) {
        mStatus = status;
    }

    public String getSuccess() {
        return mSuccess;
    }

    public void setSuccess(String success) {
        mSuccess = success;
    }

}
