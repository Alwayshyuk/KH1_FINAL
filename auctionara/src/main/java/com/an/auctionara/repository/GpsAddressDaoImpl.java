package com.an.auctionara.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.an.auctionara.entity.GpsAddressDto;

@Repository
public class GpsAddressDaoImpl implements GpsAddressDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void insert(GpsAddressDto gpsAddressDto) {
		int gpsNo = sqlSession.selectOne("gps_address.sequence");
		sqlSession.insert("gps_address.insert", GpsAddressDto.builder() 
										.gpsNo(gpsNo) 
										.memberNo(gpsAddressDto.getMemberNo())
										.gpsLatitude(gpsAddressDto.getGpsLatitude()) 
										.gpsLongitude(gpsAddressDto.getGpsLongitude()) 
										.gpsCircle(gpsAddressDto.getGpsCircle()) 
										.build());
	}

	@Override
	public List<GpsAddressDto> list(int memberNo) {
		List<GpsAddressDto> list = sqlSession.selectList("gps_address.list", memberNo);
		return list;
	}

	@Override
	public void change1(GpsAddressDto gpsAddressDto) {
		GpsAddressDto address = sqlSession.selectOne("gps_address.one1", gpsAddressDto.getMemberNo());
		sqlSession.update("gps_address.update", GpsAddressDto.builder() 
				.gpsNo(address.getGpsNo()) 
				.gpsLatitude(gpsAddressDto.getGpsLatitude()) 
				.gpsLongitude(gpsAddressDto.getGpsLongitude()) 
				.gpsCircle(gpsAddressDto.getGpsCircle()) 
				.build());		
	}

	@Override
	public void change2(GpsAddressDto gpsAddressDto) {
		GpsAddressDto address = sqlSession.selectOne("gps_address.one2", gpsAddressDto.getMemberNo());
		sqlSession.update("gps_address.update", GpsAddressDto.builder() 
				.gpsNo(address.getGpsNo()) 
				.gpsLatitude(gpsAddressDto.getGpsLatitude()) 
				.gpsLongitude(gpsAddressDto.getGpsLongitude()) 
				.gpsCircle(gpsAddressDto.getGpsCircle()) 
				.build());	
	}

	@Override
	public GpsAddressDto one1(int memberNo) {
		return sqlSession.selectOne("gps_address.one1", memberNo);
	}
}
