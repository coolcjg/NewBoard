package com.cjg.member;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cjg.vo.MemberVO;


@Repository("memberDAO")
public class MemberDAO {
	
	@Autowired
	SqlSession sqlSession;
	
	
	
	public List memberList() throws Exception{
		 List memberlist = sqlSession.selectList("mapper.member.memberList");
		return memberlist;
	}
	
	public int addMember(MemberVO member) throws Exception{
		return sqlSession.insert("mapper.member.addMember", member);
	}
	
	public MemberVO login(MemberVO member) throws Exception{
		return sqlSession.selectOne("mapper.member.login",member);
	}
	
	public int mod(MemberVO member) throws Exception{
		return sqlSession.update("mapper.member.mod", member);
	}
	
	public int delete(String id) throws Exception{
		return sqlSession.delete("mapper.member.delete", id);
	}
	

}
