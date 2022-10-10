//고객/점주 탭
const $joinCust = document.querySelector('#select--1[type=radio]');
const $joinOwn = document.querySelector('#select--2[type=radio]');
const $ownerInput = document.querySelector('.owner-input');

//주소, 상세주소 선택자
const $address = document.querySelector(".address");
const $detailedAddress = document.querySelector(".detailed-address");

//고객/점주 탭 초기 상태: 고객회원
if (!$joinCust.checked && !$joinOwn.checked) {
    $joinCust.checked = true;
}
//주소 초기 상태: 빈 값
if ($address.value != null) {
    $address.value = '';
}
//상세주소 초기 상태: 빈 값
if ($detailedAddress.value != null) {
    $detailedAddress.value = '';
}

//고객/점주 탭 초기 상태 판단
if ($joinOwn.checked) {
   $ownerInput.classList.toggle('reveal');
}

//고객/점주 탭 클릭시 상태 판단
$joinCust.addEventListener('change', e => {
    $ownerInput.classList.remove('reveal');
});
$joinOwn.addEventListener('change', e => {
    $ownerInput.classList.add('reveal');
});

//에러메세지 선택자
$idErr = document.querySelector('.id_err');
$pwErr = document.querySelector('.pw_err');
$pwcErr = document.querySelector('.pwc_err');
$nnErr = document.querySelector('.nn_err');
$nameErr = document.querySelector('.name_err');
$emailErr = document.querySelector('.email_err');
$codeErr = document.querySelector('.code_err');
$bnErr = document.querySelector('.bn_err');
$snErr = document.querySelector('.sn_err');
$spErr = document.querySelector('.sp_err');
$slErr = document.querySelector('.sl_err');
$sldErr = document.querySelector('.sld_err');
$siErr = document.querySelector('.si_err');
$snsErr = document.querySelector('.sns_err');
$termsErr = document.querySelector('.terms_err');
//에러메세지 전체
$errmsg = document.querySelectorAll('.errmsg');

//가입하기 버튼
$joinBtn = document.querySelector('.join-btn');
//회원가입 폼
$signUpForm = document.querySelector('.sign-up');

//가입하기 버튼 클릭시 호출되는 함수
function btnClick() {
    for (let i = 0; i < $errmsg.length; i++) {
      $errmsg[i].textContent = '';
    }

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    //회원가입
    $signUpForm.submit();
}

//인증코드 입력 후 엔터 입력시 회원가입
memCode.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
        for (let i = 0; i < $errmsg.length; i++) {
          $errmsg[i].textContent = '';
        }

        //유효성 검사
        if (!isValidChk()) {
            return;
        }

        //회원가입
        $signUpForm.submit();
    }
});

//상세주소 입력 후 엔터 입력시 회원가입
$detailedAddress.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
        for (let i = 0; i < $errmsg.length; i++) {
          $errmsg[i].textContent = '';
        }

        //유효성 검사
        if (!isValidChk()) {
            return;
        }

        //회원가입
        $signUpForm.submit();
    }
});

//SNS 입력 후 엔터 입력시 회원가입
memStoreSns.addEventListener('keydown', e => {
    if (e.key === 'Enter') {
        for (let i = 0; i < $errmsg.length; i++) {
          $errmsg[i].textContent = '';
        }

        //유효성 검사
        if (!isValidChk()) {
            return;
        }

        //회원가입
        $signUpForm.submit();
    }
});

//회원가입 키업시 유효성 검사
memId.addEventListener('keyup', e => keyUpValid());
memPassword.addEventListener('keyup', e => keyUpValid());
memPasswordCheck.addEventListener('keyup', e => keyUpValid());
memName.addEventListener('keyup', e => keyUpValid());
memNickname.addEventListener('keyup', e => keyUpValid());
memEmail.addEventListener('keyup', e => keyUpValid());
memCode.addEventListener('keyup', e => keyUpValid());
//회원가입(점주회원 추가입력사항) 키업시 유효성 검사
memBusinessnumber.addEventListener('keyup', e => keyUpValid());
memStoreName.addEventListener('keyup', e => keyUpValid());
memStorePhonenumber.addEventListener('keyup', e => keyUpValid());
$address.addEventListener('keyup', e => keyUpValid());
$detailedAddress.addEventListener('keyup', e => keyUpValid());
memStoreIntroduce.addEventListener('keyup', e => keyUpValid());
memStoreSns.addEventListener('keyup', e => keyUpValid());

