package com.example.tanyagemini

import coil3.Bitmap

sealed class ChatUiEvent {
    data class UpdatePrompt(val newPrompt: String) : ChatUiEvent()
    data class SendPrompt(
        val prompt: String,
        val bitmap: Bitmap?
    ) : ChatUiEvent()

    // events for new chat and clearing chat
    object CreateNewChat : ChatUiEvent()
    object ClearCurrentChat : ChatUiEvent()
}