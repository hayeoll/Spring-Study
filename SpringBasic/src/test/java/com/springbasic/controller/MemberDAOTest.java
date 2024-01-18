package com.springbasic.controller;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springbasic.persistence.MemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class MemberDAOTest {
	
	@Inject
//	@Autowired(required=false)
	private MemberDAO dao;
	
	@Test
	public void getDateTimeTest() {
		System.out.println("현재 시각");
		System.out.println("현재 시각"+dao.getDate());
	}

}
