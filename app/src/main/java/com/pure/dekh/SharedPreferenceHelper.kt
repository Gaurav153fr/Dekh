// src/main/java/com/pure/dekh/SharedPreferenceHelper.kt

package com.pure.dekh

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.pure.dekh.widget.WidgetReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SharedPreferenceHelper {

    private const val PREFS_NAME = "com.pure.dekh.WidgetPreferences"
    private const val KEY_COUNTDOWN_MILLIS = "countdown_millis"
private const val KEY_IMAGE_ID = "image_id"
    private const val  KEY_TEXT_ID="text_id"
    private const val KET_RANDOM_ID="random_id"
    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    private fun sendUpdateBroadcast(context: Context) {
        val intent = Intent(context, WidgetReceiver::class.java).apply {
            action = "com.pure.dekh.update"


        }
        context.sendBroadcast(intent)
    }
    fun saveImageId(context: Context, imageId: Int) {
        val editor = getSharedPreferences(context).edit()
        editor.putInt(KEY_IMAGE_ID, imageId)
        editor.apply()
      sendUpdateBroadcast(context)

    }
    fun getImageId(context: Context): Int {
        return getSharedPreferences(context).getInt(KEY_IMAGE_ID, 0)

    }
    fun saveTextId(context: Context, textId: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_TEXT_ID, textId)
        editor.apply()
        sendUpdateBroadcast(context)

    }
    fun getTextId(context: Context): String {

        return getSharedPreferences(context).getString(KEY_TEXT_ID,"chal na")?:"chal be"
    }
    fun saveCountdownMillis(context: Context, millis: Long) {
        val editor = getSharedPreferences(context).edit()
        editor.putLong(KEY_COUNTDOWN_MILLIS, millis)
        editor.apply()
        sendUpdateBroadcast(context)


    }

    fun getCountdownMillis(context: Context): Long {
        // Return a default value (e.g., current time + 30 days) if not set
        val thirtyDaysInMillis = 30L * 24 * 60 * 60 * 1000
        val defaultDate = System.currentTimeMillis() + thirtyDaysInMillis
        return getSharedPreferences(context).getLong(KEY_COUNTDOWN_MILLIS, defaultDate)
    }

    fun saveRandom(context: Context, random: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KET_RANDOM_ID, random)
        editor.apply()
        sendUpdateBroadcast(context)

    }
    fun getRandom(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KET_RANDOM_ID, false)}
}