//'event: key up' 시 유효성 검사 함수
function keyUpValid() {

    //공백 제거
    if(!noSpace()) {
        return;
    }

    //유효성 검사
    if (!isValidChk()) {
        return;
    }
}

//공백 제거 함수
function noSpace() {
    const space = /\s/;
    if (space.exec(memId.value)) {
        memId.value = '';
        memId.focus();
        return false;
    }
    if (space.exec(memPassword.value)) {
        memPassword.value = '';
        memPassword.focus();
        return false;
    }
    if (space.exec(memPasswordCheck.value)) {
        memPasswordCheck.value = '';
        memPasswordCheck.focus();
        return false;
    }
    if (space.exec(memName.value)) {
        memName.value = '';
        memName.focus();
        return false;
    }
    if (space.exec(memNickname.value)) {
        memNickname.value = '';
        memNickname.focus();
        return false;
    }
    if (space.exec(memEmail.value)) {
        memEmail.value = '';
        memEmail.focus();
        return false;
    }
    if (space.exec(memCode.value)) {
        memCode.value = '';
        memCode.focus();
        return false;
    }
    if (space.exec(memBusinessnumber.value)) {
        memBusinessnumber.value = '';
        memBusinessnumber.focus();
        return false;
    }
    if (space.exec(memStorePhonenumber.value)) {
        memStorePhonenumber.value = '';
        memStorePhonenumber.focus();
        return false;
    }
    return true;
}

