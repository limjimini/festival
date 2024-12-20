let mapContainer = document.getElementById('map'); // 지도를 표시할 div
let mapOptions = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(35.9, 126.85), // 지도의 중심좌표
    level: 12 //지도의 레벨(확대, 축소 정도)
};

let map = new kakao.maps.Map(mapContainer, mapOptions); //지도 생성

let currentInfowindow = null; // 인포윈도우 열려 있는지 확인

let markers = []; // 마커를 저장할 배열
let festivalList = []; // 축제 리스트를 저장할 배열
let allFestivalList = []; // 전체 축제 리스트를 저장할 변수

let currentPage = 1; // 현재 페이지
let itemsPerPage = 5; // 페이지당 축제 목록 수

// // 메뉴 상단바로 페이지 이동 시 세션 스토리지에 저장된 데이터 모두 삭제
// document.querySelector('nav').addEventListener('click', function() {
//     sessionStorage.clear();
// });

document.addEventListener('DOMContentLoaded', function () {
    initialize(); // 페이지 로드 후 초기화 함수

    // 이전 페이지 세션 스토리지 확인
    let checkDetailPage = sessionStorage.getItem('checkURL'); // 상세 페이지에 갔다 왔는지 확인
    if(checkDetailPage && checkDetailPage.includes('/festival/details/')) {
        sessionStorage.removeItem('checkURL'); //
    } else { // 다른 페이지일 경우
        sessionStorage.clear(); // 세션 스토리지 다 지우기
    }
});

// 지도 클릭 이벤트 - 확대
kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
    let latlng = mouseEvent.latLng; // 클릭한 지점의 위도, 경도

    map.setCenter(new kakao.maps.LatLng(latlng.getLat(), latlng.getLng()-0.25));
    map.setLevel(10); // 확대
});

// 지도 전체 보기 버튼 클릭 이벤트
let viewMapBtn = document.getElementById('viewMapBtn');
viewMapBtn.addEventListener('click', function () {
    map.setCenter(new kakao.maps.LatLng(35.9, 126.85));
    map.setLevel(12);
});

// 축제 모두 보기 버튼 클릭 이벤트
let viewFestivalBtn = document.getElementById('viewFestivalBtn');
viewFestivalBtn.addEventListener('click', function () {
    // 이전에 열린 인포윈도우가 있다면 닫음
    if(currentInfowindow) {
        currentInfowindow.close();
    }

    // 검색창 초기화
    document.getElementById('searchInput').value = '';

    let currentCenter = map.getCenter(); // 현재 지도 중심 가져옴
    let currentLevel = map.getLevel(); // 현재 지도 줌 레벨 가져옴

    // 버튼 클릭 후 지도 전체 보기 설정이 안 되어 있다면 전체 보기로 설정
    if(!(currentCenter.getLat()===35.9 && currentCenter.getLng()===126.85) || currentLevel!==12) {
        map.setCenter(new kakao.maps.LatLng(35.9, 126.85));
        map.setLevel(12);
    }

    festivalList = allFestivalList;
    currentPage = 1; // 첫 페이지

    savePaginationAndSearchState();
    // 모든 축제 지도에 마커 표시
    addMarkers(festivalList);
    // 모든 축제 리스트 표시
    displayFestivalList(currentPage);
    // 버튼 클릭 후 페이지네이션 업데이트
    updatePagination();
});

// 지도 확대 버튼 클릭
function zoomIn() {
    map.setLevel(map.getLevel()-1);
}

// 지도 축소 버튼 클릭
function zoomOut() {
    map.setLevel(map.getLevel()+1);
}

// 초기화 및 데이터 로딩
function initialize() {
    fetch('/api/list')
        .then(response => response.json())  // JSON 데이터로 변환
        .then(data => {
            festivalList = data;
            allFestivalList = data;

            // 상태 복원
            restorePaginationAndSearchState();
            setTimeout(restoreInfoWindowState, 90);

            addMarkers(festivalList); // 축제 리스트 마커 표시
            displayFestivalList(currentPage); // 현재 페이지 축제 리스트 표시
            updatePagination(); // 페이지네이션 업데이트

            updatePopularFestivals(); // 인기 축제 목록 업데이트
        })
        .catch(error => console.error('Error fetching festival data:', error));
}

