//고객/점주 탭
const $joinCust = document.querySelector('#select--1[type=radio]');
const $joinOwn = document.querySelector('#select--2[type=radio]');
const $ownerInput = document.querySelector('.owner-input');

$joinCust.addEventListener('change', e => {
    $ownerInput.classList.remove('reveal');
});
$joinOwn.addEventListener('change', e => {
    $ownerInput.classList.add('reveal');
});

//라디오버튼값 배열로 다 읽어오기
//const $memType = document.getElementsByName('memType');
//const $memTypeChecked = "a"; //여기에 선택된 radio 버튼의 값이 담기게 된다.
//for(let i = 0; i < $memType.length; i++) {
//    if($memType[i].checked) {
//        $memTypeChecked = $memType[i].value;
//    }
//}

//그냥생각
//정안되면 점주회원가입시만 ajax로 처리하자..

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
//에러메세지 전체
$errmsg = document.querySelectorAll('.errmsg');

//가입하기 버튼
$joinBtn = document.querySelector('.join-btn');
//회원가입 폼
$signUpForm = document.querySelector('.sign-up');

//가입하기 버튼 클릭시 호출되는 함수
function btnClick() {
    console.log(memStoreName.value);

    for (let i = 0; i < $errmsg.length; i++) {
      $errmsg[i].textContent = '';
    }

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    //회원가입
    $signUpForm.submit();

    if(!($joinCust.checked)) {
        $joinOwn.setAttribute('checked', true);
    }
}

//유효성 검사 함수
function isValidChk() {
    if (memId.value == '') {
        $idErr.textContent = '아이디는 필수입력사항입니다.'
        return false;
    } else if ($dupChkId.classList[2] != 'good') {
        $idErr.textContent = '아이디 중복확인이 필요합니다.'
        return false;
    } else if (memPassword.value == '') {
        $pwErr.textContent = '비밀번호는 필수입력사항입니다.'
        return false;
    } else if (memPasswordCheck.value == '') {
        $pwcErr.textContent = '비밀번호 확인은 필수입력사항입니다.'
        return false;
    } else if (memName.value == '') {
        $nameErr.textContent = '이름은 필수입력사항입니다.'
        return false;
    } else if (memNickname.value == '') {
        $nnErr.textContent = '닉네임은 필수입력사항입니다.'
        return false;
    } else if ($dupChkNn.classList[2] != 'good') {
        $nnErr.textContent = '닉네임 중복확인이 필요합니다.'
        return false;
    } else if (memEmail.value == '') {
        $emailErr.textContent = '이메일은 필수입력사항입니다.'
        return false;
    } else if ($sendCodeBtn.classList[2] == 'bad') {        //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
        $emailErr.textContent = '인증코드를 발송해주세요.'
        return false;
    } else if (memCode.value == '') {
        $codeErr.textContent = '인증코드는 필수입력사항입니다.'
        return false;
    }
    else if ($confirmCodeBtn.classList[2] == 'bad') {     //나중에 바꿀 거 바꾸고 !=good으로 바꿔줘야함
        $codeErr.textContent = '인증코드 확인이 필요합니다.'
        return false;
    } else if (!($termsAll.checked)) {
        alert('약관 전체동의가 필요합니다.');
        return false;
    }

    //점주회원가입이면 유효성 검사
    //고객회원이 아니면
    else if (!($joinCust.checked)) {
        if (memBusinessnumber.value == '') {
            $bnErr.textContent = '사업자번호는 필수입력사항입니다.'
            return false;
        }
        if ($bnConfirmBtn.classList[2] != 'good') {
            $bnErr.textContent = '사업자번호 인증이 필요합니다.'
            return false;
        }
        if (memStoreName.value == '') {
            $snErr.textContent = '가게명은 필수입력사항입니다.'
            return false;
        }
        if (memStorePhonenumber.value == '') {
            $spErr.textContent = '가게 연락처는 필수입력사항입니다.'
            return false;
        }
        const $address = document.querySelector(".address");
        if ($address.value == '') {
            $slErr.textContent = '가게 주소를 검색해주세요.'
            return false;
        }
        const $detailedAddress = document.querySelector(".detailed-address");
        if ($detailedAddress.value == '') {
            $sldErr.textContent = '가게 상세주소를 입력해주세요.'
            return false;
        }
        return true;
    }

    return true;
}

//아이디 중복확인 버튼
const $dupChkId = document.querySelector('.dup-chk-id-btn');

//아이디 중복확인 버튼 클릭시
$dupChkId.addEventListener('click', e => {
    console.log('아이디 중복확인 클릭!');

    const idVal = memId.value;
    if (idVal.length == 0) {
        alert('아이디를 입력해주세요.');
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
            alert ('사용가능한 아이디입니다!')
            $dupChkId.classList.remove('bad');
            $dupChkId.classList.add('good');
            //아이디 에러메세지 제거
            $idErr.textContent = ''
        } else {
            alert ('중복된 아이디가 존재합니다.')
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
            //닉네임 에러메세지 제거
            $nnErr.textContent = ''
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