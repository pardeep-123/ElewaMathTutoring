package com.elewamathtutoring.Activity.ParentOrStudent.resources

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.elewamathtutoring.Adapter.ParentOrStudent.*
import com.elewamathtutoring.R
import com.elewamathtutoring.api.Status
import com.elewamathtutoring.network.RestObservable
import com.elewamathtutoring.viewmodel.BaseViewModel
import kotlinx.android.synthetic.main.activity_resouces.*


class ResoucesActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>,
    ResourcesFilterAdapter.Id {
    lateinit var context: Context
    val baseViewModel: BaseViewModel by lazy { ViewModelProvider(this).get(BaseViewModel::class.java) }
    var list = ArrayList<CategoriesResponse.Body>()
    var listResources = ArrayList<ResourcesResponse.Body>()
    var aboutResponse: ResourcesResponse? = null
    lateinit var resourcesAdapter: ResourcesAdapter
    lateinit var resourcesFilterAdapter: ResourcesFilterAdapter
    private var popupWindow: PopupWindow? = null
    var cardId = ""
    val timeList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resouces)
        context = this
        ivBack.setOnClickListener(this)
        ivFilter.setOnClickListener(this)

        apiFilter()

//7508501520...
    }

    override fun onResume() {
        super.onResume()
        api()
    }

    private fun setPopUpWindow() {
        val viewGroup: ViewGroup = window.decorView.rootView as ViewGroup
        val inflater =
            applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_resouces, null)
        popupWindow = PopupWindow(view, 450, RelativeLayout.LayoutParams.WRAP_CONTENT, true)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_popup)

        recyclerView.adapter = ResourcesFilterAdapter(this, list, this)

        ivFilter.setOnClickListener {
            popupWindow?.showAsDropDown(it, 150, 48)
            applyDim(viewGroup, 0.5f)
        }
        popupWindow?.setOnDismissListener {
            clearDim(viewGroup)
        }
    }

    fun applyDim(parent: ViewGroup, dimAmount: Float) {
        val dim: Drawable = ColorDrawable(Color.BLACK)
        dim.setBounds(0, 0, parent.width, parent.height)
        dim.alpha = (255 * dimAmount).toInt()
        val overlay = parent.overlay
        overlay.add(dim)
    }

    fun clearDim(parent: ViewGroup) {
        val overlay = parent.overlay
        overlay.clear()
    }

    fun apiFilter() {
        baseViewModel.getcategories(this, false)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivFilter -> {
                setPopUpWindow()
            }
        }
    }


    private fun api() {
        baseViewModel.get_resources(this, true,cardId)
        baseViewModel.getCommonResponse().observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {

        when (it!!.status) {
            Status.SUCCESS -> {
                if (it.data is ResourcesResponse) {
                    listResources.clear()
                    listResources.addAll(it.data.body)
                //    listResources = it.data.body as ArrayList<ResourcesResponse.Body>


                    if (listResources.isEmpty()) {
                        tvNoData.visibility = View.VISIBLE
                        rvResources.visibility = View.GONE
                    } else {
                        tvNoData.visibility = View.GONE
                        rvResources.visibility = View.VISIBLE
                        rvResources.adapter = ResourcesAdapter(this, listResources!!)
                    }
                } else if (it.data is CategoriesResponse) {

                    list.clear()
                    list.addAll(it.data.body)

                }
            }
            Status.ERROR -> {
                if (it.error is ResourcesResponse) {

                }
                if (it.error is CategoriesResponse) {

//                    Toast.makeText(, "", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun onFilter(id: String) {
        popupWindow?.dismiss()
        cardId = id
        api()

    }
}