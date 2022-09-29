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