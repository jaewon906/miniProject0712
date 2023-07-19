# 회원가입을 통한 게시판 만들기

## 활용기술 & 라이브러리

## frontend

#### React, axios, react-router-dom, http-proxy-middleware

## backend

#### springFramework(spring security(예정), ContentValidator, Mail, JPA), redis, JWT   

## 기능

### 1. 회원 가입 & 로그인 세모 (로그인 시 회원정보가 없어도 프론트에서 로그인로직이 발동)
##### ContentValidator를 활용한 회원가입 데이터 값 검증
### 2. 아이디 비밀번호 찾기 (이메일 인증으로 진행예정) o
### 3. 회원 탈퇴 세모 (탈퇴하면 게시글 또한 삭제되야하고 세션초기화 해야함)
### 4. 게시글 등록 & 수정 o
### 5. 게시판 종류 분류 o 
### 6. 게시글 찾기 o
### 7. 게시글에 파일 업로드 기능
### 8. 페이징 기능
### 9. 쿠키 기능(로그인 해제 안되도록) (세션에 키값이 보이는거와 그냥.. 먼가 덜 완성된 느낌)