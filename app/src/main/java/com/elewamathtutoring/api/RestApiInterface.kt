package com.elewamathtutoring.api


import com.elewamathtutoring.Activity.Auth.login.LoginResponse
import com.elewamathtutoring.Activity.Auth.signup.SignUpResponse
import com.elewamathtutoring.Activity.Auth.signup.TeacherSignUpResponse
import com.elewamathtutoring.Activity.Chat.mathChat.MathChatResponse
import com.elewamathtutoring.Activity.ParentOrStudent.addBAnk.AddBankResponse
import com.elewamathtutoring.Activity.ParentOrStudent.add_card.AddCardResponse
import com.elewamathtutoring.Activity.ParentOrStudent.editProfile.EditProfileResponse
import com.elewamathtutoring.Activity.ParentOrStudent.filter.SubjectsResponse
import com.elewamathtutoring.Activity.ParentOrStudent.payment.CardListingResponse
import com.elewamathtutoring.Activity.ParentOrStudent.payment.SesionBookResponse
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details.AddCommentResponse
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.details.CommentListResponse
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.AddPostResponse
import com.elewamathtutoring.Activity.ParentOrStudent.postMathProblem.mathProblem.MathProblemListResponse
import com.elewamathtutoring.Activity.ParentOrStudent.privacy.PrivacyResponse
import com.elewamathtutoring.Activity.ParentOrStudent.resources.CategoriesResponse
import com.elewamathtutoring.Activity.ParentOrStudent.resources.ResourcesResponse
import com.elewamathtutoring.Activity.ParentOrStudent.resources.changepassword.ChangePasswordResponse
import com.elewamathtutoring.Activity.ParentOrStudent.teacherDetail.TeacherDetailResponse
import com.elewamathtutoring.Activity.ParentOrStudent.wallet.BankListingResponse
import com.elewamathtutoring.Activity.ParentOrStudent.wallet.response.WalletGetResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.EditTeachingInfoResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachinInfoResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachingLevelResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.AvailabilityResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.EditAvailabilityResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.TimeSlotsResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.edit.EditResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.editProfile.EditTeacherProfileResponse
import com.elewamathtutoring.Activity.TeacherOrTutor.request.RequestDetailResponse
import com.elewamathtutoring.Fragment.ParentOrStudent.booking.UserBookingListResponse
import com.elewamathtutoring.Fragment.ParentOrStudent.profile.ProfilResponse
import com.elewamathtutoring.Fragment.TeacherOrTutor.bookings.OccupiedResponse
import com.elewamathtutoring.Fragment.TeacherOrTutor.request.RequestListResponse
import com.elewamathtutoring.Models.Card_listing.AllSessionListResponse
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.Models.Modecommon.Commontoall2
import com.elewamathtutoring.Models.Notifications.Model_Notifications
import com.elewamathtutoring.Models.Search.Model_search
import com.elewamathtutoring.Models.TeacherRequestsList.Model_TeacherRequestList

import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface RestApiInterface {
//    @FormUrlEncoded
//     @Multipart
//    @POST(upload)
//    fun images(@Part image: MultipartBody.Part): Observable<Model_UploadImage>

    /*   @FormUrlEncoded
       @POST("signin")
       fun Userlogin(
           @Field("userType") userType: String,
           @Field("email") email: String,
           @Field("password") password: String,
           @Field("deviceType") device_type: String,
           @Field("deviceToken") device_token: String?
       ): Observable<Model_login>*/
    @FormUrlEncoded
    @POST("signin")
    fun Userlogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("deviceType") device_type: String,
        @Field("deviceToken") device_token: String?
    ): Observable<LoginResponse>

    // for social login
    @FormUrlEncoded
    @POST("sociallogin")
    fun sociallogin(
        @Field("socialId") SocialId: String,
        @Field("socialType") socialType: String,
        @Field("userType") userType: String,
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("deviceType") device_type: String,
        @Field("deviceToken") device_token: String?
    ): Observable<LoginResponse>

    @FormUrlEncoded
