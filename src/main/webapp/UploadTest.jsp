<h1>File Upload</h1>
<form action="picUpload.do", method="post" enctype="multipart/form-data">
    <p>p_member_id num only : <input type="text" name="p_member_id"></p>
    <p>p_album_id num only : <input type="text" name="p_album_id"></p>
    <input type="file", name="uploadfile" placeholder="select File" /><br/>
    <input type="submit" value="upload">
</form>
