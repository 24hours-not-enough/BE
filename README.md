# <p align="center">항해99 실전프로젝트, TriPlan [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fhaesoo9410&count_bg=%23EB8B10&title_bg=%23684327&icon=&icon_color=%23E7E7E7&title=VISIT&edge_flat=false)](https://github.com/24hours-not-enough) </p> 
<h4 align="center">📆 2022.03.04 ~ 2022.04.08</h4>
<br>
<br>

## 📌 팀원 소개

|[팀장] 김선주|[팀원] 손성진|[팀원] 김효신|[팀원] 김윤민|
|:----:|:-----:|:----:|:----:|
|<img src="https://avatars.githubusercontent.com/u/66668478?v=4" alt="avatar" height="150px" width="150px" /> | <img src="https://avatars.githubusercontent.com/u/48196352?v=4" alt="avatar" height="150px" width="150px" /> | <img src="https://avatars.githubusercontent.com/u/96279734?v=4" alt="avatar" height="150px" width="150px" /> | <img src="https://avatars.githubusercontent.com/u/66665210?v=4" alt="avatar" height="150px" width="150px" /> |
|[sunny-yo](https://github.com/sunny-yo)|[ssj9398](https://github.com/ssj9398)|[Shinnybest](https://github.com/Shinnybest)|[JerryAllMighty](https://github.com/JerryAllMighty)|

<br>

---

<h3><b>🎫 프로젝트 소개 🎫</b></h3>
- "여행과 계획을 한 번에" <br>
- 함께하는 여행, 계획과 기록도 한 번에 같이 !
<br><br> 

<h3><b>🎞 프로젝트 시연영상 🎞</b></h3>

[보러 가기](https://www.youtube.com/watch?v=y6FG8p_Geyc)

---

<br>
<h3 align="center"><b>🛠 Tech Stack 🛠</b></h3>
<p align="center">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">
<img src="https://img.shields.io/badge/GitHub%20Actions-569A31?style=for-the-badge&logo=GitHub%20Actions&logoColor=white">
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=aws&logoColor=white">
     <img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=for-the-badge&logo=Amazon%20S3&logoColor=white">
          <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">
<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/spring%20boot-6DB33F?style=for-the-badge&logo=spring%20boot&logoColor=white">
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
</br>


<br><br>


---


<!-- <br>
<h3 align="center"><b>📢 Main function 📢</b></h3>
<br>
<h4><b>📰 Login Page 📰</b></h4>

<table width="100%">
    <tr>
        <td width="50%"><img src="https://user-images.githubusercontent.com/48196352/149292868-4c797683-a57a-4b94-85ee-b133db765f8f.JPG" /></td>
        <td width="50%">
            <h5>로그인</h5>
            <ul>
                <li>JWT 방식으로 구현</li>
                <li>회원가입 버튼 클릭 시 회원가입 UI로 변경</li>
            </ul>
        </td>
    </tr>
</table>

<br><br>
 -->
<!-- --- -->

<br><br>

<h3 align="center"><b>🏷 API Table 🏷</b></h3>

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

|기능|Method|URL|request|response|
|:--:|:--:|:--:|:--:|:--:|
|컨텐츠 전체 조회|GET|/movies||전체 컨텐츠 리스트|
|특정 컨텐츠 상세 조회|GET|/detail/:category/:movie_name||특정 컨텐츠|
|즐겨찾기 확인|GET|/check_bookmark||


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



<br><br>


---


<h3 align="center"><b>✏ Trouble Shooting ✏</b></h3>
<br>
<details>
    <summary>
        <b>동시성 제어 : 계획을 여러명이 동시에 수정한다면, 먼저 수정하고 있던 사람의 데이터가 유실되는 경우가 발생 </b>
    </summary>
    <br>해결 : 누군가가 수정하고 있는 계획이라면 잠금 처리하여, 다른 사람이 수정하지 못 하게 조치.
</details>
<details>
    <summary>
        <b>채팅 데이터 영속화 문제 : 채팅 메세지가 영속화되지 않아 서버가 내려간 후에는 데이터가 모두 유실. </b>
    </summary>
    <br>해결 : AOF 를 활용하여 데이터를 보관하게 설정.
</details>
<details>
    <summary>
        <b>캐싱 : 데이터의 양이 많으며, 수정이 잦지 않고, 반복적으로 불러오는 데이터의 처리 </b>
    </summary>
    <br>해결 : 스프링 제공 캐시 어노테이션과 캐시 매니저를 활용하여 해결.
</details>
<details>
    <summary>
        <b>예외처리 : 상태 코드에 대한 정보를 저장할 수 없고, 메세지가 통일되지 않으며, 코드의 가독성 저하 </b>
    </summary>
    <br>해결 : ExceptionHandler를 통해서 모든 예외를 한 곳에서 처리.
</details>
