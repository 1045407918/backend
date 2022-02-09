package blImpl.management.hall;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallInfoForm;
import com.example.cinema.vo.HallVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class HallServiceImplTest {
    @Autowired
    HallService hallService;

    @Test
    public void addOneHallTest(){
        HallInfoForm hallInfoForm=new HallInfoForm();
        hallInfoForm.setName("4号厅");
        hallInfoForm.setColumn(5);
        hallInfoForm.setRow(5);
        System.out.println(hallService.addOneHall(hallInfoForm));
        Assert.assertEquals(5,hallService.getHallById(4).getColumn(),0);
    }

    @Test
    public void changeHallInfoTest(){
        HallVO hallVO=new HallVO();
        hallVO.setId(4);
        hallVO.setColumn(12);
        hallVO.setRow(12);
        System.out.println(hallService.changeHallInfo(hallVO));
        Assert.assertEquals(5,hallService.getHallById(3).getColumn(),0);
    }
}
