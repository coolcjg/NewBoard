package com.cjg.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjg.vo.MemberVO;

@Service("memberService")
public class MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	MemberVO member;

	
	public List<MemberVO> memberList() throws Exception{
		List<MemberVO> list = memberDAO.memberList(); 	
		return list;
	}
	
	public int addMember(MemberVO member) throws Exception{
		return memberDAO.addMember(member);
	}
	
	public MemberVO login(MemberVO member) throws Exception{
		return memberDAO.login(member);
	}
	
	public int mod(MemberVO member) throws Exception{
		return memberDAO.mod(member);
	}
	
	public int delete(String id) throws Exception{
		return memberDAO.delete(id);
	}
	

}
