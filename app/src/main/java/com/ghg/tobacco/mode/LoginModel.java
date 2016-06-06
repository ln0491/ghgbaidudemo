package com.ghg.tobacco.mode;

/**
 * Description: 登录接口
 * Author:veidy
 * Date: 2015-03-02
 * Time: 11:39
 */
public interface LoginModel {

    void login(String name, String password, OnLoginListener onLoginListener);
}
