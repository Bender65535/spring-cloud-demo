package com.demo.cloud.serviceconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
//@EnableDiscoveryClient
//3.开启熔断
//@EnableCircuitBreaker


@SpringCloudApplication  //组合注解  相当于上面三个
@EnableFeignClients    //启用feign的组件
public class ServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceConsumerApplication.class, args);
    }

    //使用feign后就不需要使用restTemplate来拼接url路径
    @Bean
    @LoadBalanced  //开启ribbon负载均衡
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
