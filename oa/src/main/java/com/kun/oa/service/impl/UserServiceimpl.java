package com.kun.oa.service.impl;

import com.kun.oa.entity.User;
import com.kun.oa.mapper.UserMapper;
import com.kun.oa.service.IUserService;
import com.kun.oa.service.ex.PasswordNotMatchException;
import com.kun.oa.service.ex.UpdateException;
import com.kun.oa.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserServiceimpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
//登录
    @Override
    public User login(String username, String password) {

        User user=userMapper.findByUsername(username);
        if(user==null){
            throw new UserNotFoundException("用户数据不存在的错误");
        }
        if(user.getState().equals(0)){
            throw  new UserNotFoundException("用户数据不存在的错误");
        }
        String salt=user.getSalt();
        String password1= getMd5Password(password,salt);
        System.out.println(password1);
        if(password1.equals(user.getPassword())==false){

            throw  new PasswordNotMatchException("密码验证失败的错误");
        }

        // 返回新的user对象
        return user;
    }
//删除人员

    @Override
    public Integer delePeople(Integer id) {
       Integer row= userMapper.delePeople(id);
       if(row!=1){
           throw new UpdateException("删除失败");
       }
        return row;
    }

    @Override
    public List<User> findPeople() {
        List<User> data=userMapper.findPeople();
        return data;
    }

    @Override
    public Integer insertPeople(User user) {
        if(user==null){
        throw new UpdateException("添加失败");
        }
        Integer row=userMapper.insert(user);
        return  row;
    }

    /*** 执行密码加密 * @param password 原始密码 * @param salt 盐值 * @return 加密后的密文 */
    private String getMd5Password(String password, String salt) {
        /** 加密规则： * 1、无视原始密码的强度 * 2、使用UUID作为盐值，在原始密码的左右两侧拼接 * 3、循环加密3次 */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
