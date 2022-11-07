//업종별 카테고리 선택
const $categories = document.querySelectorAll('.food_list li');
//상품 정렬 선택
const $array = document.querySelectorAll('.view_array-type ul li a');

//업종별 카테고리 li 선택 시 없으면 'active' 클래스 추가, 있으면 'active' 클래스 삭제
$categories.forEach(ele => {
    ele.addEventListener('click', e => {
        for (let i = 0; i < $categories.length; i++) {
            if ($categories[i].classList.contains('active')) {
                $categories[i].classList.remove('active');
            }
        }
        e.target.classList.add('active');
        //업종별 카테고리 선택 시 전체 목록 가져오기
        arrayType('recentList');
    });
});

//상품 정렬 클래스 추가 및 제거
$array.forEach(ele => {
    ele.addEventListener('click', e => {
        for (let i = 0; i < $array.length; i++) {
            if ($array[i].classList.contains('selected')) {
                $array[i].classList.remove('selected');
            }
            e.target.classList.add('selected');
        }
    });
});

//전체 목록 가져오기
arrayType('recentList');

//정렬방식 함수
function arrayType(requestUrl) {
    const zone = document.querySelector(".title.content").dataset.zone;
    const category = document.querySelector(".food-type .active").textContent;

    const urls = [
        `http://localhost:9080/api/zonning/recentList`,
        `http://localhost:9080/api/zonning/discountListDesc`,
        `http://localhost:9080/api/zonning/priceList`,
        `http://localhost:9080/api/zonning/priceListDesc`
    ];
    const urlsWithZone = urls.map(url => url + `?zone=${zone}&category=${category}`);

    const URL = requestUrl === 'recentList' ? urlsWithZone[0]
                : requestUrl === 'discountListDesc' ? urlsWithZone[1]
                : requestUrl === 'priceList' ? urlsWithZone[2]
                : requestUrl === 'priceListDesc' ? urlsWithZone[3]
                : `doesn\'t exist`;

    if (URL === `doesn\'t exist`) {
        console.log("URL load error");
        return;
    }

    fetch(URL, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res => res.json())
      .then(res => {
        let img_url = "";
        if (res.header.rtcd == '00') {
            //화면에서 조회 가능한 판매글 수
            viewTotalCount(res.data.length);
            //화면 목록 양식에 반영하기
            const result = res.data.map(product => {
                product.deadlineTime = product.deadlineTime.substr(10, 6);
                if (product.imageFiles != null && product.imageFiles.length > 0) {
                    const one = product.imageFiles.length - 1;
                    img_url = `<img class="good_Img" src="/api/attach/img/${product.imageFiles[one].code}/${product.imageFiles[one].storeFilename}" alt="이미지를 불러올 수 없습니다.">`;
                } else {
                    img_url = `<img src="/img/product/등록된 사진이 없습니다.png" alt="">`;
                }
                return `<div class="item">
                            <a href="/product/${product.pnumber}" class="item__image">${img_url}</a>
                            <button class="heart_Btn"><i class="heartIcon far fa-heart fas"></i></button>
                            <a href="#" class="item__title">${product.pname}</a>
                            <div class="item__price-box">
                                <div class="item__discount-rate">${product.discountRate}%</div>
                                <div class="item__price">${product.salePrice}원</div>
                                <div class="item__price--normal">${product.normalPrice}원</div>
                            </div>
                            <div class="item__time-box">
                                <div class="item__magam">마감시간</div>
                                <div class="item__time">${product.deadlineTime}</div>
                            </div>
                        </div>`;
            });
            document.querySelector('.main .items').innerHTML = result.join('');
        }
    }).catch(err => console.log(err));
}

//판매글 수 담기
function viewTotalCount(totalCount) {
    let $totalCount = document.querySelector('.view_array .view_array-total span');
    $totalCount.innerHTML = totalCount;
}