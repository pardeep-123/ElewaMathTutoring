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
    var timeList: ArrayList<String> = ArrayList()
    var listName: ArrayList<String> = ArrayList()
    var idList = ArrayList<String>()
    var hour = ""
    var selectedId = ""
    var selectedName = ""
   var adapter : FilterAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        ivBack.setOnClickListener(this)
        btnApply.setOnClickListener(this)
        tvClear.setOnClickListener(this)
    /*    if (intent.getStringExtra("visitCount") != null && intent.getStringExtra("visitCount") == "1") {
  val name = data.getStringExtra("id").toString()
        }*/
        val id= intent.getStringExtra("editId")
        val name= intent.getStringExtra("editName")

        if(id!== null){
            if (id.contains(",") && name?.contains(",")!!) {
                idList = id.split(",") as ArrayList<String>
                timeList = id.split(",") as ArrayList<String>
                listName = name.split(",") as ArrayList<String>
            }else{
                idList.add(id)
                timeList.add(id)
                listName.add(name!!)
            }
        }

        apiFilter()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.tvClear -> {
                timeList.clear()
                listName.clear()
                list.forEach {
                    it.check = false
                }
                adapter?.notifyDataSetChanged()
            }
            R.id.btnApply -> {

                val i = Intent()
                i.putExtra("id", selectedId)
                i.putExtra("name", selectedName)
//                i.putExtra("visitCount", "1")
                setResult(101, i)
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
                    list.forEachIndexed { index, body ->
                            idList.forEach { it2 ->
                                if (body.id.toString() == it2)
                                    list[index].check=true

                        }
                    }
                    adapter = FilterAdapter(this, list, this)
                    rvFilter.adapter = adapter
                }
            }
            Status.ERROR -> {
                if (t.error is SubjectsResponse)
                    Helper.showSuccessToast(this, t.error.message)
            }
        }

    }

    fun apiFilter() {
        baseViewModel.getsubjects(this, true)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun ondate(timeId: String,name:String) {
        if (timeList.contains(timeId)) {
            timeList.remove(timeId)
            listName.remove(name)
        } else {
            timeList.add(timeId)
            listName.add(name)
        }
        hour = timeList.size.toString()
//        Toast.makeText(this,hour, Toast.LENGTH_SHORT).show()
        selectedId = TextUtils.join(",", timeList)
        selectedName = TextUtils.join(",", listName)
    }


}
