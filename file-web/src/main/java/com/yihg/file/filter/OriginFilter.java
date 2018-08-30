package com.yihg.file.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class OriginFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(OriginFilter.class);
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		//解决跨域问题
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
//		httpResponse.addHeader("Access-Control-Allow-Headers", "application/json; charset=UTF-8");  
		httpResponse.addHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST");  
		httpResponse.addHeader("Allow", "OPTIONS,GET,POST");
		
		chain.doFilter(httpRequest, httpResponse);
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	private String getReferer(HttpServletRequest request){
		String referer = request.getHeader("Referer");
		//String referer = "http://gc.pre.jk.cn/health_path/index.html";
		if(!StringUtils.isEmpty(referer)){
//			logger.info("getReferer referer="+referer);
			String regex = "http://[^/]*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(referer);
			if(m.find()){
				String prefixReferer = m.group(0);
				logger.info("getReferer prefixReferer="+prefixReferer);
				return prefixReferer;
				
			}
//			logger.info("getReferer prefixReferer=*");
		}
		return "*";
	}
	
}
