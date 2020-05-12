package com.paperapp.ui.common

import android.content.Intent

interface ActivityResultHandlerComponent {
    fun onActivityResult(
        handled: Boolean,
        requestCode: Int,
        resultCode: Int,
        intent: Intent?
    ): Boolean
}