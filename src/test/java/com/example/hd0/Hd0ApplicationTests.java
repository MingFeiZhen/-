package com.example.hd0;



import com.example.hd0.model.UserTable;
import com.example.hd0.service.UserTableService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
//@RunWith()
class Hd0ApplicationTests {

    //Resource会默认按照Java的名称去注入属性，
    //如果是Autowired的话，只会按照类型去注入属性，
    //所以一般用Resource
    @Resource
    private UserTableService userTableService;

    @Test
    public void textAddUser(){
        UserTable userTable = new UserTable();
        userTable.setUsername("zs");
        userTable.setLoginAccount("123");
        userTable.setAvatarUrl("http://www.baidu.com/link?url=iq6H1orRsfGS3_R8VhYnepzLCYwqf8dcvy0QGUoX1t3G0wk1d5CXfDPJ0ROXs6PQUwS0oWfijLJsiv2FuIu6nwBqbzTk-716webQGtpJkqiNLRhof7Af54ldguUkjaSdq6pfIp3lIZ1sy5RUwMkvJhI3YHnbQa6rhEFzl3YuVAesGVViVqU5TxpmYptEJGfUZFMvBXPzUmxFgbcJWzvXbzeYIey0obe9x0REcYqEj5Vim-sUvb6XEu5yljpaOZoXCR4-HwdnK5OxY9IQEb65vp8iazmOpSnfR8yjhEy_0c3EO9i65PAUmzSXhgT56ry4w2GJu_YdLy0CaVeVoJDOBOn_CaO89OLJgDdoj5uJpZE3rLt8-g_y36WsOO7I5JU4WAmQiZpPwaWXC_CNwWw7Db6S9_N_xErgv4W8Fdw3CqRbq7vRUFjIRZWISmY1OUGFg-TnPkz_ApN9Tm2JmFXGcfMgOe2608A7TUZ3C-wZWF5M2XGsBSRjXvt7BwZMfdD0zQkkKoU6-aRtpzQ9LaZUK7KzwdUVciPCARiqw_jd2-ZhWkcGWQ35XBJ05rXeOs7vWzqMy0Fc4S-WWrOeJFm5dj1sSsChEXxAGHGGnqaX8Mg-gwxIXWthJDA_OMXy66j3LR9a_ZKJD6KnWdouhnMnmt_8AupnjjXRsvxl6Gyhl3Fu1bckKte0kh-JhRx3Q2ppY2Afl7WGUySWxHNdNPevD7yW9sNGvJtPuk7dEawJnxo8dMnh3M-hzjieAAIneikq&wd=&eqid=a869ca380007964100000005633e88b8");
        userTable.setGender(0);
        userTable.setUserPassword("123");
        userTable.setPhone("110");
        userTable.setEmail("1368483431@qq.com");
        boolean result = userTableService.save(userTable);
        System.out.println(userTable.getId());
        Assertions.assertTrue(result);

    }
    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu";
        result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        userPassword = "123456";
        result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "dogyupi";
        checkPassword = "12345678";
        result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yupi";
        result = userTableService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);
    }
}


