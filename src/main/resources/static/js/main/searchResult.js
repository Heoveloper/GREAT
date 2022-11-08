const $titleText = document.querySelector('.title p').innerHTML;

//검색 결과 목록 가져오기
searchResult($titleText);

//검색 결과 목록 함수
function searchResult(searchKeyword) {
    const url = `http://localhost:9080/api/searchresult?searchKeyword=${searchKeyword}`;
    console.log(url);

    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res => res.json())
      .then(res => {
        let img_url = "";
        if (res.header.rtcd == '00') {
            //화면에 조회되는 판매글 수
            viewTotalCount(res.data.length);
            //화면 목록 양식에 반영하기
            const result = res.data.map(product => {
                product.deadlineTime = product.deadlineTime.substr(10, 6);
                if (product.imageFiles != null && product.imageFiles.length > 0) {
                    img_url = `<img class="good_Img" src="/api/attach/img/${product.imageFiles[0].code}/${product.imageFiles[0].storeFilename}" alt="이미지를 불러올 수 없습니다.">`;
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