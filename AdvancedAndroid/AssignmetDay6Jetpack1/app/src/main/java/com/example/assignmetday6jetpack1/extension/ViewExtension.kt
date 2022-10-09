package com.example.assignmetday6jetpack1.extension

import android.os.Build
import android.text.Html
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter

//Custom Setter
@BindingAdapter("padding_custom")
fun bindPaddingLeft(edt: EditText, padding: Int) {
    //tv.setPadding()
    edt.setPadding(padding, padding, padding, padding / 2)

}

// Renamed Setters
@RequiresApi(Build.VERSION_CODES.N)
@BindingAdapter("text_html")
fun setText(tv: TextView, data: String) {
    tv.text =
        Html.fromHtml(data, Html.FROM_HTML_MODE_COMPACT)
}

class ViewExtension {
}