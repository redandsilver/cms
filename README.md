# Intro
이커머스 프로젝트

Use : Spring, Jpa, Mysql, Redis, Docker, AWS
Goal : 셀러와 고객 사이를 중계하는 커머스 서버 구축
## 회원
### 공통
- [x] 이메일로 인증번호 전송을 통한 회원가입
### 고객
- [x] 회원가입
- [x] 인증 (이메일)
- [x] 로그인 토큰 발행
- [x] 로그인 토큰을 통한 제어 확인 (JWT,Filter)
- [x] 예치금 관리
### 셀러
- [x] 회원가입

## 주문 서버
### 셀러
- [ ] 상품 등록, 수정
- [ ] 상품 삭제
### 구매자
- [ ] 장바구니 (Redis)
- [ ] 상품 검색 및 상세 페이지
- [ ] 장바구니에 물건 추가
- [ ] 장바구니 확인
- [ ] 주문하기
- [ ] 주문내역 이메일로 발송하기

