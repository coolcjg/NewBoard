package com.cjg.board;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cjg.vo.BoardVO;
import com.cjg.vo.MemberVO;
import com.cjg.vo.PageVO;
import com.cjg.vo.UploadFileVO;

@Controller("boardController")
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	MemberVO member;
	
	@Autowired
	BoardVO boardVO; 
	
	@Autowired
	UploadFileVO uploadFileVO; 
	
	@Autowired
	PageVO pageVO;

	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<BoardVO> list = boardService.list();
		
		ModelAndView mav = new ModelAndView("boardlist");
		mav.addObject("list", list);
		return mav;
	}
	
	@RequestMapping(value="/listPaging.do", method=RequestMethod.GET)
	public ModelAndView listPaging(PageVO pageVO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<BoardVO> list = boardService.list(pageVO);
		int count = boardService.count(pageVO); 
		pageVO.setTotal(count);
		
		
		//첫페이지,마지막페이지 계산함수
		pageVO.process();
		
		ModelAndView mav = new ModelAndView("boardlist");
		mav.addObject("list", list);
		mav.addObject("pageVO", pageVO);
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
	public ResponseEntity<String> createProcess(BoardVO boardVO, MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{

		multipartRequest.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration enu =multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements()){
			String parameterName = (String)enu.nextElement();
			map.put(parameterName, multipartRequest.getParameter(parameterName));
		}
		
		HttpSession session = multipartRequest.getSession();
		member = (MemberVO)session.getAttribute("member");
		
		map.put("id", member.getId());
		
		int result = boardService.create(map, boardVO);

		ResponseEntity<String> res = null;
		String msg;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=utf-8");		
		
		if(result ==1){
			msg="<script>";
			msg+="alert('글작성 완료');";
			msg+="location.href='listPaging.do';";
			msg+="</script>";
			
			res = new ResponseEntity<String>(msg, headers, HttpStatus.OK);
		}else{
			msg="<script>";
			msg+="alert('글작성 에러');";
			msg+="location.href='listPaging.do';";
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
	public ModelAndView modProcess(BoardVO boardVO, MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("utf-8");
		
		int result = boardService.mod(boardVO);

		int articleNO = boardVO.getArticleNO();
		ModelAndView mav = new ModelAndView("redirect:/board/read/"+articleNO);
		return mav;
		
		
	}
	
	@RequestMapping(value="del/{articleNO}", method=RequestMethod.GET)
	public ModelAndView del(@PathVariable int articleNO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		boardService.del(articleNO);
		
		ModelAndView mav = new ModelAndView("redirect:/board/listPaging.do");
		
		return mav;
	}
}
