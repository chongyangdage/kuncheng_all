package com.kun.oa.service;

import com.kun.oa.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IUserService {
    //登录
    User login(String username, String password);
    //添加人员
    Integer insertPeople(User user);
    //查询人员
    List<User> findPeople();
    //删除人员
    Integer delePeople(Integer id);
}
