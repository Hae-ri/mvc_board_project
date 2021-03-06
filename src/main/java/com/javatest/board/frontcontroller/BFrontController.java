package com.javatest.board.frontcontroller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javatest.board.command.*;

/**
 * Servlet implementation class BFrontController
 */
@WebServlet("*.do") // .do로 끝나는 주소를 모두 가져온다.
public class BFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public BFrontController() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		actionDo(request,response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		BCommand command = null;
		
		String uri = request.getRequestURI(); // 전체 주소 (http://localhost:8888/mvc_board_project/write.do)
		String conPath = request.getContextPath(); // context 주소 (http://localhost:8888/mvc_board_project)
		String com = uri.substring(conPath.length()); // 실제 이동될 주소(/*.do) = 전체 주소 - context 주소
		
		if(com.equals("/write_view.do")) {
			viewPage = "write_view.jsp";
		} else if(com.equals("/write.do")) {
			command = new BWriteCommand(); 
			command.excute(request, response);
			viewPage = "list.do";
		} else if(com.equals("/list.do")) {
			command = new BListCommand(); 
			command.excute(request, response);
			viewPage = "list.jsp";
			//response.sendRedirect(viewPage); // 기존의 request 객체의 내용을 사용하지 못함
		} else if(com.equals("/content_view.do")) {
			command = new BContentCommand(); 
			command.excute(request, response);
			viewPage = "content_view.jsp";
		}
		else if(com.equals("/modify.do")) {
			command = new BModifyCommand(); 
			command.excute(request, response);
			viewPage = "list.do";
		}
		
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
		// 기존 request 객체를 인수로 넣어서 forward하므로 기존 request 객체의 내용을 사용할 수 있음
	}
}
