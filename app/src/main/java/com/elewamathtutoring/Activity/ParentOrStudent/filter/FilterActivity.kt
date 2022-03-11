package com.elewamathtutoring.Activity.ParentOrStudent.filter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elewamathtutoring.Adapter.ParentOrStudent.FilterAdapter
import com.elewamathtutoring.Adapter.ParentOrStudent.ResourcesFilterAdapter

import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>,
   FilterAdapter.TimeSlot {
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var list = ArrayList<SubjectsResponse.Body>()
    val timeList : ArrayList<String> = ArrayList()
    var timeString = ""
    var hour = ""
    var selectedId = ""
    val selectedItems : ArrayList<String> = ArrayList()
    var getdata_toselected_certified = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        ivBack.setOnClickListener(this)
        btnApply.setOnClickListener(this)
//        if (intent.getStringExtra("visitCount")!=null&&intent.getStringExtra("visitCount")=="1"){
//
//        }
        apiFilter()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
              finish()
            }
            R.id.btnApply -> {

                val output = Intent()
                output.putExtra("id", selectedId)
//                output.putExtra("visitCount", "1")
                setResult(101, output)
                finish()

//                setResult(101,selectedId)

            }

        }
    }

    override fun onChanged(t: RestObservable?) {
        when (t!!.status) {
            Status.SUCCESS -> {
                if (t.data is SubjectsResponse) {
                    list.clear()
                    list.addAll(t.data.body)
                    Helper.showSuccessToast(this, t.data.message)
                    rvFilter.adapter= FilterAdapter(this,list,this)
                }
            }
            Status.ERROR -> {
                if (t.error is SubjectsResponse)
                    Helper.showSuccessToast(this, t.error.message)
            }
        }
        
    }
    fun apiFilter(){
        baseViewModel.getsubjects(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun ondate(timeId: String) {
        if (timeList.contains(timeId)){
            timeList.remove(timeId)
        }else{
            timeList.add(timeId)
        }
        hour = timeList.size.toString()
//        Toast.makeText(this,hour, Toast.LENGTH_SHORT).show()
        selectedId = TextUtils.join(",",timeList)
    }


}
