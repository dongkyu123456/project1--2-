//회원가입 유효성검사
// 자원을 화면에 로드하게 되면 수행할 동작(==function)
window.onload = function () {
    var join = document.join; //form데이터를 모두 join변수에 저장

    // 유효성검사할 부분을 class로 부여했기에 check class 태그를 모두 input에 저장 가져옴
    // 이때 input 한 태그당 배열 인덱스로 받는다.
    var input = document.querySelectorAll('.check');

    // 오류 문구 //erroruid : span의 uid들(각 요소마다 나타낼 오류를 표시하기 위함)
    // error : class list의 하위 span을 모두 불러냄(일괄 처리를 위함 - 반복문)
    var errorId = ["uidError", "nickError", "pwError", "pwCheckError", "phoneNumError"];
    var error = document.querySelectorAll('.list > span');


    // 오류문구 초기화 메서드
    // 오류 표시 후, 사용자가 올바르게 수정을 하면 텍스트가 사라지는 모습을 구현
    function innerReset(error) {
        for (var i = 0; i < error.length; i++) {
            error[i].innerHTML = "";
        }
    }

    // // 초기화 메서드 호출
    // innerReset(error);

    // [ uid 입력문자 유효성검사 ] 
    join.uid.onkeydown = function () {
        innerReset(error); // 초기화 메서드 호출
        var uidLimit = /^[a-z0-9_-]{5,20}$/ ; //정규식 5~20자 (a~z, A~Z, 0~9, -, _만 입력가능)
        if (!uidLimit.test(input[0].value)) { //입력값과 정규식 범위와 같지 않다면
            // uid의 오류 문구삽입
            document.getElementById(errorId[0]).innerHTML = "5~20자의 영문 소대문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";

        }
    }

    // [ 닉네임 입력문자 유효성검사 ] 
    join.nick.onkeydown = function () {
        innerReset(error); // 초기화 메서드 호출
        var idLimit = /^[a-zA-Z0-9-_]{5,20}$/; //정규식 5~20자 (a~z, A~Z, 0~9, -, _만 입력가능)
        if (!idLimit.test(input[1].value)) { //입력값과 정규식 범위와 같지 않다면
            // id의 오류 문구삽입
            document.getElementById(errorId[1]).innerHTML = "5~20자의 영문 소대문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";

        }
    }

    // [ PW 입력문자 유효성검사 ]
    join.pw.onkeydown = function () {
        innerReset(error); // 초기화 메서드 호출
        var pwLimit = /^[a-zA-Z0-9~!@#$%^&*()_-]{10,20}$/; //정규식(a~z, A~Z, 0~9, ~!@#$%^&*()_- 만 입력가능)
        if (!pwLimit.test(input[2].value)) { //입력값과 정규식 범위와 같지 않다면
            // pw의 오류 문구삽입
            document.getElementById(errorId[2]).innerHTML = " 10~20자의 영문 소대문자, 숫자와 특수기호 '~!@#$%^&*()_-'만 사용 가능합니다.";
        }
    }
    // [ PW 재확인 입력문자 초기화 ]
    //비밀번호 동일여부는 submit 버튼 클릭시 검사해줄 예정
    join.pwCheck.onkeydown = function () {
        // pw의 오류 문구삽입
        innerReset(error);// 오류문구 초기화   
    }
    // [ 휴대폰번호 입력문자 유효성검사 ]
    join.phoneNum.onkeydown = function () { //입력값과 정규식 범위와 같지 않다면
        innerReset(error); // 초기화 메서드 호출   
        var pnumLimit = /^01[0|1|6|7|8|9]{1}[0-9]{8}$/; // 정규식(^$--> 문자의미, 01, (6자리중 "1자리"), 0~9--> "8자리")
        if (!pnumLimit.test(input[3].value)) { //입력값과 정규식 범위와 같지 않다면
            // 전화번호의 오류 문구삽입 
            document.getElementById(errorId[3]).innerHTML = " 올바른 형식이 아닙니다. 다시 확인해주세요.";
        }
    }

}