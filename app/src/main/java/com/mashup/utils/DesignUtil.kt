package com.mashup.utils

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.mashup.R
import com.mashup.ui.model.Platform
import com.mashup.ui.mypage.AttendanceType

object DesignUtil {
    @JvmStatic
    @BindingAdapter("platformProfileImage")
    fun ImageView.setPlatformProfileImage(platform: Platform?) {
        setImageDrawable(
            ContextCompat.getDrawable(
                context,
                when (platform) {
                    Platform.ANDROID -> {
                        R.drawable.img_profile_android
                    }
                    Platform.DESIGN -> {
                        R.drawable.img_profile_design
                    }
                    Platform.IOS -> {
                        R.drawable.img_profile_ios
                    }
                    Platform.WEB -> {
                        R.drawable.img_profile_web
                    }
                    Platform.SPRING -> {
                        R.drawable.img_profile_spring
                    }
                    Platform.NODE -> {
                        R.drawable.img_profile_node
                    }
                    else -> {
                        R.drawable.img_profile_design
                    }
                }
            )
        )

    }

    @SuppressLint("ResourceAsColor")
    @JvmStatic
    @BindingAdapter("textPlatformColor")
    fun AppCompatTextView.setTextPlatformColor(platform: Platform?) {
        text = platform?.detailName
        when (platform) {
            Platform.ANDROID -> {
                setTextColor(ContextCompat.getColor(context, R.color.teamAndroid200))
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teamAndroid))
            }
            Platform.DESIGN -> {
                setTextColor(ContextCompat.getColor(context, R.color.teamProductDesign200))
                backgroundTintList = ColorStateList
                    .valueOf(ContextCompat.getColor(context, R.color.teamProductDesign))
            }
            Platform.IOS -> {
                setTextColor(ContextCompat.getColor(context, R.color.teamIos200))
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teamIos))
            }
            Platform.WEB -> {
                setTextColor(ContextCompat.getColor(context, R.color.teamWeb200))
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teamWeb))
            }
            Platform.SPRING -> {
                setTextColor(ContextCompat.getColor(context, R.color.teamSpring200))
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teamSpring))
            }
            Platform.NODE -> {
                setTextColor(ContextCompat.getColor(context, R.color.teamNode200))
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.teamNode))
            }
            else -> {
                setTextColor(ContextCompat.getColor(context, R.color.black))
                backgroundTintList =
                    ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
            }
        }
    }

    @JvmStatic
    @BindingAdapter("attendanceScore")
    fun AppCompatTextView.setAttendanceScore(attendanceType: AttendanceType) {
        attendanceType.score.let {
            text = if (it == 0.0) {
                setTextColor(ContextCompat.getColor(context, R.color.gray500))
                "0 점"
            } else {
                val score = if (it % 1 == 0.0) it.toInt() else it
                if (it > 0) {
                    setTextColor(ContextCompat.getColor(context, R.color.blue500))
                    "+${score}점"
                } else {
                    setTextColor(ContextCompat.getColor(context, R.color.red500))
                    "${score}점"
                }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("attendanceTitle")
    fun AppCompatTextView.setAttendanceTitle(attendanceType: AttendanceType) {
        text = attendanceType.title
    }

    @JvmStatic
    @BindingAdapter("attendanceIcon")
    fun ImageView.setAttendanceIcon(attendanceType: AttendanceType) {
        setImageDrawable(ContextCompat.getDrawable(context,attendanceType.resourceId))
    }
}