package com.cjg.file;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	
	public int maxArticleNO() throws Exception{
		
		return (int)sqlSession.selectOne("mapper.file.maxArticleNO");
		
	}

}
