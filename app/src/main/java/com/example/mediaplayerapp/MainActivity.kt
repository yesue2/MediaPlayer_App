package com.example.mediaplayerapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var btn_start : Button
    lateinit var btn_pause : Button
    lateinit var btn_stop : Button
    var mService: MusicPlayerService? = null  // 서비스 변수

    // 서비스 구성요소 연결 상태 모니터링
    val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // MusicPlayerBinder로 형변환 해줌
            mService = (service as MusicPlayerService.MusicPlayerBinder).getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // 서비스가 끊기면 mService를 null로 만듦
            mService = null
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start = findViewById(R.id.btn_start)
        btn_pause = findViewById(R.id.btn_pause)
        btn_stop = findViewById(R.id.btn_stop)

        btn_start.setOnClickListener(this)
        btn_pause.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_start -> {
                play()
            }
            R.id.btn_pause -> {
                pause()
            }
            R.id.btn_stop -> {
                stop()
            }
        }
    }

    override fun onResume() {
        // 액티비티가 사용자에게 보일 때마다 실행되는 콜백 함수
        super.onResume()
        // 서비스 실행 처리
        if (mService == null) {  // 아직 서비스가 액티비티와 연결되지 않았을 때
            // 안드로이드O 이상이면 startForegroundService 사용
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(Intent(this, MusicPlayerService::class.java))
            } else {
                startService(Intent(applicationContext, MusicPlayerService::class.java))
            }
            // 액티비티를 서비스와 바인드시킴
            val intent = Intent(this, MusicPlayerService::class.java)
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onPause() {
        super.onPause()
        // 사용자가 액티비티를 떠났을 때 처리
        if (mService != null) {            if (!mService!!.isPlaying()) {  // mService가 재생되고 있지 않다면
                mService!!.stopSelf()  // 서비스 중단
            }
            unbindService(mServiceConnection) // 서비스로부터 연결 끊기
            mService = null
        }
    }
    private fun play() {
        mService?.play()
    }
    private fun pause() {
        mService?.pause()
    }
    private fun stop() {
        mService?.stop()
    }
}