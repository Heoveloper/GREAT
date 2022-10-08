//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}
//모달팝업 닫기
function close() {
    document.querySelector(".background").className = "background";
}


//닉네임 중복확인 버튼
const $dupChkNn = document.querySelector('.dup-chk-nn-btn');

//닉네임 중복확인 버튼 클릭시
$dupChkNn.addEventListener('click', e => {
    console.log('닉네임 중복확인 클릭!');
    const nnVal = memNickname.value;
    if (nnVal.length == 0) {
        alert('닉네임을 입력해주세요.');
        memNickname.focus();
        return;
    }
    dupChkNn(nnVal);
});

//닉네임 중복확인 함수
function dupChkNn(nnVal) {
    const url = `/api/member/dupChkNickname`;
    const data = { "memNickname" : nnVal };
    fetch(url, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json'
      },
      body: JSON.stringify(data)
    }).then(res => res.json())
      .then(res => {
        console.log(res)
        if (res.header.rtcd == '00') {
            alert ('사용가능한 닉네임입니다!')
            $dupChkNn.classList.remove('bad')
            $dupChkNn.classList.add('good')
        } else {
            alert ('중복된 닉네임이 존재합니다.')
            $dupChkNn.classList.remove('good')
            $dupChkNn.classList.add('bad')
        }
      })
      .catch(err => console.log(err));
}

//인증코드 발송 버튼
$sendCodeBtn = document.querySelector('.send-code-btn');

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
            if (res.ok == true) {
                alert ('발송되었습니다! 메일을 확인해주세요.')
                $sendCodeBtn.classList.remove('bad');
                $sendCodeBtn.classList.add('good');
            } else {
                alert ('메일주소를 다시 확인해주세요.')
                $sendCodeBtn.classList.remove('good');
                $sendCodeBtn.classList.add('bad');
            }
        })
        .catch(err => console.log(err));
}

//인증코드 확인 버튼
$confirmCodeBtn = document.querySelector('.confirm-code-btn');

//인증코드 확인 버튼 클릭시
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
                } else {
                    alert ('인증번호를 다시 확인해주세요.')
                    $confirmCodeBtn.classList.remove('good');
                    $confirmCodeBtn.classList.add('bad');
                }
            })
            .catch(err => console.log(err));
}


//수정완료 버튼
$modifyBtn = document.querySelector('.modify-btn');
//고객회원정보 수정 폼
$infoForm = document.querySelector('.info');

//수정완료 버튼 클릭시 호출되는 함수
function btnClick() {

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    //수정완료
    $infoForm.submit();
}


//유효성 검사 함수
function isValidChk() {
    //console.log(memNumber);
//    if ($dupChkNn.classList[2] != 'good') {
//        alert('닉네임 중복확인이 필요합니다.');
//        return false;
//    } else if ($sendCodeBtn.classList[2] == 'bad') {        //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
//        alert ('메일주소를 다시 확인해주세요.');
//        return false;
//    } else if ($confirmCodeBtn.classList[2] == 'bad') {     //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
//        alert('인증번호 확인이 필요합니다.');
//        return false;
//    }

    return true;
}


//회원탈퇴 버튼
const $exitBtn = document.querySelector('.exit-btn');

//회원탈퇴 버튼 클릭시
$exitBtn.addEventListener('click', e => {
    if ($pwcEmpty.classList[1] == 'reveal') {
        $pwcEmpty.classList.remove('reveal');
        $pwc.classList.remove('err');
    }
    if ($exitFailed.classList[1] == 'reveal') {
        $exitFailed.classList.remove('reveal');
        $pwc.classList.remove('err');
    }

    //탈퇴 시도시 비밀번호 확인창에 입력한 값
    const $exitPwc = exitPwc.value;

    //회원탈퇴
    exit(memNumber, $exitPwc);
});

//'탈퇴 시 비밀번호 확인' 에러메세지
$pwcEmpty = document.querySelector('.pwcEmpty');
//'탈퇴 시 비밀번호 불일치' 에러메세지
$exitFailed = document.querySelector('.exitFailed');
//에러 발생시 수정할 모달팝업 부분
$pwc = document.querySelector('.pwc');

//유효성 검사 함수
function isExitValidChk() {
    if (exitPwc.value == '') {
        $pwcEmpty.classList.add('reveal');
        $pwc.classList.add('err');
        return false;
    }

    return true;
}

//회원탈퇴 AJAX
function exit(memNumber, $exitPwc) {
  const url = `/api/member/exit`;
  const data = { "memNumber" : memNumber,
                 "exitPwc" : $exitPwc };
  fetch(url, {
    method:'DELETE',
    headers: {
      'Accept':'application/json',
      'Content-type': 'application/json'
    },
    body:JSON.stringify(data)
  }).then(res => res.json())
    .then(res => {
        console.log(res);
        if (res.header.rtcd == '00') {
            alert ('탈퇴되었습니다!')
            window.location.href = 'http://localhost:9080/';
        } else {
            if (!isExitValidChk()) {return;}
            $exitFailed.classList.add('reveal');
            $pwc.classList.add('err');
            exitPwc.value = '';
            exitPwc.focus();
        }
    })
    .catch(err => console.log(err));
}