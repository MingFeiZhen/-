package com.example.hd0.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.hd0.model.UserTable;
import com.example.hd0.model.request.UserLoginRequest;
import com.example.hd0.model.request.UserRegisterRequest;
import com.example.hd0.service.UserTableService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.example.hd0.contant.UserContant.ADMIN_ROLE;
import static com.example.hd0.contant.UserContant.USER_LOGIN_STATE;

@RestController  //返回的数据类型默认为json,使用编写restful风格的api
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserTableService userTableService;

    //controller层倾向于对于请求数据参数本身的校验,不涉及业务逻辑本身的校验

    @PatchMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null){
            return null;
        }
        //取数据
        String loginAccount = userRegisterRequest.getLoginAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        //如果三个参数有任何一个为空
        if (StringUtils.isAnyBlank(loginAccount, userPassword, checkPassword)) {
            return null;
        }

        return userTableService.userRegister(loginAccount, userPassword, checkPassword);   //auto file 插件自动填充参数
        
    }

    @PatchMapping("/login")
    public UserTable userLogin(@RequestBody UserLoginRequest userLoginRequest , HttpServletRequest request) {
        if (userLoginRequest == null){
            return null;
        }
        //取数据
        String loginAccount = userLoginRequest.getLoginAccount();
        String userPassword = userLoginRequest.getUserPassword();

        //如果三个参数有任何一个为空
        if (StringUtils.isAnyBlank(loginAccount, userPassword)) {
            return null;
        }

        return userTableService.Login(loginAccount,userPassword, request);   //auto file 插件自动填充参数

    }

    @GetMapping("/search")
    public List<UserTable> searchUsers(String username,HttpServletRequest request){
        if (!isAdmin(request)){
            return new ArrayList<>();
        }

        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(username)){
            queryWrapper.like("username", username);
        }
        return userTableService.list(queryWrapper);
    }

    //逻辑删除。  逻辑删除就是更新
    @PostMapping("/delete")
    public boolean deleteUsers(@RequestBody long id,HttpServletRequest request){
        if (!isAdmin(request)){
            return false;
        }
        if (id <=0){
            return false;
        }

        return userTableService.removeById(id);
    }

    //
    private boolean isAdmin(HttpServletRequest request){
        //管理员查询权限
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserTable userTable = (UserTable) userObject;
        return userTable != null && userTable.getUserRole() == ADMIN_ROLE;
    }

}
