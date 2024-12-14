package com.example.tanyagemini

import android.graphics.Bitmap
import com.example.tanyagemini.data.Chats

data class eChatState (
    val chatList: MutableList<Chats> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)