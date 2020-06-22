package com.fth.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyFilter2 extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("filter 2 pre");
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("filter 2 run");
        //获取上下文（重要，贯穿 所有filter，包含所有参数）
        RequestContext requestContext = RequestContext.getCurrentContext();
//        requestContext.setSendZuulResponse(true);
        //requestContext.setResponseBody("请求合法");
//        String uri = requestContext.getRequest().getRequestURI();
//        try {
//            if(!"/api/client-zuul/hello".equals(uri)){
//                requestContext.getResponse().sendRedirect("/api/client-zuul/hello");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
