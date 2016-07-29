package com.lvmama.vst.hhs.downgrade;

/*
 *  Author: Luocheng Tang
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.filter.GenericFilterBean;

public class ApplicationDowngradeFilter extends GenericFilterBean
		implements ApplicationEventPublisherAware, MessageSourceAware {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationDowngradeFilter.class);
	private final String redirectAddress = "/services.html";
	
	private Long lastQueryTime = -1L;
	private int interval = 2; // minutes
	private static final String SERVICE_IS_DOWN_GRADE_EXCEPTION = "Service is temporary downgraded.";

	@Value("${info.app.name:hhs}")
	private String serviceName;
	
	@Autowired
	private DownGradeService downGradeService;
	private List<String> blockedList = new ArrayList<>();

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getServletPath();
		String method = request.getMethod();

		// redirect info to forwardAddress
		if (uri.equals("/info")) {
			LOGGER.info("redirect to " + redirectAddress);
			response.sendRedirect(this.redirectAddress);
			return;
		}

		if (!uri.startsWith("/" + serviceName)) {
			chain.doFilter(request, response);
			return;
		}

		// in every 5 minutes, check DB to see if there are new added methods
		Long now = new Date().getTime();
		if (this.lastQueryTime == -1L || now - this.lastQueryTime > interval * 60 * 1000L) {
			this.blockedList = downGradeService.getDownDradeList(serviceName);
			this.lastQueryTime = now;
		}

		LOGGER.debug(this.blockedList.toString());
		if (isTheMethodBlocked(uri + "_" + method.toUpperCase())) {
			LOGGER.info("Service is downgraded, throw an exception to caller.");
			throw new ServletException(ApplicationDowngradeFilter.SERVICE_IS_DOWN_GRADE_EXCEPTION);
		}

		chain.doFilter(request, response);
		return;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		// TODO Auto-generated method stub

	}

	private boolean isTheMethodBlocked(String method) {
		if (null == this.blockedList || this.blockedList.isEmpty()) {
			return false;
		}
		for (String block : this.blockedList) {
			return checkIfMatch(method, block);
		}
		return false;
	}

	private boolean checkIfMatch(String passing, String blocked) {
		
		if (!blocked.contains("{")) {
			return passing.equals(blocked);
		}
		
		// build an pattern from /hhs/v1/admin/stamp/{stampId}/orders/exists
		// to /hhs/v1/admin/stamp/(.+)/orders/exists
		String pattern1 = "(.+)\\{(.+)\\}(.*)";
		Pattern r1 = Pattern.compile(pattern1);
		Matcher m1 = r1.matcher(blocked);
		String someId = "";
		if (m1.find()) {
			someId = m1.group(2);
		}
		String pattern = blocked.replace("{" + someId + "}", "(.+)");
		
		// use pattern to test input URI
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(passing);
		boolean found = m.find();
		if (!found) {
			return false;
		}
		String s = m.group(1);
		if ( s != null && m.group(1).contains("/")) {
			return false;
		}
		return true;
	}

}
