package com.demo.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class LoginFilter extends ZuulFilter {
    /**
     * 过滤类型 : pre router post error
     * @return
     */
    @Override
    public String filterType() {
        //路由之前
        return "pre";
    }

    /**
     * 执行顺序 返回值越小,优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 10;  //保持可拓展性
    }

    /**
     * 是否执行该过滤器(run方法)
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 编写过滤器的业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //初始化context上下文对象
        RequestContext context = RequestContext.getCurrentContext();

        //获取request对象
        HttpServletRequest request = context.getRequest();

        //获取参数
        String token = request.getParameter("token");

        if(StringUtils.isBlank(token)){
            //如果token为空,就拦截
            //不转发请求
            context.setSendZuulResponse(false);
            //被拦截后的提示,相应状态码,401-身份未认证
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);  //未认证
            //设置响应的提示
            context.setResponseBody("request error!");
        }

        return null;
    }
}
