package com.ghg.tobacco.presenter;

import com.ghg.tobacco.bean.User;
import com.ghg.tobacco.mode.LoginModel;
import com.ghg.tobacco.mode.imple.LoginModelImple;
import com.ghg.tobacco.view.LoginView;

/**
 * Description: 主导器处理model的view
 * Author:veidy
 * Date: 2015-03-02
 * Time: 11:39
 */
public class LoginPresenter implements  com.ghg.tobacco.mode.OnLoginListener {

    private LoginModel loginModel;
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImple();
    }


    public void login() {
        String name = loginView.getName();
        String password = loginView.getPassword();
        loginView.showDialog(true);
        loginModel.login(name, password, this);
    }

    @Override
    public void onUsernameError() {
        loginView.showDialog(false);
        loginView.showToast("用户名错误");
    }

    @Override
    public void onPasswordError() {
        loginView.showDialog(false);
        loginView.showToast("密码错误");
    }

    @Override
    public void onSuccess(User user) {
        loginView.showDialog(false);
        loginView.moveToIndex();
    }

    @Override
    public void onFailure() {
        loginView.showDialog(false);
        loginView.showToast("请求失败");
    }
}
