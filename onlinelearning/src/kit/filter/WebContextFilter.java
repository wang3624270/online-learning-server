package kit.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
 
public class WebContextFilter implements Filter {
 
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
         
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse  httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:8090");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Content-Range,Range"); 
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");  
        chain.doFilter(request, httpServletResponse);
         
    }
 
    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
         
    }
 
}

