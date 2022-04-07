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
|  이미지 업로드  |  PUT |  /api/feed/image |  Multipart 형태의 이미지 파일들 |  "피드 이미지 등록 성공하였습니다."     |

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
