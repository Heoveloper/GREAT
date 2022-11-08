//전체 목록 가져오기
arrayType('view');

function arrayType(requestUrl) {
    const URL = requestUrl === 'view' ? `http://localhost:9080/api/zonning/recentList` : `doesn\'t exist`;
    if (URL === `doesn\'t exist`) {
        console.log("URL load error");
        //need error page append!
        return;
    }

    fetch(URL, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res => res.json())
      .then(res => {
        if (res.header.rtcd == '00') {
            const result = res.data.map(product => {
                product.deadlineTime = product.deadlineTime.substr(10, 6);
            return `<div class="item">
                        <a href="/product/${product.pnumber}" class="item__image"><img src="https://cdn.imweb.me/thumbnail/20210628/f9482d3bbe416.jpg" alt=""></a>
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