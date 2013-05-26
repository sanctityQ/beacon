package com.fusionspy.beacon.web;

import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * filter for amchart timestamp
 * amchart 2.2.1 version when use <reload_data_interval>x</reload_data_interval> and x>0,then it append
 *  timestamp.here is remove the param which not has value
 * User: qc
 * Date: 11-10-10
 * Time: 下午10:48
 */
public class AmchartFilter implements Filter {

    private static String[] path;

    private static final String SEP = ",";

    private static final String PATH = "path";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        path = StringUtils.split(filterConfig.getInitParameter(PATH),SEP);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (checkPath(httpServletRequest.getServletPath())) {
            HttpServletRequest newRequest = new ChartRequestWrapper(httpServletRequest);
            chain.doFilter(newRequest, response);
        }
        else{
           chain.doFilter(request, response);
        }

    }

    private boolean checkPath(String servletPath){
        if (path == null)
            return false;
        for (int i = 0, n = path.length; i < n; i++) {
            if(StringUtils.contains(servletPath, path[i]))
                return true;
        }
        return false;
    }

    @Override
    public void destroy() {
        //do nothing
    }

    class ChartRequestWrapper extends HttpServletRequestWrapper{


        public ChartRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Map getParameterMap() {
            Map params = this.getRequest().getParameterMap();
            Set set = params.keySet();
            HashMap back = new HashMap();
            for (Iterator iter = set.iterator(); iter.hasNext();) {
                Object name = iter.next();
//                    System.out.println("---------------------------111111111111111--------------------------------- =" +name );
//
//                    String values[]=this.getRequest().getParameterValues(String.valueOf(name));
//                    if(values!=null)
//                    for(int i=0,n=values.length;i<n;i++){
//                        System.out.println("------------------------------------------------------------"+i+"----- = " +values[i] );
//                    }

                try {
                    Long.parseLong(String.valueOf(name));

                } catch (NumberFormatException numberFormatException) {
                    back.put(name, params.get(name));
                }
                //System.currentTimeMillis() -
//                    if(this.getRequest().getParameterValues(String.valueOf(name))==null){
//                        params.remove(name);
//                    }

            }
            return back;
        }

    }

}
