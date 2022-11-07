//let slideIndex = 0;
//showSlides();
//
//function showSlides() {
//    let i;
//    let slides = document.getElementsByClassName("banner");
//    let dots = document.getElementsByClassName("dot");
//
//    for (i = 0; i < slides.length; i++) {
//        slides[i].style.display = "none";
//    }
//    slideIndex++;
//    if (slideIndex > slides.length) {slideIndex = 1}
//    for (i = 0; i < dots.length; i++) {
//        dots[i].className = dots[i].className.replace(" active", "");
//    }
//    slides[slideIndex-1].style.display = "block";
//    dots[slideIndex-1].className += " active";
//    setTimeout(showSlides, 4000);
//}

let slideIndex = 1;
let intervalTime = 3000;
showSlides(slideIndex);
let currentIntervalId = setInterval(showSlides, intervalTime);

function plusSlides(n) {
    if (n == -1) showSlides(slideIndex += (n));
    showSlides(slideIndex += (n - 1));
    //slideIndex += n;
    //clearInterval(currentIntervalId);
    //currentIntervalId = setInterval(showSlides, intervalTime);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    let i;
    let slides = document.getElementsByClassName("banner");
    let dots = document.getElementsByClassName("dot");

    if (n > slides.length) { slideIndex = 1 }
    if (n < 1) { slideIndex = slides.length }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex - 1].style.display = "block";
    dots[slideIndex - 1].className += " active";
    slideIndex++;
    if (slideIndex > slides.length) slideIndex = 1;
    //setTimeout(showSlides, 3000);
}


function heart(e) {
    console.log('하트 클릭됨!');
    e.target.classList.toggle('active');
}