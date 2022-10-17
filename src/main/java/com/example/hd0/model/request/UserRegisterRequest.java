package com.example.hd0.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author zs
 */
@Data //生成get、set方法
public class UserRegisterRequest implements Serializable {  //Serializable 就是序列化


    private static final long serialVersionUID = -8532441154112778782L;  //防止序列化过程中出现冲突

    private String loginAccount;

    private String userPassword;

    private String checkPassword;

}

