package com.cjg.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {
	
	@Autowired
	FileDAO fileDAO;
	
	public int maxArticleNO() throws Exception{
		return fileDAO.maxArticleNO();
		
	};

}
