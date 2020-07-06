package com.example.simple_shop_aos.fcm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.simple_shop_aos.R
import com.example.simple_shop_aos.api.ServiceApi
import com.example.simple_shop_aos.common.Prefs
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.runBlocking
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.*

class MessaginService : FirebaseMessagingService(), AnkoLogger {

    // 토큰값이 업데이트 될 때 호출되는 콜백
    override fun onNewToken(fcmToken: String?) {
        Prefs.fcmToken = fcmToken
        // 로그인이 되어 있는 경우
        if (!Prefs.token.isNullOrEmpty() && fcmToken != null) {
            runBlocking {
                try {
                    val response = ServiceApi.instance.updateFcmToken(fcmToken)
                    if (!response.success) {
                        warn(response.message ?: "토큰 업데이트 실패")
                    }
                } catch (e: Exception) {
                    error(e.message ?: "토큰 업데이트 실패", e)
                }
            }
        }
    }

    // 서버로 부터 푸시 메시지를 받았을 때 호출 되는 콜백
    override fun onMessageReceived(message: RemoteMessage?) {
        // API 서버에서 data에 필드를 채워 보냄.
        // notification 필드를 이용할 경우 FCM이 자동으로 알림을 표시하나, 알림 핸들링이 불가.(그래서, data 필드 사용)
        message?.data?.let { data ->
            debug(data)

            // Oreo 이상 버전에서는 채널을 설정해야함.
            createNotificationChannelIfNeeded()

            // 알림 메시지를 빌드
            val builder = NotificationCompat
                .Builder(this, "channel.simple.shop.default")
                .setContentTitle(data["title"])
                .setContentText(data["content"])
                .setSmallIcon(R.drawable.ic_chat)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            with(NotificationManagerCompat.from(this)) {
                notify(NotificationId.generate(), builder.build())
            }
        }

    }

    private fun createNotificationChannelIfNeeded() {
        // 채널을 생성하는 코드.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel.simple.shop.default",
                "기본 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "기본 알림"
            notificationManager.createNotificationChannel(channel)
        }
    }
}