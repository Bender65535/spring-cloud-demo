package com.demo.cloud.serviceconsumer.controller;

import com.demo.cloud.serviceconsumer.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("consumer/user")
//定义全局的熔断方法
//feign的熔断方法与普通的熔断方法不同
//@DefaultProperties(defaultFallback = "fallbackMethod")
public class UserController {

    @Autowired
    private UserClient userClient;

//    @Autowired
//    private RestTemplate restTemplate;

//    @Autowired
//    private DiscoveryClient discoveryClient; //包含了拉取所有服务信息


//    @GetMapping
//    @ResponseBody
//    @HystrixCommand(fallbackMethod = "queryUserByIdFallback")  //声明熔断的方法
//    public String queryUserById(@RequestParam("id")Long id){
        //拉取服务列表
        //List<ServiceInstance> instances = discoveryClient.getInstances("service-provider");
        //get(0)定死了节点,就无法负载均衡
        //ServiceInstance instance = instances.get(0);
        // return this.restTemplate.getForObject("http://" +instance.getHost()+":"+instance.getPort()+"/user/"+id,User.class);

        //

        //改进,将请求改为服务名
//        return this.restTemplate.getForObject("http://service-provider/user/"+id,String.class);
        //在调用过程中,http地址会被解析

        //restTemplate可以使用feign来优化
        //feign可以把Rest的请求进行隐藏,伪装成类似SpringMVC的Controller一样,你不用再自己拼接url,拼接参数等操作,一切都交给feign去做
//    }


    /**
     * 使用feign来改造调用方式
     */
    @GetMapping
    @ResponseBody
    public String queryUserById(@RequestParam("id")Long id){
        return this.userClient.queryUserById(id).toString();
    }



    /**
     * 熔断触发后执行的方法
     * 返回类型必须和调用方法相同
     * @param id
     * @return
     */
    public String queryUserByIdFallback(Long id){
        return "服务器正忙";
    }

    public String fallbackMethod(){
        return "服务器正忙";
    }
}
