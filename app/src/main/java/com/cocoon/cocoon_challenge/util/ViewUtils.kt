package com.cocoon.cocoon_challenge.util

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.GONE
}

fun View.snackBar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}

fun convertDate(date: String): String {
    var agoDate = ""
    val sdf = SimpleDateFormat(date)
    sdf.timeZone = TimeZone.getTimeZone("GMT");
    try {
        val time: Long = sdf.parse(date).time;
        val now: Long = System.currentTimeMillis();
        val ago: CharSequence =
        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
        agoDate = ago.toString()
    } catch (e: ParseException) {
        e.printStackTrace();
    }
    return agoDate
}

