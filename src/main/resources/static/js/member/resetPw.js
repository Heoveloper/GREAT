//모달팝업 열기
function show() {
    document.querySelector(".background").className = "background show";
}
//모달팝업 닫기
function close() {
    document.querySelector(".background").className = "background";
}

//재설정 완료 버튼
const $confirmBtn = document.querySelector('.confirm-btn');

//재설정 완료 버튼 클릭시
//$confirmBtn.addEventListener('click', show);