package com.elewamathtutoring.Util

import android.app.Activity
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class Validator {
    val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

    fun isNotNull(txt: String?): Boolean {
        return if (txt != null && txt.trim { it <= ' ' }.length > 0) true else false
    }

    fun isValidPassword(password: String): Boolean {
        val PASSWORD_PATTERN = "^(?=\\D*\\d)(?=.*?[a-zA-Z]).*[\\W_].*$"
        val pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher = pattern.matcher(password)
        return !matcher.matches()
    }
    fun checkStringNull(string: String?): Boolean {
        return string == null || string == "null" || string.isEmpty()
    }
    /****
     * Email Valid
     */
    fun isValidEmail(email: String): Boolean {
        val EMAIL_PATTERN = "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                ")+"
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }


    // Sign Up Page validation
    fun signUpValid(
        context: Activity,
        fullname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var check = false
     if (fullname.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_name))
        }
     else if (email.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_email))
        }
     else if (!email.isEmpty() && !isValidEmail(email)) {
            Helper.showErrorAlert(context, context.getString(R.string.please_enter_valid_email_address))
        }
       else if (password.isEmpty()) {
           Helper.showErrorAlert(context, context.getString(R.string.Pleseenter_password))
       }
     else if (password.length < 6) {
            Helper.showErrorAlert(context, "Password length should contain at least 6 characters")
        }
       else if (confirmPassword.isEmpty()) {
           Helper.showErrorAlert(context, context.getString(R.string.Plese_enterConfirm_password))
       }
    /* else if (confirmPassword.length < 6) {
            Helper.showErrorAlert(context, "Confirm password length should contain at least 6 characters")
        }*/
       else if (confirmPassword != password) {
           Helper.showErrorAlert(context, context.getString(R.string.error_mismatch_password))
       }
      else {
            check = true
        }
        return check
    }


    // update Page validation
    fun updateprofile(context: Activity, fullname: String): Boolean {
        var check = false
        if (fullname.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_all_fields))
        } else {
            check = true
        }
        return check
    }


 // update Page validation
    fun CompleteProfile(context: Activity, tv_whychoosMe: String, tv_whyilove: String,selectedCategoryList:Int,selectedPETlist:Int,
                        Selected_outdoreArea: String,Selected_Emergency_Transport: String): Boolean {
        var check = false
        if (tv_whychoosMe.isEmpty()) {
            Helper.showErrorAlert(context,"Please enter why you should choose me?")
        }
        else if(tv_whyilove.isEmpty())
        {
            Helper.showErrorAlert(context,"Please enter why i love doing this?")
        }
      /*  else if(mAlbumFiles==0)
        {
            Helper.showErrorAlert(context,"Please add at least one skill and qulification")
        }*/
        else if(selectedCategoryList==0)
        {
            Helper.showErrorAlert(context,"Please select at least one service category")
        }
        else if(selectedPETlist==0)
        {
            Helper.showErrorAlert(context,"Please select at least one accepted pet type")
        }
        else if(Selected_outdoreArea.equals("-1"))
        {
            Helper.showErrorAlert(context,"Please select access to outdoor area")
        }
       else if(Selected_Emergency_Transport.equals("-1"))
            {
                Helper.showErrorAlert(context,"Please select emergency transport")
            }
        else {
            check = true
        }
        return check
    }


    fun EditProviderProfile(context: Activity, et_name: String,etLocation: String, tv_whychoosMe: String, tv_whyilove: String,selectedCategoryList:Int,selectedPETlist:Int,
                        Selected_outdoreArea: String,Selected_Emergency_Transport: String): Boolean {
        var check = false
        if (et_name.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_name))
        }

        else if (etLocation.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.Select_Location))
        }
        else if(selectedCategoryList==0)
        {
            Helper.showErrorAlert(context,"Please select at least one service category")
        }
        else if(selectedPETlist==0)
        {
            Helper.showErrorAlert(context,"Please select at least one accepted pet type")
        }
        else if (tv_whychoosMe.isEmpty()) {
            Helper.showErrorAlert(context,"Please enter why you should choose me?")
        }
        else if(tv_whyilove.isEmpty())
        {
            Helper.showErrorAlert(context,"Please enter why i love doing this?")
        }

        else if(Selected_outdoreArea.equals("-1"))
        {
            Helper.showErrorAlert(context,"Please select access to outdoor area")
        }
       else if(Selected_Emergency_Transport.equals("-1"))
            {
                Helper.showErrorAlert(context,"Please select emergency transport")
            }
        else {
            check = true
        }
        return check
    }

    // Contact us Page validation
    fun contactUs(context: Activity, image: String, message: String): Boolean {
        var check = false
        if (message.isEmpty())
        {
            Helper.showErrorAlert(context, context.getString(R.string.error_enter_message))
        }
        else if (image.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_image))

        }
        else {
            check = true
        }
        return check
    }

    // Change Password us Page validation
    fun Change_Password(
        context: Activity,
        oldpassword: String,
        new_Password: String,
        confirm_Password: String
    ): Boolean {
        var check = false
        if (oldpassword.isEmpty()) {
            Helper.showErrorAlert(context, "Please enter old password")
        }
        else if(new_Password.isEmpty())
        {
            Helper.showErrorAlert(context, context.getString(R.string.Pleseenter_new_password))
        }
        else if (new_Password.length < 6) {
            Helper.showErrorAlert(context,"New password length should contain at least 6 characters")
        }
          else if(confirm_Password.isEmpty())
        {
            Helper.showErrorAlert(context, context.getString(R.string.Plese_enterConfirm_password))
        }
      /*  else if (confirm_Password.length < 6) {
            Helper.showErrorAlert(context,"Confirm password length should contain at least 6 characters")
        }*/
         else if (!new_Password.equals(confirm_Password)) {
            Helper.showErrorAlert(context, context.getString(R.string.Old_and_newpassword_areSane))
        } else {
            check = true
        }
        return check
    }
    // update Page validation
