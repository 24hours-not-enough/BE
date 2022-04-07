# <p align="center">항해99 실전프로젝트, TriPlan [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fhaesoo9410&count_bg=%23EB8B10&title_bg=%23684327&icon=&icon_color=%23E7E7E7&title=VISIT&edge_flat=false)](https://github.com/24hours-not-enough) </p> 
<h4 align="center">📆 2022.03.10 ~ 2022.01.13</h4>
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

[![Video Label]()]()

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
<h3 align="center"><b>🎬 Getting Started 🎬</b></h3>
<pre>
<code>
~$ cd Team13-NetflixComment
~$ sudo chmod 755 initail_ec2.sh
~$ ./initial_ec2.sh
~$ pip install flask
~$ pip install mongo
~$ python3 app.py
</code>
</pre>

<br>
<h3 align="center"><b>📂 Project Directory Structure 📁</b></h3>
<pre>
<code>
/static
     ├── /bookmark.svg
     ├── /detail.css
     ├── /home.css
     ├── /login.css
     ├── /Netflix-logo.png
/templates
     ├── /detail.html
     ├── /home.html
     └── /login.html
├── /detail.py
├── /app.py
└── /home.py
</code>
</pre>
<br>
<br>


---


<br>
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


---


<br>
<h4><b>📰 Join Membership Page 📰</b></h4>

<table width="100%">
    <tr>
        <td width="50%"><img src="https://user-images.githubusercontent.com/48196352/149292881-26320151-a136-43a1-8a36-763a5ab88b25.JPG" /></td>
        <td width="50%">
            <h5>회원가입</h5>
            <ul>
                <li>아이디 중복확인 시 아이디 입력여부, 형식, 중복 아이디 체크</li>
                <li>비밀번호 2번 입력으로 비밀번호 형식, 일치 여부 체크</li>
            </ul>
        </td>
    </tr>
</table>


---


<br>
<h4><b>📰 Movie Main Page 📰</b></h4>

<table width="100%">
    <tr>
        <td width="50%"><img src="https://user-images.githubusercontent.com/48196352/149292898-a0ac378e-1fb9-4ec1-988b-2602b6c6a39f.JPG" /></td>
        <td width="50%">
            <h5>메인 화면</h5>
            <ul>
                <li>넷플릭스의 영화 및 드라마 크롤링 하여 Jinja를 이용한 서버 사이드 렌더링으로 구현</li>
                <li>북마크 클릭 시 해당 컨텐츠 하단에 북마크 영역에 표시</li>
                <li>컨텐츠 클릭 시 영화 상세 및 리뷰 페이지로 이동</li>
            </ul>
        </td>
    </tr>
</table>


---

<br>
<h4><b>📰 Movie Detail & Reviw Page 📰</b></h4>
<table width="100%">
    <tr>
        <td width="50%"><img src="https://user-images.githubusercontent.com/48196352/149292894-501485ee-5450-45c9-8c5c-1c59c99f81fd.JPG" /></td>
        <td width="50%">
            <h5>영화 상세 화면</h5>
            <ul>
                <li>넷플릭스의 영화 및 드라마 상세정보를 크롤링 하여 Jinja를 이용한 서버 사이드 렌더링으로 구현 </li>
            </ul>
        </td>
    </tr>
    <tr>
        <td width="50%"><img src="https://user-images.githubusercontent.com/48196352/149292893-043d607a-4434-4443-a91c-280c120d3338.JPG" /></td>
        <td width="50%">
            <h5>글쓰기 화면</h5>
            <ul>
                <li>글 작성 후의 모습</li>
                <li>조회 버튼 클릭 시, DB에 저장된 리뷰 Jinja를 통한 서버 사이드 렌더링 구현</li>
                <li>저장 버튼 클릭 시, DB에 해당 리뷰 저장</li>
                <li>저장 조건 - 모든 입력란을 기입하여야 함</li>
                <li>댓글 수정, 삭제 기능 - 본인이 작성한 댓글만 수정, 삭제 가능</li>
            </ul>
        </td>
    </tr>
</table>

<br><br>

---

<br><br>

<h3 align="center"><b>🏷 API Table 🏷</b></h3>

#### User
|기능|Method|URL|request|response|
|:--:|:--:|:--:|:--:|:--:|
|로그인|POST|/login| id,pw  |    |
|회원가입|POST|/register|  id,pw  |  가입 완료 메세지  |
|로그아웃|GET|/logout|    |    |
|즐겨찾기 추가|POST|/api/addfavorite|movie_title|추가된 영화제목|
|즐겨찾기 삭제|POST|/api/delfavorite|movie_title|삭제된 영화제목|

#### Movie

|기능|Method|URL|request|response|
|:--:|:--:|:--:|:--:|:--:|
|컨텐츠 전체 조회|GET|/movies||전체 컨텐츠 리스트|
|특정 컨텐츠 상세 조회|GET|/detail/:category/:movie_name||특정 컨텐츠|
|즐겨찾기 확인|GET|/check_bookmark||


#### Review

 기능  |      Method     | URL |  request   |        response       |
| :-: | :----------: | :----: | :-------------: | :--------------: |
|  리뷰 리스트  | GET  |  /review   |                 |   "리뷰 조회"     |
|  리뷰 작성  |  POST |  /review  |review, star, movieTitle | "리뷰 등록 완료"  |
|  리뷰 수정  |  PUT |  /review|  id, date, review   |  "수정 완료"     |
|  리뷰 삭제  | DELETE  |  /review |  userid, review, starValue, writeTime   |  "삭제 완료"    |
| 모든 리뷰 리스트 | GET | /allReview |  | review list |

#### Movie Crawling (❗️최초 1회만 실행)

기능  |      Method     | URL |  request   |        response       |
| :-: | :----------: | :----: | :-------------: | :--------------: |
|  영화 크롤링  | GET  |  /save_movies   |                 |   성공     |


<br><br>


---


<h3 align="center"><b>✏ Trouble Shooting ✏</b></h3>
<br>
<details>
    <summary>
        <b>ajax로 데이터를 받아오고 화면으로 뿌려줄 때, 비동기로 작동하기 때문에 
요소들이 생성되기전에 dom에 접근하게 되어 UI를 다루기가 쉽지 않았습니다. </b>
    </summary>
    <br>해결 : 순차적으로 실행되 접근할 수 있게끔 ajax메서드 안에서 작성해서 해결했습니다.
</details>
<details>
    <summary>
        <b>순환 참조(임포트) 문제
개별 파이썬 파일 작업으로 blueprint를 사용하였는데
ex) app.py <- detail.py
이때 detail에서도 app.py를 임포트 할 경우 에러가 발생하였다. </b>
    </summary>
    <br>해결 : 전역으로 임포트 하지 않고 함수내에서 임포트 하는 방법으로 해결
</details>
