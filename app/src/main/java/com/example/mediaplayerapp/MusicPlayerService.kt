package com.example.mediaplayerapp

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MusicPlayerService : Service() {
    // 서비스를 startService()로 생성하든, bindService()로 생성하든 onCreate()는 처음에 한 번만 실행
    override fun onCreate() {
        super.onCreate()

        // 앱이 실행되고 있다는 알림 생성
        startForegroundService()  // 포그라운드 서비스 시작
    }

    // 바인드
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    // 시작된 상태 (백그라운드)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    // 서비스 종료
    override fun onDestroy() {
        super.onDestroy()
    }

    fun startForegroundService() {

    }

    fun isPlaying() {

    }

    fun play() {

    }

    fun pause() {

    }

    fun stop() {

    }

}