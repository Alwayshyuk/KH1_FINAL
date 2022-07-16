package com.an.auctionara.service;

import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.an.auctionara.entity.MemberDto;
import com.an.auctionara.repository.AttachmentDao;
import com.an.auctionara.repository.AuctionDao;
import com.an.auctionara.repository.GpsAddressDao;
import com.an.auctionara.repository.MemberDao;
import com.an.auctionara.vo.MyAuctionVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private GpsAddressDao gpsAddressDao;
	
	@Autowired
	private AttachmentDao attachmentDao;
	
	
	@Autowired
	private AuctionDao auctionDao;
	
	//첨부파일(프로필) 없이 구현함 이후 추가 구현 필요
	@Override
	@Transactional
	public void join(MemberDto memberDto, MultipartFile attachment) throws IllegalStateException, IOException {
		
		System.out.println(attachment);
		
		//프로필 등록코드
		if(!attachment.isEmpty())	{
			int attachmentNo = attachmentDao.save(attachment);
			memberDto.setAttachmentNo(attachmentNo);
			memberDao.join(memberDto);
		}
		else {
			int attachmentNo = 153;
			memberDto.setAttachmentNo(attachmentNo);
			memberDao.join(memberDto);
		}
		
		
		
	}

	@Scheduled(cron = "0 0 12 * * *") // 매일 오후 12시마다 인증 후 30일이 지난 주소 미인증 처리
	@Override
	public void changeGpsStaus() {
		Date limit = new java.sql.Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 30L));
		gpsAddressDao.changeGpsStaus(limit);
	}

	@Override
	public boolean info(MemberDto memberDto, MultipartFile attachment) throws IllegalStateException, IOException {
		
		if(memberDto == null) return false;
		
		else {
			//프로필 등록코드
			if(!attachment.isEmpty())	{
				int attachmentNo = attachmentDao.save(attachment);
				memberDto.setAttachmentNo(attachmentNo);
				memberDao.info(memberDto);
			}
			else {
				int existingNo = memberDao.recall(memberDto.getMemberEmail());

				memberDto.setAttachmentNo(existingNo);
				memberDao.info(memberDto);
			}
			
			return true;
		}

	}

	@Override
	public List<MyAuctionVO> list(int auctioneerNo, int page, Integer filter, Integer sort, Integer categoryNo, String keyword) {
		Map<String, Object> info = new HashMap<>();
		info.put("auctioneerNo", auctioneerNo);
		info.put("begin", (page * 10) - (10 - 1)); // 10개씩 불러오기
		info.put("end", page * 10);
		info.put("filter", filter);
		info.put("sort", sort);
		info.put("categoryNo", categoryNo);
		info.put("keyword", keyword);
		return auctionDao.myAuction(info);
	}
}
