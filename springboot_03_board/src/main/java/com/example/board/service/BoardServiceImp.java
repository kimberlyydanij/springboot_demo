package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dao.BoardDAO;
import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageDTO;

@Service
public class BoardServiceImp implements BoardService{
	
	@Autowired
	private BoardDAO dao;
	
	public BoardServiceImp() {
	
	}
	
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	@Override
	public int countProcess(PageDTO pv) {		
		return dao.count(pv);
	}

	@Override
	public List<BoardDTO> listProcess(PageDTO pv) {		
		return dao.list(pv);
	}

	//제목글
	@Override
	public void insertProcess(BoardDTO dto) {
		dao.save(dto);		
	}

	@Override
	public BoardDTO contentProcess(int num) {
		dao.readCount(num);
		return dao.content(num);
	}

	//답변글
	@Override
	public void reStepProcess(BoardDTO dto) {
		dao.reStepCount(dto);
		dto.setRe_step(dto.getRe_step()+1);
		dto.setRe_level(dto.getRe_level()+1);
		dao.save(dto);		
	}

	@Override
	public BoardDTO updateSelectProcess(int num) {		
		return dao.updateNum(num);
	}

	@Override
	public void updateProcess(BoardDTO dto) {
		dao.update(dto);		
	}

	@Override
	public void deleteProcess(int num) {
		dao.delete(num);		
	}

	@Override
	public String fileSelectprocess(int num) {		
		return dao.getFile(num);
	}
	
	
}//end class
