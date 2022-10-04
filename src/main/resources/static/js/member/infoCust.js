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

//회원탈퇴 버튼 클릭시
$exitBtn.addEventListener('click', e => {
    //회원탈퇴
    exit(memNumber);

    //회원탈퇴 완료 후 메인화면으로 이동
    //window.location.href = 'http://localhost:8080/';
});