package com.elewamathtutoring.Activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Models.Webview.Model_webview
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_privacy_policy.ivBack

class PrivacyPolicy : AppCompatActivity(), Observer<RestObservable> {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        window.statusBarColor = Color.TRANSPARENT;
        val intent = intent
        val text = intent.getStringExtra("key")
        //tvAppbarName.setText(text)
        api()
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

        fun api()
        {
            baseViewModel.tearmcondition_aboutus_privacypolicy(this, intent.getStringExtra("type").toString(), true)
            baseViewModel.getCommonResponse().observe(this, this)
        }

        override fun onChanged(liveData: RestObservable?) {
            when (liveData!!.status) {
                Status.SUCCESS -> {
                    if (liveData.data is Model_webview) {
                        val webview = findViewById<WebView>(R.id.tv_tctext) as WebView
                        webview.setBackgroundColor(Color.TRANSPARENT);
                        //  webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
                        webview.loadData(liveData.data.body.content, "text/html", "UTF-8")
                    }
                }
                Status.ERROR -> {
                    if (liveData.error is Model_webview)
                        Helper.showSuccessToast(this, liveData.error.message)
                }
                else -> {
                }
            }
        }
}