package com.example.tanyagemini.data

import android.graphics.Bitmap

data class Chats (
    val prompt: String,
    val bitmap: Bitmap?,
    val isFromUser: Boolean
)