// 인기 있는 축제 리스트 업데이트
function updatePopularFestivals() {
    // 인기 있는 축제 리스트 가져오기
    fetch('/api/popular')
        .then(response => response.json()) // JSON 데이터로 변환
        .then(data => {
            let popularFestivals = document.getElementById('popularFestivals');
            let festivals = data; // 인기 축제 배열 저장

            let currentIndex = 0; // 초기 인덱스 0으로 설정(첫 번째 축제부터)

            // 인기 축제 표시
            let listItem = document.createElement('li');
            listItem.classList.add('list-group-item');
            listItem.textContent = festivals[currentIndex].title; // 첫 번째 인기 축제 목록
            listItem.dataset.festivalId = festivals[currentIndex].boardId; // 축제아이디를 dataset에 추가
            popularFestivals.innerHTML = ''; // 기존 목록 지우기
            popularFestivals.appendChild(listItem);

            // 축제 주기적으로 변경
            setInterval(() => {
                // 인기 축제 목록 하나씩 보여주기
                currentIndex = (currentIndex + 1) % festivals.length; // 인덱스 업데이트
                listItem.textContent = festivals[currentIndex].title; // 이름 보여주기
                listItem.dataset.festivalId = festivals[currentIndex].boardId; // 축제 아이디 업데이트
            }, 2000); // 2초 마다 변경

            // 클릭 시 해당 마커로 이동하여 인포윈도우 열기
            popularFestivals.addEventListener('click', function (event) {
                // 클릭된 요소가 li인지 확인
                if(event.target.tagName === 'LI') {
                    let clickedFestivalId = event.target.dataset.festivalId;
                    let clickedFestival = festivals.find(festival => festival.boardId === Number(clickedFestivalId));

                    if(clickedFestival) { // 축제가 있다면 검색어에 축제명 저장
                        document.getElementById('searchInput').value = clickedFestival.title;
                    }
                }
            });
        })
        .catch(error =>  console.error('Error loading popular festival: ', error))
}

// 마커 추가
function addMarker(festival) {
    // 마커가 표시될 위치
    let markerPosition = new kakao.maps.LatLng(festival.latitude, festival.longitude);

    // 마커 생성
    let marker = new kakao.maps.Marker({
        position: markerPosition,
        clickable: true
    });
    marker.setMap(map);

    // 마커를 클릭했을 때 인포윈도우 생성
    let iwContent = `<div class="wrap">
                                <div class="info">
                                    <div class="title">
                                        <a href="/festival/details/${festival.boardId}" id="infoTitle" onclick="saveInfoWindowState(${festival.boardId})">${festival.title}</a>
                                        <button class="bookmark-btn" id="bookmark-btn-${festival.boardId}" aria-label="북마크">
                                            <span class="bookmark-icon" id="bookmark-${festival.boardId}">♡</span>
                                        </button>
                                    </div>
                                    <div class="body">
                                        <div class="img">
                                            <img src="${festival.imageUrl}" width="73" height="70"  alt="축제 이미지">
                                        </div>
                                        <div class="desc">
                                            <div class="location">${festival.location}</div>
                                            <div class="date">${festival.period}</div>
                                            <div class="contact">${festival.contact}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>`, iwRemoveable = true; // x버튼 표시

    $(document).on("click", `#bookmark-btn-${festival.boardId}`,function () {

        const boardId = festival.boardId; // festival 객체에서 boardId 가져오기
        const $bookmarkIcon = $(`#bookmark-${boardId}`); // 해당 boardId의 북마크 아이콘

        console.log(boardId);

        // AJAX 요청을 통해 북마크 상태 변경
        $.ajax({
            type: "GET", // GET 요청
            url: location.origin + "/api/map/" + boardId + "/bookmark", // 동적 URL 생성
            success: function (response) {
                console.log("Bookmark status:", response);

                // 북마크 상태에 따라 버튼 스타일 변경
                if (response.isBookmarked) {
                    $bookmarkIcon.text("♥");
                } else {
                    $bookmarkIcon.text("♡");
                }

                alert(response.message);
            },
            error: function (error) {
                console.error("Error occurred while bookmarking:", error);
            }
        });
    });

    // 인포윈도우 생성
    let infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : iwRemoveable
    });

    marker.infowindow = infowindow; // 마커에 인포윈도우 저장
    marker.festival = festival; // 마커에 축제 리스트 저장

    // 마커에 클릭이벤트 등록
    kakao.maps.event.addListener(marker, 'click', function() {
        // 이전에 열린 인포윈도우가 있다면 닫음
        if(currentInfowindow) {
            currentInfowindow.close();
        }

        // 마커 위에 인포윈도우 표시
        infowindow.open(map, marker);
        currentInfowindow = infowindow;

        map.setCenter(new kakao.maps.LatLng(festival.latitude, festival.longitude));
        map.setLevel(8);
    });

    return marker;
}

// 마커들을 한 번에 추가
function addMarkers(festivals) {
    markers.forEach(marker => marker.setMap(null)); // 기존 마커 제거
    markers = [];

    festivals.forEach(festival => {
        if (!markers.some(marker => marker.festivalId === festival.boardId)) {
            let marker = addMarker(festival);
            markers.push(marker);
        }
    });
}

