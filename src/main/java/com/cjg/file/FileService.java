package com.cjg.file;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjg.vo.UploadFileVO;

@Service
public class FileService {
	
	@Autowired
	FileDAO fileDAO;
	
	public int maxArticleNO() throws Exception{
		return fileDAO.maxArticleNO();
		
	};
	
	public List<UploadFileVO> list(int articleNO) throws Exception{
		return fileDAO.list(articleNO);
	}
	
	public UploadFileVO download(String fileName) throws Exception{
		return fileDAO.download(fileName);
	}

}
