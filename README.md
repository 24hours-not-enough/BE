# <p align="center">항해99 실전프로젝트, TriPlan [![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fhaesoo9410&count_bg=%23EB8B10&title_bg=%23684327&icon=&icon_color=%23E7E7E7&title=VISIT&edge_flat=false)](https://github.com/24hours-not-enough) </p> 
<p align="center"> 📆 2022.01.10 ~ 2022.01.13 </p> 
<br>
<br>
<br>
<br>
<br>


#  <p align="center"> :family: 팀원 소개 </p>

|[팀장] 김선주| [팀원] 손성진 | [팀원] 김효신 | [팀원] 김윤민
|:-----:|:-----:|:-----:|:-----:|
avatar	avatar	avatar
name8965	ssj9398	hyunwoome
🎫 프로젝트 소개 🎫
- "이 넷플릭스 유명하던데 재미있나?"
- 사람들과 넷플릭스의 드라마/영화의 한줄평을 공유해보자!!

🎞 프로젝트 시연영상 🎞
Video Label


🛠 Tech Stack 🛠
       
   




🎬 Getting Started 🎬

~$ cd Team13-NetflixComment
~$ sudo chmod 755 initail_ec2.sh
~$ ./initial_ec2.sh
~$ pip install flask
~$ pip install mongo
~$ python3 app.py


📂 Project Directory Structure 📁

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




📢 Main function 📢

📰 Login Page 📰
	
로그인
JWT 방식으로 구현
회원가입 버튼 클릭 시 회원가입 UI로 변경

📰 Join Membership Page 📰
	
회원가입
아이디 중복확인 시 아이디 입력여부, 형식, 중복 아이디 체크
비밀번호 2번 입력으로 비밀번호 형식, 일치 여부 체크

📰 Movie Main Page 📰
	
메인 화면
넷플릭스의 영화 및 드라마 크롤링 하여 Jinja를 이용한 서버 사이드 렌더링으로 구현
북마크 클릭 시 해당 컨텐츠 하단에 북마크 영역에 표시
컨텐츠 클릭 시 영화 상세 및 리뷰 페이지로 이동

📰 Movie Detail & Reviw Page 📰
	
영화 상세 화면
넷플릭스의 영화 및 드라마 상세정보를 크롤링 하여 Jinja를 이용한 서버 사이드 렌더링으로 구현
	
글쓰기 화면
글 작성 후의 모습
조회 버튼 클릭 시, DB에 저장된 리뷰 Jinja를 통한 서버 사이드 렌더링 구현
저장 버튼 클릭 시, DB에 해당 리뷰 저장
저장 조건 - 모든 입력란을 기입하여야 함
댓글 수정, 삭제 기능 - 본인이 작성한 댓글만 수정, 삭제 가능






🏷 API Table 🏷
User
기능	Method	URL	request	response
로그인	POST	/login	id,pw	
회원가입	POST	/register	id,pw	가입 완료 메세지
로그아웃	GET	/logout		
즐겨찾기 추가	POST	/api/addfavorite	movie_title	추가된 영화제목
즐겨찾기 삭제	POST	/api/delfavorite	movie_title	삭제된 영화제목
Movie
기능	Method	URL	request	response
컨텐츠 전체 조회	GET	/movies		전체 컨텐츠 리스트
특정 컨텐츠 상세 조회	GET	/detail/:category/:movie_name		특정 컨텐츠
즐겨찾기 확인	GET	/check_bookmark		
Review
기능	Method	URL	request	response
리뷰 리스트	GET	/review		"리뷰 조회"
리뷰 작성	POST	/review	review, star, movieTitle	"리뷰 등록 완료"
리뷰 수정	PUT	/review	id, date, review	"수정 완료"
리뷰 삭제	DELETE	/review	userid, review, starValue, writeTime	"삭제 완료"
모든 리뷰 리스트	GET	/allReview		review list
Movie Crawling (❗️최초 1회만 실행)
기능	Method	URL	request	response
영화 크롤링	GET	/save_movies		성공



✏ Trouble Shooting ✏

ajax로 데이터를 받아오고 화면으로 뿌려줄 때, 비동기로 작동하기 때문에 요소들이 생성되기전에 dom에 접근하게 되어 UI를 다루기가 쉽지 않았습니다.
순환 참조(임포트) 문제 개별 파이썬 파일 작업으로 blueprint를 사용하였는데 ex) app.py <- detail.py 이때 detail에서도 app.py를 임포트 할 경우 에러가 발생하였다.
