package com.test;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void init(ServletConfig config) throws ServletException {

		ServletContext sc = config.getServletContext();

		String webAppPath = sc.getRealPath("/");
		String log4jProp = webAppPath + "WEB-INF/log4j.properties";

		File log4j_file = new File(log4jProp);
		if (log4j_file.exists()) {
			PropertyConfigurator.configure(log4jProp);
		} else {
			System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		}

		super.init(config);
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().print("hello");
		Logger.getLogger(getClass().getName()).warning("my message");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