// @Field("UserType") userType: String,
    @POST("withdrawlAmount")
    fun withdrawlAmount(

        @Field("amount") amount: String,
        @Field("bankId") bankId: String
    ): Observable<Commontoall>

    @FormUrlEncoded
    @POST("change_password")
    fun change_password(
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String
    ): Observable<ChangePasswordResponse>


    @GET("NotificationList")
    fun notifications(): Observable<Model_Notifications>

    @GET("myMathProblems")
    fun myMathProblems(): Observable<MathProblemListResponse>

    @GET("mathProblems")
    fun mathProblems(): Observable<MathProblemListResponse>


    @FormUrlEncoded
    @POST("comments")
    fun AddComments(
        @FieldMap hashMap: HashMap<String, String>
    ): Observable<AddCommentResponse>

    @GET("get_time_slots")
    fun get_time_slots(): Observable<TimeSlotsResponse>

/*
    @FormUrlEncoded
    @POST("delete_account")
    fun Delete_account(@Field("usertype") usertype: String): Observable<Commontoall>

*/


    @HTTP(method = "DELETE", path = "delete_account", hasBody = false)
    fun Delete_account(
    ): Observable<Commontoall>

    /* @DELETE("delete_account")
     fun Delete_account(@Field("usertype") usertype: String): Observable<Commontoall>
 */
    @FormUrlEncoded
    @PUT("promocode_exist")
    fun promocode_exist(@Field("code") code: String): Observable<Commontoall>



    @FormUrlEncoded
    @POST("buy_plan")
    fun buy_plan(
        @Field("type") type: String,
        @Field("price") price: String,
        @Field("name") name: String,
        @Field("duration") duration: String,
        @Field("planId") planId: String,
        @Field("planExpiryDate") planExpiryDate: String
    ):
            Observable<Model_login>

    @FormUrlEncoded
    @POST("checkSocialLoginExists")
    fun checkSocialLoginExists(
        @Field("socialid") socialid: String,
        @Field("socialType") socialType: String,
        @Field("device_type") device_type: String,
        @Field("device_token") device_token: String,
        @Field("userType") userType: String
    ): Observable<Commontoall>

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
    /*  @FormUrlEncoded
      @POST("book_Session")
      fun book_Session(
          @Field("teacherId") teacherId: String, @Field("availability") availability: String,
          @Field("time") time: String, @Field("About") About: String,
          @Field("personVirtual") personVirtual: String, @Field("Hour") Hour: String,
          @Field("perHour") perHour: String, @Field("Total") Total: String,
          @Field("cardId") cardId: String, @Field("date") date: String
      ): Observable<Commontoall2>*/

    @FormUrlEncoded
    @POST("change_session_status")
    fun change_session_status(
        @Field("sessionStatus") sessionStatus: String,
        @Field("sessionId") sessionId: String
    ): Observable<Commontoall>

    @FormUrlEncoded
    @POST("update_notifications_status")
    fun call_notification_on_offApi(
        @Field("notificationStatus") notificationStatus: String,
        @Field("userType") userType: String
    ): Observable<Commontoall2>


    @FormUrlEncoded
    @POST("occupiedStatus")
    fun occupiedStatus(
        @Field("occupiedStatus") occupiedStatus: String
    ): Observable<OccupiedResponse>


    @FormUrlEncoded
    @POST("bank")
    fun add_bank(
        @Field("bankbranch") bankbranch: String,
        @Field("accountNo") accountNo: String,
        @Field("accountHolder") accountHolder: String,
        @Field("ifscCode") ifscCode: String,
        @Field("bankType") bankType: String?
    ): Observable<AddBankResponse>


    @FormUrlEncoded
    @PUT("PastTeacher")
    fun PastTeacher(
        @Field("status") status: String,
        @Field("userType") userType: String
    ): Observable<Model_TeacherRequestList>

    @FormUrlEncoded
    @POST("teachersDetails")
    fun teachersDetails(
        @Field("teacher_id") teacher_id: String
    ): Observable<TeacherDetailResponse>

    // @Field("bankId") bankId: String,     @Field("bankType") bankType: String?
    @FormUrlEncoded
    @POST("bank")
    fun edit_bank(
        @Field("bankId") bankId: String,
        @Field("bankbranch") bankbranch: String,
        @Field("accountNo") accountNo: String,
        @Field("accountHolder") accountHolder: String,
        @Field("ifscCode") ifscCode: String,
        @Field("bankType") bankType: String?
    ): Observable<AddBankResponse>


    @FormUrlEncoded
    @POST("book_Session")
    fun book_Session(
        @Field("teacherId") teacherId: String,
        //@Field("time") time: String,
        @Field("About") About: String,
        @Field("cardId") cardId: String,
        @Field("date") date: String,
        @Field("times") times: String,
        @Field("Hour") Hour: String
    ): Observable<SesionBookResponse>


    @FormUrlEncoded
    @POST("card")
    fun addcard(
        @Field("card_number") card_number: String,
        @Field("expiry_year") expiry_year: String,
        @Field("expiry_month") expiry_month: String,
        @Field("holder_name") holder_name: String,
        @Field("isSave") isSave: String,
        @Field("cvv") cvv: String?
    ): Observable<AddCardResponse>


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
    ): Observable<AddCardResponse>


    @FormUrlEncoded
    @PUT("EditTeacherAvailablity")
    fun EditTeacherAvailablity(
        @Field("availability") availability: String,
        @Field("timeslot") timeslot: String,
        @Field("free_slots") freeSlots: String
    ): Observable<EditAvailabilityResponse>

