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

//재설정 완료 버튼 클릭시
$confirmBtn.addEventListener('click', e => {
    console.log("재설정 완료 버튼 클릭!");

    //재설정할 비밀번호값
    const pwVal = memPassword.value;
    if (pwVal == null) {alert('재설정할 비밀번호를 입력하세요.');}
    //재설정할 비밀번호 확인값
    const pwValChk = memPasswordCheck.value;
    if (pwValChk == null) {alert('비밀번호를 재입력하세요.'); pwValChk.focus()}

    //변경해줄 데이터
    const member = {
        "memId" : ids,
        "memPassword" : pwVal,
        "memPasswordCheck" : pwValChk
    }

    //비밀번호 재설정
    resetPw(member);
});

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