package com.fusionspy.beacon.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EncodeInterceptor extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(EncodeInterceptor.class);

    private String encoding;

	private boolean forceEncoding = false;


	/**
	 * Set the encoding to use for requests. This encoding will be passed into a
	 * {@link javax.servlet.http.HttpServletRequest#setCharacterEncoding} call.
	 * <p>Whether this encoding will override existing request encodings
	 * (and whether it will be applied as default response encoding as well)
	 * depends on the {@link #setForceEncoding "forceEncoding"} flag.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * Set whether the configured {@link #setEncoding encoding} of this filter
	 * is supposed to override existing request and response encodings.
	 * <p>Default is "false", i.e. do not modify the encoding if
	 * {@link javax.servlet.http.HttpServletRequest#getCharacterEncoding()}
	 * returns a non-null value. Switch this to "true" to enforce the specified
	 * encoding in any case, applying it as default response encoding as well.
	 * <p>Note that the response encoding will only be set on Servlet 2.4+
	 * containers, since Servlet 2.3 did not provide a facility for setting
	 * a default response encoding.
	 */
	public void setForceEncoding(boolean forceEncoding) {
		this.forceEncoding = forceEncoding;
	}


	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding() == null)) {
			request.setCharacterEncoding(this.encoding);
			if (this.forceEncoding) {
				response.setCharacterEncoding(this.encoding);
			}

            String requestedWith = request.getHeader("x-requested-with");
            String type = request.getContentType();
            log.debug("ContentType = {}",type);
            if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)
                && null != type
                && ("application/x-www-form-urlencoded".equals(type) || "application/x-www-form-urlencoded; charset=UTF-8".equals(type))) {
                request.setCharacterEncoding("UTF-8");
                response.setCharacterEncoding("UTF-8");
                request.getParameterMap();
            }
		}
		filterChain.doFilter(request, response);
	}

}
