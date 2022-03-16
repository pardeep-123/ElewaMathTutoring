package com.elewamathtutoring.Util.Location

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.elewamathtutoring.R
import com.elewamathtutoring.Util.helper.Helper
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place

import kotlinx.android.synthetic.main.activity_my_new_map.*

class MyNewMapActivity : AppCompatActivity(), PlaceAutoCompleteAdapter.ClickListener {
    private var mAutoCompleteAdapter: PlaceAutoCompleteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_new_map)
        Places.initialize(this, resources.getString(R.string.google_maps_key))
        place_search.requestFocus()

        iv_backArrow.setOnClickListener { finish()
        }
        place_search.addTextChangedListener(filterTextWatcher);
        mAutoCompleteAdapter = PlaceAutoCompleteAdapter(this)
        places_recycler_view.setLayoutManager(LinearLayoutManager(this))
        mAutoCompleteAdapter!!.setClickListener(this)
        places_recycler_view.setAdapter(mAutoCompleteAdapter)
        mAutoCompleteAdapter!!.notifyDataSetChanged()
    }

    private val filterTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (Helper.isNetworkConnected(this@MyNewMapActivity)) {

                if (s.toString() != "")
                {
                    mAutoCompleteAdapter!!.getFilter().filter(s.toString())
                    if (places_recycler_view.getVisibility() == View.GONE) {
                        places_recycler_view.setVisibility(View.VISIBLE)
                    }
                }
                else
                {
                    if (places_recycler_view.getVisibility() == View.VISIBLE) {
                        places_recycler_view.setVisibility(View.GONE)
                    }
                }
            }
        }


        override fun beforeTextChanged(
            s: CharSequence, start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }
    }

    override fun click(place: Place?) {
        val returnIntent = Intent()
        returnIntent.putExtra("address", place!!.address)
        returnIntent.putExtra("latitude", place!!.latLng!!.latitude.toString())
        returnIntent.putExtra("longitude", place!!.latLng!!.longitude.toString())
        returnIntent.putExtra("name", place!!.name)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}