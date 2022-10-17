package com.example.hd0.service;

import com.example.hd0.model.UserTable;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
* @author zs
* @description 针对表【user_table(用户)】的数据库操作Service
* @createDate 2022-10-05 22:22:45
*/
//定义方法不去实现
public interface UserTableService extends IService<UserTable> {



    /**
     * 用户注册
     *
     * @param loginAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return
     */
    long userRegister(String loginAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param loginAccount
     * @param userPassword
     * @return
     */
    UserTable Login(String loginAccount, String userPassword, HttpServletRequest request);


    //4.用户脱敏
    UserTable getsafeUser(UserTable originUser);
}
