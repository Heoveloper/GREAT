function show() {
    document.querySelector(".background").className = "background show";
}
function close() {
    document.querySelector(".background").className = "background";
}

document.querySelector(".confirm-btn").addEventListener('click', show);
document.querySelector(".confirm-btn").addEventListener('click', close);