package com.example.cinema.bl.user;

import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存再session中
     * @return
     */
    UserVO login(UserForm userForm);

    /**
     * 添加员工信息
     * @param userForm
     * @return
     */
    ResponseVO addStaff(UserForm userForm);

    /**
     * 修改员工信息
     * @param userForm
     * @return
     */
    ResponseVO updateStaffInfo(UserForm userForm);

    /**
     * 根据id查询员工信息
     * @param userId
     * @return
     */
    ResponseVO searchStaffById(int userId);

    /**
     * 查询所有员工信息
     * @return
     */
    ResponseVO searchAllStaff();

    /**
     * 根据id删除员工
     * @param userId
     * @return
     */
    ResponseVO delStaffById(int userId);



}
