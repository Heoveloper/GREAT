//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}
//모달팝업 닫기
function close() {
    document.querySelector(".background").className = "background";
}

//회원탈퇴 버튼
const $exitBtn = document.querySelector('.exit-btn');

//주소 검색 버튼
$addrSearchBtn = document.querySelector('.addr-search-btn');

//회원탈퇴 버튼 클릭시
$exitBtn.addEventListener('click', e => {
    //회원탈퇴
    exit(memNumber);

    //회원탈퇴 완료창 노출
    alert('탈퇴되었습니다.');

    //회원탈퇴 완료 후 메인화면으로 이동
    window.location.href = 'http://localhost:8080/';
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