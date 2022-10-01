//확인 버튼
const $confirmBtn = document.querySelector('.confirm-btn');
//찾은 아이디 보여줄 곳
const $spanId = document.querySelector('.span-id');
//찾은 등록일자 보여줄 곳
const $spanReg = document.querySelector('.span-reg');

//확인 버튼 클릭시
$confirmBtn.addEventListener('click', e => {
    $spanId.textContent = '찾은 아이디';
    $spanReg.textContent = '찾은 등록일자';

    const name = inputName.value;
    const email = inputEmail.value;
    findId(name, email);

    console.log(name, email);

    show();
});

//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}

//아이디 찾기 AJAX
function findId(name, email) {
    const url = `/api/member/findId`;
    const data = { "memName" : name, "memEmail" : email };
    fetch(url, {
        method:'POST',
        headers:{
          'Accept':'application/json',
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
      .then(res => {
        console.log(res);
        if (res.header.rtcd == '00') {
            res.data[1] = res.data[1].substr(0, 10);
            $spanId.textContent = res.data[0];
            $spanReg.textContent = res.data[1];
        } else {
            console.log(res);
        }
      }).catch(err => console.log(err));
}