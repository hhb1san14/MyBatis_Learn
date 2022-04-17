package com.hhb.dao;

import com.hhb.pojo.User;

import java.util.List;

/**
 * @author hhb
 * @date 2022/4/17
 * @description
 */
public interface UserMapper {

    /**
     * 获取全部用户信息
     *
     * @return
     */
    List<User> queryAll();

    /**
     * 通过ID获取信息
     *
     * @param
     * @return
     */
    User selectById(User user);
}