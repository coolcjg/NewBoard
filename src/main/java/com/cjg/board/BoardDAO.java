package com.cjg.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cjg.vo.BoardVO;

@Repository("boardDAO")
public class BoardDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	public List<BoardVO> list(){
		return sqlSession.selectList("mapper.board.list");
	}
	
	public int create(BoardVO boardVO) throws Exception{
		return sqlSession.insert("mapper.board.create", boardVO);
	}
	
	public BoardVO read(int articleNO) throws Exception{
		return sqlSession.selectOne("mapper.board.read", articleNO);
	}
	
	public int mod(BoardVO boardVO) throws Exception{
		return sqlSession.update("mapper.board.mod", boardVO);
	}

}
