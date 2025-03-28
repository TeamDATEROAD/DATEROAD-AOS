# 쉽고 빠른 데이트로 가는 지름길, 데이트로드 👩🏻‍❤️‍👨🏻

커플들이 직접 공유하는 ‘장소 중심’이 아닌 ‘코스 중심’ 데이트 코스 공유 서비스 데이트로드입니다.

<br>

![DateRoad](https://github.com/user-attachments/assets/3e0c4678-25de-4e48-8939-581733a9ec5b)

---

## 💟 *****Contributors*****

| <img alt="image" src="https://github.com/woowacourse-study/2024-GaegSa5-study/assets/85734140/80896a2a-d417-498b-81cc-9e2c239f37ee" width="150" height="150">  | <img alt="image" src="https://github.com/chaehyuns/GaegSa5/assets/80222352/aea5197f-8651-45a4-8bec-1e28baeae41c" width="150" height="150"> |  
|---------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 김진우([@jinuemong](https://github.com/jinuemong))                                                                                                                | 박예찬([@dpcks0509](https://github.com/dpcks0509))  | 

## 👩🏻‍💻 ***𝙏𝙚𝙘𝙝 𝙎𝙩𝙖𝙘𝙠***

| Title                   | Content                                                                     |
|-------------------------|-----------------------------------------------------------------------------|
| Architecture            | Clean Architecture, MVI, Single Activity Architecture                       |
| Design Pattern          | Repository Pattern, Delegation Pattern                                      |
| UI                      | Jetpack Compose                                                             |
| Jetpack Components      | encryptedsharedpreferences, Lifecycle, ViewModel, Navigation                |
| Dependency Injection    | Hilt                                                                        |
| Network                 | Retrofit, OkHttp, MultiPart                                                 |
| Asynchronous Processing | Coroutine                                                                   |
| Third Party Library     | Coil, Firebase, Timber, kotlinSerialization, Amplitude, Lottie, Kakao |
| Strategy                | Git Flow                                                                    |
| CI                      | GitHub Action(KtLint, Complie Check)                                        |
| CD                      | GitHub Action                                                               |
| Other Tool              | Slack, Notion, Figma, Postman, Discord                                      |

<br>

## 📁 *****Foldering*****
```
📂 app
┣ 📂 manifests
┃ ┣ 📜 AndroidManifest.xml
┣ 📂 kotlin+java
┃ ┣ 📂 org.sopt.dateroad
┃ ┃ ┣ 📂 data
┃ ┃ ┃ ┣ 📂 datalocal
┃ ┃ ┃ ┃ ┣ 📂 datasource
┃ ┃ ┃ ┃ ┣ 📂 datasourceimpl
┃ ┃ ┃ ┣ 📂 dataremote
┃ ┃ ┃ ┃ ┣ 📂 datasource
┃ ┃ ┃ ┃ ┣ 📂 datasourceimpl
┃ ┃ ┃ ┃ ┣ 📂 interceptor
┃ ┃ ┃ ┃ ┣ 📂 model
┃ ┃ ┃ ┃ ┃ ┣ 📂 request
┃ ┃ ┃ ┃ ┃ ┣ 📂 response
┃ ┃ ┃ ┃ ┣ 📂 service
┃ ┃ ┃ ┃ ┣ 📂 util
┃ ┃ ┃ ┣ 📂 mapper
┃ ┃ ┃ ┃ ┣ 📂 todata
┃ ┃ ┃ ┃ ┣ 📂 todomain
┃ ┃ ┃ ┃ ┣ 📂 toEntity
┃ ┃ ┃ ┣ 📂 repositoryimpl
┃ ┃ ┣ 📂 di
┃ ┃ ┣ 📂 domain
┃ ┃ ┃ ┣ 📂 model
┃ ┃ ┃ ┣ 📂 repository
┃ ┃ ┃ ┣ 📂 type
┃ ┃ ┃ ┣ 📂 usecase
┃ ┃ ┃ ┣ 📂 util
┃ ┃ ┣ 📂 presentation
┃ ┃ ┃ ┣ 📂 model
┃ ┃ ┃ ┣ 📂 type
┃ ┃ ┃ ┣ 📂 ui
┃ ┃ ┃ ┃ ┣ 📂 advertisement
┃ ┃ ┃ ┃ ┣ 📂 component
┃ ┃ ┃ ┃ ┃ 📂 coursedetail
┃ ┃ ┃ ┃ ┣ 📂 enroll
┃ ┃ ┃ ┃ ┣ 📂 home
┃ ┃ ┃ ┃ ┣ 📂 look
┃ ┃ ┃ ┃ ┣ 📂 mycourse
┃ ┃ ┃ ┃ ┣ 📂 mypage
┃ ┃ ┃ ┃ ┣ 📂 navigator
┃ ┃ ┃ ┃ ┣ 📂 onboarding
┃ ┃ ┃ ┃ ┣ 📂 past
┃ ┃ ┃ ┃ ┣ 📂 pointguide
┃ ┃ ┃ ┃ ┣ 📂 pointhistory
┃ ┃ ┃ ┃ ┣ 📂 profile
┃ ┃ ┃ ┃ ┣ 📂 read
┃ ┃ ┃ ┃ ┣ 📂 signin
┃ ┃ ┃ ┃ ┣ 📂 splash
┃ ┃ ┃ ┃ ┣ 📂 timeline
┃ ┃ ┃ ┃ ┣ 📂 timelinedetail
┃ ┃ ┃ ┣ 📂 util
┃ ┣ 📂 ui.theme
┃ ┣ 📄 DateRoadApp.kt

```

<br>

## ➿ *****Convention*****

[ABC의 깃 컨벤션과 브랜치 전략이 궁금하다면? click ✔️](https://hooooooni.notion.site/Git-Convention-Branch-Strategy-fdcac833649d41beaea4fc5c4f7250a8?pvs=4)
<br>

[ABC의 코드 컨벤션이 궁금하다면? click ✔️](https://hooooooni.notion.site/Android-Coding-Convention-019d81b86cdb44cf8ab3ffa55c10c64d?pvs=4)
<br>

[ABC의 ISSUE, PR 컨벤션이 궁금하다면? click ✔️](https://hooooooni.notion.site/ISSUE-PR-Convention-c5718ebddba64678a001339cd5e148b2?pvs=4)
<br>

[ABC의 칸반보드가 궁금하다면? click ✔️](https://hooooooni.notion.site/ROLE-e46dd81ac93e47d999c2bb4147069ce6?pvs=4)
<br>

[ABC의 Github Project가 궁금하다면? click ✔️](https://github.com/orgs/TeamDATEROAD/projects/1)
<br>

---

## 💡 *****About Project*****

### 🩷 프로젝트 설명
<aside>
장소 중심이 아닌 코스 중심의 데이트 코스 공유 서비스 데이트로드입니다.

데이트로드에서는 다른 커플들의 실제 데이트 코스 후기를 포인트를 통해 열람할 수 있습니다.

코스 둘러보기를 통해 마음에 드는 코스를 클릭하고 미리보기를 통해 사전정보를 획득할 수 있습니다.

포인트가 없다고 걱정하지 마세요. 최초 3회는 무료로 데이트 코스를 열람할 수 있습니다. 해당 코스대로 데이트를 떠나고 싶다면 내 일정에 추가하기 버튼을 눌러 내 데이트 일정으로 등록할 수도 있습니다.
</aside>

### 📝 문제상황 정의
![6](https://github.com/user-attachments/assets/c856fdaf-86f2-4f6d-bc20-3109eb072bdf)

- 기존 앱은 코스가 아닌 장소 중심, 이로 인해 데이트 코스를 찾기 위해 여러 앱을 쓰며 피로감을 느낌
- 광고가 아닌 직접 방문한 사람의 후기를 기반으로 데이트 코스를 짜고 싶어 하는 니즈 존재

### 🎯 핵심 타겟
- 센스 있게 데이트 코스를 짜고 싶은 여자/남자친구
- 색다른 데이트 코스를 찾기 위해 인스타그램 등을 탐색하는 커플
- 네이버 블로그, 인스타그램을 통해 여러 번 데이트 장소의 후기를 얻는 커플

### 📍 주요 기능
#### 1️⃣ 코스 등록하기 및 열람

|![Instagram_post_-_4](https://github.com/user-attachments/assets/6232fd24-f906-49a8-9ea0-16d296545931)|![Instagram_post_-_5](https://github.com/user-attachments/assets/0d648884-0d06-4043-9d31-03df52d434b9)|
|---|---|
- 내가 한 데이트 코스를 등록하고 포인트를 획득할 수 있습니다.
- 다른 커플들이 한 데이트를 포인트를 사용해 열람할 수 있습니다.
- 코스 상세 페이지에서 ‘내 일정에 추가하기’ 버튼을 눌러 내 데이트 일정으로 불러올 수 있습니다.

#### 2️⃣ 일정 등록하기 및 열람

|![Instagram_post_-_10](https://github.com/user-attachments/assets/8e3a627f-e567-4ad5-ac11-63e560a09b67)|![Instagram_post_-_6](https://github.com/user-attachments/assets/e9accf21-1c6c-4fb5-9ad8-f834ab5750fa)|
|---|---|

- 내 데이트 일정을 등록할 수 있습니다.
- 내 데이트 일정을 확인할 수 있습니다.
- 지난 데이트는 코스 등록하기로 연동해 등록하고 포인트를 받을 수 있습니다.
- 카카오톡 공유하기를 통해 데이트 일정을 연인에게 공유할 수 있습니다.

## 💰 비즈니스 모델

> **포인트를 통한 수익 모델**
>
- 유저들은 데이트 코스를 등록하고 포인트를 획득해 제휴 매장에 할인받아 방문합니다.
- 구글 애드센스를 연결하여 광고를 시청하면 포인트를 획득할 수 있습니다. 데이트로드는 광고 수익을 얻을 수 있습니다.

> **입점처를 통한 수익 모델**
>
- 입점 가게는 매장을 홍보하고 유저 방문으로 매출을 증가시키며, 광고주는 유저에게 광고를 노출하여 제품이나 서비스를 홍보합니다. 데이트로드는 이를 통해 수익을 창출하고, 모든 참여자가 상호 이익을 얻는 생태계를 구축합니다.

<br/><br/>
