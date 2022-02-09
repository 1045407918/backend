package com.example.cinema.controller.user;

import com.example.cinema.blImpl.user.AccountServiceImpl;
import com.example.cinema.config.InterceptorConfiguration;
import com.example.cinema.vo.UserForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    @Autowired
    private AccountServiceImpl accountService;
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        UserVO user = accountService.login(userForm);
        if(user==null){
            System.out.println("搞错了");
           return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
        }
        //注册session
        session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
        return ResponseVO.buildSuccess(user);
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @RequestMapping(value = "/staff/add", method = RequestMethod.POST)
    public ResponseVO addStaff(@RequestBody UserForm staffForm){
        return accountService.addStaff(staffForm);
    }

    @RequestMapping(value = "/staff/all", method = RequestMethod.GET)
    public ResponseVO searchAllStaff(){
        return accountService.searchAllStaff();
    }

    @RequestMapping(value = "/staff/all/{staffId}", method = RequestMethod.GET)
    public ResponseVO getStaffInfo(@PathVariable int staffId){
        return accountService.searchStaffById(staffId);
    }

    @RequestMapping(value = "/staff/update",method = RequestMethod.POST)
    public ResponseVO updateStaff(@RequestBody UserForm updateStaffForm){
        return accountService.updateStaffInfo(updateStaffForm);
    }

    @RequestMapping(value = "/staff/del/{staffId}", method = RequestMethod.POST)
    public ResponseVO delStaff(@PathVariable int staffId){
        return accountService.delStaffById(staffId);
    }
}
