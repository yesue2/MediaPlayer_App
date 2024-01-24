package com.example.mediaplayerapp

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi

class MusicPlayerService : Service() {
    var mMediaPlayer: MediaPlayer? = null  // 미디어 플레이어 객체를 null로 초기화
    var mBinder: MusicPlayerBinder = MusicPlayerBinder()

    inner class MusicPlayerBinder : Binder() {
        fun getService(): MusicPlayerService {
            return this@MusicPlayerService
        }
    }
    // 서비스를 startService()로 생성하든, bindService()로 생성하든 onCreate()는 처음에 한 번만 실행
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        // 앱이 실행되고 있다는 알림 생성
        startForegroundService()  // 포그라운드 서비스 시작
    }

    // 바인드
    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    // 시작된 상태 (백그라운드)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    // 서비스 종료
    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            stopForeground(true)
        }
    }

    @SuppressLint("ForegroundServiceType")
    @RequiresApi(Build.VERSION_CODES.O)
    fun startForegroundService() {
        // 안드로이드O 부터는 반드시 알림 채널을 사용하여 사용자에게 알림을 보여줘야 함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            // 알림 채널: 알림을 용도나 중요도에 따라 구분하여 사용자가 앱의 알림을 관리할 수 있게 함
            val mChannel = NotificationChannel( // 알림 채널을 생성
                "CHANNEL_ID",
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(mChannel)
        }

        // 알림 생성
        val notification: Notification = Notification.Builder(this, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_play) // 알림 아이콘
            .setContentTitle("뮤직 플레이어 앱")   // 알림의 제목을 설정
            .setContentText("앱이 실행 중입니다.")  // 알림의 내용을 설정
            .build()

        startForeground(1, notification)  // startForeground(알림 id, 알림 내용)
    }

    fun isPlaying() : Boolean{
        return (mMediaPlayer != null && mMediaPlayer?.isPlaying ?: false)
    }

    fun play() {
        if (mMediaPlayer == null) {  // 음악이 재생 중이지 않을 때
            // 음악 파일의 리소스를 가져와 미디어 플레이어 객체를 할당
            mMediaPlayer = MediaPlayer.create(this, R.raw.sample)

            mMediaPlayer?.setVolume(1.0f, 1.0f) // 볼륨 지정
            mMediaPlayer?.isLooping = true  // 반복재생 여부
            mMediaPlayer?.start()  // 음악 재생
        } else {  // 음악이 재생 중일 때
            if (mMediaPlayer!!.isPlaying) {
                Toast.makeText(this, "이미 음악이 실행 중입니다.", Toast.LENGTH_SHORT).show()
            } else {
                mMediaPlayer?.start()  // 음악 재생
            }
        }
    }

    fun pause() {
        mMediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun stop() {
        mMediaPlayer?.let {
            if(it.isPlaying) {
                it.stop()
                it.release()
                mMediaPlayer = null
            }
        }
    }

}