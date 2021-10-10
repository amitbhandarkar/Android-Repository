package com.amit.nasaa.pod.ui.main.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.amit.nasaa.pod.R
import com.amit.nasaa.pod.ui.main.database.APODEntity
import com.amit.nasaa.pod.ui.main.model.Result
import com.amit.nasaa.pod.ui.main.view.glide.loadImage
import com.amit.nasaa.pod.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = "MainFragment"
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.main_fragment, container, false)

        Log.i(TAG, "onCreateView")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getApod()?.observe(requireActivity(), object : Observer<Result> {

            override fun onChanged(result: Result?) {

                when (result) {
                    is Result.Success -> {
                        updateView(result.data, root)
                        Log.i(TAG, "Result.Success")
                    }
                    is Result.Failure -> {
                        showError(result.data, root)
                        Log.i(TAG, "Result.Failure")
                    }
                }
            }
        })

        return root
    }

    private fun updateView(data: APODEntity, root: View) {

        Log.i(TAG, "updateView")
        root.findViewById<LinearLayout>(R.id.error).visibility = View.GONE
        root.findViewById<LinearLayout>(R.id.main).visibility = View.VISIBLE

        val imageView = root.findViewById<ImageView>(R.id.image)
        //loadImage(requireActivity(), data.url, imageView)
        loadImage(
            requireActivity(),
            "https://apod.nasa.gov/apod/image/2110/peg51_desmars_ex1024.jpg",
            imageView
        )

        root.findViewById<TextView>(R.id.title).text = data.title
        root.findViewById<TextView>(R.id.desc).text = data.explanation
    }

    private fun showError(data: APODEntity?, root: View) {

        Log.i(TAG, "showError")
        if (data != null) {
            updateView(data, root)
        } else {
            root.findViewById<LinearLayout>(R.id.main).visibility = View.GONE
        }

        root.findViewById<LinearLayout>(R.id.error).visibility = View.VISIBLE
    }
}