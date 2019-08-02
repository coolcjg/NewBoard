package com.cjg.board;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjg.vo.BoardVO;
import com.cjg.vo.PageVO;

@Service("boardService")
public class BoardService {
	
	@Autowired
	BoardDAO boardDAO;
	
	
	public  List<BoardVO> list() throws Exception{
		return boardDAO.list();
		
	}
	
	public  List<BoardVO> list(PageVO pageVO) throws Exception{
		return boardDAO.list(pageVO);
		
	}
	
	public int create(BoardVO boardVO) throws Exception{
		return boardDAO.create(boardVO);
	}
	
	public int create(Map<String, Object> map, BoardVO boardVO)throws Exception{
		return boardDAO.create(map, boardVO);
	}
	
	public BoardVO read(int articleNO) throws Exception{
		return boardDAO.read(articleNO);
	}
	
	public int mod(BoardVO boardVO) throws Exception{
		return boardDAO.mod(boardVO);
	}
	
	public int del(int articleNO) throws Exception{
		return boardDAO.del(articleNO);
	}
	 
	public int count(PageVO pageVO) throws Exception{
		return boardDAO.count(pageVO);
	}
	
}
