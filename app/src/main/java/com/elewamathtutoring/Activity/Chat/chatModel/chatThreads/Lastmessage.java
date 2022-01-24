
package com.elewamathtutoring.Activity.Chat.chatModel.chatThreads;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Lastmessage {

    @SerializedName("id")
    private Long mId;

    @SerializedName("message")
    private String mMessage;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

}
