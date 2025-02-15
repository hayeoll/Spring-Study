package com.springbasic.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springbasic.vo.Student;

@Controller
@RequestMapping("/student/*") // /student/*의 모든 url에 대해서 mapping
public class StudentController {
	
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@RequestMapping("outputStudent")
	public void outputStudent(Model model) {
		logger.info("outputStudent가 호출됨");
		
		Student student = new Student("171215", "노짱아");
		
		model.addAttribute("student", student); // 바인딩
	}
	
	@RequestMapping("inputStudent")
	public void inputStudent() {
		logger.info("inputStudent가 호출됨");
	}
	
//	@RequestMapping(value="saveStudent", method=RequestMethod.POST)
//	public void inputStudentPOST(@RequestParam("stuNo") String stuNo, @RequestParam("stuName") String stuName, Model model) {
//		logger.info("saveStudent가 호출됨");
//		Student student = new Student(stuNo, stuName);
//		model.addAttribute("student", student); 
//	}
//	
//	@RequestMapping(value="saveStudent", method=RequestMethod.POST)
//	public void inputStudentPOST(Student student, Model model) {
//		// Student studet : 기본생성자로 student 객체 생성 
//		// 					-> setter 호출로 reqPram을 멤버 변수에 넣어줌
//		// -> 기본생성자 없으면 예외 발생
//		
//		logger.info("saveStudent가 호출됨");
//		model.addAttribute("student", student); 
//	}
	
	// Redirect 시키는 경우
	// RedirectAttributes : redirect 할 때 쿼리스트링으로 어떤 값을 넘기고자 할 때 사용하는 객체
	@RequestMapping(value="saveStudent", method=RequestMethod.POST)
	public String inputStudentPOST(Student student, Model model, RedirectAttributes rttr) {
		logger.info("saveStudent가 호출됨");
		
		// redirect
		rttr.addAttribute("status", "success");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", student.getStuName());
		map.put("stuNo", student.getStuNo());
		
		rttr.addAllAttributes(map);
		rttr.addFlashAttribute("flash", "flash");
		
		return "redirect:homeStudent";
	}
	
	@RequestMapping("homeStudent")
	public void homeStudent(@ModelAttribute(name="status") String status,
					@RequestParam Map<String, String> map,
					@ModelAttribute(name="flash") String flash) {
		logger.info("homeStudent GET 요청됨");
		logger.info(status);
		logger.info(flash);
		
	}
	
	// jackson-databind 라이브러리 추가 -> json으로 응답
	@RequestMapping(value="output")
	public @ResponseBody Student sampleStudent() {
		
		logger.info("sampleStudent가 호출됨");
		Student tmp = new Student("1231", "최종수");
		
		return tmp;
	}
}
