package com.example.mychessclock

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Spinner
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.mychessclock.databinding.PresetsPopupBinding
import com.example.mychessclock.databinding.TimePickerPopupBinding

class MySettingsFragment: PreferenceFragmentCompat() {
    private lateinit var timePickerPopupBinding: TimePickerPopupBinding
    private lateinit var presetsPopupBinding: PresetsPopupBinding
    private lateinit var timePickerPopupView: View
    private lateinit var presetsPopupView: View

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        timePickerPopupBinding = TimePickerPopupBinding.inflate(layoutInflater)
        presetsPopupBinding = PresetsPopupBinding.inflate(layoutInflater)
        timePickerPopupView = timePickerPopupBinding.root
        presetsPopupView = presetsPopupBinding.root

        setPreferencesFromResource(R.xml.preferences, rootKey)

    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            "starting_time" -> {
                timePickerPopupBinding.hourPicker.minValue = 0
                timePickerPopupBinding.hourPicker.maxValue = 9
                timePickerPopupBinding.minutePicker.minValue = 0
                timePickerPopupBinding.minutePicker.maxValue = 59
                timePickerPopupBinding.secondPicker.minValue = 0
                timePickerPopupBinding.secondPicker.maxValue = 59

                val popupWindow = PopupWindow(timePickerPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                popupWindow.isFocusable = true
                popupWindow.elevation = 10.0f
                popupWindow.showAtLocation(timePickerPopupView, Gravity.CENTER, 0, 0)

                timePickerPopupBinding.startingTimeOkBtn.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("HOURS", timePickerPopupBinding.hourPicker.value)
                    sharedPreferences?.putInt("MINUTES", timePickerPopupBinding.minutePicker.value)
                    sharedPreferences?.putInt("SECONDS", timePickerPopupBinding.secondPicker.value)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()

                }

            }
            "increment" -> {
                timePickerPopupBinding.hourPicker.visibility = Spinner.GONE
                timePickerPopupBinding.hoursTextview.visibility = TextView.GONE
                timePickerPopupBinding.minutePicker.minValue = 0
                timePickerPopupBinding.minutePicker.maxValue = 59
                timePickerPopupBinding.secondPicker.minValue = 0
                timePickerPopupBinding.secondPicker.maxValue = 59

                val popupWindow = PopupWindow(timePickerPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                popupWindow.isFocusable = true
                popupWindow.elevation = 10.0f
                popupWindow.showAtLocation(timePickerPopupView, Gravity.CENTER, 0, 0)

                timePickerPopupBinding.startingTimeOkBtn.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES_INC", timePickerPopupBinding.minutePicker.value)
                    sharedPreferences?.putInt("SECONDS_INC", timePickerPopupBinding.secondPicker.value)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()

                }
            }
            "presets" -> {

                val popupWindow = PopupWindow(presetsPopupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

                popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
                popupWindow.isFocusable = true
                popupWindow.elevation = 10.0f
                popupWindow.showAtLocation(presetsPopupView, Gravity.CENTER, 0, 0)

                presetsPopupBinding.onePlusZero.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 1)
                    sharedPreferences?.putInt("SECONDS_INC", 0)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.onePlusOne.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 1)
                    sharedPreferences?.putInt("SECONDS_INC", 1)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.threePlusZero.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 3)
                    sharedPreferences?.putInt("SECONDS_INC", 0)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.threePlusTwo.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 3)
                    sharedPreferences?.putInt("SECONDS_INC", 2)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.fivePlusZero.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 5)
                    sharedPreferences?.putInt("SECONDS_INC", 0)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.fivePlusThree.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 5)
                    sharedPreferences?.putInt("SECONDS_INC", 3)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.fivevplusFive.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 5)
                    sharedPreferences?.putInt("SECONDS_INC", 5)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.tenPlusZero.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 10)
                    sharedPreferences?.putInt("SECONDS_INC", 0)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.fifteenPlusZero.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 15)
                    sharedPreferences?.putInt("SECONDS_INC", 0)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

                presetsPopupBinding.fifteenPlusTen.setOnClickListener {
                    val sharedPreferences = activity?.getSharedPreferences("PREFPREF", Context.MODE_PRIVATE)?.edit()
                    sharedPreferences?.putInt("MINUTES", 15)
                    sharedPreferences?.putInt("SECONDS_INC", 10)
                    sharedPreferences?.apply()
                    popupWindow.dismiss()
                }

            }
        }
        return super.onPreferenceTreeClick(preference)
    }
}