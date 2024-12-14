package com.example.tanyagemini.data

import android.graphics.Bitmap
import com.example.tanyagemini.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ChatData {

    private fun handleResponse(response: String?, exception: Exception? = null): Chats {
        return if (exception == null) {
            Chats(prompt = response ?: "error", bitmap = null, isFromUser = false)
        } else {
            Chats(prompt = exception.message ?: "error", bitmap = null, isFromUser = false)
        }
    }

    suspend fun getResponse(prompt: String): Chats {
        val generativeModel = GenerativeModel(modelName = "gemini-1.5-flash", apiKey = BuildConfig.apiKey)
        return try {
            val response = withContext(Dispatchers.IO) { generativeModel.generateContent(prompt) }
            handleResponse(response.text)
        } catch (e: Exception) {
            handleResponse(null, e)
        }
    }

    suspend fun getResponseWithImage(prompt: String, bitmap: Bitmap): Chats {
        val generativeModel = GenerativeModel(modelName = "gemini-1.5-flash", apiKey = BuildConfig.apiKey)
        return try {
            val inputContent = content { image(bitmap); text(prompt) }
            val response = withContext(Dispatchers.IO) { generativeModel.generateContent(inputContent) }
            handleResponse(response.text)
        } catch (e: Exception) {
            handleResponse(null, e)
        }
    }
}