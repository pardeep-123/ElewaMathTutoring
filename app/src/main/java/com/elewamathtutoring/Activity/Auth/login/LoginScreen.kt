package com.elewamathtutoring.Activity.Auth.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.Auth.DescSignupScreen
import com.elewamathtutoring.Activity.Auth.ForgotPassword
import com.elewamathtutoring.Activity.Auth.LoginGuestActivity
import com.elewamathtutoring.Activity.Auth.signup.SignUp
import com.elewamathtutoring.Activity.TeacherOrTutor.editProfile.AboutYouActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.TeachingInfo.TeachingInfoActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.availability.AvailablityActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.Models.Modecommon.Commontoall
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.*
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

import kotlinx.android.synthetic.main.activity_login_screen.*
import javax.inject.Inject

class LoginScreen : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>
, FacebookHelper.FacebookHelperCallback, GoogleHelper.GoogleHelperCallback{
    ////  GoogleHelper.GoogleHelperCallback
    @Inject
    lateinit var validator: Validator
    var mRcSignIn = 0
    private lateinit var googleHelper: GoogleHelper
    private var facebookHelper: FacebookHelper? = null
    var socialFirstName = ""
    var Api_type = "login"
    var token = ""
    lateinit var context: Context
    var callbackManager: CallbackManager? = null
    lateinit var shared: SharedPrefUtil

    private var socialLoginType = ""
    var firstName = ""
    var lastName = ""
    var socialEmail = ""
    var socialId = ""
    var socialImage = ""
    var socialtype = ""

    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        shared = SharedPrefUtil(this)
        getFirebaseToken()
        App.getinstance().getmydicomponent().inject(this)
        facebookHelper = FacebookHelper(this, this)

        googleHelper = GoogleHelper(this,this)
        allClickListeners()
        // initFbSignin()
        if (intent.getStringExtra("Name").equals("Tutor")) {
            btnGuest.visibility = View.GONE

        }
    }

    private fun allClickListeners() {
        btnCreate.setOnClickListener(this)
        googleBtn.setOnClickListener(this)
        fbBtn.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        btnGuest.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnCreate -> {
                if (intent.getStringExtra("Name").equals("Tutor")) {
                    startActivity(
                        Intent(this, SignUp::class.java)
                            .putExtra("Name", "Tutor")
                    )
                } else {
                    startActivity(
                        Intent(this, SignUp::class.java)
                    )
                }
            }

            R.id.btnLogin -> {

                val email = email_text.text.toString().trim()
                val password = password_text.text.toString().trim()
                if (validator.loginValid(this, email, password)) {
                    baseViewModel.Userlogin(this, email, password, "1",token,true)
                    baseViewModel.getCommonResponse().observe(this, this)
                }

            }
            R.id.tvForgotPassword -> {
                startActivity(Intent(this, ForgotPassword::class.java))
            }
            R.id.btnGuest -> {
                startActivity(Intent(this, LoginGuestActivity::class.java))
            }
            R.id.fbBtn ->{
                socialLoginType = "Fb"
                facebookHelper!!.login(this)
            }

            R.id.googleBtn ->{
                signIn()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //If signIn with google
        if (socialLoginType == "Fb")
            facebookHelper!!.onResult(requestCode, resultCode, data)
        else if (socialLoginType == "Google")
            googleHelper.onResult(requestCode, resultCode, data)
    }

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is LoginResponse) {
                    if (Api_type.equals("login")) {
                        Log.e("DEVICETOCKEN", "" + liveData.data.body.token + " dT--" + liveData.data.body.deviceToken)
                        savePrefrence(Constants.AUTH_KEY, liveData.data.body.token)

                        savePrefrence(
                            Constants.notificationStatus,
                            liveData.data.body.notificationStatus.toString())

                        savePrefrence(Constants.user_type, liveData.data.body.userType.toString())

                        if (liveData.data.body.userType == 2) {
                            when {
                                liveData.data.body.isTechingInfo == 0 -> {
                                    startActivity(
                                        Intent(this, TeachingInfoActivity::class.java)
                                            .putExtra("signup", "teacher")
                                    )
                                    finishAffinity()
                                }
                                liveData.data.body.IsAvailable == 0 -> {
                                    startActivity(
                                        Intent(this, AvailablityActivity::class.java)
                                            .putExtra("signup", "teacher")
                                    )
                                    finishAffinity()
                                }
                                liveData.data.body.status == 0 -> {
                                    shared.saveString("isApproved", "0")
                                    Toast.makeText(
                                        this,
                                        "Your request is disapproved",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                liveData.data.body.status == 1 -> {
                                    shared.saveString("isApproved", "1")

                                    savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                                    shared.isLogin = true

                                    startActivity(
                                        Intent(this, MainTeacherActivity::class.java)
                                            .putExtra("signup", "teacher")
                                    )
                                    finishAffinity()


                                }
                                liveData.data.body.status == 2 -> {
                                    shared.saveString("isApproved", "2")
                                    Toast.makeText(
                                        this,
                                        "Account is not approved from panel. Please wait and contact with admin",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }
                                else -> {
                                    savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                                    shared.isLogin = true
                                    startActivity(
                                        Intent(this, MainTeacherActivity::class.java)
                                            .putExtra("signup", "teacher")
                                    )
                                    finishAffinity()
                                }
                            }

                        } else {
                            savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                            shared.isLogin = true
                            startActivity(Intent(this, MainActivity::class.java))
                            finishAffinity()
                        }

                    }

                } else if (liveData.data is Commontoall) {
                    savePrefrence(Constants.Social_login, "True")
                    if (liveData.data.message.equals("Sorry There is no user with this socialId.")) {
                        if (getPrefrence(Constants.user_type, "").equals("1")) {
                            intent = Intent(this, DescSignupScreen::class.java)
                            intent.putExtra("image", socialImage)
                            intent.putExtra("Name", socialFirstName)
                            intent.putExtra("email", socialEmail)
                            intent.putExtra("socialId", socialId)
                            intent.putExtra("socialtype", mRcSignIn.toString())

                            // student
                        } else {
                            // Teacher
                            intent = Intent(this, AboutYouActivity::class.java)
                            intent.putExtra("key", "signup")
                            intent.putExtra("image", socialImage)
                            intent.putExtra("Name", socialFirstName)
                            intent.putExtra("email", socialEmail)
                            intent.putExtra("socialId", socialId)
                            intent.putExtra("socialtype", mRcSignIn.toString())

                        }
                        startActivity(intent)
                    } else {
                        if (getPrefrence(Constants.user_type, "").equals("1")) {
                            intent = Intent(this, DescSignupScreen::class.java)
                        } else {
                            intent = Intent(this, AboutYouActivity::class.java)
                        }
                        startActivity(intent)
                    }

                }
            }

            Status.ERROR -> {
                when (liveData.error) {
                    is Model_login -> {
                        Helper.showSuccessToast(this, liveData.error.message)
                    }
                    is Model_login -> {
                        Helper.showSuccessToast(this, liveData.error.message)
                    }
                    else -> Helper.showSuccessToast(this, "Something went wrong. Please try again")
                }
            }
            else -> {}
        }
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("Login", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result.toString()
            Log.e("Fetching FCM ", token)
        })
    }

    override fun onSuccessFacebook(bundle: Bundle?) {
        firstName = bundle!!.getString(FacebookHelper.FIRST_NAME)!!
        lastName = bundle.getString(FacebookHelper.LAST_NAME)!!

        socialEmail = if (bundle.getString(FacebookHelper.EMAIL) != null){

            bundle.getString(FacebookHelper.EMAIL)!!
        }else{
            "$socialId@gmail.com"
        }
        socialId = bundle.getString(FacebookHelper.FACEBOOK_ID)!!
        socialImage = bundle.getString(FacebookHelper.PROFILE_PIC)!!
        socialtype = "2"

        baseViewModel.sociallogin(this, socialId,socialtype,getPrefrence(Constants.user_type, ""),socialEmail,firstName+lastName,
                            "1",token,true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onCancelFacebook() {
    }

    override fun onErrorFacebook(ex: FacebookException?) {
    }

    private fun signIn() {
        socialLoginType = "Google"
        googleHelper.signIn()

    }

    override fun onSuccessGoogle(account: GoogleSignInAccount) {
        try {
            var photo = ""
            if (account.photoUrl != null) {
                photo = account.photoUrl.toString()
            }

            googleHelper.signOut()
            val fatchName = account.displayName!!.split(" ")

            try {
                var firstNames = ""
                for (i in 0 until fatchName.size - 1) {
                    firstNames = if (firstNames.isEmpty()) {
                        fatchName[i]
                    } else {
                        firstNames + " " + fatchName[i]
                    }
                }

                socialtype = "3"
                firstName = firstNames
                lastName = fatchName[fatchName.size - 1]
                socialEmail = account.email!!
                socialId = account.id!!
                socialImage = photo

                // hit api here

                baseViewModel.sociallogin(this@LoginScreen, socialId,socialtype,getPrefrence(Constants.user_type, "")
                    ,socialEmail,firstName+lastName,
                    "1",token,true)
                  baseViewModel.getCommonResponse().observe(this@LoginScreen, this)
            } catch (e: Exception) {
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    override fun onErrorGoogle() {

    }
}