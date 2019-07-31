package com.cjg.file;


import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.cjg.vo.uploadFileVO;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="/fileUpload.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<uploadFileVO>> fileUpload(MultipartFile[] uploadFile, HttpServletResponse response) throws Exception{
		
		
		//게시판 새 글 번호 가져오기.
		int newArticleNO = fileService.maxArticleNO();
		newArticleNO++;
		if((Object)newArticleNO==null)
			newArticleNO =1;
		String stringNewArticleNO = File.separator+String.valueOf(newArticleNO);
		
		List<uploadFileVO> list = new ArrayList<>();
		
		//첨부파일 올라갈 경로 지정
		String basePath = "C:\\upload";
		String directoryPath = makeFolder();
		directoryPath+=stringNewArticleNO;
		File uploadPath = new File(basePath, directoryPath);
		
		//경로 생성
		if(!uploadPath.exists())
			uploadPath.mkdirs();
		
		
		for(MultipartFile multipartFile : uploadFile){
			
			uploadFileVO uploadfile = new uploadFileVO();
			
			String originalFileName = multipartFile.getOriginalFilename();
			String uploadFileName = originalFileName.substring(originalFileName.lastIndexOf("\\")+1); 
			String fileType = multipartFile.getContentType();
			UUID uuid = UUID.randomUUID();
			
			
			try{
				File saveFile = new File(uploadPath, uuid.toString()+"_"+uploadFileName);
				multipartFile.transferTo(saveFile);
				
			}catch(Exception e){
				e.getMessage();
			}
			
			
			uploadfile.setArticleNO(newArticleNO);
			uploadfile.setFileName(uploadFileName);
			uploadfile.setFileType(fileType);
			uploadfile.setUploadPath(uploadPath.toString());
			uploadfile.setUuid(uuid.toString());
			
			list.add(uploadfile);
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
		

	}
	
	@RequestMapping(value="/showImage.do", method=RequestMethod.GET)
	public ResponseEntity<byte[]> showImage(String path){
			System.out.println("a" + path);
			File file = new File(path);
			byte[] byteArray=null;
			String type=null;
			
			try{
			byteArray = FileCopyUtils.copyToByteArray(file);
			type=Files.probeContentType(file.toPath());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			HttpHeaders header = new HttpHeaders();
			
			
			header.add("Content-Type", type);
	

		
		ResponseEntity<byte[]> res = new ResponseEntity<>(byteArray, header, HttpStatus.OK);
		
		return res;
		
		
	}
	
	
	
	
	
	private String makeFolder(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//util에 있는것을 해야지 생성된다. sql에 있는걸로 임포트하면 안됨.
		Date date = new Date();
		
		String dateString = sdf.format(date);
		
		return dateString.replace("-", File.separator);
		
	}
	
	
	
	

}
