
package com.elewamathtutoring.Activity.Chat.chatModel;
import com.google.gson.annotations.SerializedName;


public class DeleteChatModel {

    @SerializedName("status")
    private Integer mStatus;
    @SerializedName("success")
    private String mSuccess;
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
