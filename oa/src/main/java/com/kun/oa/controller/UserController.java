package com.kun.oa.controller;

import com.kun.oa.entity.User;
import com.kun.oa.service.impl.UserServiceimpl;
//import com.kun.oa.util.JsonResult.JsonResult;
import com.kun.oa.util.JsonResult.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController extends BaseController{
    Integer code=200;
    @Autowired
    private UserServiceimpl userServiceimpl;

    @Autowired
    private JsonResult jsonResult;

    @RequestMapping("login")
    @ResponseBody
    public JsonResult<User> login( String username, String password){
       User userData= userServiceimpl.login(username,password);
        jsonResult.setState(code);
        jsonResult.setData(userData);
        JsonResult data=jsonResult;
        return data;
    }


    @RequestMapping("insertPeople")
    @ResponseBody
    public JsonResult insertPeople( User user){
        Integer userData= userServiceimpl.insertPeople(user);

        jsonResult.setState(code);

        return jsonResult;
    }


    @RequestMapping("findPeople")
    @ResponseBody
    public JsonResult findPeople( ){
        List<User> userData= userServiceimpl.findPeople();
        jsonResult.setData(userData);
        jsonResult.setState(code);

        return jsonResult;
    }

    @RequestMapping("delePeople")
    @ResponseBody
    public JsonResult delePeople(Integer id ){
        Integer userData= userServiceimpl.delePeople(id);

        jsonResult.setState(code);

        return jsonResult;
    }
}
