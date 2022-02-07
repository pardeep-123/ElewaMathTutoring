package com.elewamathtutoring.Activity.Auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Activity.TeacherOrTutor.AboutYouActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.MainTeacherActivity
import com.elewamathtutoring.Activity.TeacherOrTutor.SubscriptionsActivity
import com.elewamathtutoring.MainActivity
import com.elewamathtutoring.Models.Login.Model_login
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.GoogleHelper
import com.elewamathtutoring.Util.Validator
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.Util.helper.extensions.savePrefrence
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.pawskeeper.Modecommon.Commontoall
import kotlinx.android.synthetic.main.activity_login_screen.*
import org.json.JSONException
import java.lang.Exception
import javax.inject.Inject

class LoginScreen : AppCompatActivity() , View.OnClickListener, Observer<RestObservable>{
  ////  GoogleHelper.GoogleHelperCallback
    @Inject
    lateinit var validator:Validator
    var mRcSignIn = 0
    private lateinit var googleHelper: GoogleHelper
    var socialFirstName=""
    var socialId = ""
    var socialImage = ""
    var  socialEmail=""
    var  Api_type="login"
    lateinit var context: Context
    var callbackManager: CallbackManager? = null
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_screen)
        App.getinstance().getmydicomponent().inject(this)
      //  googleHelper = GoogleHelper(this, this)
        allClickListeners()
       // initFbSignin()
        if( intent.getStringExtra("Name").equals("Tutor")){
            btnGuest.visibility=View.GONE


        }
    }

    fun allClickListeners()
    {
        btnCreate.setOnClickListener(this)
        googleBtn.setOnClickListener(this)
        fbBtn.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        tvForgotPassword.setOnClickListener(this)
        btnGuest.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btnCreate->
            {
                if( intent.getStringExtra("Name").equals("Tutor")){
                    startActivity(Intent(this, SignUp::class.java)
                        .putExtra("Name","Tutor"))
                }else {
                    startActivity(
                        Intent(this, SignUp::class.java))
                }
            }
          /*  R.id.googleBtn-> {
                progressBar.visibility=View.VISIBLE
                if (Helper.isNetworkConnected(this)) {
                    mRcSignIn = 2
                    googleHelper.signIn()
                    Log.e("shigsyw", "nde")
                }
                else
                {
                    Helper.showErrorAlert(this, "Check your internet connection!")
                }
            }
            R.id.fbBtn-> {

                if (Helper.isNetworkConnected(this)) {
                    progressBar.visibility=View.VISIBLE
                    mRcSignIn = 1
                    LoginManager.getInstance().logInWithReadPermissions(
                        this@LoginScreen,
                        listOf("public_profile", "email"))
                }
                else
                {
                    Helper.showErrorAlert(this, "Check your internet connection!")
                }

            }*/
            R.id.btnLogin-> {
                if( intent.getStringExtra("Name").equals("Tutor")){
                    startActivity(Intent(this, MainTeacherActivity::class.java))
                    finishAffinity()
                }else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
               /* val email = email_text.text.toString().trim()
                val password = password_text.text.toString().trim()
                if (validator.loginValid(this, email, password))
                {
                    baseViewModel.Userlogin(this,  email, password, Constants.DEVICE_TYPE_VALUE,  true)
                    baseViewModel.getCommonResponse().observe(this, this)
                }*/
            }
            R.id.tvForgotPassword->{
                startActivity(Intent(this, ForgotPassword::class.java))
            }
            R.id.btnGuest->{
                startActivity(Intent(this, LoginGuestActivity::class.java))
            }

           /* R.id.tvNeweTeacher->{
                savePrefrence(Constants.user_type, "2")
                startActivity(Intent(this, SignUp::class.java))
            }*/

        }
    }
  /*  private fun initFbSignin() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                GetFBData(loginResult)
            }
            override fun onCancel() {
                progressBar.visibility=View.GONE
            }
            override fun onError(exception: FacebookException) {
                progressBar.visibility=View.GONE
            }
        })
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        Log.e("poonam", "qwqw" + mRcSignIn)
        super.onActivityResult(requestCode, resultCode, data)
        //If signIn with google
        googleHelper.onResult(9001, resultCode, data)
    }

/*
    private fun GetFBData(loginResult: LoginResult) {
       progressBar.visibility=View.GONE
        val request = GraphRequest.newMeRequest(loginResult.accessToken) { `object`, response ->
            try {
                socialId=response.jsonObject.getString("id")
                val fbId = response.jsonObject.getString("id")

                if (!fbId.isEmpty()) {
                    socialFirstName = response.jsonObject.getString("first_name")+" "+response.jsonObject.getString("last_name")

                    try {
                        if(response.jsonObject.getString("email").equals("null")||response.jsonObject.getString("email").equals(""))
                        {
                            socialEmail = response.jsonObject.getString("email")
                        }
                        else
                        {
                            socialEmail=fbId.toString()+"@gmail.com"
                        }
                    }catch (e: Exception)
                    {
                        socialEmail=fbId.toString()+"@gmail.com"
                    }
                    checksocialId(fbId.toString(),"1")
                }
            } catch (e: JSONException) {
                Toast.makeText(context, "" + e, Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }

        }
        val parameters = Bundle()
        parameters.putString("fields","id,email,picture.type(large),first_name,last_name,gender,birthday,location")
        request.parameters = parameters
        request.executeAsync()
    }

    override fun onSuccessGoogle(account: GoogleSignInAccount?) {
        progressBar.visibility=View.GONE
        Log.e("checkgoogle", "trueeee")
        socialId = account!!.id!!

        if(account.email.equals("null")||account.email.equals(""))
        {
            socialEmail = ""
        }
        else
        {
            socialEmail = account.email!!
        }

        if (account.photoUrl != null && !account.photoUrl.toString().isEmpty())
        {
            socialImage = account.photoUrl!!.toString()
        }
        else
        {
            socialImage = ""
        }
        try
        {
            val fatchName = account.displayName!!.split(" ")
            Log.i("fatchName", fatchName.get(fatchName.size - 1))
            socialFirstName = fatchName.get(0)
        }
        catch (e: Exception) {
            socialFirstName = ""
            Log.e("checkgoogle", "catchsucess")
        }
        checksocialId(socialId,"2")
    }

    override fun onErrorGoogle() {
        progressBar.visibility=View.GONE
        Log.e("checkgoogle", "falls")
        if(mRcSignIn==2)
        {
            Helper.showErrorAlert(this, "Something went wrong")
        }
    }*/

    override fun onChanged(liveData: RestObservable?) {
        when (liveData!!.status) {
            Status.SUCCESS -> {
                if (liveData.data is Model_login) {
                    if (Api_type.equals("login")) {
                        Log.e("DEVICETOCKEN", "" + liveData.data.body.token + " dT--" + liveData.data.body.deviceToken)
                        savePrefrence(Constants.AUTH_KEY, liveData.data.body.token.toString())
                        savePrefrence(Constants.USER_ID, liveData.data.body.id.toString())
                        savePrefrence(Constants.notificationStatus, liveData.data.body.notificationStatus.toString())
                        savePrefrence(Constants.Social_login, "False")
                        savePrefrence(Constants.user_type, liveData.data.body.userType.toString())
                        Constants.USER_IDValue=liveData.data.body.id.toString()
                        if (liveData.data.body.isBuyPlan == 0&&liveData.data.body.userType == 2)
                        {
                            startActivity(Intent(this, SubscriptionsActivity::class.java))
                            finishAffinity()
                        }
                        else
                        {
                            if (liveData.data.body.userType == 1)
                            {
                                startActivity(Intent(this, MainActivity::class.java))
                                finishAffinity()
                            }
                            else
                            {
                                startActivity(Intent(this, MainTeacherActivity::class.java))
                                finishAffinity()
                            }
                        }
                    }
                    else if(Api_type.equals("checksocialId"))
                    {
                    }
                }
            else if(liveData.data is Commontoall)
                {
                    savePrefrence(Constants.Social_login, "True")
                    if(liveData.data.message.equals("Sorry There is no user with this socialId."))
                    {
                        if (getPrefrence(Constants.user_type, "").equals("1")) {
                            intent = Intent(this, DescSignupScreen::class.java)
                            intent.putExtra("image", socialImage)
                            intent.putExtra("Name", socialFirstName)
                            intent.putExtra("email", socialEmail)
                            intent.putExtra("socialId", socialId)
                            intent.putExtra("socialtype", mRcSignIn.toString())

                            // student
                        }
                        else
                        {
                            // Teacher
                            intent = Intent(this, AboutYouActivity::class.java)
                            intent.putExtra("key", "signup")
                            intent.putExtra("image", socialImage)
                            intent.putExtra("Name", socialFirstName)
                            intent.putExtra("email", socialEmail)
                            intent.putExtra("socialId", socialId)
                            intent.putExtra("socialtype",  mRcSignIn.toString())

                        }
                        startActivity(intent)
                    }
                    else
                    {
                        if (getPrefrence(Constants.user_type, "").equals("1")) {
                            intent = Intent(this, DescSignupScreen::class.java)
                        }
                        else
                        {
                            intent = Intent(this, AboutYouActivity::class.java)
                        }
                        startActivity(intent)
                    }

                }
            else
            {

            }
            }

            Status.ERROR -> {
                if (liveData.error is Model_login)
                {
                    Helper.showSuccessToast(this, liveData.error.message)
                }
               else if (liveData.error is Model_login)
                                {
                                    Helper.showSuccessToast(this, liveData.error.message)
                                }
            }
            else -> {
            }
        }
    }


    fun checksocialId(f_id: String,type:String)
    {
        Log.e("checkmybitmapaarray","---"+f_id.toString())

        Api_type="checksocialId"
        baseViewModel.checkSocialLoginExists(this,  f_id, type,   true)
        baseViewModel.getCommonResponse().observe(this, this)
    }
}