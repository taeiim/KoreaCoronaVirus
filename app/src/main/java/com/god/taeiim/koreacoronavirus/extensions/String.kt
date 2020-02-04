package com.god.taeiim.myapplication.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Html
import androidx.core.content.ContextCompat


fun String?.fromHtml() =
    this?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(this)
        }
    } ?: ""

fun String?.linkIntent(context: Context) =
    ContextCompat.startActivity(
        context,
        Intent(Intent.ACTION_VIEW, Uri.parse(this)),
        null
    )
