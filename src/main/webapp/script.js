function chkForm() {
	var f = document.frm; // form태그
	if(f.title.value == '') { // name이 title인 input태그의 입력된 값을 가져온다. 
		alert("제목을 입력해주세요 !");
		return false;
	}
	
	if(f.user_id.value == '') { 
		alert("아이디를 입력해주세요!");
		return false;
	}
	
	if(f.content.value == '') { 
		alert("글내용을 입력해주세요 !");
		return false;
	}
	
	f.submit(); // 폼태그 전송
}

function chkDelete(board_no) {
	const result = confirm("삭제하시겠습니까?");
	
	if(result) {
		const url = location.origin;
		location.href = url + "/Board/delete?board_no=" + board_no;
	} else {
		false;
	}
}