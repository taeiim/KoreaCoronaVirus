package com.god.taeiim.koreacoronavirus.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.god.taeiim.myapplication.extensions.fromHtml
import java.text.DecimalFormat

@BindingAdapter("textInt")
fun TextView.textInt(value: Int?) {
    val myFormatter = DecimalFormat("###,###")
    val formattedStringNum = myFormatter.format(value ?: 0)
    this.text = formattedStringNum
}

@BindingAdapter("setTextHtml")
fun TextView.setTextHtml(text: String?) {
    this.text = text?.fromHtml()
}

@BindingAdapter("intentLinkOnClick")
fun View.intentLinkOnClick(link: String?) {
    setOnClickListener {
        link?.let {
            ContextCompat.startActivity(
                context,
                Intent(Intent.ACTION_VIEW, Uri.parse(it)),
                null
            )
        }
    }
}