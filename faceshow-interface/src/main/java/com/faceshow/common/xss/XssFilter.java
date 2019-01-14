package com.faceshow.common.xss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * XSS过滤
 * @author Gaosx
 * @email Gaoshanxi@gmail.com
 * @date 2017-04-01 10:20
 */
public class XssFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssRequest, response);

		logger.info("---------------------------------------程序结束-----------------------------");
	}

	@Override
	public void destroy() {
	}

}