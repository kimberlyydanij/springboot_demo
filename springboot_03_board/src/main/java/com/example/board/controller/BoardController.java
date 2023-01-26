package com.example.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.PageData;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageDTO;
import com.example.board.service.BoardService;


@Controller
public class BoardController {
	private BoardService service;
	private PageDTO pdto;
	int currentPage;
	

	public BoardController() {

	}

	public void setService(BoardService service) {
		this.service = service;
	}

	public String filePath(HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("/");
		String saveDirectory = root + "temp" + File.separator;
		return saveDirectory;
	}
	
	@RequestMapping("/list.sb")	
	public ModelAndView listMethod(PageDTO pv, ModelAndView mav) {
	 	int totalRecord = service.countProcess(pv);
		
            //System.out.println("totalRecord:" + totalRecord);
		if (totalRecord >= 1) {
			if (pv.getCurrentPage() == 0)
				currentPage = 1;
			else
				currentPage = pv.getCurrentPage();

			pdto = new PageDTO(currentPage, totalRecord, pv.getSearchKey(), pv.getSearchWord() );
			mav.addObject("pv", pdto);
			mav.addObject("aList", service.listProcess(pdto));
			
		}

		mav.setViewName("board/list");
		return mav;
	}// end listMethod()///////////////////////////

	@RequestMapping("/view.sb")
	public ModelAndView viewMethod(PageDTO pv , int num, ModelAndView mav) {
		mav.addObject("dto", service.contentProcess(num));
		//mav.addObject("currentPage", pv.getCurrentPage());
		mav.setViewName("board/view");
		return mav;
	}// end viewMethod()///////////////////////////////

	@RequestMapping(value = "/write.sb", method = RequestMethod.GET)
	public ModelAndView writeMethod(PageDTO pv, BoardDTO dto, ModelAndView mav) {
		if (dto.getRef() != 0) { // 답변글이면
			mav.addObject("currentPage", pv.getCurrentPage());
			mav.addObject("dto", dto);
		}
		mav.setViewName("board/write");
		return mav;
	}// end writeMethod()/////////////////////

	@RequestMapping(value = "/write.sb", method = RequestMethod.POST)
	public ModelAndView writeProMethod(PageDTO pv, BoardDTO dto, HttpServletRequest request, ModelAndView mav) {

		MultipartFile file = dto.getFilename();
		if (!file.isEmpty()) {
			UUID random = saveCopyFile(file, request);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}

		dto.setIp(request.getRemoteAddr());

		// 답변글이면
		if (dto.getRef() != 0) {
			service.reStepProcess(dto);
		} else {
			// 제목글이면
			service.insertProcess(dto);
		}
		
		mav.addObject("currentPage",pv.getCurrentPage());
	    mav.setViewName("redirect:/list.sb");
		return mav;
	}// end writeProMethod()/////////////////

	public UUID saveCopyFile(MultipartFile file, HttpServletRequest request) {
		String fileName = file.getOriginalFilename();
		// 중복파일명을 처리하기 위해 난수 발생
		UUID random = UUID.randomUUID();
		String saveDirectory = filePath(request);
		System.out.println(saveDirectory);
		File fe = new File(saveDirectory);
		if (!fe.exists()) {
			fe.mkdir();
		}

		File ff = new File(saveDirectory, random + "_" + fileName);
		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(ff));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return random;
	}// end saveCopyFile()/////////////////

	@RequestMapping("/contentdownload.sb")
	public ModelAndView downMethod(int num, ModelAndView mav) {
		mav.addObject("num", num);
		mav.setViewName("download");
		return mav;
	}// end downMethod()////////////////////////

	@RequestMapping(value = "/update.sb", method = RequestMethod.GET)
	public ModelAndView updateMethod(int num, int currentPage, ModelAndView mav) {
		mav.addObject("dto", service.updateSelectProcess(num));
		mav.addObject("currentPage", currentPage);
		mav.setViewName("board/update");
		return mav;
	}// end updateMethod()//////////////////////

	
	@RequestMapping(value = "/update.sb", method = RequestMethod.POST)
	public ModelAndView updateProMethod(BoardDTO dto, int currentPage, HttpServletRequest request, ModelAndView mav) {
		//기존 첨부파일
		String filename = service.fileSelectprocess(dto.getNum());
		String saveDirectory = filePath(request);
				
		//수정할 첨부파일
		MultipartFile file = dto.getFilename();
		//수정 첨부파일이 있으면
		if (!file.isEmpty()) {
			//기존 첨부파일이 있으면
			if(filename !=null) {
				File fe = new File(saveDirectory, filename);
				fe.delete();						
			}
			
			UUID random = saveCopyFile(file, request);
			dto.setUpload(random + "_" + file.getOriginalFilename());
		}

		dto.setIp(request.getRemoteAddr());		
		service.updateProcess(dto);
		mav.addObject("currentPage", currentPage);
		mav.setViewName("redirect:/list.sb");		
		return mav;
	}
	
	@RequestMapping("/delete.sb")
	public ModelAndView deleteMethod(int num, 
			PageDTO pv, HttpServletRequest request,
			ModelAndView mav) {
		
		String upload = service.fileSelectprocess(num);
		//첨부파일이 저장되여 있으면
		if(upload != null) {
			String saveDirectory = filePath(request);
			File fe = new File(saveDirectory, upload);
			fe.delete();			
		}
		
		service.deleteProcess(num);
		PageDTO pdto = new PageDTO(pv.getCurrentPage(), service.countProcess(pv));
		if(pdto.getTotalPage()<pv.getCurrentPage())
			mav.addObject("currentPage", pdto.getTotalPage());
		else						
		   mav.addObject("currentPage", pv.getCurrentPage());
		
		mav.setViewName("redirect:/list.sb");		
		return mav;
	}
}// end class




