package com.springbasic.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springbasic.vo.Student;

@Controller // 현재 클래스가 컨트롤러 단임을 명시
public class HomeController {
	
	// logger : 로그를 남길 수 있도록 하는 객체
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// "/"가 GET방식으로 요청되면, 아래의 home메서드를 호출한다.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate ); // 바인딩
		
		return "home"; // view Resolver에게 "home"이라는 문자열 반환
		// "/WEB-INF/views/ + home +.jsp 리졸버가 앞뒤로 문자열 붙여서 경로 완성시켜줌
		// 최종 DispatcherServlet에 의해서 response됨.
	}
	
	@RequestMapping(value="/doAct1")
	public String doAction1() {
		System.out.println("doAction1이 호출됨!");
		
		return "doAction1"; // doAction1.jsp
	}
	
	@RequestMapping(value="/doAct2")
	public void doAction2() {
		System.out.println("doAction2가 호출됨!");
		logger.info("doAction2가 호출됨!");
		
		// String을 반환하지 않으면
		// 메시지 파일 [/WEB-INF/views/doAct2.jsp]을(를) 찾을 수 없습니다.
		// mapping주소.jsp를 반환함
	}
	
	@RequestMapping(value = "doAct3", method = RequestMethod.GET)
	public String doAction3(Model model) {
		
		logger.info("doAction3가 호출됨!");
		
		String name = "hay";
		model.addAttribute("name", name); // 바인딩
		
		return "doAction3";
	}
	
	@RequestMapping(value = "/doAct4")
	public ModelAndView doAction4() {
		
		logger.info("doAction4가 호출됨!");
		
		String name = "hay";
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("name", name);
		mav.setViewName("doAction4");
		
		return mav;
	}
	
	@RequestMapping(value="/doAct5")
	public void doAction5(Model model){
		logger.info("doAction5 호출됨!");
		
		Student student = new Student("171215", "노짱아");
		Student student2 = new Student("1231", "최종수");
		
//		model.addAttribute("student", student);
		model.addAttribute(student);
		model.addAttribute(student2);   // overwrite돼서 student2가 출력됨
		// 바인딩하는 객체의 이름을 지정하지 않은 경우에는
		// 자동으로 클래스명(앞글자 소문자로)으로 바인딩됨
	}
	
}

