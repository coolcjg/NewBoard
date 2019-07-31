package com.cjg.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjg.vo.BoardVO;

@Service("boardService")
public class BoardService {
	
	@Autowired
	BoardDAO boardDAO;
	
	
	public  List<BoardVO> list() throws Exception{
		return boardDAO.list();
		
	}
	
	public int create(BoardVO boardVO) throws Exception{
		return boardDAO.create(boardVO);
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
	
}
