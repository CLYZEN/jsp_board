package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

import DAO.BoardDAO;
import DTO.Board;

@WebServlet("/")
@MultipartConfig(maxFileSize=1024*1024*2, location="c:/Temp/img")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardDAO dao;
	private ServletContext ctx; // 자원관리. 페이지 이동. 포워드를 위해 사용

	public BoardController() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new BoardDAO();
		ctx = getServletContext();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 한글깨짐방지
		String command = request.getServletPath(); // 서블렛 경로를 가지고 온다.
		String site = null;

		System.out.println("command : " + command);

		// 1. 라우팅
		switch (command) {
		case "/list":
			site = getList(request);
			break;
		case "/view":
			site = getView(request);
			break;
		case "/write":
			site = "write.jsp"; // 글쓰는 화면을 보여줌
			break;
		case "/insert":
			site = insertBoard(request);
			break;
		case "/edit":
			site = getViewForEdit(request);
			break;
		case "/update":
			site = updateBoard(request);
			break;
		case "/delete":
			site = deleteBoard(request);
			break;
		} // switch END
		
		/*	 redirect, forward
		 * 	- 둘다 새로운 페이지로 이동
		 *  redirect : 데이터(response, request 객체)를 가지고 이동하지 않음, 주소가 변한다.
		 *  	DB에 변화가 생기는 요청(글쓰기, 회원가입...)
		 *  	INSERT, UPDATE, DELETE...
		 
		 *	forward : 데이터(response, request 객체)를 가지고 이동, 주소가 변하지 않는다.
		 *		단순조회(상세페이즈 보기, 리스트 보기, 검색...)
		 *		SELECT...
		 */
		if(site.startsWith("redirect:/")) { // redirect
			String rview = site.substring("redirect:/".length());
			response.sendRedirect(rview); //  페이지 이동 list
		} else { // forward			
			ctx.getRequestDispatcher("/" + site).forward(request, response);
		}
	} // service END

	public String getList(HttpServletRequest request) {
		ArrayList<Board> list;

		try {
			list = dao.getList();
			request.setAttribute("boardList", list);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("게시판 목록 생성 과정에서 문제 발생");
			request.setAttribute("error", "게시판 목록이 정상적으로 처리되지 않았습니다");
		}

		return "index.jsp";
	}
	
	public String getView(HttpServletRequest request) {
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		try {
			dao.updateViews(board_no);
			Board b = dao.getView(board_no);
			request.setAttribute("board", b);
		} catch (Exception e) {
			e.printStackTrace();
			ctx.log("게시글을 가져오는 과정에서 문제 발생");
			request.setAttribute("error", "게시글을 정상적으로 불러오지 못했습니다.");
		}
		
		return "view.jsp";
	}

	public String insertBoard(HttpServletRequest request) {
		Board b = new Board();
		
		try {
			BeanUtils.populate(b, request.getParameterMap());
			
			// 1. 이미지 파일 자체를 서버에 저장
			Part part = request.getPart("file"); // 이미지파일 받기
			String fileName = getFilename(part); // 파일 이름 가져오기
			
			if(fileName != null && !fileName.isEmpty()) {
				part.write(fileName); // 파일을 서버에 저장
			}
			// 2. 이미지 파일 이름에 "/img/" 경로룰 붙여서 board 객체에 저장
			b.setImg("/img/"+ fileName);
			
			dao.insertBoard(b);
			
		} catch (Exception e) {
			ctx.log("게시글을 작성과정에서 문제 발생");
			
			try {
				// GET 방식으로 넘길 때 한글 깨짐 방지
				String encodeName = URLEncoder.encode("게시물이 정상적으로 등록되지 않았습니다.", "UTF-8");
				return "redirect:/list?error=" + encodeName;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} 
		
		/*
		b.setUser_id(request.getParameter("user_id"));	
		.
		.
		.
		.
		*/
		return "redirect:/list";
	}
	
	public String getViewForEdit(HttpServletRequest request) {
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		
		try {
			Board b = dao.getViewForEdit(board_no);
			request.setAttribute("board", b);
		} catch (Exception e) {
			ctx.log("게시글을 가져오는 과정에서 문제 발생");
			request.setAttribute("error", "게시글을 정상적으로 불러오지 못했습니다.");
			e.printStackTrace();
		} 
		
		return "edit.jsp";
	}
	
	public String updateBoard(HttpServletRequest request) {
		Board b = new Board();
		try {
			BeanUtils.populate(b, request.getParameterMap());
			
			// 1. 이미지 파일 자체를 서버에 저장
			Part part = request.getPart("file"); // 이미지파일 받기
			String fileName = getFilename(part); // 파일 이름 가져오기
			
			if(fileName != null && !fileName.isEmpty()) {
				part.write(fileName); // 파일을 서버에 저장
			}
			// 2. 이미지 파일 이름에 "/img/" 경로룰 붙여서 board 객체에 저장
			b.setImg("/img/"+ fileName);
			
			dao.updateBoard(b);
			
		} catch (Exception e) {
			ctx.log("게시글을 수정과정에서 문제 발생");
			
			try {
				// GET 방식으로 넘길 때 한글 깨짐 방지
				String encodeName = URLEncoder.encode("게시물이 정상적으로 수정되지 않았습니다.", "UTF-8");
				return "redirect:/view?board_no=" +b.getBoard_no() + "&error=" + encodeName;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return "redirect:/view?board_no=" +b.getBoard_no();
	}
	
	public String deleteBoard(HttpServletRequest request) {
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		
		try {
			dao.deleteBoard(board_no);
		} catch (Exception e) {
			ctx.log("게시글을 삭제과정에서 문제 발생");
			
			try {
				// GET 방식으로 넘길 때 한글 깨짐 방지
				String encodeName = URLEncoder.encode("게시물이 정상적으로 삭제되지 않았습니다.", "UTF-8");
				return "redirect:/list?error=" + encodeName;
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return "redirect:/list";
	}
	
	// 파일 이름 추출
	private String getFilename(Part part) {
		String fileName = null;
		String header = part.getHeader("content-disposition");
		
		int start = header.indexOf("filename=");
		fileName = header.substring(start+10, header.length()-1);
		
		return fileName;
	}
	
} // class END