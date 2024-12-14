package com.example.tanyagemini

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.graphics.Bitmap
import com.example.tanyagemini.data.ChatData
import com.example.tanyagemini.data.Chats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatState(
    val chatList: List<Chats> = mutableListOf(),
    val prompt: String = "",
    val bitmap: Bitmap? = null,
    val currentChatId: String = ""
)

class ChatViewModel : ViewModel() {

    private val _chatState = MutableStateFlow(ChatState())
    val chatState = _chatState.asStateFlow()

    fun onEvent(event: ChatUiEvent) {
        when (event) {
            is ChatUiEvent.SendPrompt -> {
                if (event.prompt.isNotEmpty() || event.bitmap != null) {
                    addPrompt(event.prompt, event.bitmap)

                    if (event.bitmap != null) {
                        getResponseWithImage(event.prompt, event.bitmap)
                    } else {
                        getResponse(event.prompt)
                    }
                }
            }

            is ChatUiEvent.UpdatePrompt -> {
                _chatState.update {
                    it.copy(prompt = event.newPrompt)
                }
            }

            is ChatUiEvent.CreateNewChat -> {
                createNewChat()
            }

            is ChatUiEvent.ClearCurrentChat -> {
                clearCurrentChat()
            }

            is ChatUiEvent.ResetBitmap -> {
                resetBitmap()
            }
        }
    }

    // method to reset bitmap
    private fun resetBitmap() {
        _chatState.update {
            it.copy(bitmap = null)
        }
    }

    private fun createNewChat() {
        // Reset the chat state to initial conditions
        _chatState.update {
            ChatState(
                chatList = emptyList(),
                prompt = "",
                bitmap = null,
                currentChatId = generateUniqueId() // Placeholder for future database integration
            )
        }
    }

    private fun clearCurrentChat() {
        // Clear only the current chat's messages
        _chatState.update {
            it.copy(chatList = emptyList())
        }
    }

    private fun updateChatList(newChat: Chats) {
        _chatState.update { it.copy(chatList = listOf(newChat) + it.chatList) }
    }

    private fun addPrompt(prompt: String, bitmap: Bitmap?) {
        updateChatList(Chats(prompt, bitmap, true))
        _chatState.update { it.copy(prompt = "", bitmap = null) }
    }

    private fun getResponse(prompt: String) {
        viewModelScope.launch {
            val chat = ChatData.getResponse(prompt)
            updateChatList(chat)
        }
    }

    private fun getResponseWithImage(prompt: String, bitmap: Bitmap) {
        viewModelScope.launch {
            val chat = ChatData.getResponseWithImage(prompt, bitmap)
            updateChatList(chat)
        }
    }

    // Placeholder function for generating unique ID
    private fun generateUniqueId(): String {
        return System.currentTimeMillis().toString()
    }
}