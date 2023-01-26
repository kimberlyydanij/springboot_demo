package com.example.board.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageDTO;

public class BoardDaoImp implements BoardDAO{
	private SqlSessionTemplate sqlSession;
	
	public BoardDaoImp() {
	
	}
	
	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	@Override
	public int count(PageDTO pv) {		
		return sqlSession.selectOne("board.count", pv);
	}

	@Override
	public List<BoardDTO> list(PageDTO pv) {
		return sqlSession.selectList("board.list", pv);
	}

	@Override
	public void readCount(int num) {
		sqlSession.update("board.readCount", num);		
	}

	@Override
	public BoardDTO content(int num) {		
		return sqlSession.selectOne("board.content", num);
	}

	@Override
	public void reStepCount(BoardDTO dto) {
		sqlSession.update("board.reStepCount", dto);		
	}

	@Override
	public void save(BoardDTO dto) {
		sqlSession.insert("board.save", dto);		
	}

	@Override
	public BoardDTO updateNum(int num) {		
		return sqlSession.selectOne("board.content", num);
	}

	@Override
	public void update(BoardDTO dto) {
		sqlSession.update("board.upt", dto);
	}

	@Override
	public void delete(int num) {
		sqlSession.delete("board.del", num);	
	}

	@Override
	public String getFile(int num) {		
		return sqlSession.selectOne("board.uploadFile", num);
	}

}//end class