// 축제 리스트 표시
function displayFestivalList(page) {
    let start = (page - 1) * itemsPerPage; // 해당 페이지에서 표시할 축제 인덱스 시작
    let end = start + itemsPerPage; // 해당 페이지에서 표시할 축제 인덱스 끝
    let pageFestivals = festivalList.slice(start, end); // 현재 페이지에 해당하는 축제 리스트 가져옴

    let festivalListContainer = document.getElementById('festivalList');
    festivalListContainer.innerHTML = ''; // 기존 리스트 비우기

    // 해당 페이지에 해당하는 축제 리스트 표시
    pageFestivals.forEach(festival => {
        let festivalItem = document.createElement('a');
        // festivalItem.href = "#";
        festivalItem.className = "list-group-item list-group-item-action";

        festivalItem.innerHTML = `
                <div class="d-flex w-100 justify-content-between">
                    <h5 id="listTitle">${festival.title}</h5>
                    <button class="bookmark-btn" id="bookmark-btn-${festival.boardId}" aria-label="북마크">
                        <span class="bookmark-icon" id="bookmark-${festival.boardId}">♡</span>
                    </button>
                </div>
                <p>${festival.location}</p>
                <p>${festival.period}</p>
                <small>${festival.content}</small>
            `;

        // 축제 리스트 목록 클릭 시 해당 마커로 이동하여 인포윈도우 열기
        festivalItem.addEventListener('click', function () {
            event.preventDefault();

            let marker = markers.find(m => m.festivalId === festival.boardId);
            if (marker) {
                kakao.maps.event.trigger(marker, 'click');
            }
            moveToMarker(festival);
        });

        festivalListContainer.appendChild(festivalItem);
    });
}

// 해당 축제의 마커로 이동하여 윈포윈도우 열기
function moveToMarker(festival) {
    // 해당 축제의 마커 찾기
    let marker = markers.find(m => m.festival.boardId === festival.boardId);

    // 해당 마커의 인포윈도우 열기
    if(marker && marker.infowindow) {
        // 이전에 열린 인포윈도우가 있다면 닫음
        if(currentInfowindow) {
            currentInfowindow.close();
        }

        marker.infowindow.open(map, marker);
        currentInfowindow = marker.infowindow;

        // 지도 중심을 마커로 이동
        map.setCenter(new kakao.maps.LatLng(festival.latitude, festival.longitude));
        map.setLevel(8);
    }
}

// 페이지네이션 업데이트
function updatePagination() {
    let pagination = document.getElementById('festivalPage');
    let totalPages = Math.ceil(festivalList.length / itemsPerPage); // 전체 페이지 수

    let pageSize = 5; // 보여줄 페이지 버튼: 1~5
    let endPage = Math.ceil(currentPage/pageSize) * pageSize; // 현재 페이지의 마지막 번호
    let startPage = endPage - (pageSize-1); // 현재 페이지의 시작 번호

    // 마지막 페이지가 총 페이지 수를 초과하면 마지막 페이지로 설정
    if(endPage > totalPages) {
        endPage = totalPages;
    }

    // 페이지 버튼
    let pageBtns = '';
    for (let i = startPage; i <= endPage; i++) {
        pageBtns += `
                <li class="page-item ${i === currentPage ? 'active' : ''}" onclick="savePaginationState()">
                    <a class="page-link" href="javascript:void(0);" onclick="handlePageChange(${i})">${i}</a>
                </li>
            `;
    }

    pagination.innerHTML = `
            <li class="page-item ${startPage === 1 ? 'disabled' : ''}">
                <a class="page-link" href="javascript:void(0);" aria-label="Previous" onclick="handlePageChange(${startPage - 1})">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            ${pageBtns}
            <li class="page-item ${endPage === totalPages ? 'disabled' : ''}">
                <a class="page-link" href="javascript:void(0);" aria-label="Next" onclick="handlePageChange(${endPage + 1})">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        `;
}

// 페이지 변경 처리
function handlePageChange(pageNumber) {
    let totalPages = Math.ceil(allFestivalList.length / itemsPerPage); // 전체 페이지 수
    if(pageNumber<1 || pageNumber>totalPages) { // 잘못된 pageNumber이면 작업 안함
        return;
    }

    currentPage = pageNumber;
    savePaginationAndSearchState();

    // 해당 페이지의 축제 리스트 표시
    displayFestivalList(currentPage);
    // 페이지네이션 업데이트
    updatePagination();
}

