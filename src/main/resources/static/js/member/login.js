//로그인 버튼
$loginBtn = document.querySelector('.login-btn');
//회원가입 폼
$loginForm = document.querySelector('.login');
//로그인 에러메세지
$loginEmpty = document.querySelector('.loginEmpty');

//로그인 버튼 클릭시 호출되는 함수
function btnClick() {

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    //로그인
    $loginForm.submit();
}


//유효성 검사 함수
function isValidChk() {
    if (memId.value == '') {
        $loginEmpty.classList.add('reveal');
        return false;
    } else if (memPassword.value == '') {
        $loginEmpty.classList.add('reveal');
        return false;
    }

    return true;
}