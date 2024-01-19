package app.maigret.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import app.maigret.R
import app.maigret.receivers.sms.SmsReceiver

class MainService : Service() {
    private val smsReceiver = SmsReceiver()
    private val channelID = "some_channel_id"
    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create a notification channel for Android Oreo and above
        createNotificationChannel()

        // Create a notification
        val notification = NotificationCompat.Builder(this, channelID)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running...")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        // Start the service in foreground
        startForeground(1, notification)

        // Register Sms Receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(
            smsReceiver,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
        )

        return START_STICKY // Restart service if it's killed
    }

    override fun onBind(intent: Intent?): IBinder? {
        // Not used for foreground services, return null
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelID,
                "My Foreground Service",
                NotificationManager.IMPORTANCE_DEFAULT,
            )

            notificationManager.createNotificationChannel(channel)
        }
    }
}