package com.kun.oa.mapper;

import com.kun.oa.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /*** 插入用户数据 * @param user 用户数据 * @return 受影响的行数 */
    Integer insert(User user);
    /*** 根据用户名查询用户数据 * @param username 用户名 * @return 匹配的用户数据，如果没有匹配的数据，则返回null */
    User findByUsername(String username);
//查找所有用户
//删除人员
Integer delePeople(Integer id);
    //查询人员
    List<User> findPeople();
}
