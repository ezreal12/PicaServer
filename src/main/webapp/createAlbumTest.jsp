<%@ page contentType="text/html;charset=euc-kr" %>

<h1>�ٹ� �߰��ϱ� �׽�Ʈ ������</h1>
<form action="createAlbum.do", method="post" enctype="multipart/form-data">
    <p>name(10�� ���� ����) : <input type="text" name="name" value="testuser"></p>
    <p>description(����) : <input type="text" name="description" value="�׽�Ʈ �ٹ� �Դ� ��"></p>
    <p>create_p_member_id(����) : <input type="text" name="create_p_member_id" value="1"></p>
    <input type="file", name="file" placeholder="select File" /><br/>
    <input type="submit" value="upload">
</form>

<br/>
