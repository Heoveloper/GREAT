//인증번호 발송 버튼
$sendCodeBtn = document.querySelector('.send-code-btn');
//인증번호 확인 버튼
$confirmCodeBtn = document.querySelector('.confirm-code-btn');
//인증코드 확인 인풋창
$memCode = document.querySelector('#memCode');

//인증코드 발송 버튼 클릭시
$sendCodeBtn.addEventListener('click', e => {
    console.log('인증코드 발송 버튼 클릭!');

    const mailVal = memEmail.value;
    if (mailVal.length == 0) {
        alert('인증코드를 받을 이메일을 입력하세요.');
        memEmail.focus();
        return;
    }
    sendCode(mailVal);

    console.log(mailVal);
});

//인증코드 발송 함수
function sendCode(mailVal) {
    const url = `/api/member/mailConfirm`;
    const data = { "email" : mailVal };
      fetch(url, {
        method:'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
      }).then(res => res)
        .then(res => {
            console.log(res)
            console.log(res.body)
            console.log(res.headers)
            if (res.ok == true) {
                alert ('발송되었습니다! 메일을 확인해주세요.')
                $sendCodeBtn.classList.remove('bad');
                $sendCodeBtn.classList.add('good');
                //이메일 에러메세지 제거
                $emailErr.textContent = ''
            } else {
                alert ('메일주소를 다시 확인해주세요.')
                $sendCodeBtn.classList.remove('good');
                $sendCodeBtn.classList.add('bad');
            }
        })
        .catch(err => console.log(err));
}

//인증번호 확인 버튼 클릭시
$confirmCodeBtn.addEventListener('click', e => {
    console.log('인증코드 확인 버튼 클릭!');

    const mailVal = memEmail.value;
    const codeVal = memCode.value;
    if (codeVal.length == 0) {
        alert('인증코드를 입력하세요.');
        memCode.focus();
        return;
    }
    confirmCode(mailVal, codeVal);

    console.log(mailVal, codeVal);
});

//인증코드 확인 함수
function confirmCode(mailVal, codeVal) {
    const url = `/api/member/codeConfirm`;
    const data = { "email" : mailVal,
                   "code" : codeVal };
    fetch(url, {
            method:'POST',
            headers: {
              'Content-type': 'application/json'
            },
            body: JSON.stringify(data)
          }).then(res => res.json())
            .then(res => {
                console.log(res)
                if (res.header.rtcd == '00') {
                    alert ('인증번호 확인되었습니다!')
                    $confirmCodeBtn.classList.remove('bad');
                    $confirmCodeBtn.classList.add('good');
                    //인증코드 에러메세지 제거
                    findPwErrmsg.textContent = ''
                } else {
                    alert ('인증번호를 다시 확인해주세요.')
                    $confirmCodeBtn.classList.remove('good');
                    $confirmCodeBtn.classList.add('bad');
                }
            })
            .catch(err => console.log(err));
}


//확인 버튼
$confirmBtn = document.querySelector('.confirm-btn');
//비밀번호 찾기 폼
$findPwForm = document.querySelector('.find-pw');
//비밀번호 찾기 에러메세지
$findPwEmpty = document.querySelector('.findPwEmpty');

//확인 버튼 클릭시 호출되는 함수
function btnClick() {

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    //로그인
    $findPwForm.submit();
}

//비밀번호 입력 후 엔터 입력시 로그인
memCode.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
        //유효성 검사
        if (!isValidChk()) {
            return;
        }

        //로그인
        $findPwForm.submit();
    }
});


//유효성 검사 함수
function isValidChk() {
    if (memId.value == '' || memEmail.value == '' || memCode.value == '') {
        $findPwEmpty.classList.add('reveal');
        findPwErrmsg.textContent = '비밀번호를 찾으려는 회원의 정보를 입력하세요.';
        return false;
    }
    if (!emailFormat()) {
        $findPwEmpty.classList.add('reveal');
        findPwErrmsg.textContent = '이메일 형식에 맞지 않습니다.';
        return false;
    }
    else if ($sendCodeBtn.classList[2] != 'good') {
        findPwErrmsg.textContent = '인증코드를 발송해주세요.';
        return false;
    }
    if ($confirmCodeBtn.classList[2] != 'good') {     //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
        findPwErrmsg.textContent = '인증코드 확인이 필요합니다.';
        return false;
    }

    return true;
}

//이메일 형식 검사 함수
function emailFormat() {
        //const emailFormat = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
        const emailFormat = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;

        if (!emailFormat.exec(memEmail.value)) {
            return false;
        }
        return true;
}