/*
    @FormUrlEncoded
    @PUT("EditTeacheringinfo")
    fun EditTeacherProfileProfile(
        @Field("teachingLevel") teachingLevel: ArrayList<MultipartBody.Part>?,
        @Field("educationLevel") educationLevel: RequestBody,
        @Field("majors") majors: RequestBody,
        @Field("specialties") specialties: RequestBody,
        @Field("cancelPolicy") cancelPolicy: RequestBody,
        @Field("certificate_images") certificate_images: RequestBody,
        @Field("address") address: RequestBody,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
    ): Observable<EditTeachingInfoResponse>

*/


    @Multipart
    @PUT("EditTeacheringinfo")
    fun EditTeacherProfileProfile(
        @Part certificate_images: ArrayList<MultipartBody.Part>,
        @Part("teachingLevel") teachingLevel: RequestBody,
        @Part("educationLevel") educationLevel: RequestBody,
        @Part("majors") majors: RequestBody,
        @Part("specialties") specialties: RequestBody,
        @Part("cancelPolicy") cancelPolicy: RequestBody,
        @Part("hourlyPrice") hourlyPrice: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
    ): Observable<EditTeachingInfoResponse>

/*

    @FormUrlEncoded
    @DELETE("deleteCard")
    fun deletecard(@Field("cardId") cardId: String): Observable<Commontoall>
*/


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "card", hasBody = true)
    fun deletecard(
        @Field("cardId") cardId: String
    ): Observable<Commontoall>


    @FormUrlEncoded
    @POST("signup")
    fun signUpApi(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String
    ): Observable<SignUpResponse>


    @Multipart
    @POST("TeacherSignup")
    fun signUpTeacherApi(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("about") about: RequestBody,
        @Part("TeachingHistory") TeachingHistory: RequestBody,
        @Part("deviceType") deviceType: RequestBody,
        @Part("deviceToken") deviceToken: RequestBody,
    ): Observable<TeacherSignUpResponse>


    @Multipart
    @PUT("TeacherInfo")
    fun teachingInfoApi(
        @Part certificate_images: ArrayList<MultipartBody.Part>,
        @Part("educationLevel") educationLevel: RequestBody,
        @Part("majors") majors: RequestBody,
        @Part("teachingLevel") teachingLevel: RequestBody,
        @Part("specialties") specialties: RequestBody,
        @Part("hourlyPrice") hourlyPrice: RequestBody,
        @Part("cancelPolicy") cancelPolicy: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
    ): Observable<TeachinInfoResponse>

    @FormUrlEncoded
    @PUT("TeacherAvailability")
    fun TeacherAvailability(
        @Field("availability") availability: String,
        @Field("timeSlot") timeSlot: String,
        @Field("free_slots") freeSlots: String
    ): Observable<AvailabilityResponse>

    /*  @FormUrlEncoded
      @POST("bank")
      fun deleteBank(@Field("bankId") bankId: String): Observable<Commontoall>*/

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "bank", hasBody = true)
    fun deleteBank(@Field("bankId") bankId: String): Observable<Commontoall>


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "myMathProblems", hasBody = true)
    fun deletePostMathProblem(@Field("id") id: String): Observable<Commontoall>


    @FormUrlEncoded
    @POST("SetDefault_Bank")
    fun SetDefault_Bank(
        @Field("bankId") bankId: String,
        @Field("isdefault") isdefault: String
    ): Observable<Commontoall2>

    @FormUrlEncoded
    @POST("report")
    fun report(
        @Field("comments") comments: String,
        @Field("teacherId") teacherId: String
    ): Observable<Commontoall>

    @GET("session_Details")
    fun sessionDetails(@Query("sessionId") sessionId: String): Observable<RequestDetailResponse>

    @FormUrlEncoded
    @POST("get_content")
    fun tearmcondition_aboutus_privacypolicy(@Field("type") type: String): Observable<PrivacyResponse>

