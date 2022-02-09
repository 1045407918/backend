package blImpl.promotion;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.po.VIPDiscountStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class VIPServiceImplTest {

    @Autowired
    VIPService vipService;

    @Test
    public void addVIPDiscountStrategyTest(){
        int quota=10000;
        int gift=5000;
        System.out.println(vipService.addVIPDiscountStrategy(quota,gift));
    }

    @Test
    public void updateStrategyTest(){
        VIPDiscountStrategy vipDiscountStrategy=new VIPDiscountStrategy();
        vipDiscountStrategy.setId(1);
        vipDiscountStrategy.setGift(200);
        vipDiscountStrategy.setQuota(1500);
        System.out.println(vipService.updateStrategy(vipDiscountStrategy));
    }
}