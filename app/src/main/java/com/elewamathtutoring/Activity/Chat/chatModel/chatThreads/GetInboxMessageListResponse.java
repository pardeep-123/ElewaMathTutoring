package com.elewamathtutoring.Activity.Chat.chatModel.chatThreads;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInboxMessageListResponse {


        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("typing")
        @Expose
        private Integer typing;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("lastMessage")
        @Expose
        private String lastMessage;
        @SerializedName("userName")
        @Expose
        private String userName;
        @SerializedName("onlinestatus")
        @Expose
        private Integer onlinestatus;
        @SerializedName("userImage")
        @Expose
        private String userImage;
        @SerializedName("created")
        @Expose
        private Integer created;
        @SerializedName("msg_type")
        @Expose
        private Integer msgType;
        @SerializedName("unreadcount")
        @Expose
        private Integer unreadcount;
        @SerializedName("blockstatus")
        @Expose
        private Integer blockstatus;
        @SerializedName("user2Id")
        @Expose
        private Integer user2Id;
        @SerializedName("lastMsgId")
        @Expose
        private Integer lastMsgId;
        @SerializedName("deletedId")
        @Expose
        private Integer deletedId;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getTyping() {
            return typing;
        }

        public void setTyping(Integer typing) {
            this.typing = typing;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public void setLastMessage(String lastMessage) {
            this.lastMessage = lastMessage;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getOnlinestatus() {
            return onlinestatus;
        }

        public void setOnlinestatus(Integer onlinestatus) {
            this.onlinestatus = onlinestatus;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public Integer getCreated() {
            return created;
        }

        public void setCreated(Integer created) {
            this.created = created;
        }

        public Integer getMsgType() {
            return msgType;
        }

        public void setMsgType(Integer msgType) {
            this.msgType = msgType;
        }

        public Integer getUnreadcount() {
            return unreadcount;
        }

        public void setUnreadcount(Integer unreadcount) {
            this.unreadcount = unreadcount;
        }

        public Integer getBlockstatus() {
            return blockstatus;
        }

        public void setBlockstatus(Integer blockstatus) {
            this.blockstatus = blockstatus;
        }

        public Integer getUser2Id() {
            return user2Id;
        }

        public void setUser2Id(Integer user2Id) {
            this.user2Id = user2Id;
        }

        public Integer getLastMsgId() {
            return lastMsgId;
        }

        public void setLastMsgId(Integer lastMsgId) {
            this.lastMsgId = lastMsgId;
        }

        public Integer getDeletedId() {
            return deletedId;
        }

        public void setDeletedId(Integer deletedId) {
            this.deletedId = deletedId;
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
