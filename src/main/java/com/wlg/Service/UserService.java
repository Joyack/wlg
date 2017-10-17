package com.wlg.Service;

import com.wlg.Model.PageBean;
import java.util.List;
import java.util.Map;

import com.wlg.Model.User;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

/**
 * Created by LvLiangFeng on 2016/11/23.
 */

public interface UserService {
    /**
     * 保存User实体
     * @param user
     * @return
     */
    int saveUser(User user);

    /**
     * 保存User实体
     * @param user
     * @return
     */
    int checkUserInfo(User user);

    /**
     * 保存User实体
     * @param user
     * @return
     */
    int saveUserByMeter(User user);

    /**
     * 保存User实体
     * @param user
     * @param role_id
     * @return
     */
    int saveUser(User user,String role_id);

    /**
     * 更新User实体
     * @param user
     * @return
     */
    int updateUserByself(User user, String oldPassword,PrincipalCollection principalCollection);

    /**
     * 更新User实体
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 更新User实体
     * @param user
     * @return
     */
    int updateUserByUserName(User user);

    /**
     * 更新User实体密码
     * @param user
     * @return
     */
    int updateUserPasswordByUserName(User user);

    /**
     * 删除User实体
     * @param user
     * @return
     */
    int deleteUser(User user);

    /**
     * 删除User实体
     * @param user
     * @return
     */
    int deleteUserByUserName(User user);

    /**
     * 根据实体类对象查找所有记录
     * @param user
     * @return
     */
    List<User> getUserList(User user);

    /**
     * 分页查询User列表
     * @param page 页数
     * @param user 实体对象
     * @return PageBean
     */
    PageBean sreachUserForPage(Integer page, User user);

    User getUserByUserName(String username);

    PageBean getUserListByParams(int page, int pageSize, Map<String,Object> map);

    public List<Map<String, Object>> getUserByUserName1(String username) ;

}