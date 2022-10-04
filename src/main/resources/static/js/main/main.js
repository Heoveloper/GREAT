var slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function currentSlide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("banner");
    var dots = document.getElementsByClassName("dot");
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
    setTimeout(showSlides, 6000);
}

if(!document.querySelector('.items').innerHTML==''){
    document.querySelector('.heart_Btn').addEventListener('click', heart);
}

// mouse click event
function heart(e) {
    console.log('하트 클릭됨!');
    e.target.classList.toggle('active');
}


// const screenHight = document.body.clientHeight;
// if(screenHight <=740){
//   document.querySelector('.topbtn').style.display="none";
// }
// else if(screenHight > 800){
//   document.querySelector('.topbtn').style.display="block";
// }

// const dm = document.documentElement;
// const topButton = document.querySelector('.topbtn');
// const documentHeight = dm.scrollHeight;

// window.addEventListener('scroll', function () {
//     const scrollToTop = dm.scrollTop;

//     if (documentHeight != 0 ){
//         const actionHeight = 800;

//         if (scrollToTop > actionHeight) {
//             topButton.classList.add('action');
//         } else {
//             topButton.classList.remove('action');
//         }
//     }
// });

// topButton.addEventListener('click', function (e) {
//     e.preventDefault();
//     scrollUp();
// });

// function scrollUp() {
//     const upper = setInterval(function () {
//         if (dm.scrollTo != 0) {
//             window.scrollBy(0, -55);
//         } else {
//             clearInterval(upper);
//         }
//     }, 10);
// }
