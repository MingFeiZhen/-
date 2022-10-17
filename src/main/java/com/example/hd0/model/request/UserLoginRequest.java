package com.example.hd0.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = -8532441154112778782L;  //防止序列化过程中出现冲突

    private String loginAccount;

    private String userPassword;

    private String checkPassword;
}
