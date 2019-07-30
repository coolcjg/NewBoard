package com.cjg.member;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cjg.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@Log4j
@Controller("memberController")
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberVO member;
		
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public ModelAndView memberList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 
		List memberlist = memberService.memberList();
		ModelAndView mav = new ModelAndView("memberlist");
		mav.addObject("list", memberlist);
		return mav;	
	}
	
	@RequestMapping(value="/joinForm.do", method=RequestMethod.GET)
	public ModelAndView join(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("join");
		return mav;
	}
	
	@RequestMapping(value="/joinprocess.do", method=RequestMethod.POST)
	public ModelAndView joinprocess(MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) throws Exception{
		multipartRequest.setCharacterEncoding("UTF-8");
		Map<String, Object> memberMap = new HashMap<String, Object>();
		Enumeration enu = multipartRequest.getParameterNames();
		
		while(enu.hasMoreElements()){
			String name = (String)enu.nextElement();
			String value = (String)multipartRequest.getParameter(name);
			memberMap.put(name, value);
		}
		
		member.setId((String)memberMap.get("id"));
		member.setPwd((String)memberMap.get("pwd"));
		member.setName((String)memberMap.get("name"));
		member.setEmail((String)memberMap.get("email"));
		
		log.info(member.toString());
		
		int result=0;
		result = memberService.addMember(member);
		
		System.out.println(result);
		
		ModelAndView mav = new ModelAndView("redirect:/member/list.do");
		return mav;

	}
	
	@RequestMapping(value="/loginForm.do", method=RequestMethod.GET)
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		
		session.setAttribute("redirectURI", request.getHeader("Referer"));
		
		return new ModelAndView("login");
	}
	
	@RequestMapping(value="/loginProcess.do", method=RequestMethod.GET)
	public ModelAndView login(MemberVO member1, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		ModelAndView mav = new ModelAndView();
		member = memberService.login(member1);
		
		String returnURI = (String)request.getSession().getAttribute("redirectURI");
		System.out.println(returnURI);
				
		if(member !=null){
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			session.setAttribute("isLogOn", true);
			mav.setViewName("redirect:"+returnURI);
		}else{
			mav.setViewName("redirect:/member/list.do");
		}
		return mav;
	}

	
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		session.invalidate();
		
		ResponseEntity<String> res = null;
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/html; charset=utf-8");
		
		String msg = "<script>location.href=document.referrer;</script>";
		
		res = new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
		return res;
	}
	
	
	@RequestMapping(value="/modForm.do", method=RequestMethod.GET)
	public ModelAndView modForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView("memberModForm");
		return mav;
	}
	
	
	@RequestMapping(value="/modProcess.do", method=RequestMethod.POST)
	public ResponseEntity<String> modProcess(MultipartHttpServletRequest multipartRequest, MemberVO member1, HttpServletRequest request, HttpServletResponse response) throws Exception{

		HttpSession session = multipartRequest.getSession();

		member = (MemberVO)session.getAttribute("member");
		member1.setId(member.getId());
		
		int result = memberService.mod(member1);
		
		ResponseEntity<String> res=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		String msg;
		
		if(result == 1){
			
			msg ="<script>";
			msg +="alert('회원정보가 수정되었습니다');";
			msg +="location.href='"+request.getContextPath()+"/member/list.do';";
			msg +="</script>";
			
			res = new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);

		}else{
			
			msg ="<script>";
			msg +="alert('오류');";
			msg +="location.href='"+request.getContextPath()+"/member/list.do';";
			msg +="</script>";
			
			res = new ResponseEntity<String>(msg, responseHeaders, HttpStatus.OK);
		}
		return res;
	}
	
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public ResponseEntity<String> delete(HttpServletRequest request, HttpServletResponse response) throws Exception{
		HttpSession session = request.getSession();
		
		member = (MemberVO)session.getAttribute("member");
		String id = member.getId();
		
		int result = memberService.delete(id);
		
		ResponseEntity<String> res = null;
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/html; charset=utf-8");
		String msg;
		
		
		if(result==1){
			session.removeAttribute("member");
			session.removeAttribute("isLogOn");
			msg="<script>";
			msg+="alert('회원탈퇴 완료');";
			msg+="location.href='"+request.getContextPath()+"/member/list.do';";
			msg+="</script>";
			
			
			
		}else{
			msg="<script>";
			msg+="alert('다시 시도하세요.');";
			msg+="</script>";
		}
		
		res = new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
		return res;
		
	}
}
