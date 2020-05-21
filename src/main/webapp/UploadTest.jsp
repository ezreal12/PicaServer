<%@ page contentType="text/html;charset=utf-8"%>
<h1>File Upload</h1>
<form action="picUpload.do", method="post" enctype="multipart/form-data">
	<p>latitude double only : <input type="text" name="latitude" value="3.14"></p>
	<p>longitude double only : <input type="text" name="longitude" value="3.24"></p>
	<p>contents 45자 제한 : <input type="text" name="contents" value="이것은 테스트용입니다. 파일은 실존합니다."></p>
    <p>p_member_id num only : <input type="text" name="p_member_id" value="1"></p>
    <p>p_album_id num only : <input type="text" name="p_album_id" value="1"></p>
    <p>태그값을 아예 안줘서 NULL은 가능하지만 반드시 태그틑 '/'으로 구분하는게 신상에 좋을것이요.</p>
    <p>tags : <input type="text" name="tags" value="이것은/태그/입니다/테스트용/태그"></p>
    <input type="file", name="realfile" placeholder="select File" /><br/>
    <input type="submit" value="upload">
</form>

<br/>
