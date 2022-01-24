
package com.elewamathtutoring.Activity.Chat.chatModel;

import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("first_name")
    private String mFirstName;
    @SerializedName("image")
    private String mImage;
    @SerializedName("last_name")
    private String mLastName;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

}
