package com.server.pica.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.server.pica.dto.CreateAlbumDTO;
import com.server.pica.dto.MyAlbumDTO;
import com.server.pica.dto.PicUploadDTO;
import com.server.pica.dto.PictureDTO;
import com.server.pica.dto.PictureDTOWrapper;
import com.server.pica.dto.ShowPictureDataResultVO;
import com.server.pica.dto.ShowPictureResultVO;

public class FileUtil {
	private static final String IMAGE_SRC = "/resource";
	private static final String IMAGE_PATH = IMAGE_SRC + "/";

	// ������ �����ϰ� ������ ������ DTO�� ��� ����
	public static PicUploadDTO saveFile(MultipartFile file, PicUploadDTO dto, String uploadPath) {
		// TODO : ȿ������ �α� ������� ����� �ʿ䰡 ����
		File dir = new File(uploadPath);
		// ������ �������� �������
		if (!dir.exists()) {
			// ���� ����
			if (!dir.mkdirs()) {
				// ���� �������� ���� �߻��� �α� �����
			}
		}

		String fileFullStr = file.getOriginalFilename();
		String fileExt = getFileType(fileFullStr);
		String saveName = fileNameAtDate(fileExt);
		
		// logger.info("saveName: {}",saveName);
		System.out.println("saveName: {" + saveName + "}");

		// ������ File ��ü�� ����(������ ����)��
		File saveFile = new File(uploadPath, saveName); // ������ ���� �̸�, ������ ���� �̸�

		try {
			file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
		} catch (IOException e) {
			// ���� �α� �����
			e.printStackTrace();
			return null;
		}
		dto.setFile(saveName);
		return dto;
	}

	// Ǯ Ȯ������ ���ϸ��� �Է¹޾Ƽ� ���� Ȯ���ڸ� �����ϱ�
	// 1234.jpg -> .jpg
	public static String getFileType(String strFileName) {
		int pos = strFileName.lastIndexOf(".");
		String ext = strFileName.substring(pos);
		return ext;
	}
	// ����Ȯ���ڸ� �Է¹޾Ƽ� ��¥���� �����̸� ������ֱ�
	// ext = .jpg => ���� : 20200629112559.jpg
	public static String fileNameAtDate(String ext) {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyyMMddHHmmss");
		String format_time1 = format1.format (System.currentTimeMillis());
		return format_time1+ext;
	}

	// ������ �����ϰ� ���� ���� ������
	public static String saveFile(MultipartFile file, String uploadPath) {
		// TODO : ȿ������ �α� ������� ����� �ʿ䰡 ����
		File dir = new File(uploadPath);
		// ������ �������� �������
		if (!dir.exists()) {
			// ���� ����
			if (!dir.mkdirs()) {
				// ���� �������� ���� �߻��� �α� �����
			}
		}
		String saveName = file.getOriginalFilename();

		// logger.info("saveName: {}",saveName);
		System.out.println("saveName: {" + saveName + "}");

		// ������ File ��ü�� ����(������ ����)��
		File saveFile = new File(uploadPath, saveName); // ������ ���� �̸�, ������ ���� �̸�

		try {
			file.transferTo(saveFile); // ���ε� ���Ͽ� saveFile�̶�� ������ ����
		} catch (IOException e) {
			// ���� �α� �����
			e.printStackTrace();
			return null;
		}
		return saveName;
	}

	// �ٹ� DTO�� ����Ʈ�� �Է¹޾� �ش� ����Ʈ�� DTO ���� ��ο� ���� �ּҸ� �߰��ؼ� �����Ѵ�.
	public static List<MyAlbumDTO> insertServerUrlInImages(List<MyAlbumDTO> list, HttpServletRequest request) {
		if (list == null)
			return list;
		for (MyAlbumDTO e : list) {
			if (e.getDefaultPicture() == null)
				continue;
			e.setDefaultPicture(parseImageSrc(e.getDefaultPicture(), request));
		}
		return list;
	}

	// ���� DTO 1���� �Է¹޾� �ش� ����Ʈ�� DTO ���� ��ο� ���� �ּҸ� �߰��ؼ� �����Ѵ�.
	public static ShowPictureDataResultVO insertServerUrlInImages(ShowPictureDataResultVO data,
			HttpServletRequest request) {
		PictureDTOWrapper dto = data.getResult();
		if (dto == null)
			return data;
		dto.setFile(parseImageSrc(dto.getFile(), request));
		data.setResult(dto);
		return data;
	}

	// �ٹ� DTO�� ����Ʈ�� �Է¹޾� �ش� ����Ʈ�� DTO ���� ��ο� ���� �ּҸ� �߰��ؼ� �����Ѵ�.
	public static ShowPictureResultVO insertServerUrlInImages(ShowPictureResultVO vo, HttpServletRequest request) {
		List<PictureDTO> list = vo.getResult();
		if (list == null)
			return vo;
		for (PictureDTO e : list) {
			if (e.getFile() == null)
				continue;
			e.setFile(parseImageSrc(e.getFile(), request));
		}
		vo.setResult(list);
		return vo;
	}

	// ���� �̸��� �Է¹޾Ƽ� ���������+���� �̸����� �� ������� �����ϱ�
	public static String parseImageSrc(String fileName, HttpServletRequest request) {
		String url = request.getRequestURL().toString().replace(request.getRequestURI(), "");
		url = url + request.getContextPath() + IMAGE_PATH;
		return url + fileName;
	}

	// HttpServletRequest�� �Է¹޾� ���� ������ �����ϱ�
	public static String getFileSavePath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath(IMAGE_SRC);
	}
}
