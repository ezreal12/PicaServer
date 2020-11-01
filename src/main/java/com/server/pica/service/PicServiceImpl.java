package com.server.pica.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dao.PicDAO;
import com.server.pica.dto.AlbumMemberDTO;
import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.LikePictureDTO;
import com.server.pica.dto.LoginVO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.PictureDTOWrapper;
import com.server.pica.dto.RegisterMemberDTO;
import com.server.pica.dto.ReplyDTO;
import com.server.pica.dto.ReplyDTOWrapper;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;
import com.server.pica.dto.ShowReplyResultVO;
import com.server.pica.util.FileUtil;
import com.server.pica.util.StringUtil;

// �� ���� ������ ���񽺶�� ������ִ°� ��������
// ��â�Ѵ� �������� �ֳ����̼��� ����!
// @Repository ������ AutoWired �ֳ����̼� �����ڵ� �ݵ�� ������ �߿�
@Repository
public class PicServiceImpl implements PicService {
	// ������� �׽�Ʈ ���� ���� ���� ���
	// private static final String UPLOAD_PATH =
	// "C:\\Users\\SiuKim\\Desktop\\sample";
	// ��������
	// ����! : ���� ��η� ������ �����Ҽ��ִ´�� �ش� ��ο� ��������� �ݵ�� �ʿ��� chmod 777 filetest

	public static final int UPLOAD_ERROR_FILE = -1;
	// DB���� �����͸� ã�� �������� (select���� ���� �ȳ�������)
	public static final int NOT_FOUND_DATA = -1;
	public static final int ERROR_DATABASE = -2;
	// ���� ���� ����
	public static final int NO_PERMISSOIN = -3;
	// (�α��� ���) ��й�ȣ Ʋ��
	public static final int PASSWARD_ERROR = -2;
	// (�α��� ���) ID ����
	public static final int NOT_FOUND_ID = -3;
	// ��û �Ϸ�
	public static final int REQUEST_OK = 0;
	// �α��� �Ϸ�
	public static final int LOGIN_OK = 0;
	@Autowired
	PicDAO dao;

	// ��Ʈ�ѷ����� ���� �Է¹ް� ��������
	// ���� ���� �Ŀ� DB�� �����͸� �Է��ϰ� ����� ������
	// ����:0 / ���� ������� -1 / DB���� : -2

	@Override
	public int savePicture(PicUploadDTO dto, String tags, MultipartFile uploadfile, String savePath) {
		// insertPicData(PicUploadDTO dto,List<String> tags);
		// 1. ���� ����
		dto = FileUtil.saveFile(uploadfile, dto, savePath);
		if (dto == null)
			return UPLOAD_ERROR_FILE;
		List<String> tagList;
		if (tags == null)
			tagList = null;
		else {
			// DB ���� �� tag �Ľ��ϱ�
			tagList = StringUtil.insertTags(tags);
		}
		// 2. DB�� �����Է�
		int result = dao.insertPicData(dto, tagList);
		// DB ���� �߻���
		if (result < 0) {
			System.out.println("! -- insertPicData �±� �Է� DB ���� �ڵ� : " + result);
			return ERROR_DATABASE;
		}
		return REQUEST_OK;
	}

	@Override
	public int registerMember(RegisterMemberDTO dto) {
		// 2. DB�� �����Է�
		int result = dao.registerMember(dto);
		// DB ���� �߻���
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}

	@Override
	public int createAlbum(CreateAlbumDTO dto, MultipartFile uploadfile, String savePath) {
		// 1. ���� ���� realFileName= ������ ����� ���ϸ�
		String realFileName = FileUtil.saveFile(uploadfile, savePath);
		if (realFileName == null)
			return UPLOAD_ERROR_FILE;
		// ������ ����� ���ϸ� dto�� �Է�
		dto.setDefaultPicture(realFileName);
		// 2. DB�� �����Է�
		int result = dao.createAlbum(dto);
		// TODO : 3. �ٹ� �����ѻ���� �ٹ� ����� �߰� (�������� �ο�)
		/*
		 * 6�� 8�� ���� ���⼭ ������ �ٹ��� ���� ID�� �˾Ƴ��� album_member ���̺� �ٹ� ID�� ��� ID�� ��������ϴµ� �ٷ�
		 * ���ٿ��� ������ �ٹ��� ���� ID�� �˾Ƴ����ִ� ����� ���� ���� DB ���� ������
		 */
		// DB ���� �߻���
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}

	@Override
	public List showTable(String tableName) {
		return dao.showTable(tableName);
	}

