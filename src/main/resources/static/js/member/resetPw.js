//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}
//모달팝업 닫기
function close() {
    document.querySelector(".background").className = "background";
}


//재설정 완료 버튼
$confirmBtn = document.querySelector('.confirm-btn');
//비밀번호 재설정 에러메세지
$resetPwEmpty = document.querySelector('.resetPwEmpty');

//재설정 완료 버튼 클릭시
$confirmBtn.addEventListener('click', e => {
    console.log("재설정 완료 버튼 클릭!");

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    const ids = memId.value;
    //재설정할 비밀번호값
    const pwVal = memPassword.value;
    if (pwVal == null) {alert('재설정할 비밀번호를 입력하세요.');}
    //재설정할 비밀번호 확인값
    const pwValChk = memPasswordCheck.value;
    if (pwValChk == null) {alert('비밀번호를 재입력하세요.'); pwValChk.focus();}

    //변경해줄 데이터
    const member = {
        "memId" : ids,
        "memPassword" : pwVal,
        "memPasswordCheck" : pwValChk
    }

    //비밀번호 재설정
    resetPw(member);
});

//비밀번호 형식 검사 함수
function pwFormat() {
    //const password = /^[a-z0-9_-]{8,15}$/;
    const pwFormat = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/;


    if (!pwFormat.exec(memPassword.value)) {
        return false;
    }
    return true;
}

//유효성 검사 함수
function isValidChk() {
    if (memId.value == '' || memPassword.value == '' || memPasswordCheck.value == '') {
        $resetPwEmpty.classList.add('reveal');
        resetPwErrmsg.textContent = '재설정할 비밀번호를 입력하세요.';
        return false;
    }
    if (!pwFormat()) {
        $resetPwEmpty.classList.add('reveal');
        resetPwErrmsg.textContent = '비밀번호 형식이 맞지 않습니다.';
        return false;
    }
    if (memPassword.value != memPasswordCheck.value) {
        $resetPwEmpty.classList.add('reveal');
        resetPwErrmsg.textContent = '비밀번호가 일치하지 않습니다.';
        return false;
    }

    return true;
}

//재설정 완료 함수
function resetPw(member) {
    const url = `/api/member/resetPw`;
      fetch(url, {
        method: 'PATCH',
        headers: {
          'Accept': 'application/json',
          'Content-type': 'application/json'
        },
        body: JSON.stringify(member)
      }).then(res => res.json())
        .then(res => {
            console.log(res);
            if (res.header.rtcd == '00') {
                show();
            } else if (res.header.rtcd == '01') {
                alert ('재설정할 비밀번호를 다시 확인해주세요.')
                memPassword.value = '';
                memPasswordCheck.value = '';
                memPassword.focus();
            } else if (res.header.rtcd == '99') {
                alert ('아이디를 찾을 수 없습니다.')
                memPassword.value = '';
                memPasswordCheck.value = '';
                memPassword.focus();
            }
        })
        .catch(err => console.log(err));
}