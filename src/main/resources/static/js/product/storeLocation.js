const $lat = document.querySelector('.store_location .lat').textContent;
const $lng = document.querySelector('.store_location .lng').textContent;

//지도 띄우기!
//지도를 담을 영역의 DOM 레퍼런스
const container = document.getElementById('map');
const $store_location = new kakao.maps.LatLng($lat, $lng);

//지도를 생성할 때 필요한 기본 옵션
const options = {
    center: $store_location,    //지도의 중심 좌표
    level: 2                    //지도의 레벨(확대, 축소 정도)
};

//지도 생성 및 객체 리턴
const map = new kakao.maps.Map(container, options);

//마커가 표시될 위치입니다
const markerPosition = $store_location;

//마커를 생성합니다
const marker = new kakao.maps.Marker({
    position: markerPosition
});

console.log("marker", marker);

//마커가 지도 위에 표시되도록 설정합니다
marker.setMap(map);
//마우스 휠로 지도 확대, 축소 금지
setZoomable(false);

//인포창 렌더링
const renderInfowindow = data => {
    return `<div class='infowindow'>
            <p class = 'infowindow_title'>${data.title}</p>
            <p class = 'infowindow_addr'>${data.addr}</p>
            </div>`;
    //데이터 인포창에 각각 가져오기
    const infowindows = data.map((ele, idx, arr) => {
        return new naver.maps.InfoWindow({
            content: renderInfowindow(ele),
            disableAnchor: true
        });
    });
}


//마커 클릭 시 인포창 띄우기
markers.forEach((ele, idx, arr) => {
    naver.maps.Event.addListener(ele, 'click', () => {
        //인포창이 있으면 닫고 없으면 인포창 띄우기
        if (infowindows[idx].getMap()) {
            infowindows[idx].close();
        } else {
            infowindows[idx].open(map, ele);
        }
    });
});

//지도 클릭 시 인포창 닫기
naver.maps.Event.addListener(map, 'click', () => {
    markers.forEach((ele, idx) => {
        infowindows[idx].close();
    });
});

//지도 중심 좌표로 부드럽게 이동시키기
function panTo() {
    const moveLatLon = $store_location;

    map.panTo(moveLatLon);
}

//버튼 클릭에 따라 지도 확대, 축소 기능을 막거나 풀고 싶은 경우에는 map.setZoomable 함수를 사용합니다
function setZoomable(zoomable) {
    //마우스 휠로 지도 확대, 축소 가능 여부를 설정합니다
    map.setZoomable(zoomable);    
}

//지도 확대, 축소 컨트롤에서 확대 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
function zoomIn() {
    map.setLevel(map.getLevel() - 1);
}

//지도 확대, 축소 컨트롤에서 축소 버튼을 누르면 호출되어 지도를 확대하는 함수입니다
function zoomOut() {
    map.setLevel(map.getLevel() + 1);
}