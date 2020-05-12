package com.paperapp.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.webkit.MimeTypeMap
import androidx.annotation.*
import androidx.core.content.ContextCompat
import io.reactivex.functions.Consumer
import java.util.*

class ResourceProvider(private val context: Context) {

    fun getPath(uri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val result: String?
        val cursor = context.contentResolver.query(uri, proj, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    fun getStringArray(@ArrayRes resId: Int): Array<String> {
        return context.resources.getStringArray(resId)
    }

    fun getIntArray(@ArrayRes resId: Int): IntArray {
        return context.resources.getIntArray(resId)
    }

    fun getBooleanArray(@ArrayRes resId: Int): BooleanArray {
        val stringArray = getStringArray(resId)
        val boolArray = BooleanArray(stringArray.size)
        for (i in stringArray.indices) {
            boolArray[i] = java.lang.Boolean.parseBoolean(stringArray[i])
        }
        return boolArray
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.resources.getString(resId, *formatArgs)
    }

    fun getString(locale: Locale, @StringRes resId: Int, vararg formatArgs: Any): String {
        val conf = context.resources.configuration
        conf.setLocale(locale)
        return context.createConfigurationContext(conf).resources.getString(resId, *formatArgs)
    }

    fun getQuantityString(@PluralsRes resId: Int, quantity: Int, vararg formatArgs: Any): String {
        return context.resources.getQuantityString(resId, quantity, *formatArgs)
    }

    fun getDrawable(@DrawableRes resId: Int): Drawable {
        return ContextCompat.getDrawable(context, resId)!!
    }

    fun getDimensionInPx(@DimenRes resId: Int): Int {
        return context.resources.getDimensionPixelSize(resId)
    }

    fun getFileType(uri: Uri) = context.contentResolver.getType(uri)

    fun getMimeType(uri: Uri): String? {
        return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            context.contentResolver.getType(uri)
        } else {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase())
        }
    }

//    fun getCreateOrderPrivacyAndAgreementText(
//        privacyCallback: Consumer<Unit>,
//        deliveryCallback: Consumer<Unit>
//    ): SpannableString {
//        val ss = SpannableString(context.getString(R.string.privacy))
//        setClickableSpan(ss, 11, 50, privacyCallback)
//        setClickableSpan(ss, 53, 85, deliveryCallback)
//        return ss
//    }

    private fun setClickableSpan(
        string: SpannableString,
        start: Int,
        end: Int,
        callback: Consumer<Unit>
    ) {
        val privacySpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                callback.accept(Unit)
            }
        }
        string.setSpan(privacySpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

