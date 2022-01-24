package com.elewamathtutoring.Activity.Chat.chatModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat_list_bothside {
        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("SenderName")
        @Expose
        public String senderName;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("SenderID")
        @Expose
        public Integer senderID;
        @SerializedName("SenderImage")
        @Expose
        public String senderImage;
        @SerializedName("ReceiverName")
        @Expose
        public String receiverName;
        @SerializedName("ReceiverId")
        @Expose
        public Integer receiverId;
        @SerializedName("ReceiverImage")
        @Expose
        public String receiverImage;

        @SerializedName("extension")
        @Expose
        public String extension;
        @SerializedName("msg_type")
        @Expose
        public Integer msgType;
        @SerializedName("created_at")
        @Expose
        public String createdAt;

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getSenderName() {
                return senderName;
        }

        public void setSenderName(String senderName) {
                this.senderName = senderName;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }

        public Integer getSenderID() {
                return senderID;
        }

        public void setSenderID(Integer senderID) {
                this.senderID = senderID;
        }

        public String getSenderImage() {
                return senderImage;
        }

        public void setSenderImage(String senderImage) {
                this.senderImage = senderImage;
        }

        public String getReceiverName() {
                return receiverName;
        }

        public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
        }

        public Integer getReceiverId() {
                return receiverId;
        }

        public void setReceiverId(Integer receiverId) {
                this.receiverId = receiverId;
        }

        public String getReceiverImage() {
                return receiverImage;
        }

        public void setReceiverImage(String receiverImage) {
                this.receiverImage = receiverImage;
        }

        public Integer getMsgType() {
                return msgType;
        }

        public void setMsgType(Integer msgType) {
                this.msgType = msgType;
        }

        public String getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
        }
}
