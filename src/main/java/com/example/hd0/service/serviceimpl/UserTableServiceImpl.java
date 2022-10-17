package com.example.hd0.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hd0.model.UserTable;
import com.example.hd0.service.UserTableService;
import com.example.hd0.mapper.UserTableMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.management.Query;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.hd0.contant.UserContant.USER_LOGIN_STATE;

/**用户服务实现类
 *
* @author zs

*/
@Service
@Slf4j
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable>
    implements UserTableService{

    @Resource
    private UserTableMapper userTableMapper;

    /**
     * 盐值,混淆密码->让人难以偷窃代码
     */
    private static final String SALT = "zs";

    /**
     * 用户登录态键，key，找到唯一的值
     */
    // public static final String USER_LOGIN_STATE = "userLoginState";



    @Override
    public long userRegister(String loginAccount, String userPassword, String checkPassword) {

        //1.校验
        // if (loginAccount == null || userPassword == null || checkPassword == null){ }
        if (StringUtils.isAnyBlank(loginAccount, userPassword, checkPassword)){

            return -1;
        }
        if (loginAccount.length() < 4 ){
            return -1;
        }
        if (userPassword.length() < 8 ){
            return -1;
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(loginAccount);
        if (matcher.find()) {
            return -1;
        }

        //校验密码和密码相同
        if (!userPassword.equals(checkPassword)){
            return -1;
        }

        //账户不重复
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginAccount",loginAccount);
        long count = userTableMapper.selectCount(queryWrapper);
        if (count > 0){
            return -1;
        }

        //2. 加密密码
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 插入数据
        UserTable userTable = new UserTable();
        userTable.setLoginAccount(loginAccount);
        userTable.setUserPassword(userPassword);
        boolean saveResult = this.save(userTable);
        if (!saveResult){
            return -1;
        }
        return userTable.getId();
    }

    @Override
    public UserTable Login(String loginAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        // if (loginAccount == null || userPassword == null || checkPassword == null){ }
        if (StringUtils.isAnyBlank(loginAccount, userPassword)){
            return null;
        }
        if (loginAccount.length() < 4 ){
            return null;
        }
        if (userPassword.length() < 8 ){
            return null;
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(loginAccount);
        if (matcher.find()) {
            return null;
        }

        //2. 加密密码
        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<UserTable> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginAccount",loginAccount);
        queryWrapper.eq("userPassword",userPassword);

        UserTable user = userTableMapper.selectOne(queryWrapper);//查询第一条数据试试
        //用户不存在
        if (user==null){
            log.info("usertable login ,查询数据报错");
            return null;
        }
        //U
        UserTable  safeUser = getsafeUser(user);

        //3.记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, user); //



        //4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);


        return safeUser;


    }
    //4.用户脱敏
    @Override
     public UserTable getsafeUser(UserTable originUser) {
        UserTable safeUser = new UserTable();
         safeUser.setId(originUser.getId());
         safeUser.setUsername(originUser.getUsername());
         safeUser.setLoginAccount(originUser.getLoginAccount());
         safeUser.setAvatarUrl(originUser.getAvatarUrl());
         safeUser.setGender(originUser.getGender());
         safeUser.setPhone(originUser.getPhone());
         safeUser.setEmail(originUser.getEmail());
         safeUser.setUserRole(originUser.getUserRole());
         safeUser.setUserStatus(originUser.getUserStatus());
         safeUser.setCreateTime(originUser.getCreateTime());
         return safeUser;
     }
}




