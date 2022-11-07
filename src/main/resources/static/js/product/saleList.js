//상품별 판매 선택
//const $each_pickUp_status = document.querySelector('#each_pickUp_status');

//판매 상태별 조회할 목록 키워드
const $pickUp_status_keyword = document.querySelector(".drop__status select");

//판매 상태별 조회할 목록
$pickUp_status_keyword.addEventListener('change', pickUp_status_keyword_h);

let pickUp_status_selected = "3";

function pickUp_status_keyword_h() {
    if ($pickUp_status_keyword.value == '전체') {
        pickUp_status_selected = "3";
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }
    if ($pickUp_status_keyword.value == '픽업 예정') {
        pickUp_status_selected = "0";
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }
    if ($pickUp_status_keyword.value == '픽업 완료') {
        pickUp_status_selected = "1";
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }

    return pickUp_status_selected;
}

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
    const pickUp_status = pickUp_status_selected;

    return {
        "startDate" : startDateData,
        "endDate" : finishDateData,
        "pickUp_status" : pickUp_status
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
    manageList(searchData.pickUp_status, searchData.startDate, searchData.endDate);
}

//--------------------------------------------------
//해당 점주 전체 상품 목록 조회
manageList(pickUp_status_selected, $startDate.value, $finishDate.value);

//상품 관리 목록
function manageList(pickUp_status, startDate, endDate) {
    const ownerNumber = document.querySelector('.memNum').textContent;
    const url = 'http://localhost:9080/api/saleList/' + ownerNumber + '?pickUp_status=' + pickUp_status + '&history_start_date=' + startDate + '&history_end_date=' + endDate;

    fetch(url, {
        method: 'GET',
        headers:{
            'Accept':'application/json'
        }
    }).then(res => res.json())
      .then(res => {
        if (res.header.rtcd == '00') {
            let i = 0;
            const result = res.data.map(product => {
                i++;
                product.deal.orderdate = product.deal.orderdate.substr(0, 10);
                product.deal.visittime = product.deal.visittime.substr(10, 6);
                if (product.imageFiles != null && product.imageFiles.length > 0) {
                    img_url = `<img class="good_Img" src="/api/attach/img/${product.imageFiles[0].code}/${product.imageFiles[0].storeFilename}" alt="이미지를 불러올 수 없습니다.">`;
                } else {
                    img_url = `<img src="/img/product/등록된 사진이 없습니다.png" alt="">`;
                }
                return `<tr>
                            <td>${product.deal.orderNumber}</td>
                            <td>${product.deal.orderdate}</td>
                            <td>${product.member.memNickname}</td>
                            <td><a href="/product/${product.pnumber}/">${product.pname}</a></td>
                            <td>${product.deal.pcount}</td>
                            <td>${product.deal.price}</td>
                            <td>${product.deal.visittime}</td>
                            <td>
                                <p>${product.deal.buyType == 0 ? '온라인 결제' : '현장 결제'}</p>
                            </td>
                            <td>
                                <select name="" id="" onchange="javascript:each_pickUp_status(this, ${product.pnumber}, ${product.deal.pickupStatus})">
                                    <option value="0" ${product.deal.pickupStatus == 0 ? 'selected' : ''}>픽업 예정</option>
                                    <option value="1" ${product.deal.pickupStatus == 1 ? 'selected' : ''}>픽업 완료</option>
                                </select>
                            </td>
                        </tr>`;
            });
            document.querySelector('.sell_list-tb tbody').innerHTML=result.join('');
        }
    }).catch(err=>console.log(err));
}

//상품별 픽업 상태 선택
function each_pickUp_status(obj, pNumber, pickStatus) {
    console.log("pNumber -> " + pNumber);

    if (obj.value == '0') {
        pickUp_st_uf(pNumber, obj.value);
    }
    if (obj.value == '1') {
        pickUp_st_uf(pNumber, obj.value);
    }
}

//픽업 상태 변경
function pickUp_st_uf(pNumber, pickStatus) {
    const url = 'http://localhost:9080/api/saleList/' + pNumber + '/' + pickStatus;
    fetch(url, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify(data),
    }).then(res => res.json())
      .then(data => {
        //1) 입력데이터 가져오기
        const inputDate = getInputData();
        //2) 날짜 조회 처리
        search(inputDate);
    }).catch(err => console.log(err));
}