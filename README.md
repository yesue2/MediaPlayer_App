## 🎧 MusicPlayer Application
## 📌 주요 기능
### 뮤직플레이어
1. **[재생]** 버튼을 누르면 상단의 **상태 표시줄에서 플레이 표시의 알림**이 뜸
2. 앱을 떠나서 앱 자체를 종료해도 **음악이 계속 재생**, 상태 표시줄에서도 **플레이 표시 지속**
3. 재생 중 [재생] 버튼을 다시 눌렀을 시 '음악이 재생 중입니다.' 알림 메세지 출력
4. 음악 **일시정지**, **재생중지** 가능

## 💡 구현 설명
1. **시작되고 바인드된 서비스 사용**: 앱의 생명 주기와 무관하게 백그라운드에서 음악 재생이 지속되도록 구현
1. **포그라운드 서비스 사용**: 음악 재생이 지속될 때 상태 표시줄에 알림이 표시되도록 구현
2. **MediaPlayer API 사용**: 음악을 재생, 일시정지, 재생중지 할 수 있도록 해당 API 사용하여 구현

## 📱 실행 화면
![Screen_recording_20240124_151417](https://github.com/yesue2/MediaPlayer_App/assets/108323785/fbb2a2a5-c807-4a29-a46d-839f836f5f43)
