<%@ page contentType="text/html;charset=utf-8"%>

<h1>회원가입 테스트</h1>
<form action="registerMember.do", method="post" enctype="multipart/form-data">
    <p>닉네임(8자 제한) : <input type="text" name="nickname" value="hello"></p>
    <p>password(문자) : <input type="text" name="password" value="1234"></p>
    <p>email(문자) : <input type="text" name="email" value="test@naver.com"></p>
    <p>phonenumber(문자) : <input type="text" name="phonenumber" value="010-1234-5678"></p>
      <input type="submit" value="전송">
</form>

<br/>
