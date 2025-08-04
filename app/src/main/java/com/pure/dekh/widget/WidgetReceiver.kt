package com.pure.dekh.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Action constants for widget updates
private const val REFRESH_ACTION = "com.pure.dekh.refresh"
private const val UPDATE_ACTION = "com.pure.dekh.update"

/**
 * The receiver for the countdown widget.
 * It manages the widget's lifecycle events and handles updates.
 */
class WidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = Widget()

    // This method is called when the first instance of a widget is created.
    // It's the ideal place to schedule periodic updates.
    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d("WidgetReceiver", "onEnabled: Scheduling AlarmManager for periodic updates.")
        scheduleUpdates(context)
    }

    // This method is called when the last instance of a widget is deleted.
    // We must cancel the AlarmManager to prevent battery drain.
    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d("WidgetReceiver", "onDisabled: Cancelling AlarmManager updates.")
        cancelUpdates(context)
    }

    // This method is called when a broadcast is received.
    // It handles both the manual refresh and the periodic updates from AlarmManager.
    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("WidgetReceiver", "onReceive: ${intent.action}")

        // Check for the "refresh" action from the widget button
        if (intent.action == REFRESH_ACTION) {
            Log.d("WidgetReceiver", "Refresh action received. Updating all widgets.")
            CoroutineScope(Dispatchers.IO).launch {
                glanceAppWidget.updateAll(context)
            }
        }

        // Check for the "update" action from the AlarmManager
        if (intent.action == UPDATE_ACTION) {
            Log.d("WidgetReceiver", "Periodic update action received. Updating all widgets.")
            CoroutineScope(Dispatchers.IO).launch {
                glanceAppWidget.updateAll(context)
            }
            // Reschedule the next alarm to create the continuous loop
            scheduleUpdates(context)
        }
    }

    /**
     * Schedules a single alarm using AlarmManager to update the widget in one minute.
     */
    private fun scheduleUpdates(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WidgetReceiver::class.java).apply {
            action = UPDATE_ACTION
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Set a non-repeating alarm to fire in 1 minute
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC,
            System.currentTimeMillis() + 60000,
            pendingIntent
        )
    }

    /**
     * Cancels the pending alarm.
     */
    private fun cancelUpdates(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, WidgetReceiver::class.java).apply {
            action = UPDATE_ACTION
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }


    suspend fun updateAllWidgets(context: Context) {
        glanceAppWidget.updateAll(context)
    }
}
