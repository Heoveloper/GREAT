//판매 상태별 조회할 목록 키워드
const $sell_status_keyword = document.querySelector(".drop__status select");

//판매 상태별 조회할 목록 클릭
$sell_status_keyword.addEventListener('change', sell_status_keyword_h);

let sell_status_selected = "3";

function sell_status_keyword_h() {
    if ($sell_status_keyword.value == '전체') {
        sell_status_selected = "3";
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }
    if ($sell_status_keyword.value == '판매중') {
        sell_status_selected = "0";
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }
    if ($sell_status_keyword.value == '판매완료') {
        sell_status_selected = "1";
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }

    return sell_status_selected;
}

//$each_sell_status.addEventListener('change', e => {
//    if ($each_sell_status.value == '삭제') {
//        alert("판매글을 삭제 하시겠습니까?");
//    }
//});

//--------------------------------------------------
//날짜 선택
let $startDate = document.querySelector('#startDate');
let $finishDate = document.querySelector('#finishDate');

//현재 접속 시간 -> Mon Oct 03 2022 14:17:44 GMT+0900 (한국 표준시)
let nowTime = new Date();
startDate = lastdateFormat(nowTime);
finishDate = nowdateFormat(nowTime);

$startDate.value = startDate;
$finishDate.value = finishDate;

//Date()를 오늘 YYYY-MM-DD로 변환
function nowdateFormat(date) {
    let month = date.getMonth() + 1;
    let day = date.getDate();

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;

    return date.getFullYear() + '-' + month + '-' + day;
}

//Date()를 어제 YYYY-MM-DD로 변환
function lastdateFormat(date) {
    let month = date.getMonth();
    let day = date.getDate() + 1;

    month = month >= 10 ? month : '0' + month;
    day = day >= 10 ? day : '0' + day;

    return date.getFullYear() + '-' + month + '-' + day;
}

//날짜 조회 버튼
const $period = document.querySelector('.drop__period #searchBtn');

//입력데이터 가져오기
function getInputData() {
    const startDateData = $startDate.value;
    const finishDateData = $finishDate.value;
    const sell_status = sell_status_selected;

    return {
        "startDate" : startDateData,
        "endDate" : finishDateData,
        "sell_status" : sell_status
    };
}

//날짜 조회 버튼 클릭
$period.addEventListener('click', e => {
    //1) 입력데이터 가져오기
    const inputDate = getInputData();
    //2) 날짜 조회 처리
    search(inputDate);
});

function search(searchData) {
    manageList(searchData.sell_status, searchData.startDate, searchData.endDate);
}

//--------------------------------------------------
//해당 점주 전체 상품 목록 조회
manageList(sell_status_selected, $startDate.value, $finishDate.value);

//상품 관리 목록
function manageList(sell_status, startDate, endDate) {
    const ownerNumber = document.querySelector('.memNum').textContent;
    const url = 'http://localhost:9080/api/manage/' + ownerNumber + '?sell_status=' + sell_status + '&history_start_date=' + startDate + '&history_end_date=' + endDate;

    fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    }).then(res => res.json())
      .then(res => {
        if (res.header.rtcd == '00') {
            let i = 0;
            const result = res.data.map(product => {
                i++;
                product.rdate = product.rdate.substr(0, 10);
                if (product.imageFiles != null && product.imageFiles.length > 0) {
                    img_url = `<img class="good_Img" src="/api/attach/img/${product.imageFiles[0].code}/${product.imageFiles[0].storeFilename}" alt="이미지를 불러올 수 없습니다.">`;
                } else {
                    img_url = `<img src="/img/product/등록된 사진이 없습니다.png" alt="">`;
                }
                return `<tr>
                            <td>${i}</td>
                            <td>${img_url}</td>
                            <td>
                                <select name="" id="productStatus" onchange="javascript:each_sell_status(this, ${product.pnumber}, ${product.pstatus})">
                                    <option value="0" ${product.pstatus == 0 ? 'selected' : ''}>판매중</option>
                                    <option value="1" ${product.pstatus == 1 ? 'selected' : ''}>판매완료</option>
                                    <option value="del">삭제</option>
                                </select>
                            </td>
                            <td><a href="/product/${product.pnumber}/">${product.pname}</a></td>
                            <td>${product.salePrice}/${product.normalPrice}원</td>
                            <td>${product.remainCount}/${product.totalCount}개</td>
                            <td>${product.rdate}</td>
                            <td><a class="updateBtn" href="/products/${product.pnumber}/edit">수정</a></td>
                        </tr>`;
            });
            document.querySelector('.product_manage-tb tbody').innerHTML = result.join('');
        }
    }).catch(err => console.log(err));
}

//상품별 판매 선택
function each_sell_status(obj, pNumber, pstatus) {
    console.log("pNumber -> " + pNumber);

    if (obj.value == '0') {
        sell_st_uf(pNumber, obj.value);
    }
    if (obj.value == '1') {
        sell_st_uf(pNumber, obj.value);
    }
    if (obj.value == 'del') {
        if (confirm("판매글을 삭제하시겠습니까? \n삭제 후 복구 불가합니다.")) {
            deleteProduct(pNumber);
        } else {
            obj.value = pstatus;
        }
    }
}

//삭제 함수
function deleteProduct(pNumber) {
    const url = `http://localhost:9080/api/saleList/${pNumber}/del`;
    fetch(url, {
        method: 'DELETE'
    }).then(res => {
        const inputDate = getInputData();
        search(inputDate);
        console.log('삭제 완료');
    }).catch(err => console.log(err));
}

//상품 상태 변경
function sell_st_uf(pNumber, pstatus) {
    const url = 'http://localhost:9080/api/manage/' + pNumber + '/' + pstatus;
    fetch(url, {                    //url
        method:'PATCH',             //http method
        headers:{                   //http header
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify(data), //http body
    }).then(res => res.json())
      .then(data => {
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }).catch(err => console.log(err));
}