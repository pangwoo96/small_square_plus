# Vue 폴더 구조

### 1. [assets](frontend/src/assets)
- 정적인 자원(.png, css, 폰트 등)
- 직접 사용자와 상호작용을 하지는 않는다.

### 2. [components](frontend/src/components)
- 작게 쪼개서 사용할 수 있는 컴포넌트
- 버튼(BaseButton.vue), 모달창(BaseModal.vue), 입력 필드 등
- 일반적으로 UI 구성요소를 단독으로 묶어두는 느낌
- 각 컴포넌트는 HTPM + CSs + TypeScript를 포함할 수 있음

### 3. [views](frontend/src/pages)
- 한 페이지 단위의 컴포넌트, 사용자와 직접적으로 보여지는 화면
- 실제로 router 설정할 때 연결되는 대상
- LoginView.vue, SignupView.vue, MyPageView.vue
- 여기서 components/의 UI 컴포넌트를 조합해서 화면을 만든다.

### 4. [router](frontend/src/router)
- 페이지 이동(라우팅)을 정의 (예: /login -> Login.vue)
- /login 주소를 입력하면 LoginView.vue를 보여주도록 연결
- 실제 API 통신과는 관계 없음. 라우팅 -> 페이지 전환 경로 설정
> {
> 
>path: '/login',
>  
>name: 'Login',
> 
>component: () => import('@/views/LoginView.vue')
> 
>}

### 5. [store](frontend/src/store)
- 상태관리 (Vue나 Pinia 사용 시)

### 6. [App.vue](frontend/src/App.vue)
- 루트 컴포넌트

### 7. [main.ts](frontend/src/main.ts)
- 진입점. 여기서 Vue 앱을 생성하고 마운트

---

## 사용자 관점 흐름

### 1. 사용자가 특정 경로로 이동 [index.ts](frontend/src/router/index.ts)
- 사용자가 /login 주소로 접속하면 LoginView.vue가 보여진다.

### 2. View 파일이 화면 구성 + 데이터 요청을 담당 (view/LoginView.vue)
- LoginView.vue는 보통 전체 레이아웃을 구성
- 내부에 <LoginForm /> 컴포넌트를 포함시켜 화면을 구성
- LoginFomr이라는 컴포넌트 파일의 정보를 가져온다.

### 3. Component 파일에서 사용자 입력 + API 호출 처리 (components/LoginForm.vue)
- component에서 사용자의 입력을 받아 loginUser() 함수 (백엔드 API 호출)를 실행해서 데이터를 받아오고 결과를 화면에 반영

> 1. 사용자가 URL 입력 또는 버튼 클릭 (예: /login)
> 
>    ↓
> 
> 2. Vue Router가 해당 URL에 맞는 View 컴포넌트를 연결 (LoginView.vue)
> 
>    ↓
> 
> 3. View는 전체 화면 레이아웃을 구성하고
> 
>    ↓
> 
> 4. 내부에 다양한 Components를 포함시킴 (예: LoginForm.vue)
> 
>    ↓
> 
> 5. Component에서 사용자 입력을 받고 → API 호출 (axios 또는 fetch)
> 
>    ↓
> 
> 6. 백엔드로 실제 HTTP 요청 발생 (예: POST http://localhost:8080/login)
> 
>    ↓
> 
> 7. 백엔드에서 응답을 보내옴 (예: 로그인 성공 + 유저 정보)
> 
>    ↓
> 
> 8. Component가 응답 데이터를 받아 저장하거나 처리
> 
>    ↓
> 
> 9. View에 전달되거나 View 자체에서 반영됨
> 
>    ↓
> 
> 10. 최종적으로 사용자 화면(UI)에 데이터가 반영됨

---

## 개발 중 메모

### 1. [LoginForm.vue](frontend/src/components/user/LoginForm.vue)
- 왜 비동기 + await를 사용하지?
> const result = axios.post("/login")
- 동기로 코드를 작성하면 API 호출 부분이 오래 걸리기 때문에 전체 화면이 멈추는 경우가 생길 수 있음
- 사용자 UI가 얼어버릴 수 있음
> const result = await axios.post("/login")
- 화면을 멈추지 않고 백그라운드에서 요청을 보내고, 응답이 오면 다음 줄을 실행























