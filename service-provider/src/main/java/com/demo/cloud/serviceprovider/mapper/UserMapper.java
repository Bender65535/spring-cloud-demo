package com.demo.cloud.serviceprovider.mapper;

import com.demo.cloud.serviceprovider.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
}
