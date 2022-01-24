package com.elewamathtutoring.Activityimport android.graphics.Colorimport androidx.appcompat.app.AppCompatActivityimport android.os.Bundleimport android.view.Viewimport androidx.lifecycle.Observerimport androidx.lifecycle.ViewModelProviderimport com.elewamathtutoring.Adapter.ParentOrStudent.NotificationsAdapterimport com.elewamathtutoring.Models.Notifications.Bodyimport com.elewamathtutoring.Models.Notifications.Model_Notificationsimport com.elewamathtutoring.Rimport com.elewamathtutoring.api.Statusimport com.elewamathtutoring.network.RestObservableimport com.elewamathtutoring.viewmodel.BaseViewModelimport kotlinx.android.synthetic.main.activity_notifications.*import kotlinx.android.synthetic.main.activity_notifications.ivBackclass NotificationsActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {    var listNotifications = ArrayList<Body>()    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }    override fun onCreate(savedInstanceState: Bundle?) {        super.onCreate(savedInstanceState)        setContentView(R.layout.activity_notifications)        window.decorView.systemUiVisibility =            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)        window.statusBarColor = Color.TRANSPARENT;        clicks()        api()    }    private fun api() {        baseViewModel.notifications(this, true)        baseViewModel.getCommonResponse().observe(this, this)    }    private fun clicks() {        ivBack.setOnClickListener(this)    }    override fun onClick(v: View?) {        when (v!!.id) {            R.id.ivBack -> {                finish()            }        }    }    override fun onChanged(liveData: RestObservable?) {        when (liveData!!.status) {            Status.SUCCESS -> {                if (liveData.data is Model_Notifications) {                    listNotifications = liveData.data.body as ArrayList<Body>                    listNotifications.reverse()                    if(listNotifications.size==0)                    {                        tv_whennodata.visibility=View.VISIBLE                    }                    else                    {                        tv_whennodata.visibility=View.GONE                        rv_notifications.adapter = NotificationsAdapter(this,listNotifications)                    }                }            }            Status.ERROR -> {                if (liveData.error is Model_Notifications) {                }            }        }    }}