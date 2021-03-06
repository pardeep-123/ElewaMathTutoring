package com.elewamathtutoring.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.service.controls.DeviceTypes
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elewamathtutoring.Model.ImageModel
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.App
import com.elewamathtutoring.Util.OnNoInternetConnectionListener
import com.elewamathtutoring.Util.constant.Constants
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.Util.helper.extensions.getPrefrence
import com.elewamathtutoring.api.RestApiInterface
import com.elewamathtutoring.network.RestObservable
import com.google.firebase.iid.FirebaseInstanceId
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject


class BaseViewModel : ViewModel() {
    @Inject
    lateinit var apiService: RestApiInterface

    init {
        App.getinstance().getmydicomponent().inject(this)
    }

    private var mResponse: MutableLiveData<RestObservable> = MutableLiveData()

    fun getCommonResponse(): LiveData<RestObservable> {
        return mResponse
    }

    @SuppressLint("CheckResult")
    fun checkSocialLoginExists(
        activity: Activity,
        socialid: String,
        socialType: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.checkSocialLoginExists(
                socialid,
                socialType,
                Constants.deviceType,
                "" + FirebaseInstanceId.getInstance().getToken(),
                getPrefrence(Constants.user_type, "")
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        checkSocialLoginExists(activity, socialid, socialType, true)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun Userlogin(
        activity: Activity,
        email: String,
        password: String,
        type: String,
        token: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            Log.e("checkmyauth", "----" + FirebaseInstanceId.getInstance().getToken())
            apiService.Userlogin(email, password, type, token)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {
                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        Userlogin(activity, email, password, "1", token, isDialogShow)
                    }
                })
        }
    }

    // social login


    @SuppressLint("CheckResult")
    fun sociallogin(
        activity: Activity,
        socialId: String,
        socialType: String,
        user_type: String,
        email: String,
        name: String,
        type: String,
        token: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.sociallogin(socialId, socialType, user_type, email, name, type, token)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {
                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        apiService.sociallogin(
                            socialId,
                            socialType,
                            user_type,
                            email,
                            name,
                            type,
                            token
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun withdrawlAmount(
        activity: Activity,
        amount: String,
        bankId: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            Log.e("checkmyauth", "----" + FirebaseInstanceId.getInstance().getToken())
            apiService.withdrawlAmount(
                // getPrefrence(Constants.user_type, ""),
                amount,
                bankId
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        withdrawlAmount(activity, amount, bankId, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun Change_Password(
        activity: Activity,
        oldpassword: String,
        newpassword: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.change_password(oldpassword, newpassword)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        Change_Password(activity, oldpassword, newpassword, true)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun AddComments(
        activity: Activity,
        hashMap: HashMap<String, String>,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.AddComments(hashMap)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        AddComments(activity, hashMap, true)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun notifications(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.notifications()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        notifications(activity, true)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun myMathProblems(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.myMathProblems()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        myMathProblems(activity, true)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun mathProblems(
        activity: Activity,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.mathProblems()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        mathProblems(activity, true)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun get_time_slots(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.get_time_slots()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        notifications(activity, true)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun Delete_account(activity: Activity, user_type: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.Delete_account()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        Delete_account(activity, user_type, true)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun promocode_exist(activity: Activity, code: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.promocode_exist(code)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        promocode_exist(activity, code, true)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun buy_plan(
        activity: Activity,
        type: String,
        price: String,
        name: String,
        duratio: String,
        planId: String,
        planExpiryDate: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.buy_plan(type, price, name, duratio, planId, planExpiryDate)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        buy_plan(activity, type, price, name, duratio, planId, planExpiryDate, true)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun Logout(activity: Activity, user_type: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.Logout(user_type)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        Logout(activity, user_type, true)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun change_session_status(
        activity: Activity,
        sessionStatus: String,
        sessionId: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.change_session_status(sessionStatus, sessionId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        change_session_status(activity, sessionStatus, sessionId, true)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun book_Session(
        activity: Activity,
        teacherId: String,
        //  time: String,
        About: String,
        cardId: String,
        date: String,
        times: String,
        Hour: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.book_Session(
                teacherId,
                // time,
                About,
                cardId,
                date,
                times,
                Hour

            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        book_Session(
                            activity,
                            teacherId,
                            //  time,
                            About,
                            cardId,
                            date,
                            times,
                            Hour,
                            true
                        )
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun call_notification_on_offApi(activity: Activity, status: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.call_notification_on_offApi(status, getPrefrence(Constants.user_type, ""))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        call_notification_on_offApi(activity, status, true)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun occupiedStatus(activity: Activity, occupiedStatus: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.occupiedStatus(occupiedStatus)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        occupiedStatus(activity, occupiedStatus, true)
                    }
                })
        }

    }


    @SuppressLint("CheckResult")
    fun addBank(
        activity: Activity,
        id: String,
        code: String,
        branch: String,
        accountNumber: String,
        accountHolderNumber: String,
        type: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            if (type.equals("Edit")) {
                apiService.edit_bank(id, branch, accountNumber, accountHolderNumber, code, "1")
            } else {
                apiService.add_bank(branch, accountNumber, accountHolderNumber, code, "1")
            }

                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        addBank(
                            activity,
                            id,
                            code,
                            branch,
                            accountNumber,
                            accountHolderNumber, type,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun PastTeacher(activity: Activity, status: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.PastTeacher(status, getPrefrence(Constants.user_type, ""))
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        PastTeacher(activity, status, isDialogShow)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun teachersDetails(activity: Activity, teacher_id: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.teachersDetails(teacher_id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        teachersDetails(activity, teacher_id, isDialogShow)
                    }
                })
        }

    }

    @SuppressLint("CheckResult")
    fun addcard(
        activity: Activity, etCardnumber: String, year: String, month: String,
        edNameOfCard: String, cvv: String, issave: String, type: String, isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {

            //edit

            apiService.addcard(etCardnumber, year, month, edNameOfCard, issave, cvv)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        addcard(
                            activity,
                            etCardnumber,
                            year,
                            month,
                            edNameOfCard,
                            cvv,
                            issave,
                            type,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun editCard(
        activity: Activity,
        etCardnumber: String,
        year: String,
        month: String,
        edNameOfCard: String,
        cvv: String,
        issave: String,
        type: String,
        idd: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {

            //edit
            apiService.editCard(etCardnumber, year, month, edNameOfCard, issave, cvv, idd)


                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editCard(
                            activity,
                            etCardnumber,
                            year,
                            month,
                            edNameOfCard,
                            cvv,
                            issave,
                            type,
                            idd,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun EditTeacherAvailablity(
        activity: Activity,
        availability: String,
        timeslot: String,
        freeSlot: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {

            //edit
            apiService.EditTeacherAvailablity(availability, timeslot, freeSlot)


                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        EditTeacherAvailablity(
                            activity,
                            availability, timeslot, freeSlot,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun bankAccounts(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            //edit
            apiService.bankAccounts()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        bankAccounts(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getTeacherStudentList(
        activity: Activity,
        userType: String,
        subjects_id: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.getTeacherStudentList(userType, subjects_id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getTeacherStudentList(activity, userType, subjects_id, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun EditTeacherProfileProfile(
        activity: Activity,
        certificate_images: ArrayList<ImageModel>,
        teachingLevel: String,
        educationLevel: String,
        majors: String,
        specialties: String,
        cancelPolicy: String,
        hourlyPrice: String,
        address: String,
        latitude: String,
        longitude: String,
        isDialogShow: Boolean
    ) {
        var imageFileBody: ArrayList<MultipartBody.Part>? = ArrayList()

        certificate_images.forEach { it ->
            var newFile: File? = null
            if (it.isGalleryAdded) {
                if (it.image != "") {
                    newFile = File(it.image)
                }
                if (newFile != null && newFile.exists() && !newFile.equals("")) {
                    val mediaType: MediaType?
                    if (it.image.endsWith("png")) {
                        mediaType = "image/png".toMediaTypeOrNull()
                    } else {
                        mediaType = "image/jpeg".toMediaTypeOrNull()
                    }

                    val requestBody: RequestBody = newFile.asRequestBody(mediaType)
                    imageFileBody!!.add(
                        MultipartBody.Part.createFormData(
                            "certificate_images",
                            newFile.name,
                            requestBody
                        )
                    )
                }
            }

        }
        val teachingLevel_value1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), teachingLevel)
        val education_Level1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), educationLevel)
        val majorss1: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), majors)
        val specialties_value1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), specialties)
        val cancelPolicy_value1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), cancelPolicy)
        val hourlyPrice1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), hourlyPrice)
        val address_value1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), address)
        val latitude_value1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), latitude)
        val longitude_value1: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), longitude)
        if (Helper.isNetworkConnected(activity)) {
            if (imageFileBody != null) {
                apiService.EditTeacherProfileProfile(
                    imageFileBody,
                    teachingLevel_value1,
                    education_Level1,
                    majorss1,
                    specialties_value1,
                    cancelPolicy_value1,
                    hourlyPrice1,
                    address_value1,
                    latitude_value1,
                    longitude_value1
                )

                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.doOnSubscribe {
                        mResponse.value = RestObservable.loading(activity, isDialogShow)
                    }
                    ?.subscribe(
                        { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                        { mResponse.value = RestObservable.error(activity, it) }
                    )
            }
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        EditTeacherProfileProfile(
                            activity,
                            certificate_images,
                            teachingLevel,
                            educationLevel,
                            majors,
                            specialties,
                            cancelPolicy,
                            hourlyPrice,
                            address,
                            latitude,
                            longitude,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun card_listing(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.card_listing()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        card_listing(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun deletecard(activity: Activity, cardId: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.deletecard(cardId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deletecard(activity, cardId, isDialogShow)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun TeacherAvailability(
        activity: Activity,
        availability: String,
        timeSlot: String,
        freeSlot: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.TeacherAvailability(availability, timeSlot, freeSlot)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        TeacherAvailability(
                            activity,
                            availability,
                            timeSlot,
                            freeSlot,
                            isDialogShow
                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun signUpApi(
        activity: Activity, name: String, email: String, password: String, role: String,
        isDialogShow: Boolean,deviceType : String,deviceToken:String
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.signUpApi(name, email, password, role,deviceType,deviceToken)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        signUpApi(activity, name, email, password, role, isDialogShow,deviceType,deviceToken)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun signUpTeacherApi(
        activity: Activity,
        imageUrl: String,
        name: String,
        email: String,
        password: String,
        about: String,
        TeachingHistory: String,
        isDialogShow: Boolean
    ) {
        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }
            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }
        val device_type_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), Constants.DEVICE_TYPE_VALUE)

        val namee: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val emaill: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val passwordd: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), password)
        val aboutt: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val teachingHistoryy: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), TeachingHistory)
        val token_valuee: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            FirebaseInstanceId.getInstance().getToken().toString()
        )

        if (Helper.isNetworkConnected(activity)) {
            apiService.signUpTeacherApi(
                imageFileBody, namee, emaill, passwordd, aboutt, teachingHistoryy,
                device_type_value, token_valuee
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        signUpTeacherApi(
                            activity,
                            imageUrl,
                            name,
                            email,
                            password,
                            about,
                            TeachingHistory,
                            isDialogShow
                        )
                    }
                })
        }
    }

 @SuppressLint("CheckResult")
    fun imageUpload(
        activity: Activity,
        imageUrl: String,
        isDialogShow: Boolean
    ) {
        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }
            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }
        if (Helper.isNetworkConnected(activity)) {
            apiService.imageUpload(
                imageFileBody
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        imageUpload(
                            activity,
                            imageUrl,
                            isDialogShow
                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun deleteBank(activity: Activity, bank_id: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.deleteBank(bank_id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deleteBank(activity, bank_id, isDialogShow)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun deletePostMathProblem(activity: Activity, id: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.deletePostMathProblem(id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        deletePostMathProblem(activity, id, isDialogShow)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun SetDefault_Bank(activity: Activity, bank_id: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.SetDefault_Bank(bank_id, "1")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        SetDefault_Bank(activity, bank_id, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun report(activity: Activity, cmt: String, idd: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.report(cmt, idd)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        report(activity, cmt, idd, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun tearmcondition_aboutus_privacypolicy(
        activity: Activity,
        type: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.tearmcondition_aboutus_privacypolicy(type)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        tearmcondition_aboutus_privacypolicy(activity, type, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun forgetPassword(activity: Activity, email: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.forgetPassword(email)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        forgetPassword(activity, email, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun get_profile(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.get_profile()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        get_profile(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun getProfile(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.getProfile()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getProfile(activity, isDialogShow)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun getsubjects(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.getsubjects()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {
                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getsubjects(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun TeacherRequestList(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.TeacherRequestList()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        TeacherRequestList(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun get_resources(activity: Activity, isDialogShow: Boolean, category_id: String) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.get_resources(category_id)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        get_resources(activity, isDialogShow, category_id)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun get_comments(activity: Activity, isDialogShow: Boolean, problemId: String) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.get_comments(problemId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        get_comments(activity, isDialogShow, problemId)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun getcategories(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.getcategories()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        getcategories(activity, isDialogShow)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun teacher_level(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.teacher_level()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        teacher_level(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun parentSchedulingList(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.parentSchedulingList()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        parentSchedulingList(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun sessionDetails(activity: Activity, isDialogShow: Boolean, sessionId: String) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.sessionDetails(sessionId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        sessionDetails(activity, isDialogShow, sessionId)
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun requestAccept(
        activity: Activity,
        status: String,
        sessionId: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.requestAccept(status, sessionId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        requestAccept(activity, status, sessionId, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun requestReject(
        activity: Activity,
        sessionId: String,
        status: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.requestReject(sessionId, status)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        requestReject(activity, sessionId, status, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun listViewSession(activity: Activity, isDialogShow: Boolean, date: String) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.listViewSession(date)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {
                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        teacher_level(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun seach_teachers(
        activity: Activity,
        limit: String,
        page: String,
        CertifiedAs: String,
        maximumDistance: String,
        searchText: String,
        teachingLevel: String,
        lat: String,
        lng: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.seach_teachers(
                Constants.SECURITY_KEY_VALUE,
                limit,
                page,
                CertifiedAs,
                maximumDistance,
                searchText,
                teachingLevel,
                lat,
                lng
            )
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {
                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        seach_teachers(
                            activity,
                            limit,
                            page,
                            CertifiedAs,
                            maximumDistance,
                            searchText,
                            teachingLevel,
                            lat,
                            lng,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun Get_Wallet(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.Get_Wallet()
                //getPrefrence(Constants.user_type, "")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    {

                        mResponse.value = RestObservable.error(activity, it)
                    })
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        Get_Wallet(activity, isDialogShow)
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun signinUser(
        activity: Activity,
        imageUrl: String,
        password: String,
        firstName: String,
        email: String,
        about: String,
        isDialogShow: Boolean
    ) {

        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }

            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }
        val device_type_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), Constants.DEVICE_TYPE_VALUE)
        val firstName_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), firstName)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val pasword_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), password)
        val email_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val token_value: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            FirebaseInstanceId.getInstance().getToken().toString()
        )

        if (Helper.isNetworkConnected(activity)) {
            apiService.signinUser(
                imageFileBody,
                firstName_value,
                email_value,
                pasword_value,
                about_value,
                device_type_value,
                token_value
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        signinUser(
                            activity,
                            imageUrl,
                            password,
                            firstName,
                            email,
                            about,
                            isDialogShow
                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun ParentSocialSignup(
        activity: Activity,
        imageUrl: String?,
        socialid: String,
        firstName: String,
        email: String,
        socialtype: String,
        about: String,
        isDialogShow: Boolean
    ) {

        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl!!.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }

            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }

        val device_type_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), Constants.DEVICE_TYPE_VALUE)
        val firstName_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), firstName)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val socialid_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), socialid)
        val email_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val socialtype_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), socialtype)
        val token_value: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            FirebaseInstanceId.getInstance().getToken().toString()
        )

        if (Helper.isNetworkConnected(activity)) {
            apiService.ParentSocialSignup(
                imageFileBody, firstName_value, email_value,
                socialid_value, about_value, socialtype_value, device_type_value, token_value
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        ParentSocialSignup(
                            activity,
                            imageUrl,
                            socialid,
                            firstName,
                            email,
                            about,
                            socialtype,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun editParentProfile(
        activity: Activity,
        imageUrl: String,
        firstName: String,
        about: String,
        isDialogShow: Boolean
    ) {
        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }
            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }
        val firstName_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), firstName)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        if (Helper.isNetworkConnected(activity)) {
            apiService.editParentProfile(
                imageFileBody,
                firstName_value,
                about_value
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editParentProfile(
                            activity,
                            imageUrl,

                            firstName,

                            about,
                            isDialogShow
                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun editMyMathProblems(
        activity: Activity,
        imageUrl: String,
        description: String,
        id: String,
        isDialogShow: Boolean
    ) {
        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }
            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("document", newFile.name, requestBody)
        }
        val post_description: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val post_id: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), id)
        if (Helper.isNetworkConnected(activity)) {
            apiService.editMyMathProblems(
                imageFileBody,
                post_description,
                post_id
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        editMyMathProblems(
                            activity,
                            imageUrl,
                            description,
                            id,
                            isDialogShow
                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun postMathProblem(
        activity: Activity,
        imageUrl: String,
        description: String,
        type: String,
        isDialogShow: Boolean
    ) {
        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }
            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("document", newFile.name, requestBody)
        }
        val post_description: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), description)
        val post_type: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), type)

        if (Helper.isNetworkConnected(activity)) {
            apiService.postMathProblem(
                imageFileBody,
                post_description,
                post_type
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        postMathProblem(
                            activity,
                            imageUrl,
                            description,
                            type,
                            isDialogShow
                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun EditTeacherBasicProfile(
        activity: Activity,
        imageUrl: String,
        name: String,
        about: String,
        history: String,
        isDialogShow: Boolean
    ) {

        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }

            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }
        val firstName_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val history_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), history)


        if (Helper.isNetworkConnected(activity)) {
            apiService.EditTeacherBasicProfile(
                imageFileBody,
                firstName_value,
                about_value,
                history_value
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        EditTeacherBasicProfile(
                            activity,
                            imageUrl,
                            name,
                            about,
                            history,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun signas_teacher(
        activity: Activity,
        imageUrl: String,
        firstName: String,
        email: String,
        password: String,
        about: String,
        TeachingHistory: String,
        teachingLevel: String,
        specialties: String,
        inPersonRate: String,
        cancelPolicy: String,
        VirtualRate: String,
        CertifiedAs: String,
        address: String,
        latitude: String,
        longitude: String,
        availability: String,
        timeSlot: String,
        isDialogShow: Boolean
    ) {

        var newFile: File? = null
        var imageFileBody: MultipartBody.Part? = null
        if (imageUrl != "") {
            newFile = File(imageUrl)
        }
        if (newFile != null && newFile.exists() && !newFile.equals("")) {
            val mediaType: MediaType?
            if (imageUrl.endsWith("png")) {
                mediaType = "image/png".toMediaTypeOrNull()
            } else {
                mediaType = "image/jpeg".toMediaTypeOrNull()
            }

            val requestBody: RequestBody = newFile.asRequestBody(mediaType)
            imageFileBody = MultipartBody.Part.createFormData("image", newFile.name, requestBody)
        }
        val device_type_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), Constants.DEVICE_TYPE_VALUE)
        val firstName_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), firstName)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val pasword_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), password)
        val email_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val TeachingHistory_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), TeachingHistory)
        val teachingLevel_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), teachingLevel)
        val specialties_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), specialties)
        val inPersonRate_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), inPersonRate)
        val cancelPolicy_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), cancelPolicy)
        val VirtualRate_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), VirtualRate)
        val CertifiedAs_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), CertifiedAs)
        val address_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), address)
        val latitude_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), latitude)
        val longitude_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), longitude)
        val availability_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), availability)
        val timeSlot_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), timeSlot)
        val token_value: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            FirebaseInstanceId.getInstance().getToken().toString()
        )

        if (Helper.isNetworkConnected(activity)) {
            apiService.signas_teacher(
                imageFileBody,
                firstName_value,
                email_value,
                pasword_value,
                about_value,
                TeachingHistory_value,
                teachingLevel_value,
                specialties_value,
                inPersonRate_value,
                cancelPolicy_value,
                VirtualRate_value,
                CertifiedAs_value,
                address_value,
                latitude_value,
                longitude_value,
                availability_value,
                timeSlot_value,
                device_type_value,
                token_value
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        signas_teacher(
                            activity,
                            imageUrl,
                            firstName,
                            email,
                            password,
                            about,
                            TeachingHistory,
                            teachingLevel,
                            specialties,
                            inPersonRate,
                            cancelPolicy,
                            VirtualRate,
                            CertifiedAs,
                            address,
                            latitude,
                            longitude,
                            availability,
                            timeSlot,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun teachingInfoApi(
        activity: Activity,
        certificate_images: ArrayList<ImageModel>,
        educationLevel: String,
        majors: String,
        teachingLevel: String,
        specialties: String,
        hourlyPrice: String,
        cancelPolicy: String,
        address: String,
        latitude: String,
        longitude: String,
        isDialogShow: Boolean
    ) {
        var imageFileBody: ArrayList<MultipartBody.Part>? = ArrayList()

        certificate_images.forEach { it ->
            var newFile: File? = null

            if (it.isGalleryAdded) {
                if (it.image != "") {
                    newFile = File(it.image)
                }
                if (newFile != null && newFile.exists() && !newFile.equals("")) {
                    val mediaType: MediaType?
                    if (it.image.endsWith("png")) {
                        mediaType = "image/png".toMediaTypeOrNull()
                    } else {
                        mediaType = "image/jpeg".toMediaTypeOrNull()
                    }

                    val requestBody: RequestBody = newFile.asRequestBody(mediaType)
                    imageFileBody!!.add(
                        MultipartBody.Part.createFormData(
                            "certificate_images",
                            newFile.name,
                            requestBody
                        )
                    )
                }
            }

        }

        val education_Level: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), educationLevel)
        val majorss: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), majors)
        val teachingLevel_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), teachingLevel)
        val specialties_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), specialties)
        val hourly_Price: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), hourlyPrice)
        val cancelPolicy_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), cancelPolicy)
        val address_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), address)
        val latitude_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), latitude)
        val longitude_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), longitude)
        if (Helper.isNetworkConnected(activity)) {
            if (imageFileBody != null) {
                apiService.teachingInfoApi(
                    imageFileBody,
                    education_Level,
                    majorss,
                    teachingLevel_value,
                    specialties_value,
                    hourly_Price,
                    cancelPolicy_value,
                    address_value,
                    latitude_value,
                    longitude_value,
                )
                    .subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.doOnSubscribe {
                        mResponse.value = RestObservable.loading(activity, isDialogShow)
                    }
                    ?.subscribe(
                        { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                        { mResponse.value = RestObservable.error(activity, it) }
                    )
            }
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        teachingInfoApi(
                            activity,
                            certificate_images,
                            educationLevel,
                            majors,
                            teachingLevel,
                            specialties,
                            hourlyPrice,
                            cancelPolicy,
                            address,
                            latitude,
                            longitude,
                            isDialogShow

                        )
                    }
                })
        }
    }


    @SuppressLint("CheckResult")
    fun TeacherSocialSignup(
        activity: Activity,
        imageUrl: String,
        socialtype: String,
        socialId: String,
        firstName: String,
        email: String,
        password: String,
        about: String,
        TeachingHistory: String,
        teachingLevel: String,
        specialties: String,
        inPersonRate: String,
        cancelPolicy: String,
        VirtualRate: String,
        CertifiedAs: String,
        address: String,
        latitude: String,
        longitude: String,
        availability: String,
        timeSlot: String,
        isDialogShow: Boolean
    ) {
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageUrl)
        val imageFileBody =
            MultipartBody.Part.createFormData("image", "image_" + ".jpg", requestFile)


        val device_type_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), Constants.DEVICE_TYPE_VALUE)
        val firstName_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), firstName)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val pasword_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), password)
        val email_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val TeachingHistory_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), TeachingHistory)
        val teachingLevel_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), teachingLevel)
        val specialties_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), specialties)
        val inPersonRate_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), inPersonRate)
        val cancelPolicy_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), cancelPolicy)
        val VirtualRate_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), VirtualRate)
        val CertifiedAs_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), CertifiedAs)
        val address_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), address)
        val latitude_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), latitude)
        val longitude_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), longitude)
        val availability_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), availability)
        val timeSlot_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), timeSlot)
        val socialId_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), socialId)
        val socialtype_value: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), socialtype)
        val token_value: RequestBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            FirebaseInstanceId.getInstance().getToken().toString()
        )

        if (Helper.isNetworkConnected(activity)) {
            apiService.TeacherSocialSignup(
                imageFileBody, socialtype_value, socialId_value,
                firstName_value,
                email_value,
                pasword_value,
                about_value,
                TeachingHistory_value,
                teachingLevel_value,
                specialties_value,
                inPersonRate_value,
                cancelPolicy_value,
                VirtualRate_value,
                CertifiedAs_value,
                address_value,
                latitude_value,
                longitude_value,
                availability_value,
                timeSlot_value,
                device_type_value,
                token_value
            )
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        TeacherSocialSignup(
                            activity,
                            imageUrl, socialtype, socialId,
                            firstName,
                            email,
                            password,
                            about,
                            TeachingHistory,
                            teachingLevel,
                            specialties,
                            inPersonRate,
                            cancelPolicy,
                            VirtualRate,
                            CertifiedAs,
                            address,
                            latitude,
                            longitude,
                            availability,
                            timeSlot,
                            isDialogShow
                        )
                    }
                })
        }
    }

    @SuppressLint("CheckResult")
    fun SendFeedback(
        activity: Activity, message: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.sendFeedback(message)
                .subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.doOnSubscribe {
                    mResponse.value = RestObservable.loading(activity, isDialogShow)
                }
                ?.subscribe(
                    { mResponse.value = it?.let { it1 -> RestObservable.success(it1) } },
                    { mResponse.value = RestObservable.error(activity, it) }
                )
        } else {
            Helper.showNoInternetAlert(activity,
                activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        SendFeedback(activity, message, isDialogShow)
                    }
                })
        }
    }


}


