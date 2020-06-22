package com.fth.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * 自定义过滤器 继承 ZuulFilter 类
 */
@Component
public class MyFilter extends ZuulFilter {

    /**
     * 过滤器类型
     * PRE_TYPE
     * POST_TYPE
     * ROUTE_TYPE
     * ERROR_TYPE
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤器执行的顺序，返回数字越小 越先执行。
     * http://localhost:9001/actuator/filters 查看过滤器
     * 默认过滤器中 ServletDetectionFilter 设置的order 为 -3  若要保证 自定义过滤器第一个执行，order 小于 -3 即可
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要进行 过滤 ，返回 true 才执行 下面的run 方法
     * @return
     */
    @Override
    public boolean shouldFilter() {
        System.out.println("filter 1 pre");
        return true;
    }

    /**
     * 过滤器 过滤的具体逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("filter 1 run");

        //获取上下文（重要，贯穿 所有filter，包含所有参数）
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取认证信息
        String authorization = requestContext.getRequest().getHeader("Authorization");
        //获取请求uri
        String requestURI = requestContext.getRequest().getRequestURI();
//        if(!"/api/client-zuul/hello".equals(requestURI)){
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(444);
//            //重中之重，这里一定要加要给Response设置CharacterEncoding编码为UTF-8
//            requestContext.getResponse().setCharacterEncoding("UTF-8");
//            requestContext.getResponse().setContentType("text/html;cahrset=UTF-8");
//            requestContext.setResponseBody("请求非法");
//        }
        return null;

    }
}