	// ��� ID�� �ٹ��� ������ �ִ��� Ȯ���ϱ�
	// false : ���Ѿ��� , true : ���� ����.
	private boolean checkPermissoinAlbum(int member_id, int album_id) {
		List<AlbumMemberDTO> list = dao.getAlbumMember(member_id);
		if (list == null || list.size() == 0)
			return false;
		for (AlbumMemberDTO d : list) {
			if (d.getA_album_id() == album_id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MyAlbumDTO> getMyalbum(int create_p_member_id) {
		List<CreateAlbumDTO> list = dao.getMyalbum(create_p_member_id);
		if (list == null)
			return null;
		List<MyAlbumDTO> result = new ArrayList<MyAlbumDTO>();
		for (CreateAlbumDTO d : list) {
			String nick = dao.getNickNameFromId(d.getCreate_p_member_id());
			MyAlbumDTO data = new MyAlbumDTO(d, nick);
			result.add(data);
		}
		return result;
	}

	// �ٹ� id�� �Է¹ް� �ٹ��� ����ִ� ���� ������ ���� ��������
	// 0 ����(), -1 �ٹ�����, -2 �������� ����, -3 ���Ѿ���
	// showPicture.do
	@Override
	public ShowPictureResultVO showPictureList(int album_id, int member_id) {
		ShowPictureResultVO result = new ShowPictureResultVO();
		List<PictureDTO> list = dao.showPictureList(album_id);

		// �ٹ� ������ Ȯ���ؼ� ������ �������
		if (!checkPermissoinAlbum(member_id, album_id)) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		// �˻� ����� 0�ϰ��
		if (list == null || list.size() == 0) {
			/*
			 * DB �˻��� �ش� �ٹ��� �������� �������, �ش� �ٹ��� �����ϴµ� ������ ��������������� �Ѵ� size 0�� �迭�� �����ع��� �ٹ���
			 * ����� �Ұ�� -1�� �ƴ϶� 0�� �����ϰ� �����͸� �������
			 */
			// �ٹ��� �����ϴ��� Ȯ��
			CreateAlbumDTO d = dao.getAlbum(album_id);
			if (d == null) {
				// �ٹ��� �ƿ� �������� ������ �ش� �ٹ�����
				result.setCode(NOT_FOUND_DATA);
			}
			// �ٹ��� �����ϸ� �׳� �Ʒ����� �ϸ��
		}
		// �ٹ� �̸�, �ٹ�����, �ٹ� Ÿ��Ʋ���� ���
		CreateAlbumDTO album = dao.getAlbum(album_id);
		result.setName(album.getName());
		result.setDescription(album.getDescription());
		// ������ ��Ʈ�ѷ��ܿ��� http ��κٿ��� �ݽô�
		result.setDefaultPicture(album.getDefaultPicture());
		result.setCode(REQUEST_OK);
		result.setResult(list);
		return result;
	}

	// ���� id�� �Է¹ް� ���� 1�� ��ȸ�ؼ� �����ϱ�
	@Override
	public ShowPictureDataResultVO showPicture(int picture_id, int member_id) {
		ShowPictureDataResultVO result = new ShowPictureDataResultVO();
		PictureDTO dto = dao.showPicture(picture_id);
		if (dto == null) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		/*
		 * TODO : member_id�� p_member_id�� ������ Ȯ�� (���Ŀ� ģ���ʴ� ��ɱ����� AlbumMember���� ã�ƾ���) ����
		 * ģ���ʴ� ����� �����Ǹ� ������ (�ٹ��� �Ҽӵ� ����� ����ִ� ���̺���
		 */
		// 1. �ش� ������ ���� �ٹ� ID ��ȸ�ϱ�
		int albumId = dto.getP_album_id();
		// 2. �ش� �ٹ� ID�� �����ִ� ������ ����ڿ��� �ִ°� ��ȸ�ϱ�
		if (!checkPermissoinAlbum(member_id, albumId)) {
			result.setCode(NO_PERMISSOIN);
			return result;
		}
		// ���� �Խ��� �г��� ��������
		PictureDTOWrapper dtoWrapper = new PictureDTOWrapper(dto);
		dtoWrapper.setNickName(dao.getNickNameFromId(dtoWrapper.getP_member_id()));

		// ���� ���ε��� �����ΰ�? �˾ƺ���
		// ���δ� ID
		int uploaderId = dto.getP_member_id();
		// �ڱⰡ ���ε� �� �����̸�
		if (uploaderId == member_id) {
			result.setIsMyUpload('y');
		}
		// �ڱⰡ ���ε��� ������ �ƴҰ��
		else {
			result.setIsMyUpload('n');
		}

		// ���� ������ ���ƿ並 ���� �����ΰ�?
		LikePictureDTO sample = new LikePictureDTO();
		sample.setMember_id(member_id);
		sample.setPicture_id(dto.getPicture_id());
		LikePictureDTO serchResult = dao.serchLikePicture(sample);
		if (serchResult != null) {
			result.setIsLikePicture('y');
		} else {
			result.setIsLikePicture('n');
		}

		result.setCode(REQUEST_OK);
		result.setResult(dtoWrapper);
		return result;
	}

	// 0 ����, -1 ����(�Ⱦ�), -2 ��й�ȣ ����, -3 ���̵����
	@Override
	public LoginVO login(String email, String password) {
		RegisterMemberDTO member = dao.getMemberFromEmail(email);
		LoginVO result = new LoginVO();

		if (member == null) {
			result.setCode(NOT_FOUND_ID);
			return result;
		}
		if (!password.equals(member.getPassword())) {
			result.setCode(PASSWARD_ERROR);
			return result;
		}
		result.setCode(LOGIN_OK);
		result.setMember_id(member.getMember_id());
		return result;
	}

	@Override
	public int addLikePicture(LikePictureDTO dto) {
		// ���� ���ƿ� �� ����� �ִ��� Ȯ���ϱ�
		LikePictureDTO getData = dao.serchLikePicture(dto);
		// ������ �������
		if (getData == null) {
			// DB�� �����Է�
			int result = dao.addLikePicture(dto);
			// DB ���� �߻���
			if (result < 0)
				return ERROR_DATABASE;
			return REQUEST_OK;
		}
		// ������ ������� ���ƿ� ���� ����
		else {
			// DB�� �����Է�
			int result = dao.deleteLikePicture(dto);
			// DB ���� �߻���
			if (result < 0)
				return ERROR_DATABASE;
			return REQUEST_OK;

		}
	}

	// ��� id�� �Է¹ް� �ش� ������ �����ϴ� ���� ������ ���� ��������

	@Override
	public ShowPictureResultVO showLikePictureList(int member_id) {
		ShowPictureResultVO result = new ShowPictureResultVO();
		// 1. �ش� ������ ���ƿ� ���� ���� ��������
		List<LikePictureDTO> likeList = dao.getLike(member_id);
		if (likeList == null || likeList.size() == 0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		// 2. ���ƿ��� ���� ���� ��������
		// private List<PictureDTO> result;
		List<PictureDTO> picArr = new ArrayList<PictureDTO>();
		for (LikePictureDTO d : likeList) {
			// ���� 1�� id�� ���� ��������
			PictureDTO p = dao.getPicture(d.getPicture_id());
			if (p != null) {
				picArr.add(p);
			}
		}

		if (picArr.size() == 0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		result.setCode(REQUEST_OK);
		result.setResult(picArr);
		return result;
	}

	@Override
	public int addReply(int member_id, int picture_id, String reply_text) {
		ReplyDTO dto = new ReplyDTO();
		dto.setMember_id(member_id);
		dto.setPicture_id(picture_id);
		dto.setReply_text(reply_text);

		// DB�� �����Է�
		int result = dao.addReply(dto);
		// DB ���� �߻���
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}

	// ��� �����
	// ��� id�� �ۼ������� Ȯ���ϰ� �ۼ��ڰ� ������ �׶� ������
	// ����:0 / ���� -1 / ���Ѿ��� -3
	@Override
	public int deleteReply(int member_id, int reply_id) {
		ReplyDTO sample = new ReplyDTO();
		sample.setMember_id(member_id);
		sample.setReply_id(reply_id);
		// 1. ���� Ȯ���ϱ� NO_PERMISSOIN
		ReplyDTO d = dao.serchReply(sample);
		if (d == null) {
			return NO_PERMISSOIN;
		}
		int result = dao.deleteReply(reply_id);
		// DB ���� �߻���
		if (result < 0)
			return ERROR_DATABASE;
		return REQUEST_OK;
	}
	// <!-- ���� 1���� ��� ���� ��������-->
	// ��� id�� �ش� ������ �г����� �������µ�, �ڱ��ڽ��� �ۼ��ߴ��� ���θ� Ȯ���ϴ°��� ����

	@Override
	public ShowReplyResultVO getReply(int member_id, int picture_id) {
		ShowReplyResultVO result = new ShowReplyResultVO();
		// 1. ��ۺ��� ���� ��������
		List<ReplyDTO> list = dao.getReply(picture_id);
		// �˻� ����� 0�ϰ��
		if (list == null || list.size() == 0) {
			result.setCode(NOT_FOUND_DATA);
			return result;
		}
		// 2. �г��� ��������
		String nick = dao.getNickNameFromId(member_id);
		List<ReplyDTOWrapper> wList = new ArrayList<ReplyDTOWrapper>();
		for(ReplyDTO d : list) {
			// 2. �ڱ��ڽ��� ����� ���� Ȯ���ϱ�
			char isMyReply = 'n';
			if(d.getMember_id()==member_id) {
				isMyReply='y';
			}
			ReplyDTOWrapper w = new ReplyDTOWrapper(d);
			w.setNickName(nick);
			w.setIsMyReply(isMyReply);
			wList.add(w);
		}
		result.setNickName(nick);
		result.setCode(REQUEST_OK);
		result.setResult(wList);
		return result;
	}
	
	
	@Override
	public int deletePicData(int member_id, int picture_id) {
		PictureDTO pic = dao.getPicture(picture_id);
		if (pic == null) {
			return NOT_FOUND_DATA;
		}
		else if(pic.getP_member_id()!=member_id) {
			return NO_PERMISSOIN;
		}
		int result = dao.deletePicData(picture_id);
		if(result<0)
			return ERROR_DATABASE;
		else 
			return REQUEST_OK;
	}
	
}
