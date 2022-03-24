package com.elewamathtutoring.Model
import com.google.gson.annotations.SerializedName


class GroupListModel : ArrayList<GroupListModel.GroupListModelItem>(){
    data class GroupListModelItem(
        @SerializedName("id")
        val id: Int, // 380
        @SerializedName("image")
        val image: String, // 164801554720210922_153633565_2d89da593cd46bde18b1dd2f919b5516.jpg
        @SerializedName("name")
        val name: String, // Ss
        @SerializedName("userType")
        val userType: Int, // 2
        var isCheck : Boolean = false
    )
}