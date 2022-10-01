// 고객/점주 탭
const $joinCust = document.querySelector('#select--1[type=radio]');
const $joinOwn = document.querySelector('#select--2[type=radio]');
const $ownerInput = document.querySelector('.owner-input');

$joinCust.addEventListener('change', e => {
    $ownerInput.classList.remove('reveal');
});
$joinOwn.addEventListener('change', e => {
    $ownerInput.classList.add('reveal');
});


// 약관 전체선택
//1) 전체 동의 클릭시
function checkAllTerms(checkAllTerms) {
    const $checkboxes = document.querySelectorAll('input[type="checkbox"]');

    $checkboxes.forEach(e => {
        e.checked = checkAllTerms.checked
    });
}

//2) 약관 동의 클릭시
function checkTerms() {
    // 전체 동의 체크박스
    const $termsAll = document.querySelector('input[name="agreeAllTerms"]');
    // 약관 체크박스
    const $terms = document.querySelectorAll('input[name="agreeTerms"]');
    // 선택된 약관 체크박스
    const $checkedTerms = document.querySelectorAll('input[name="agreeTerms"]:checked');

    $termsAll.checked = ($terms.length === $checkedTerms.length);
}

/////

//인증번호 발송 버튼
$sendCodeBtn = document.querySelector('.send-code-btn');
//인증번호 확인 버튼
$confirmCodeBtn = document.querySelector('.confirm-code-btn');

//주소 검색 버튼
$addrSearchBtn = document.querySelector('.addr-search-btn');

//사업자번호 인증 버튼
$bnConfirmBtn = document.querySelector('.bn-confirm-btn');

//사업자번호 입력값
const $memBusinessnumber = memBusinessnumber.value;

//공공데이터 encoding 인증키
//const enKey = rWGHLB92x6jWBuF2Vi7vGCyIOqWUR5A7otp6POH1Nh9ZrU5Z%2FPg0ebD8OFZz2%2Fvx5XFDgH7o%2BKaOoIG9IVDYNw%3D%3D;
//공공데이터 decoding 인증키
//const deKey = rWGHLB92x6jWBuF2Vi7vGCyIOqWUR5A7otp6POH1Nh9ZrU5Z/Pg0ebD8OFZz2/vx5XFDgH7o+KaOoIG9IVDYNw==;

//인증번호 발송 버튼 클릭시
$sendCodeBtn.addEventListener('click', e => {
    console.log('인증번호 발송 버튼 클릭');
    console.log(memEmail.value);
    const sss=memEmail.value;
    sendCode(sss);
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

//인증번호 확인 버튼 클릭시
$confirmCodeBtn.addEventListener('click', e => {
    console.log('인증번호 확인 버튼 클릭');
});

//주소 검색 버튼 클릭시
$addrSearchBtn.addEventListener('click', e => {
    console.log('클릭');
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            let addr = ''; // 주소 변수
            let extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.querySelector(".extra-address").value = extraAddr;

            } else {
                document.querySelector(".extra-address").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.querySelector('.postcode').value = data.zonecode;
            document.querySelector(".address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.querySelector(".detailedAddress").focus();
        }
    }).open();
});

//사업자번호 인증 버튼 클릭시
$bnConfirmBtn.addEventListener('click', e => {
    if(!confirm('조회?')) return;
    //bnConfirm($memBusinessnumber);
});

//사업자번호 인증 함수
function bnConfirm($memBusinessnumber) {
    const url = `https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=` + deKey;
    const data = { "b_no": [$memBusinessNumber] };
    fetch(url, {
        method: 'POST',
        headers: {
          'Accept':'application/json',
          'Content-type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => res.json())
      .then(res => console.log(res))
      .catch(err => console.log(err));
}

//회원탈퇴 함수
//function exit(memNumber){
//  const url = `/api/member/exit`;
//  const data = { "memNumber" : memNumber };
//  fetch(url, {
//    method:'DELETE',
//    headers: {
//      'Accept':'application/json',
//      'Content-type': 'application/json'
//    },
//    body:JSON.stringify(data)
//  }).then(res => res.json())
//    .then(res => console.log(res))
//    .catch(err => console.log(err));
//}