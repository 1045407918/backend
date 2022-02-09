package blImpl.user;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.user.AccountService;
import com.example.cinema.po.User;
import com.example.cinema.vo.UserForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;




@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class AccountServiceImplTest {
    @Autowired
    AccountService accountService;

    @Test
    public void addStaffTest(){
        UserForm userForm=new UserForm();
        userForm.setUsername("ycf");
        userForm.setPassword("123456");
        userForm.setUserlevel(2);
        System.out.println(accountService.addStaff(userForm));
    }
    @Test
    public void updateStaffTest(){
        UserForm userForm=new UserForm();
        userForm.setUsername("ycf");
        userForm.setPassword("ggwp");
        userForm.setUserlevel(1);
        System.out.println(accountService.updateStaffInfo(userForm));
    }
    @Test
    public void searchStaffByIdTest(){
        int id = 16;
        User user=(User)accountService.searchStaffById(id).getContent();
        System.out.println(user.getUsername());
    }

}
