# triPlan
<div>
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">
<img src="https://img.shields.io/badge/GitHub%20Actions-569A31?style=for-the-badge&logo=GitHub%20Actions&logoColor=white">
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=aws&logoColor=white">
     <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white">
          <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=spring%20boot&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
</div>
<br>

### ❓ 여행 계획 좀 더 잘 짤 수는 없을까?
### ❗ 친구와 함께 여행 계획을 공유하고, 인스타처럼 여행 기록을 쌓아갈 수 있는 여행 플랫폼, triPlan!

📆 2022.03.04 ~ 2022.04.08

<br>

## 👋 팀원 소개

|[팀원] 손성진|[팀원] 김효신|[팀원] 김윤민|
|:-----:|:----:|:----:|
| <img src="https://avatars.githubusercontent.com/u/48196352?v=4" alt="avatar" height="150px" width="150px" /> | <img src="https://avatars.githubusercontent.com/u/96279734?v=4" alt="avatar" height="150px" width="150px" /> | <img src="https://avatars.githubusercontent.com/u/66665210?v=4" alt="avatar" height="150px" width="150px" /> |
|[ssj9398](https://github.com/ssj9398)|[Shinnybest](https://github.com/Shinnybest)|[JerryAllMighty](https://github.com/JerryAllMighty)|

<br>

## 💟 프로젝트 구성

### 프로젝트 시연영상
[보러 가기](https://www.youtube.com/watch?v=y6FG8p_Geyc)

### 백엔드 아키텍처
![triplan_backend_arc](https://user-images.githubusercontent.com/96279734/163741060-bd670a1c-a2e3-47a0-84e7-d6e42a90852f.png)


### ⭐ 문제해결방식
#### - 로그인, 왜 JWT로 관리할까? - <a href="https://github.com/24hours-not-enough/BE/wiki/%EB%A1%9C%EA%B7%B8%EC%9D%B8,-%EC%99%9C-JWT%EB%A1%9C-%EA%B4%80%EB%A6%AC%ED%95%A0%EA%B9%8C%3F">WIKI</a>
#### - Web Socket, 보완해야할 점은 무엇인가? - <a href="https://github.com/24hours-not-enough/BE/wiki/Web-Socket,-%EB%B3%B4%EC%99%84%ED%95%B4%EC%95%BC%ED%95%A0-%EC%A0%90%EC%9D%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80%3F%3F">WIKI</a>
#### - Redis 데이터 유실, 어떻게 관리할까? - <a href="https://github.com/24hours-not-enough/BE/wiki/Redis-%EB%8D%B0%EC%9D%B4%ED%84%B0-%EC%9C%A0%EC%8B%A4,-%EC%96%B4%EB%96%BB%EA%B2%8C-%EA%B4%80%EB%A6%AC%ED%95%A0%EA%B9%8C%3F%3F">WIKI</a>
#### - 캐싱, 성능개선을 위해 반드시 필요할까? - <a href="https://github.com/24hours-not-enough/BE/wiki/%EC%BA%90%EC%8B%B1,-%EC%84%B1%EB%8A%A5%EA%B0%9C%EC%84%A0%EC%9D%84-%EC%9C%84%ED%95%B4-%EB%B0%98%EB%93%9C%EC%8B%9C-%ED%95%84%EC%9A%94%ED%95%A0%EA%B9%8C%3F">WIKI</a>
#### - 쿼리성능최적화, 항상 같은 방법으로 가능한 것은 아니야! - <a href="https://github.com/24hours-not-enough/BE/wiki/%EC%BF%BC%EB%A6%AC%EC%84%B1%EB%8A%A5%EC%B5%9C%EC%A0%81%ED%99%94,-%ED%95%AD%EC%83%81-%EA%B0%99%EC%9D%80-%EB%B0%A9%EB%B2%95%EC%9C%BC%EB%A1%9C-%EA%B0%80%EB%8A%A5%ED%95%9C-%EA%B2%83%EC%9D%80-%EC%95%84%EB%8B%88%EC%95%BC!">WIKI</a>
#### - 동시성 제어 - <a href="">WIKI</a>
<details>
    <summary>
        <b>동시성 제어 : 계획을 여러명이 동시에 수정한다면, 먼저 수정하고 있던 사람의 데이터가 유실되는 경우가 발생 </b>
    </summary>
    <br>해결 : 누군가가 수정하고 있는 계획이라면 잠금 처리하여, 다른 사람이 수정하지 못 하게 조치.
</details>

### 💡 API 설계

#### User
기능  |      Method     | URL |  request   |        response       |
| :-: | :----------: | :----: | :-------------: | :--------------: |
|  카카오로그인  |  GET |  /api/kakaologin | authorization code |  "카카오 로그인 성공"     |
|  구글로그인  |  GET |  /api/googlelogin |  authorization code  |  "구글 로그인 성공"     |
|  회원가입추가정보  |  POST |  /api/login/userinfo |  닉네임, 이미지파일 |  "정식 회원가입 완료되었습니다."     |
|  닉네임중복확인  |  POST |  /api/username | 닉네임  |  "사용가능한 닉네임입니다."     |
|  사용자초대  |  GET |  /api/user/{nickname} |  닉네임 |  "해당 사용자를 초대할 수 있습니다."     |
|  로그아웃  |  GET |  /api/logout |  없음 |  "로그아웃 성공입니다."     |
|  회원탈퇴  |  POST |  /api/withdrawal |  없음 |  "회원 탈퇴되었습니다."     |
|  유저정보조회  | GET |  /api/user |  없음 |  "유저 정보입니다."     |
|  토큰 재발급  |  POST |  /api/token | accessToken, refreshToken  |  newAccessToken, newRefreshToken    |
|  마이페이지수정  |  PUT |  /api/mypage | 닉네임, 이미지파일 |  "마이페이지 수정완료입니다."   |
|  알림내용조회   |  GET |  /api/notification  | 없음 | 해당 유저의 전체 알림 정보 |
|  알림생성  | POST |  /app/notification/{receiveuserId}  | 알림 내용 관련 정보 | 없음 |
|  알림내용전달  |  GET |  /queue/{receiveuserId} | 없음 |  알림 내용 관련 정보   |


#### Plan

기능  |      Method     | URL |  request   |        response       |
| :-: | :----------: | :----: | :-------------: | :--------------: |
| 계획 전체 조회 | GET | /api/plan/planDetails | 없음 | 계획 상세 정보 |
| 계획등록 | POST | /api/plan | 없음 | 계획 상세 정보 |
| 계획수정 | PUT | /api/plan/{planId} | 없음 | 계획 상세 정보 |
| 계획임시삭제 | PUT | /api/plan/{planId}/storage | 없음 | 계획 상세 정보 |
| 계획복구 | PUT | /api/plan/{planId}/storage | 없음 | 계획 상세 정보 |
| 계획영구삭제 | DELETE | /api/plan/{planId}/storage | 없음 | 계획 상세 정보 |
| 계획초대 (url) | POST | /api/member/plan/room/{roomId} | 없음 | 계획 상세 정보 |
| 일정등록 | POST | /api/plan/{planId}/days | 없음 | 계획 상세 정보 |
| 가고싶은장소 | POST | /api/plan/{planId}/location | 없음 | 계획 상세 정보 |
| 일정상세등록 | POST | /api/plan/{planId}/days/calendar | 없음 | 계획 상세 정보 |
| 체크리스트 등록 및 수정 | POST | /api/plan/{planId}/checkLists | 없음 | 계획 상세 정보 |
| 여행계획나가기 | DELETE | /api/plan/{planId}/member | 없음 | 계획 상세 정보 |
| 마이페이지 초대 수락 | POST | /api/member/plan/{planId}/active | 없음 | 계획 상세 정보 |
| 체크리스트 잠금 | POST | /api/plan/{planId}/checkLists | 없음 | 계획 상세 정보 |


#### Feed

기능  |      Method     | URL |  request   |        response       |
| :-: | :----------: | :----: | :-------------: | :--------------: |
|  메인페이지   |  GET |  /api/map  |x좌표 최소, 최댓값, y좌표 최소, 최댓값 | 지역별 피드 조회 정보  |
|  이미지 업로드  |  POST |  /api/feed/image |  Multipart 형태의 이미지 파일들 |  "피드 이미지 등록 성공하였습니다."     |
|  이미지 삭제  |  DELETE |  /api/feed/image |  이미지 파일 이름들 |  "피드 이미지 삭제 성공하였습니다."     |
|  전체 피드 조회  |  GET |  /api/feed |  이미지 파일 이름들 |  내가 작성한 피드 + 좋아요 한 피드 정보   |
|  피드 등록  |  POST |  /api/feed |  피드 정보 |  "피드 등록 성공하였습니다."     |
|  피드 수정  |  PUT |  /api/feed/{feedId} |  피드 정보 |  "피드 수정 성공하였습니다."     |
|  피드 삭제  |  DELETE |  /api/feed/{feedId} |  없음 |  "피드 삭제 성공하였습니다."     |
|  피드 좋아요  |  POST |  /api/feed/{feedDetailLocId}/like |  없음 |  "피드 좋아요 등록에 성공하였습니다."     |
|  피드 좋아요 삭제  |  DELETE |  /api/feed/{feedDetailLocId}/like |  없음 |  "피드 좋아요 취소에 성공하였습니다."     |
|  피드 댓글 등록  |  POST |  /api/feed/comment/{feedDetailLocId} |  댓글 내용 |  "댓글 등록 성공하였습니다."     |
|  피드 댓글 수정  |  PUT |  /api/feed/comment/{feedDetailLocId} |  댓글 내용 |  "댓글 수정 성공하였습니다."     |
|  피드 댓글 삭제  |  DELETE |  /api/feed/comment/{feedDetailLocId} |  없음 |  "댓글 삭제 성공하였습니다."     |
|  북마크 등록  |  POST |  /api/feed/{feedLocId}/bookmark |  없음 |  "피드 북마크 등록에 성공하였습니다."     |
|  북마크 해제  |  DELETE |  /api/feed/{feedLocId}/unbookmark |  없음 |  "피드 북마크 취소에 성공하였습니다."     |
|  채팅방 입장  |  POST |  /topic/{planId}      |  없음 |  입장한 방 번호     |
|  채팅방 메세지 보내기  |  POST |  /app/chat/{planId}    |  메세지, user_id, 보내는 채팅 방 번호 |  "메세지 내용"     |
