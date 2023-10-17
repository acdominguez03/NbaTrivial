package com.example.nbatrivial.utils

import android.content.Context
import android.content.SharedPreferences

object PreferenceService {

    private const val NAME = "Preferences"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    // list of app specific preferences
    private val SCORE = "SCORE"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var score: Int
        get() = preferences.getInt(SCORE, 0)
        set(value) = preferences.edit().putInt(SCORE, value).apply()

    private fun remove(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun clearData() {
        remove(SCORE)
    }
}
