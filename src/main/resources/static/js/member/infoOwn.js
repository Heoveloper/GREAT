//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}
//모달팝업 닫기
function close() {
    document.querySelector(".background").className = "background";
}

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
                $emailErr.textContent = '';
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
                    $codeErr.textContent = '';
                } else {
                    alert ('인증번호를 다시 확인해주세요.')
                    $confirmCodeBtn.classList.remove('good');
                    $confirmCodeBtn.classList.add('bad');
                }
            })
            .catch(err => console.log(err));
}


//사업자번호 인증 버튼
const $bnConfirmBtn = document.querySelector('.bn-confirm-btn');

//사업자번호 인증 버튼 클릭시
$bnConfirmBtn.addEventListener('click', e => {
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
          'Accept': 'application/json',
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
            $bnErr.textContent = '';
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

//주소 변경 버튼
$addrSearchBtn = document.querySelector('.addr-search-btn');
//주소 입력창
$address = document.querySelector('.address');
//상세주소 입력창
$detailedAddress = document.querySelector('.detailed-address');
//감춰둔 상세주소
const $detailAddr = document.querySelector('.hidden-addr');

//주소 변경 버튼 클릭시 상세주소 입력창 보여주기
$addrSearchBtn.addEventListener('click', e => {
    //$detailedAddress.setAttribute('id', "memStoreLocation");

    //주소, 상세주소 입력창 빈값
    $address.value='';
    $detailedAddress.value='';

    //상세주소 보여주기
    $detailAddr.classList.add('show-addr');
});

//주소 검색 버튼 클릭시
$addrSearchBtn.addEventListener('click', e => {
    new daum.Postcode({
        oncomplete: function(data) {
            //팝업에서 검색 결과 항목을 클릭했을때 실행할 코드를 작성하는 부분

            //각 주소의 노출 규칙에 따라 주소를 조합한다.
            //내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기한다.
            let addr = ''; // 주소 변수
            let extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') {    //사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else {                                //사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            //사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                //법정동명이 있을 경우 추가한다. (법정리는 제외)
                //법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                //건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                //표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                //조합된 참고항목을 해당 필드에 넣는다.
                document.querySelector(".extra-address").value = extraAddr;
            } else {
                document.querySelector(".extra-address").value = '';
            }

            //우편번호와 주소 정보를 해당 필드에 넣는다.
            document.querySelector('.postcode').value = data.zonecode;
            document.querySelector(".address").value = addr;

            //주소로 좌표 검색(위도, 경도 찾기 콜백함수 사용)
            geocoder.addressSearch(addr, callback);

            //커서를 상세주소 필드로 이동한다.
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


//입력값 변경시 인증 해제
memNickname.addEventListener('change', e => changeNn());
memEmail.addEventListener('change', e => changeEmail());
memCode.addEventListener('change', e => changeCode());
memBusinessnumber.addEventListener('change', e => changeBn());
function changeNn() {
    $dupChkNn.classList.add('reveal');
    $dupChkNn.classList.remove('bad');
    $dupChkNn.classList.remove('good');
}
function changeEmail() {
    $sendCodeBtn.classList.add('reveal');
    $sendCodeBtn.classList.remove('bad');
    $sendCodeBtn.classList.remove('good');
    $confirmCodeBtn.classList.add('reveal');
    $confirmCodeBtn.classList.remove('bad');
    $confirmCodeBtn.classList.remove('good');
    memCode.removeAttribute("readonly");
}
function changeCode() {
    //$confirmCodeBtn.classList.add('reveal');
    $confirmCodeBtn.classList.remove('bad');
    $confirmCodeBtn.classList.remove('good');
}
function changeBn() {
    $bnConfirmBtn.classList.add('reveal');
    $bnConfirmBtn.classList.remove('bad');
    $bnConfirmBtn.classList.remove('good');
}


//수정완료 버튼
$modifyBtn = document.querySelector('.modify-btn');
//점주회원정보 수정 폼
$infoForm = document.querySelector('.info');

//수정완료 버튼 클릭시 호출되는 함수
function btnClick() {

    //유효성 검사
    if (!isValidChk()) {
        return;
    }

    //회원가입
    $infoForm.submit();
}

//회원가입 키업시 유효성 검사
memPassword.addEventListener('keyup', e => keyUpValid());
memPasswordCheck.addEventListener('keyup', e => keyUpValid());

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
    console.log(memNumber);
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
        return false;
    }
    else if (memPassword.value != memPasswordCheck.value) {
        $pwcErr.textContent = '비밀번호가 일치하지 않습니다.';
        return false;
    }
    else {$pwcErr.textContent = '';}
    //닉네임 검사
    if ($dupChkNn.classList[2] == 'reveal') {
        if ($dupChkNn.classList[3] != 'good') {
            $nnErr.textContent = '닉네임 중복확인이 필요합니다.';
            return false;
        }
        else {$nnErr.textContent = '';}
    }
    //이메일 검사
    if ($sendCodeBtn.classList[2] == 'reveal') {
        if ($sendCodeBtn.classList[3] != 'good') {
            $emailErr.textContent = '인증코드를 발송해주세요.';
            return false;
        }
        else {$emailErr.textContent = '';}
    }
    //인증코드 검사
    if ($confirmCodeBtn.classList[2] == 'reveal') {
        if ($confirmCodeBtn.classList[3] != 'good') {
            $codeErr.textContent = '인증코드 확인이 필요합니다.';
            return false;
        }
        else {$codeErr.textContent = '';}
    }
    //사업자번호 검사
    if ($bnConfirmBtn.classList[2] == 'reveal') {
        if ($bnConfirmBtn.classList[3] != 'good') {
            $bnErr.textContent = '사업자번호 인증이 필요합니다.';
            return false;
        }
        else {$bnErr.textContent = '';}
    }

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
        'Accept': 'application/json',
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