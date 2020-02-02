package com.god.taeiim.koreacoronavirus.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

@BindingAdapter("textInt")
fun TextView.textInt(value: Int?) {
    val myFormatter = DecimalFormat("###,###")
    val formattedStringNum = myFormatter.format(value ?: 0)
    this.text = formattedStringNum
}