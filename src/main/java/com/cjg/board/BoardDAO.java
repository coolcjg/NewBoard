package com.cjg.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cjg.vo.BoardVO;
import com.cjg.vo.PageVO;
import com.cjg.vo.UploadFileVO;

@Repository("boardDAO")
public class BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	public List<BoardVO> list(){
		return sqlSession.selectList("mapper.board.list");
	}
	
	public List<BoardVO> list(PageVO pageVO){
		return sqlSession.selectList("mapper.board.listPaging", pageVO);
	}	
	
	public int create(BoardVO boardVO) throws Exception{
		return sqlSession.insert("mapper.board.create", boardVO);
	}
	
	@Transactional
	public int create(Map<String, Object> map, BoardVO boardVO) throws Exception{
		
		int result = sqlSession.insert("mapper.board.createByMap", map);
		
		List<UploadFileVO> list = boardVO.getFileList();
		
		if(list!=null)
		{
			list.forEach(uploadFile->{
				
				int articleNO = sqlSession.selectOne("mapper.board.maxArticleNO");
				
				uploadFile.setArticleNO(articleNO);

				sqlSession.insert("mapper.file.insert", uploadFile);
			});
		}
		
		return result;
	}
	
	public BoardVO read(int articleNO) throws Exception{
		return sqlSession.selectOne("mapper.board.read", articleNO);
	}
	
	public int mod(BoardVO boardVO) throws Exception{
		int result = sqlSession.update("mapper.board.mod", boardVO);
		
		int articleNO = boardVO.getArticleNO();
		
		sqlSession.delete("mapper.file.deleteAll", articleNO );
				
		List<UploadFileVO> fileList = boardVO.getFileList();
		
		if(fileList != null){
			fileList.forEach(file->{
				file.setArticleNO(articleNO);
				sqlSession.insert("mapper.file.insert", file);
			});
		}
		
		return result;
	}
	
	@Transactional
	public int del(int articleNO) throws Exception{
		
		int result1 = sqlSession.delete("mapper.file.deleteAll", articleNO);
		int result2 = sqlSession.delete("mapper.board.del", articleNO);
		
		return result2;

	}
	
	public int count(PageVO pageVO) throws Exception{
	
		return (int)sqlSession.selectOne("mapper.board.count", pageVO);
	}

}
