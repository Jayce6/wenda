package com.wenda.Configuration;

import com.wenda.Interceptor.LoginrequestInterceptor;
import com.wenda.Interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @auther 张伟豪
 * @create 2019/6/24-21:06
 */
@Component
public class WendaWebConfiguration implements WebMvcConfigurer {


    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
      LoginrequestInterceptor loginrequestInterceptor;

/*    @Autowired
    InterceptorRegistry interceptorRegistry;*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginrequestInterceptor).addPathPatterns("/user/*");

    }

/*   @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }*/
}
