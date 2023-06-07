package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Board;

public class BoardDAO {
	Connection conn = null; // 데이터베이스의 연결을 담당
	PreparedStatement pstmt; // 쿼리문의 실행을 담당

	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";

	// DB 연결 메소드
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER); // 드라이버 로드
			conn = DriverManager.getConnection(JDBC_URL, "test", "test1234"); // DB연결 (URL,"계정","비밀번호")
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	// 게시판 리스트 가져오기
	public ArrayList<Board> getList() throws SQLException {
		Connection conn = open();
		
		// Board 객체를 담을 ArrayList
		ArrayList<Board> boardList = new ArrayList<>();
		
		String sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date , views from board";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try (conn; pstmt; rs){
			while(rs.next()) { // 1 라인씩 데이터를 읽어온다
				Board b = new Board();
				
				b.setBoard_no(rs.getInt("board_no"));
				b.setTitle(rs.getString("title"));
				b.setUser_id(rs.getString("user_id"));
				b.setReg_date(rs.getString("reg_date"));
				b.setViews(rs.getInt("views"));
				
				boardList.add(b);
			}
			return boardList;
		}
	}
	
	// 게시물 내용 가져오기
	/*
	 * public Board getView(int board_no) {
	 * 
	 * }
	 */
	
	// 조회수 증가
	public void updateViews(int board_no) {
		
	}
	
	// 게시글 등록
	public void insertBoard(Board b) {
		
	}
	
	// 게시글 수정화면 보여주기
	/*
	 * public Board getViewForEdit(int board_no) {
	 * 
	 * }
	 */	
	// 게시글 수정하기
	public void updateBoard(Board b) {
		
	}
	
	// 게시글 삭제하기
	public void deleteBoard(int board_no) {
		
	}
	
}