//비밀번호 형식 검사 함수
function pwFormat() {
        //const password = /^[a-z0-9_-]{8,15}$/;
        const pwFormat = /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/;


        if (!pwFormat.exec(memPassword.value)) {
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

//사업자번호 형식 검사 함수
function bnFormat() {
        const bnFormat = /^[0-9]+/g;

        if (!bnFormat.exec(memBusinessnumber.value)) {
            return false;
        }
        return true;
}

//가게 연락처 형식 검사 함수
function phoneFormat() {
        const phoneFormat = /^[0-9]+/g;

        if (!phoneFormat.exec(memStorePhonenumber.value)) {
            return false;
        }
        return true;
}


//유효성 검사 함수
function isValidChk() {
    //아이디 검사
    if (memId.value == '') {
        $idErr.textContent = '아이디는 필수입력사항입니다.';
        return false;
    }
    else if (memId.value.trim().length < 8 || memId.value.trim().length > 15) {
        $idErr.textContent = '아이디는 8~15자입니다.';
        return false;
    }
    else if ($dupChkId.classList[2] != 'good') {
        $idErr.textContent = '아이디 중복확인이 필요합니다.';
        return false;
    }
    else {$idErr.textContent = '';}
    //비밀번호 검사
    if (memPassword.value == '') {
        $pwErr.textContent = '비밀번호는 필수입력사항입니다.';
        return false;
    }
    else if (!pwFormat()) {
        $pwErr.textContent = '비밀번호: 숫자/소문자/특수문자 포함 8~15자';
        return false;
    }
    else {$pwErr.textContent = '';}
    //비밀번호 확인 검사
    if (memPasswordCheck.value == '') {
        $pwcErr.textContent = '비밀번호 확인은 필수입력사항입니다.';
        return;
    }
    else if (memPassword.value != memPasswordCheck.value) {
        $pwcErr.textContent = '비밀번호가 일치하지 않습니다.';
        return;
    }
    else {$pwcErr.textContent = '';}
    //이름 검사
    if (memName.value == '') {
        $nameErr.textContent = '이름은 필수입력사항입니다.';
        return;
    }
    else {$nameErr.textContent = '';}
    //닉네임 검사
    if (memNickname.value == '') {
        $nnErr.textContent = '닉네임은 필수입력사항입니다.';
        return false;
    }
    else if (memNickname.value.trim().length < 2 || memNickname.value.trim().length > 6) {
        $nnErr.textContent = '닉네임은 2~6자입니다.';
        return false;
    }
    else if ($dupChkNn.classList[2] != 'good') {
        $nnErr.textContent = '닉네임 중복확인이 필요합니다.';
        return false;
    }
    else {$nnErr.textContent = '';}
    //이메일 검사
    if (memEmail.value == '') {
        $emailErr.textContent = '이메일은 필수입력사항입니다.';
        return false;
    }
    else if (!emailFormat()) {
        $emailErr.textContent = '이메일 형식에 맞지 않습니다.';
        return false;
    }
    else if ($sendCodeBtn.classList[2] == 'bad') {        //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
        $emailErr.textContent = '인증코드를 발송해주세요.';
        return false;
    }
    else {$emailErr.textContent = '';}
    //인증코드 검사
    if (memCode.value == '') {
        $codeErr.textContent = '인증코드는 필수입력사항입니다.';
        return false;
    }
    else if ($confirmCodeBtn.classList[2] == 'bad') {     //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
        $codeErr.textContent = '인증코드 확인이 필요합니다.';
        return false;
    }
    else {$codeErr.textContent = '';}

    //점주회원가입이면 유효성 검사
    //고객회원이 아니면
    if (!($joinCust.checked)) {
        //사업자번호 검사
        if (memBusinessnumber.value == '') {
            $bnErr.textContent = '사업자번호는 필수입력사항입니다.'
            return false;
        }
        else if (!bnFormat()) {
            $bnErr.textContent = '숫자만 입력가능합니다.'
        }
        else if (memBusinessnumber.value.trim().length != 10) {
            $bnErr.textContent = '사업자번호는 10자입니다.'
        }
        else if ($bnConfirmBtn.classList[2] != 'good') {
            $bnErr.textContent = '사업자번호 인증이 필요합니다.'
            return false;
        }
        else {$bnErr.textContent = ''}
        //가게명 검사
        if (memStoreName.value == '') {
            $snErr.textContent = '가게명은 필수입력사항입니다.'
            return false;
        }
        else {$snErr.textContent = ''}
        //가게 연락처 검사
        if (memStorePhonenumber.value == '') {
            $spErr.textContent = '가게 연락처는 필수입력사항입니다.'
            return false;
        }
        else if (!phoneFormat()) {
            $spErr.textContent = '숫자만 입력가능합니다.'
        }
        else {$spErr.textContent = ''}
        //가게 주소 검사
        if ($address.value == '') {
            $slErr.textContent = '가게 주소를 검색해주세요.'
            return false;
        }
        else {$slErr.textContent = ''}
        //가게 상세주소 검사
        if ($detailedAddress.value == '') {
            $sldErr.textContent = '가게 상세주소를 입력해주세요.'
            return false;
        }
        else {$sldErr.textContent = ''}
    }
    //약관 검사
    if (!($termsAll.checked)) {
        $termsErr.textContent = '약관 전체동의 필요';
        return false;
    }
    else {$termsErr.textContent = '';}
    return true;
}

//아이디 중복확인 버튼
const $dupChkId = document.querySelector('.dup-chk-id-btn');

//아이디 중복확인 버튼 클릭시
$dupChkId.addEventListener('click', e => {
    console.log('아이디 중복확인 클릭!');

    const idVal = memId.value;
    if (idVal.trim().length == 0 || idVal.trim().length < 8 || idVal.trim().length > 15) {
        alert('아이디 조건에 맞지 않습니다.')
        memId.focus();
        return;
    }
    dupChkId(idVal);
});

//아이디 중복확인 함수
function dupChkId(idVal) {
    const url = `/api/member/dupChkId`;
    const data = { "memId" : idVal };
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
            //alert ('사용가능한 아이디입니다!')
            $dupChkId.classList.remove('bad');
            $dupChkId.classList.add('good');
            //아이디 에러메세지 제거
            $idErr.textContent = ''
        } else {
            //alert ('중복된 아이디가 존재합니다.')
            $dupChkId.classList.remove('good');
            $dupChkId.classList.add('bad');
        }
      })
      .catch(err => console.log(err));
}

//닉네임 중복확인 버튼
const $dupChkNn = document.querySelector('.dup-chk-nn-btn');

