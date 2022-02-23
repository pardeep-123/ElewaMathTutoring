package com.elewamathtutoring.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
import retrofit2.http.Field
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
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            Log.e("checkmyauth", "----" + FirebaseInstanceId.getInstance().getToken())
            apiService.Userlogin(email, password)
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
                        Userlogin(activity, email, password, isDialogShow)
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
        availability: String,
        time: String,
        About: String,
        personVirtual: String,
        Hour: String,
        perHour: String,
        Total: String,
        cardId: String,
        date: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.book_Session(
                teacherId,
                availability,
                time,
                About,
                personVirtual,
                Hour,
                perHour,
                Total,
                cardId,
                date
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
                            availability,
                            time,
                            About,
                            personVirtual,
                            Hour,
                            perHour,
                            Total,
                            cardId,
                            date,
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
    fun teachersDetails(activity: Activity, teacht_id: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.teachersDetails(Constants.SECURITY_KEY_VALUE, teacht_id)
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
                        teachersDetails(activity, teacht_id, isDialogShow)
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
        isDialogShow: Boolean) {
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

        isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {

            //edit
            apiService.EditTeacherAvailablity(availability, timeslot)


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
            Helper.showNoInternetAlert(activity, activity.getString(R.string.no_internet_connection),
                object : OnNoInternetConnectionListener {
                    override fun onRetryApi() {
                        EditTeacherAvailablity(
                            activity,
                            availability, timeslot,
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
    fun EditTeacherProfileProfile(
        activity: Activity,
        teachingLevel: String,
        educationLevel: String,
        majors: String,
        specialties: String,
        cancelPolicy: String,
        certificate_images: String,
        address: String,
        latitude: String,
        longitude: String,

        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.EditTeacherProfileProfile(
                teachingLevel,educationLevel,majors, specialties, cancelPolicy,certificate_images,
               address, latitude, longitude
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
                        EditTeacherProfileProfile(
                            activity,
                            teachingLevel,educationLevel,majors, specialties, cancelPolicy,certificate_images,
                            address, latitude, longitude,true
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
    fun deletecard(activity: Activity, card_id: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.deletecard(card_id)
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
                        deletecard(activity, card_id, isDialogShow)
                    }
                })
        }
    }


  @SuppressLint("CheckResult")
    fun TeacherAvailability(activity: Activity, availability: String,timeSlot: String, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.TeacherAvailability(availability,timeSlot)
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
                        TeacherAvailability(activity, availability,timeSlot, isDialogShow)
                    }
                })
        }
    }





    @SuppressLint("CheckResult")
    fun signUpApi(
        activity: Activity, name: String, email: String, password: String, role: String,
        isDialogShow: Boolean
    ) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.signUpApi( name,email, password,role)
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
                        signUpApi(activity, name,email, password,role, isDialogShow)
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
                imageFileBody,  namee, emaill,passwordd, aboutt, teachingHistoryy,
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
    fun get_resources(activity: Activity, isDialogShow: Boolean) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.get_resources()
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
                        get_resources(activity, isDialogShow)
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
    fun sessionDetails(activity: Activity, isDialogShow: Boolean, id: String) {
        if (Helper.isNetworkConnected(activity)) {
            apiService.sessionDetails(id)
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
                        sessionDetails(activity, isDialogShow, id)
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
    fun EditTeacherBasicProfile(activity: Activity, imageUrl: String, name: String, about: String, history: String, isDialogShow: Boolean) {

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
        val firstName_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val about_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), about)
        val history_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), history)


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
        certificate_images: ArrayList<String>,
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
        var imageFileBody:  ArrayList<MultipartBody.Part> ?= ArrayList()

        certificate_images.forEach {imageUrl->
            var newFile: File? = null

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
                imageFileBody!!.add(MultipartBody.Part.createFormData("certificate_images", newFile.name, requestBody))
            }
        }

        val education_Level: RequestBody =
            RequestBody.create("text/plain".toMediaTypeOrNull(), educationLevel)
        val majorss: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), majors)
        val teachingLevel_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), teachingLevel)
        val specialties_value: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), specialties)
        val hourly_Price: RequestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), hourlyPrice)
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


