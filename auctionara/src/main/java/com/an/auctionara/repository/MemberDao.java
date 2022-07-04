package com.an.auctionara.repository;

import java.util.List;

import com.an.auctionara.entity.MemberDto;

public interface MemberDao {
	
	void join(MemberDto memberDto);

	MemberDto login(String memberEmail, String memberPw);
	
	MemberDto memberSearch(int memberNo);

	int checkEmail(String memberEmail);

	void plusRedCount(int memberNo);

	// 관리자 - 회원 목록 조회 메소드 
	List<MemberDto> list(String type, String keyword, int p, int s);

	// 관리자 - 회원 목록 페이징을 위한 count 메소드 
	int count(String type, String keyword);
	
}
