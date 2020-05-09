<%@ page contentType="text/html;charset=euc-kr" %>

<h1>앨범 추가하기 테스트 페이지</h1>
<form action="createAlbum.do", method="post" enctype="multipart/form-data">
    <p>name(10자 제한 문자) : <input type="text" name="name" value="testuser"></p>
    <p>description(문자) : <input type="text" name="description" value="테스트 앨범 입니 다"></p>
    <p>create_p_member_id(숫자) : <input type="text" name="create_p_member_id" value="1"></p>
    <input type="file", name="file" placeholder="select File" /><br/>
    <input type="submit" value="upload">
</form>

<br/>
