package com.demo.cloud.serviceconsumer.client;

import com.demo.cloud.serviceconsumer.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//等同于拿到host和port
@FeignClient(value="service-provider" , fallback = UserClientFallback.class)
//表示调用的服务是service-provider , 告诉feign,熔断类是在UserClientFallback中


/**
 * feign的总结:
 * 1.引入openFeign启动器
 * 2.Feign.hystrix.enable=true,开启feign的熔断器
 * 3.在引导类上,@EnableFeignClients
 */
public interface UserClient {
    @GetMapping("user/{id}")  //路径的最佳实践 ,  如果在类上加@RequestMapping可能会报错
    User queryUserById(@PathVariable("id")Long id);
}

