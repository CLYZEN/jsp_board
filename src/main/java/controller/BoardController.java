package controller;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BoardDAO;
import DTO.Board;

@WebServlet("/")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BoardDAO dao;
    private ServletContext ctx; //자원관리. 페이지 이동. 포워드를 위해 사용
    public BoardController() {
        super();
    }
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new BoardDAO();
		ctx = getServletContext();
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 한글깨짐방지
		String command = request.getServletPath(); // 서블렛 경로를 가지고 온다.
		String site = null;
		
		System.out.println("command : " + command);
		
		//1. 라우팅
		switch (command) {
		case "/list":
			site = getList(request);
			break;
		/*
		 * case "/list":
		 * 
		 * break; case "/list":
		 * 
		 * break; case "/list":
		 * 
		 * break; case "/list":
		 * 
		 * break; case "/list":
		 * 
		 * break; case "/list":
		 * 
		 * break;
		 */
		} // switch END
		ctx.getRequestDispatcher("/"+site).forward(request, response);
	} // service END
	
	public String getList(HttpServletRequest request) {
		ArrayList<Board> list;
		
		try {
			list = dao.getList();
			request.setAttribute("boardList", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "index.jsp";
	}
} // class END