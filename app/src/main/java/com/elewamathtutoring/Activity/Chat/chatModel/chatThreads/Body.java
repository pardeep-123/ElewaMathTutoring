
package com.elewamathtutoring.Activity.Chat.chatModel.chatThreads;


import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Body {
    @SerializedName("userTwochat_notification")
    private String muserTwochat_notification;
    @SerializedName("userOnechat_notification")
    private String muserOnechat_notification;

    @SerializedName("chat_notification")
    private String mChatNotification;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("lastMessageId")
    private Long mLastMessageId;
    @SerializedName("lastmessage")
    private Lastmessage mLastmessage;
    @SerializedName("type")
    private Long mType;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("userOne")
    private Long mUserOne;
    @SerializedName("user_onee")
    private UserOnee mUserOnee;
    @SerializedName("user_too")
    private UserToo mUserToo;
    @SerializedName("userTwo")
    private Long mUserTwo;

    public String getMuserTwochat_notification() {
        return muserTwochat_notification;
    }

    public void setMuserTwochat_notification(String muserTwochat_notification) {
        this.muserTwochat_notification = muserTwochat_notification;
    }

    public String getMuserOnechat_notification() {
        return muserOnechat_notification;
    }

    public void setMuserOnechat_notification(String muserOnechat_notification) {
        this.muserOnechat_notification = muserOnechat_notification;
    }

    public String getmChatNotification() {
        return mChatNotification;
    }

    public void setmChatNotification(String mChatNotification) {
        this.mChatNotification = mChatNotification;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getLastMessageId() {
        return mLastMessageId;
    }

    public void setLastMessageId(Long lastMessageId) {
        mLastMessageId = lastMessageId;
    }

    public Lastmessage getLastmessage() {
        return mLastmessage;
    }

    public void setLastmessage(Lastmessage lastmessage) {
        mLastmessage = lastmessage;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Long getUserOne() {
        return mUserOne;
    }

    public void setUserOne(Long userOne) {
        mUserOne = userOne;
    }

    public UserOnee getUserOnee() {
        return mUserOnee;
    }

    public void setUserOnee(UserOnee userOnee) {
        mUserOnee = userOnee;
    }

    public UserToo getUserToo() {
        return mUserToo;
    }

    public void setUserToo(UserToo userToo) {
        mUserToo = userToo;
    }

    public Long getUserTwo() {
        return mUserTwo;
    }

    public void setUserTwo(Long userTwo) {
        mUserTwo = userTwo;
    }

}