/*    @FormUrlEncoded
    @PUT("get_profile")
    fun get_profile(@Field("userType") userType: String): Observable<ProfileResponse>*/

    @GET("get_profile")
    fun get_profile(): Observable<ProfilResponse>

    @GET("get_profile")
    fun getProfile(): Observable<EditResponse>

    @GET("getsubjects")
    fun getsubjects(): Observable<SubjectsResponse>

    @GET("TeacherRequestList")
    fun TeacherRequestList(): Observable<RequestListResponse>

    @FormUrlEncoded
    @PUT("acceptReject")
    fun requestAccept(
        @Field("status") status: String,
        @Field("sessionId") sessionId: String
    ): Observable<Commontoall?>

    @FormUrlEncoded
    @PUT("cancelBooking")
    fun requestReject(
        @Field("sessionId") sessionId: String,
        @Field("status") status: String
    ):
            Observable<Commontoall?>


    @GET("resources")
    fun get_resources(
        @Query("category_id") category_id: String
    ): Observable<ResourcesResponse>

    @GET("comments")
    fun get_comments(
        @Query("problemId") problemId: String
    ): Observable<CommentListResponse>


    @GET("getcategories")
    fun getcategories(): Observable<CategoriesResponse>


    @FormUrlEncoded
    @POST("forgotPassword")
    fun forgetPassword(@Field("email") email: String): Observable<Commontoall?>

    @GET("card")
    fun card_listing(): Observable<CardListingResponse>

    @GET("userBookingList")
    fun parentSchedulingList(): Observable<UserBookingListResponse>

    @GET("Get_teaching_level")
    fun teacher_level(): Observable<TeachingLevelResponse>

    @FormUrlEncoded
    @POST("All_Sessions_list")
    fun listViewSession(@Field("date") date: String): Observable<AllSessionListResponse>

    @FormUrlEncoded
    @PUT("seach_teachers")
    fun seach_teachers(
        @Header("security_key") security_key: String,
        @Field("limit") limit: String,
        @Field("page") page: String,
        @Field("CertifiedAs") CertifiedAs: String,
        @Field("maximumDistance") maximumDistance: String,
        @Field("searchText") searchText: String,
        @Field("teachingLevel") teachingLevel: String,
        @Field("lat") lat: String,
        @Field("lng") lng: String
    ): Observable<Model_search>
//@Field("UserType") UserType: String

    @GET("Get_Wallet")
    fun Get_Wallet(): Observable<WalletGetResponse>

    @GET("bank")
    fun bankAccounts(): Observable<BankListingResponse>

/* @GET("getTeacherStudentList?userType=2")
    fun getTeacherStudentList(): Observable<MathChatResponse>*/

    @GET("getTeacherStudentList")
    fun getTeacherStudentList(
        @Query("userType") userType: String,
        @Query("subjects_id") subjects_id: String
    ): Observable<MathChatResponse?>?


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
    @PUT("editProfile")
    fun editParentProfile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("about") about: RequestBody
    ): Observable<EditProfileResponse>


    @Multipart
    @PUT("myMathProblems")
    fun editMyMathProblems(
        @Part document: MultipartBody.Part?,
        @Part("description") description: RequestBody,
        @Part("id") id: RequestBody
    ): Observable<Commontoall>

    @Multipart
    @POST("myMathProblems")
    fun postMathProblem(
        @Part document: MultipartBody.Part?,
        @Part("description") description: RequestBody,
        @Part("type") type: RequestBody
    ): Observable<AddPostResponse>



    @Multipart
    @PUT("EditTeacherBasicProfile")
    fun EditTeacherBasicProfile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("about") about: RequestBody,
        @Part("TeachingHistory") TeachingHistory: RequestBody
    ): Observable<EditTeacherProfileResponse>

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

    @FormUrlEncoded
    @POST("send_feedback")
    fun sendFeedback(
        @Field("message") message: String
    ): Observable<Commontoall>

}