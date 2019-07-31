package com.cjg.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cjg.vo.BoardVO;
import com.cjg.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller("boardController")
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberVO member;
	
	@Autowired
	BoardVO boardVO; 

	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<BoardVO> list = boardService.list();
		
		ModelAndView mav = new ModelAndView("boardlist");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value="/create.do", method=RequestMethod.GET)
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception{
				
		ModelAndView mav = new ModelAndView("boardForm");
		
		String stringParentNO =  request.getParameter("parentNO");
		int parentNO;
		
		if(stringParentNO != null)
			parentNO = Integer.parseInt(stringParentNO);
		else
			parentNO = 0;
		
		mav.addObject("parentNO", parentNO);
				
		return mav;
	}
	
	@RequestMapping(value="/createProcess.do", method=RequestMethod.POST)
	public ResponseEntity<String> createProcess(HttpServletRequest request, HttpServletResponse response) throws Exception{

		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		member = (MemberVO)session.getAttribute("member");
			
		int parentNO = Integer.parseInt(request.getParameter("parentNO"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String id = member.getId();

		boardVO.setParentNO(parentNO);
		boardVO.setTitle(title);		
		boardVO.setContent(content);
		boardVO.setId(id);

		int result = boardService.create(boardVO);
		
		ResponseEntity<String> res = null;
		String msg;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=utf-8");		
		
		if(result ==1){
			msg="<script>";
			msg+="alert('글작성 완료');";
			msg+="location.href='list.do';";
			msg+="</script>";
			
			res = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}else{
			msg="<script>";
			msg+="alert('글작성 에러');";
			msg+="location.href='list.do';";
			msg+="</script>";
			
			res = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}
		return res;
	}
	
	@RequestMapping(value="/read/{articleNO}", method=RequestMethod.GET)
	public ModelAndView read(@PathVariable int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		boardVO = boardService.read(articleNO);
		
		ModelAndView mav = new ModelAndView("read");
		mav.addObject("boardVO", boardVO);
		
		return mav;
	}
	
	@RequestMapping(value="/mod/{articleNO}", method=RequestMethod.GET)
	public ModelAndView mod(@PathVariable int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		boardVO = boardService.read(articleNO);
		
		ModelAndView mav = new ModelAndView("mod");
		mav.addObject("boardVO", boardVO);
		
		return mav;
	}
	
	@RequestMapping(value="/modProcess.do", method=RequestMethod.POST)
	public ModelAndView modProcess(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		
		int articleNO = Integer.parseInt(request.getParameter("articleNO"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		boardVO.setArticleNO(articleNO);
		boardVO.setTitle(title);
		boardVO.setContent(content);
		
		int result = boardService.mod(boardVO);
		
		if(result ==1){
			ModelAndView mav = new ModelAndView("redirect:/board/list.do");
			return mav;
			
		}else{
			ModelAndView mav = new ModelAndView("redirect:/board/list.do");
			return mav;
		}
	}
	
	@RequestMapping(value="del/{articleNO}", method=RequestMethod.GET)
	public ModelAndView del(@PathVariable int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		 
		boardService.del(articleNO);
		
		ModelAndView mav = new ModelAndView("redirect:/board/list.do");
		
		return mav;

	}
	

	
	
	

}
