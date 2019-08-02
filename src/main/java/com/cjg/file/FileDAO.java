package com.cjg.file;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cjg.vo.UploadFileVO;

@Repository
public class FileDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	
	public int maxArticleNO() throws Exception{
		
		return (int)sqlSession.selectOne("mapper.file.maxArticleNO");
		
	}
	
	public List<UploadFileVO> list(int articleNO) throws Exception{
		return sqlSession.selectList("mapper.file.list", articleNO);
	}
	
	public UploadFileVO download(String fileName) throws Exception{
		return sqlSession.selectOne("mapper.file.download", fileName);
	}

}
