
package com.elewamathtutoring.Activity.Chat.chatModel;

import com.google.gson.annotations.SerializedName;


import java.util.List;

public class ChatMessagesModel {

    @SerializedName("data")
    private List<Chat_list_bothside> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Boolean mStatus;

    public List<Chat_list_bothside> getData() {
        return mData;
    }

    public void setData(List<Chat_list_bothside> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
