package com.example.cinema.data.user;

import com.example.cinema.po.User;
import com.example.cinema.vo.UserForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Mapper
public interface AccountMapper {

    /**
     * 创建一个新的账号
     * @param username
     * @param password
     * @param userlevel
     * @return
     */
    public int createNewAccount(@Param("username") String username, @Param("password") String password, @Param("userlevel") int userlevel);

    /**
     * 根据用户名查找账号
     * @param username
     * @return
     */
    public User getAccountByName(@Param("username") String username);


    /**
     * 根据id查找员工
     * @param staffId
     * @return
     */
    User selectStaffById(@Param("staffId") int staffId);
    /**
     * 展示所有员工
     * @return
     */
    List<User> selectAllStaff();
    /**
     * 修改员工信息
     * @param updateStaffForm
     * @return
     */
    int updateStaff(UserForm updateStaffForm);
    /**
     * 根据ID删除员工信息
     * @param id
     * @return
     */
    int delStaffById(@Param("id") int id);
}
