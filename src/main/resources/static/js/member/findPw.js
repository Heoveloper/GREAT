//인증번호 발송 버튼
$sendCodeBtn = document.querySelector('.send-code-btn');
//인증번호 확인 버튼
$confirmCodeBtn = document.querySelector('.confirm-code-btn');
//인증코드 확인 인풋창
$memCode = document.querySelector('#memCode');

//인증코드 발송 버튼 클릭시
$sendCodeBtn.addEventListener('click', e => {
    const mailVal = memEmail.value;
    if (mailVal == null) {alert('인증코드를 받을 이메일을 입력하세요.');}
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
            } else {
                alert ('메일주소를 다시 확인해주세요.')
            }
        })
        .catch(err => console.log(err));
}

//인증번호 확인 버튼 클릭시
$confirmCodeBtn.addEventListener('click', e => {
    console.log('인증번호 확인 버튼 클릭');
});