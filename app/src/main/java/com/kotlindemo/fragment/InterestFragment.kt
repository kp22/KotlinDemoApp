package com.kotlindemo.fragment

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.kotlindemo.common.showToast
import com.kotlindemo.main.R
import kotlinx.android.synthetic.main.fragment_one.*
import android.widget.Toast.makeText as makeText1

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabOneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabOneFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }


    private fun initFragment() {

        btnCalculate.setOnClickListener {
            val amount = etAmount.text
            val interet = etInterest.text

            if (TextUtils.isEmpty(amount)) {
                showToast(activity as Context,"Enter amount")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(interet)) {
                showToast(activity as Context,"Enter interet")
                return@setOnClickListener
            }
            val res = amount.toString().toInt() * interet.toString().toFloat() / 100f;
            var result = res.toString()
            Log.e("TAG", "initFragment: result = $result")
            if (result.endsWith(".0"))
                result = result.replace(".0", "")

            tvResut.text = "Result : ${result}"
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabOneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TabOneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}