//닉네임 중복확인 버튼 클릭시
$dupChkNn.addEventListener('click', e => {
    console.log('닉네임 중복확인 클릭!');

    const nnVal = memNickname.value;
    if (nnVal.trim().length == 0 || nnVal.length < 2 || nnVal.length > 6) {
        alert('닉네임은 2~6자입니다.');
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
            //alert ('사용가능한 닉네임입니다!')
            $dupChkNn.classList.remove('bad')
            $dupChkNn.classList.add('good')
            //닉네임 에러메세지 제거
            $nnErr.textContent = ''
        } else {
            //alert ('중복된 닉네임이 존재합니다.')
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
                    //인증코드 에러메세지 제거
                    $codeErr.textContent = ''
                } else {
                    alert ('인증번호를 다시 확인해주세요.')
                    $confirmCodeBtn.classList.remove('good');
                    $confirmCodeBtn.classList.add('bad');
                }
            })
            .catch(err => console.log(err));
}

////공공데이터 encoding 인증키
//const enKey = rWGHLB92x6jWBuF2Vi7vGCyIOqWUR5A7otp6POH1Nh9ZrU5Z%2FPg0ebD8OFZz2%2Fvx5XFDgH7o%2BKaOoIG9IVDYNw%3D%3D;
////공공데이터 decoding 인증키
//const deKey = rWGHLB92x6jWBuF2Vi7vGCyIOqWUR5A7otp6POH1Nh9ZrU5Z/Pg0ebD8OFZz2/vx5XFDgH7o+KaOoIG9IVDYNw==;

//사업자번호 인증 버튼
const $bnConfirmBtn = document.querySelector('.bn-confirm-btn');

//사업자번호 인증 버튼 클릭시
$bnConfirmBtn.addEventListener('click', e => {

    //사업자번호 에러메세지 제거
    $bnErr.textContent = ''

    //사업자번호 입력값
    const bNoVal = memBusinessnumber.value;

    //사업자번호 미입력시 알림
    if (bNoVal.length == 0) {
        alert('사업자번호를 입력하세요.');
        memBusinessnumber.focus();
        return;
    }

    //사업자번호 인증
    bnConfirm(bNoVal);
});

//사업자번호 인증 함수
function bnConfirm(bNoVal) {
    const url = `https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=rWGHLB92x6jWBuF2Vi7vGCyIOqWUR5A7otp6POH1Nh9ZrU5Z%2FPg0ebD8OFZz2%2Fvx5XFDgH7o%2BKaOoIG9IVDYNw%3D%3D`;
    const data = { "b_no": [bNoVal] };

    console.log(url, JSON.stringify(data));
    fetch(url, {
        method: 'POST',
        headers: {
          'Accept':'application/json',
          'Content-type': 'application/json'
        },
          body: JSON.stringify(data)
    }).then(res => res.json())
      .then(res => {
        console.log(res)
        console.log(res.data[0].b_stt_cd)
        if (res.data[0].b_stt_cd == '01') {
            alert('인증되었습니다!')
            $bnConfirmBtn.classList.remove('bad');
            $bnConfirmBtn.classList.add('good');
        } else {
            alert('사업자번호를 다시 확인해주세요.')
            memBusinessnumber.value=''
            memBusinessnumber.focus();
            $bnConfirmBtn.classList.remove('good');
            $bnConfirmBtn.classList.add('bad');
        }
      })
      .catch(err => console.log(err));
}


//카카오 geocoder(주소-좌표간 변환 서비스 객체) 생성
const geocoder = new kakao.maps.services.Geocoder();

//주소 검색 버튼
$addrSearchBtn = document.querySelector('.addr-search-btn');

//주소 검색 버튼 클릭시
$addrSearchBtn.addEventListener('click', e => {
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

            // 주소로 좌표 검색(위도, 경도 찾기 콜백함수 사용)
            geocoder.addressSearch(addr, callback);

            // 커서를 상세주소 필드로 이동한다.
            document.querySelector(".detailed-address").focus();

            //에러메세지 제거
            $slErr.textContent = ''
        }
    }).open();
});

//위도, 경도 찾기 콜백함수
const callback = function(result, status) {
    if (status === kakao.maps.services.Status.OK) {
        console.log(result);
        document.querySelector('.lng').value = result[0].x;
        document.querySelector('.lat').value = result[0].y;
    }
};


//'약관 전체선택' 체크박스
const $termsAll = document.querySelector('#terms--all');

// 약관 전체선택
//1) 전체 동의 클릭시
function checkAllTerms(checkAllTerms) {
    const $checkboxes = document.querySelectorAll('input[type="checkbox"]');

    $checkboxes.forEach(e => {
        e.checked = checkAllTerms.checked
    });

    $termsErr.textContent = '';
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