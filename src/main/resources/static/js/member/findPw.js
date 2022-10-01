//인증번호 발송 버튼
$sendCodeBtn = document.querySelector('.send-code-btn');
//인증번호 확인 버튼
$confirmCodeBtn = document.querySelector('.confirm-code-btn');

//인증번호 발송 버튼 클릭시
$sendCodeBtn.addEventListener('click', e => {
    console.log('인증번호 발송 버튼 클릭');
    console.log(memEmail.value);
    const sss=memEmail.value;
    sendCode(sss);
});

//인증번호 확인 버튼 클릭시
$confirmCodeBtn.addEventListener('click', e => {
    console.log('인증번호 확인 버튼 클릭');
});

//인증번호 발송 함수
function sendCode(sss) {
    const url = `/api/member/mailConfirm`;
    const data = { "email" : sss };
    console.log("--->"+sss);
      fetch(url, {
        method:'POST',
        headers: {
          'Content-type': 'application/json'
        },
        body:JSON.stringify(data)
      }).then(res => res.json())
        .then(res => console.log(res))
        .catch(err => console.log(err));
}