
package com.elewamathtutoring.Activity.Chat.chatModel.getchatmessageModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Body {

        @SerializedName("userid")
        @Expose
        public Integer senderId;
        @SerializedName("user2Id")
        @Expose
        public Integer receiverId;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("messageType")
        @Expose
        public Integer messageType;
        @SerializedName("createdAt")
        @Expose
        public String createdAt;
        @SerializedName("updatedAt")
        @Expose
        public String updatedAt;
    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