/*
    companion object {
        fun checkstatus(status: Int): String {
            var check =""
            if (status==0) {
                check ="Pending"
            }
            else if(status==1)
            {
                check = "Accepted"
            }
            else if(status==2)
            {
                Log.e("checkingghitt","--"+getPrefrence(Constants.AUTH_KEY, ""))
                if(getPrefrence(Constants.user_type, "").equals("1"))
                {
                    check = "Cancelled by you"
                }
                else
                {
                    check = "Cancelled by user"
                }
            }
            else if(status==3)
            {
                Log.e("checkingghitt","--"+getPrefrence(Constants.AUTH_KEY, ""))

                if(getPrefrence(Constants.user_type, "").equals("1"))
                {
                    check = "Cancelled by provider"
                }
                else
                {
                    check = "Cancelled by you"
                }
            }
            else if(status==4)
            {
                check = "Completed"
            }
            return check
        }
        fun HomeStayIn(status: Int): String {
            var check =""
            if (status==1) {
                check ="House"
            }
            else if(status==2)
            {
                check = "Apartment"
            }

            return check
        }

        fun YesNocheck(status: Int): String {
            var check =""
            if (status==0) {
                check ="No"
            }
            else if(status==1)
            {
                check = "Yes"
            }
            else
            {
                check ="No"
            }
            return check
        }
    }
*/
    // Phone validation

    // Emal validation
    fun Emailvalidation(context: Activity, email: String): Boolean {
        var check = false
        if (email.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_email))
        } else if (!email.isEmpty() && !isValidEmail(email)) {
            Helper.showErrorAlert(
                context,
                context.getString(R.string.please_enter_valid_email_address)
            )
        } else {
            check = true
        }
        return check
    }

    // flldotp validation
    fun flldotp(context: Activity, otp: String): Boolean {
        var check = false
        if (otp.length < 4) {
            Helper.showErrorAlert(context, "Enter 4 digit OTP")
        } else {
            check = true
        }
        return check
    }

    /***
     * Login In Page validation
     */
    fun loginValid(context: Activity, email: String, password: String): Boolean {
        var check = false
        if (email.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_email))
        }
        else if (!email.isEmpty() && !isValidEmail(email)) {
            Helper.showErrorAlert(context,context.getString(R.string.please_enter_valid_email_address))
        }
        else if (password.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.Pleseenter_password))
        }
        else
        {
            check = true
        }
        return check
    }

    fun Teacheraboutus(context: Activity, firstimage: String,edaboutyou: String, edTeachingHistory: String, edTeachingname: String, type: String): Boolean {
        var check = false

         if (firstimage.isEmpty()&&!type.equals("edit")) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_image))
        }
        else if (edTeachingname.isEmpty()&&type.equals("edit")) {
             Helper.showErrorAlert(context, context.getString(R.string.error_empty_name))
         }
        else if (edaboutyou.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_aboutyou))
        }
        else if (edTeachingHistory.isEmpty() ) {
            Helper.showErrorAlert(context,context.getString(R.string.please_enter_teacherhistory))
        }
        else
        {
            check = true
        }
        return check
    }

    fun Teachercompleteprofile(context: Activity, CertifiedAs: String,edSpeacialities: String,edInPersonRate: String, edVirtualRate: String,
                               edCancelationPolicy: String, address: String, teachingLevel: String): Boolean {
        var check = false
        if (CertifiedAs.equals("1")) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_teachinglevel))
        }
        else if (teachingLevel.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_teachinglevel))
        }
        else if (edSpeacialities.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_empty_specialities))
        }
        else if (edInPersonRate.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.please_enter_in_person_rate))
        }
        else if (edVirtualRate.isEmpty() ) {
            Helper.showErrorAlert(context,context.getString(R.string.please_enter_virtual_rate))
        }
        else if (edCancelationPolicy.isEmpty() ) {
            Helper.showErrorAlert(context,context.getString(R.string.please_enter_cancelation_policy))
        }
        else if (address.isEmpty() ) {
            Helper.showErrorAlert(context,context.getString(R.string.Select_Location))
        }
        else
        {
            check = true
        }
        return check
    }
    fun Teacherdelectdatetime(context: Activity, date: ArrayList<String>,time: ArrayList<String>): Boolean {
        var check = false
        if (date.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.Please_select_date))
        }
        else if (time.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.Please_select_time))
        }
        else
        {
            check = true
        }
        return check
    }



    fun addBankValidation(context: Activity, ifscCode: String, branch: String, acNumber: String, cacNumber:String,acHolderName: String): Boolean {

        if (ifscCode.isEmpty()) {
            //  Helper.showErrorAlert(context, "IFSC Code length should contain at least 11 characters")
            Helper.showErrorAlert(context, context.getString(R.string.errorEnter_ifsc))
            return false
        } else if (ifscCode.length < 11) {
            //  Helper.showErrorAlert(context, "IFSC Code length should contain at least 11 characters")
            Helper.showErrorAlert(context, context.getString(R.string.error_ifsc))
            return false
        }
        else if (branch.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_branch))
            return false
        } else if (acNumber.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_account))
            return false
        }
        else if (acNumber.length<16) {
            Helper.showErrorAlert(context, context.getString(R.string.error_acNumber))
            return false
        }
        else if (cacNumber.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_Confirm_accountNumber))
            return false
        }
      /*  else if (cacNumber.length<16) {
            Helper.showErrorAlert(context, context.getString(R.string.error_Confirm_acNumber))
            return false
        }*/
        else if (!cacNumber.equals(acNumber) ) {
            Helper.showErrorAlert(context, context.getString(R.string.error_mismatch_account_number))
            return false
        }
        else if (acHolderName.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_HolderNumber))
            return false
        }
        else {
            return true
        }

    }
    fun addcard(context: Activity, name: String, tvCard: String, EXP_DATE: String, cvv:String): Boolean {
        if (name.isEmpty()) {
            //  Helper.showErrorAlert(context, "IFSC Code length should contain at least 11 characters")
            Helper.showErrorAlert(context, context.getString(R.string.please_enter_nameon_card))
            return false
        } else if (tvCard.isEmpty()) {
            //  Helper.showErrorAlert(context, "IFSC Code length should contain at least 11 characters")
            Helper.showErrorAlert(context, context.getString(R.string.error_cardnumber))
            return false
        }else if (tvCard.length < 16) {
            //  Helper.showErrorAlert(context, "IFSC Code length should contain at least 11 characters")
            Helper.showErrorAlert(context, context.getString(R.string.error_valid_cardnumber))
            return false
        }
        else if (EXP_DATE.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.error_expiry_date))
            return false
        } else if (cvv.isEmpty()) {
            Helper.showErrorAlert(context, context.getString(R.string.please_enter_cvv))
            return false
        } else if (cvv.length < 3) {
            Helper.showErrorAlert(context, context.getString(R.string.please_enter_valid_cvv))
            return false
        }
        else {
            return true
        }

    }


}