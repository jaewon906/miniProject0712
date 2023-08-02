# 회원가입을 통한 게시판 만들기

## 활용기술 & 라이브러리

## frontend

#### React, axios, react-router-dom, http-proxy-middleware

## backend

#### springFramework(spring security(예정), Mail, JPA), redis, JWT(진행중)   

#### JWT 활용할 섹션 : 로그인, 글쓰기, 글수정, 글삭제

## 기능

### 1. 회원 가입 & 로그인 세모 (로그인 시 회원정보가 없어도 프론트에서 로그인로직이 발동)
##### ContentValidator를 활용한 회원가입 데이터 값 검증
### 2. 아이디 비밀번호 찾기 (이메일 인증으로 진행예정) o
### 3. 회원 탈퇴 세모 (탈퇴하면 게시글 또한 삭제되야하고 세션초기화 해야함)
### 4. 게시글 등록 & 수정 o
### 5. 게시판 종류 분류 o 
### 6. 게시글 찾기 o
### 7. 게시글에 파일 업로드 기능
### 8. 페이징 기능 (쿼리 파라미터와 Pageable을 통한 페이징 기능) -> 큰 기능은 완료. 페이지 번호를 누르면 해당하는 영역 보여주는 기능 구현예정
### 9. JWT 쿠키 기능(로그인 해제 안되도록) (되긴 함 다만 JWT가 HTTPOnly이기 때문에 JS에서 회원 정보 추출이 안되서 서버에서 유저의 닉네임을 따로 불러옴)
### 10. passwordEncoder(BCrypt + salted)를 통한 비밀번호 암호화 o
### 11. JWT 엑세스 토큰을 활용한 스프링 시큐리티 auth 구현 x
##### 내가 생각한 엑세스 토큰을 활용한 스프링 시큐리티 로직
##### 1) 사용자가 특정 권한이 필요한 서비스에 접근할 때 엑세스 토큰과 리프레쉬 토큰을 제공
##### 2) 제공 받은 서버는 각 토큰의 유효성을 검증, 검증이 완료되었다면 권한을 부여 (근데 권한을 사용자의 어디로 부여할지를 모름)
##### 
### 12. 스프링 리액트 통합 빌드 o
### 로그인이 성공하면 JWT token을 부여하는 방식으로 진행하겠습니다.

아래는 login 요청이 들어왔을 때의 절차 입니다.

요청이 들어오면 AbstractAuthenticationProcessingFilter에 들어가게 됩니다.
그 다음 filter의 attemptAuthenticationg메소드를 통해 유저의 정보가 담긴 Authentication객체(인증 전)를 AuthenticationManager에 전달합니다.
Authentication객체는 UsernamePasswordAuthenticationToken을 통해 만듭니다.
내부적으로 Spring Security의 ProviderManager를 통해 적잘한 AuthenticationProvider를 찾습니다.
AuthenticationProvider의 authenticate메소드로 인증을 진행합니다.
인증에 성공했다면 성공한 Authentication객체(인증 후)를 filter에 다시 반환해 authenticationSuccessHandler를 수행합니다.
authenticationSuccessHandler를 통해 jwt token을 발급하고 response를 채워줍니다.