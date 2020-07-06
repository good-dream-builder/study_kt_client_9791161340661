package com.example.simple_shop_aos.fcm

import com.example.simple_shop_aos.common.Prefs
import java.util.concurrent.Semaphore

/**
 * notificationId를 순차적으로 증가시켜 반환
 */
class NotificationId {
    companion object {
        // id 중복을 방지하기 위해 id 값을 변경하는 부분을 하나의 세마포어로 감쌈.
        private val lock = Semaphore(1)

        // 앱이 초기화 되면, Prefs에 저장된 id 값을 미리 가져옴.
        private var id = Prefs.notificationId


        fun generate(): Int {
            // id 값을 변경하는 로직 진입 시 다른 스레드가 접근하지 못하도록 함.
            lock.acquire()

            // 다음 값을 반환할 때 id 값이 변경될 수 있으므로 임시 변수인 next를 사용.
            val next = id + 1
            id = next

            // Prefs에 다음 값을 저장.
            Prefs.notificationId = next

            // id 값 변경에 필요한 로직이 끝났으므로, 세마포어 락을 풀어줌.
            lock.release()

            // id는 변경 위험이 있으므로 next를 반환.
            return next
        }
    }
}