// 검색
function searchFestival() {
    let keyword = document.getElementById('searchInput').value.replace(/\s+/g, '').toLowerCase(); // 입력된 검색어의 공백 제거, 소문자로 변환

    // 검색어 필터링
    let filteredList = keyword === '' ? allFestivalList : allFestivalList.filter(festival =>
        festival.title.replace(/\s+/g, '').toLowerCase().includes(keyword) ||
        festival.content.replace(/\s+/g, '').toLowerCase().includes(keyword) ||
        festival.location.replace(/\s+/g, '').toLowerCase().includes(keyword)
    );

    // 검색어에 지역명이 있는 경우
    geocodeLocation(keyword)
        .then(result => {
            console.log("success", result);
        })
        .catch(error => {
            console.log("fail", error);
        });

    festivalList = filteredList; // 필터링된 축제 리스트 업데이트
    currentPage = 1; // 검색 후 첫 페이지

    // 필터링된 축제만 지도에 마커 표시
    addMarkers(festivalList);
    // 필터링된 축제 리스트 표시
    displayFestivalList(currentPage);
    // 검색 후 페이지네이션 업데이트
    updatePagination();

    let currentCenter = map.getCenter(); // 현재 지도 중심 가져옴
    let currentLevel = map.getLevel(); // 현제 지도 줌 레벨 가져옴

    // 검색 후 지도 전체 보기 설정이 안 되어 있다면 전체 보기로 설정
    if(!(currentCenter.getLat()===35.9 && currentCenter.getLng()===126.85) || currentLevel!==12) {
        map.setCenter(new kakao.maps.LatLng(35.9, 126.85));
        map.setLevel(12);
    }

    // 이전에 열린 인포윈도우가 있다면 닫음
    if(currentInfowindow) {
        currentInfowindow.close();
    }
}
// 키보드 엔터 눌렀을 때 검색 가능
document.getElementById('searchInput').addEventListener('keydown', function (event) {
    if(event.key === 'Enter') {
        searchFestival(); // 검색
        savePaginationAndSearchState();
    }
});
// 검색어가 지역일 경우 위도 경도 얻어서 검색 지역 확대
function geocodeLocation(location) {
    return new Promise((resolve, reject) => {
        let geocoder = new kakao.maps.services.Geocoder(); // 주소 -> 좌표로 변환하는 객체 생성

        // 주소로 위도, 경도 찾기
        geocoder.addressSearch(location, function (result, status) {
            if (status === kakao.maps.services.Status.OK) { // 주소로 검색 성공
                let lat = result[0].y;
                let lng = result[0].x;

                // 검색 지역으로 확대
                map.setCenter(new kakao.maps.LatLng(lat, lng));
                map.setLevel(8); // 확대

                resolve("주소 검색 성공");
            } else {
                reject("주소 검색 실패");
            }
        });
    });
}

// 상태 유지
// 현재 열린 인포윈도우 저장
function saveInfoWindowState(festivalId) {
    sessionStorage.setItem('infoWindowFestivalId', festivalId);
}
// 저장된 인포윈도우 열기
function restoreInfoWindowState() {
    let festivalId = sessionStorage.getItem('infoWindowFestivalId');

    if(festivalId) {
        let marker = markers.find(m => m.festival.boardId === Number(festivalId)); // 해당 마커 찾기
        if(marker) {
            if (currentInfowindow) {
                currentInfowindow.close(); // 인포윈도우 닫기
            }

            // 인포윈도우 열기
            if(marker.infowindow) {
                marker.infowindow.open(map, marker);
                currentInfowindow = marker.infowindow;
            }

            map.setCenter(new kakao.maps.LatLng(marker.festival.latitude, marker.festival.longitude));
            map.setLevel(8);

            sessionStorage.removeItem('infoWindowFestivalId'); // 인포윈도우 상태 삭제
        }
    }
}

// 페이지 상태와 검색 상태 저장
function savePaginationAndSearchState() {
    let keyword = document.getElementById('searchInput').value;

    sessionStorage.setItem('searchKeyword', keyword); // 검색 저장
    sessionStorage.setItem('currentPage', currentPage); // 페이지 저장
}
// 저장된 페이지, 검색 가져오기
function restorePaginationAndSearchState() {
    // 검색 가져오기
    let keyword = sessionStorage.getItem('searchKeyword');
    if(keyword) {
        document.getElementById('searchInput').value = keyword;
        searchFestival();

        sessionStorage.removeItem('searchKeyword');
    } else {
        document.getElementById('searchInput').value = '';
    }

    // 페이지 가져오기
    let savePage = sessionStorage.getItem('currentPage');
    if(savePage) {
        currentPage = parseInt(savePage);
        handlePageChange(currentPage);

        sessionStorage.removeItem('currentPage');
    } else {
        currentPage = 1;
    }
}