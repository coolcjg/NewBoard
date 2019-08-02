package com.cjg.file;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cjg.vo.UploadFileVO;

@Controller
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	UploadFileVO uploadFileVO;
	
	@Autowired
	FileService fileService;
	
	@RequestMapping(value="/fileUpload.do", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<UploadFileVO>> fileUpload(MultipartFile[] uploadFile, HttpServletResponse response) throws Exception{
		
		
		//게시판 새 글 번호 가져오기.
		int newArticleNO = fileService.maxArticleNO();
		newArticleNO++;
		if((Object)newArticleNO==null)
			newArticleNO =1;
		String stringNewArticleNO = File.separator+String.valueOf(newArticleNO);
		
		List<UploadFileVO> list = new ArrayList<>();
		
		//첨부파일 올라갈 경로 지정
		String basePath = "C:\\upload";
		String directoryPath = makeFolder();
		directoryPath+=stringNewArticleNO;
		File uploadPath = new File(basePath, directoryPath);
		
		//경로 생성
		if(!uploadPath.exists())
			uploadPath.mkdirs();
		
		
		for(MultipartFile multipartFile : uploadFile){
			
			UploadFileVO uploadfile = new UploadFileVO();
			
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
	
	
	//이미지보여주기
	@RequestMapping(value="/showImage.do", method=RequestMethod.GET)
	public ResponseEntity<byte[]> showImage(String path){
			System.out.println("path : " + path);
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
	
	//파일삭제
	@RequestMapping(value="/deleteFile.do", method=RequestMethod.POST)
	public ResponseEntity<String> deleteFile(String targetFile){
		File file;
		
		try{
			String decodedTargetFile = URLDecoder.decode(targetFile, "UTF-8");
			
			file = new File(decodedTargetFile);
			file.delete();
			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
	//파일리스트보여주기
	@RequestMapping(value="/list.do", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<UploadFileVO>> list(int articleNO) throws Exception{
		List<UploadFileVO> list = fileService.list(articleNO);
		return new ResponseEntity(list, HttpStatus.OK);
	}

	//파일다운로드
	@RequestMapping(value="/download.do", method=RequestMethod.GET, produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> download(String path) throws Exception{
		
		Resource resource = new FileSystemResource(path);
		
		if(resource.exists() == false){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String fileName = resource.getFilename();
		
		
		
		String downloadName = fileName.substring(fileName.indexOf("_")+1);
		
		
		//다운로드파일이름이 이상하게 나오지 않게 바이트코드로 바꿔준다.
		String newDownloadName = new String(downloadName.getBytes("UTF-8"), "ISO-8859-1");
		
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Content-Disposition", "attachment; filename="+newDownloadName);
		
		
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);		
		
	}
	
	
	
	
	

	
	private String makeFolder(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//date를 util에 있는것을 해야지 생성된다. sql에 있는걸로 임포트하면 안됨.
		Date date = new Date();
		
		String dateString = sdf.format(date);
		
		return dateString.replace("-", File.separator);
		
	}
	
	
	
	

}
