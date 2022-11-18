//좋아요 추가
function goodAdd(pNumber, good) {
    const url = `http://localhost:9080/mypage/good/${pNumber}`;
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(good)
    }).then(res => res.json())
      .then(data => console.log(data))
      .catch(err => console.log(err));
}

//좋아요 삭제
function goodDel(pNumber) {
    const url = `http://localhost:9080/mypage/good/del/${pNumber}`;
    fetch(url, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json'
        },
    }).then(res => res.json())
      .then(data => console.log(data))
      .catch(err => console.log(err));
}