package com.example.tanyagemini

import coil3.Bitmap
import com.example.tanyagemini.data.Chats

data class eChatState (
    val chatList: MutableList<Chats> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null
)