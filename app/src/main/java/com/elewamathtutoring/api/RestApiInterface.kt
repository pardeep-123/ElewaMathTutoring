package com.elewamathtutoring.api

import com.elewamathtutoring.Models.Add_Card.Model_addcards
import com.elewamathtutoring.Models.BankAccountsModel.Model_BankAccount
import com.elewamathtutoring.Models.Card_listing.Model_cardlisting
import com.elewamathtutoring.Models.ListView.Model_myschdeullist
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.Models.My_Schedule.Model_schedule
import com.elewamathtutoring.Models.TeacherRequestsList.Model_TeacherRequestList
import com.elewamathtutoring.Models.Notifications.Model_Notifications
import com.elewamathtutoring.Models.Search.Model_search
import com.elewamathtutoring.Models.Session_detail.Model_session_detail
import com.elewamathtutoring.Models.Teacher_details.Model_teacherdetails
import com.elewamathtutoring.Models.Teacher_level.Model_teacher_level
import com.elewamathtutoring.Models.Time_slots.Model_timeslots
import com.elewamathtutoring.Models.Wallet.Model_wallet_amount_history
import com.elewamathtutoring.Models.Webview.Model_webview
import com.pawskeeper.Modecommon.Commontoall
import com.pawskeeper.Modecommon.Commontoall2
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface RestApiInterface {
//    @FormUrlEncoded
//     @Multipart
//    @POST(upload)
//    fun images(@Part image: MultipartBody.Part): Observable<Model_UploadImage>

    @FormUrlEncoded
    @POST("signin")
    fun Userlogin(
        @Field("userType") userType: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("deviceType") device_type: String,
        @Field("deviceToken") device_token: String?
    ): Observable<Model_login>

   @FormUrlEncoded
    @POST("withdrawlAmount")
    fun withdrawlAmount(
        @Field("UserType") userType: String,
        @Field("amount") amount: String,
        @Field("bankId") bankId: String
    ): Observable<Commontoall>

    @FormUrlEncoded
    @POST("change_password")
    fun change_password(@Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String): Observable<Commontoall>


    @GET("NotificationList")
    fun notifications(): Observable<Model_Notifications>

    @GET("get_time_slots")
    fun get_time_slots(): Observable<Model_timeslots>

    @FormUrlEncoded
    @POST("delete_account")
    fun Delete_account(@Field("usertype") usertype: String): Observable<Commontoall>

   @FormUrlEncoded
    @PUT("promocode_exist")
    fun promocode_exist(@Field("code") code: String): Observable<Commontoall>

    @FormUrlEncoded
    @POST("buy_plan")
    fun buy_plan(@Field("type") type: String,@Field("price") price: String,@Field("name") name: String
                 ,@Field("duration") duration: String ,@Field("planId") planId: String,@Field("planExpiryDate") planExpiryDate: String):
            Observable<Model_login>

   @FormUrlEncoded
    @POST("checkSocialLoginExists")
    fun checkSocialLoginExists(@Field("socialid") socialid: String,
                               @Field("socialType") socialType: String,
                               @Field("device_type") device_type: String,
                               @Field("device_token") device_token: String,
                               @Field("userType") userType: String): Observable<Commontoall>

    @FormUrlEncoded
    @PUT("logout")
    fun Logout(@Field("usertype") usertype: String): Observable<Commontoall>

    //teacherId:39
    //availability:2,3
    //time:1
    //About:heyy
    //personVirtual:1
    //Hour:2
    //perHour:25
    //Total:100
    //cardId:1
    //date:2021-04-28
    @FormUrlEncoded
    @POST("book_Session")
    fun book_Session(@Field("teacherId") teacherId: String,@Field("availability") availability: String,
               @Field("time") time: String, @Field("About") About: String,
               @Field("personVirtual") personVirtual: String, @Field("Hour") Hour: String,
               @Field("perHour") perHour: String, @Field("Total") Total: String,
                @Field("cardId") cardId: String, @Field("date") date: String): Observable<Commontoall2>

     @FormUrlEncoded
    @POST("change_session_status")
    fun change_session_status(@Field("sessionStatus") sessionStatus: String,
                              @Field("sessionId") sessionId: String): Observable<Commontoall>

    @FormUrlEncoded
    @POST("update_notifications_status")
    fun call_notification_on_offApi(@Field("notificationStatus") notificationStatus: String,
        @Field("userType") userType: String
    ): Observable<Commontoall2>



    @FormUrlEncoded
    @POST("add_bank")
    fun add_bank(
        @Field("bankbranch") bankbranch: String,
        @Field("accountNo") accountNo: String,
        @Field("accountHolder") accountHolder: String,
        @Field("ifscCode") ifscCode: String,

        @Field("bankType") bankType: String?
    ): Observable<Commontoall>


    @FormUrlEncoded
    @PUT("PastTeacher")
    fun PastTeacher(@Field("status") status: String,@Field("userType") userType: String): Observable<Model_TeacherRequestList>

  @FormUrlEncoded
    @PUT("teachersDetails")
    fun teachersDetails(@Header("security_key") security_key: String,@Field("teacher_id") teacher_id: String): Observable<Model_teacherdetails>

    @FormUrlEncoded
    @POST("Edit_bank")
    fun edit_bank(
        @Field("bankId") bankId: String,
        @Field("bankbranch") bankbranch: String,
        @Field("accountNo") accountNo: String,
        @Field("accountHolder") accountHolder: String,
        @Field("ifscCode") ifscCode: String,
        @Field("bankType") bankType: String?
    ): Observable<Commontoall>

    @FormUrlEncoded
    @POST("add_card")
    fun addcard(
        @Field("card_number") card_number: String,
        @Field("expiry_year") expiry_year: String,
        @Field("expiry_month") expiry_month: String,
        @Field("holder_name") holder_name: String,
        @Field("isSave") isSave: String,
        @Field("cvv") cvv: String?
    ): Observable<Model_addcards>


    @FormUrlEncoded
    @POST("EditCard")
    fun editCard(
        @Field("card_number") card_number: String,
        @Field("expiry_year") expiry_year: String,
        @Field("expiry_month") expiry_month: String,
        @Field("holder_name") holder_name: String,
        @Field("isSave") isSave: String,
        @Field("cvv") cvv: String?,
        @Field("card_id") card_id: String?
    ): Observable<Model_addcards>

    @FormUrlEncoded
    @POST("EditTeacherProfileProfile")
    fun EditTeacherProfileProfile(
        @Field("teachingLevel") teachingLevel: String,
        @Field("specialties") specialties: String,
        @Field("inPersonRate") inPersonRate: String,
        @Field("cancelPolicy") cancelPolicy: String,
        @Field("VirtualRate") VirtualRate: String,
        @Field("CertifiedAs") CertifiedAs: String,
        @Field("address") address: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("availability") availability: String,
        @Field("timeslot") timeSlot: String,
    ): Observable<Commontoall>

    @FormUrlEncoded
    @PUT("deleteCard")
    fun deletecard(@Field("cardId") cardId: String): Observable<Commontoall>

     @FormUrlEncoded
    @PUT("checkEmail")
    fun checkEmail(@Field("email") email: String): Observable<Commontoall>

    @FormUrlEncoded
    @POST("Delete_Bank")
    fun deleteBank(@Field("bankId") bankId: String): Observable<Commontoall>

  @FormUrlEncoded
    @POST("SetDefault_Bank")
    fun SetDefault_Bank(@Field("bankId") bankId: String,@Field("isdefault") isdefault: String): Observable<Commontoall2>

   @FormUrlEncoded
    @POST("report")
    fun report(@Field("comments") comments: String,@Field("teacherId") teacherId: String): Observable<Commontoall>

    @FormUrlEncoded
    @PUT("session_Details")
    fun sessionDetails(@Field("sessionId") sessionId: String): Observable<Model_session_detail>

    @FormUrlEncoded
    @PUT("web_Data")
    fun tearmcondition_aboutus_privacypolicy(@Field("type") type: String): Observable<Model_webview>


    @FormUrlEncoded
    @PUT("get_profile")
    fun get_profile(@Field("userType") userType: String): Observable<Model_login>

    @FormUrlEncoded
    @POST("forgotPassword")
    fun forgetPassword(@Field("email") email: String): Observable<Commontoall?>

    @GET("card_listing")
    fun card_listing(): Observable<Model_cardlisting>

    @GET("parentSchedulingList")
    fun parentSchedulingList(): Observable<Model_schedule>

    @GET("Get_teaching_level")
    fun teacher_level(): Observable<Model_teacher_level>

    @FormUrlEncoded
    @PUT("All_Sessions_list_with_dates")
    fun listViewSession(@Field("date") date: String): Observable<Model_myschdeullist>

    @FormUrlEncoded
    @PUT("seach_teachers")
    fun seach_teachers(@Header("security_key") security_key: String,@Field("limit") limit: String,@Field("page") page: String,
                       @Field("CertifiedAs") CertifiedAs: String,@Field("maximumDistance") maximumDistance: String
                       ,@Field("searchText") searchText: String,
                       @Field("teachingLevel") teachingLevel: String,@Field("lat") lat: String,@Field("lng") lng: String): Observable<Model_search>

    @FormUrlEncoded
    @PUT("Get_Wallet")
    fun Get_Wallet(@Field("UserType") UserType: String): Observable<Model_wallet_amount_history>

    @GET("Bank_listing")
    fun bankAccounts(): Observable<Model_BankAccount>

    @Multipart
    @POST("parentSignup")
    fun signinUser(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("about") about: RequestBody,
        @Part("deviceType") deviceType: RequestBody,
        @Part("deviceToken") deviceToken: RequestBody
    ): Observable<Model_login>



      @Multipart
    @POST("ParentSocialSignup")
    fun ParentSocialSignup(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("socialId") socialId: RequestBody,
        @Part("about") about: RequestBody,
        @Part("socialType") socialType: RequestBody,
        @Part("deviceType") deviceType: RequestBody,
        @Part("deviceToken") deviceToken: RequestBody
    ): Observable<Model_login>

    @Multipart
    @POST("ParentEditProfile")
    fun editParentProfile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("about") about: RequestBody
    ): Observable<Model_login>

    @Multipart
    @POST("EditTeacherBasicProfile")
    fun EditTeacherBasicProfile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("about") about: RequestBody,
        @Part("TeachingHistory") TeachingHistory: RequestBody
    ): Observable<Model_login>

    @Multipart
    @POST("TeacherSignup")
    fun signas_teacher(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("about") about: RequestBody,
        @Part("TeachingHistory") TeachingHistory: RequestBody,
        @Part("teachingLevel") teachingLevel: RequestBody,
        @Part("specialties") specialties: RequestBody,
        @Part("inPersonRate") inPersonRate: RequestBody,
        @Part("cancelPolicy") cancelPolicy: RequestBody,
        @Part("VirtualRate") VirtualRate: RequestBody,
        @Part("CertifiedAs") CertifiedAs: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("availability") availability: RequestBody,
        @Part("timeSlot") timeSlot: RequestBody,
        @Part("deviceType") deviceType: RequestBody,
        @Part("deviceToken") deviceToken: RequestBody
    ): Observable<Model_login>

    //name:Amu
    //email:ame47@mail.com
    //password:123456789
    //deviceType:1
    //deviceToken:124354t
    //about:Good boy
    //TeachingHistory:11
    //teachingLevel:2,3
    //specialties:Maths
    //inPersonRate:45
    //VirtualRate:89
    //cancelPolicy:abcd
    //CertifiedAs:1
    //address:Rajpura
    //latitude:8.9999
    //longitude:9.09999
    //availability:1,2,3,4,5
    //timeSlot:1,3
    //socialId:1vgrnnjbkjgfdwetwye
    //socialType:1
     @Multipart
    @POST("TeacherSocialSignup")
    fun TeacherSocialSignup(
        @Part image: MultipartBody.Part?,
        @Part("socialType") socialType: RequestBody,
        @Part("socialId") socialId: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("about") about: RequestBody,
        @Part("TeachingHistory") TeachingHistory: RequestBody,
        @Part("teachingLevel") teachingLevel: RequestBody,
        @Part("specialties") specialties: RequestBody,
        @Part("inPersonRate") inPersonRate: RequestBody,
        @Part("cancelPolicy") cancelPolicy: RequestBody,
        @Part("VirtualRate") VirtualRate: RequestBody,
        @Part("CertifiedAs") CertifiedAs: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("availability") availability: RequestBody,
        @Part("timeSlot") timeSlot: RequestBody,
        @Part("deviceType") deviceType: RequestBody,
        @Part("deviceToken") deviceToken: RequestBody
    ): Observable<Model_login>

    @Multipart
    @POST("send_feedback")
    fun Contact_us(
        @Part images: MultipartBody.Part?,
        @Part("message") message: RequestBody
    ): Observable<Commontoall>

}