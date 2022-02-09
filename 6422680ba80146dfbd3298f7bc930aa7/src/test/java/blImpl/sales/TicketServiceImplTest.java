package blImpl.sales;

import com.example.cinema.CinemaApplication;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.RefundStrategyForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CinemaApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class TicketServiceImplTest {
    @Autowired
    TicketService ticketService;

    @Test
    public void getTicketByUserTest(){
        List<Ticket> ticketList=ticketService.selectTicketByUser(3);
        System.out.println(ticketList.get(0).getId());
    }

    @Test
    public void addRefundStrategyTest(){
        RefundStrategyForm refundStrategyForm=new RefundStrategyForm();
        refundStrategyForm.setTime(6);
        System.out.println(ticketService.addRefundStrategy(refundStrategyForm));
    }

    @Test
    public void updateRefundStrategyTest(){
        RefundStrategyForm refundStrategyForm=new RefundStrategyForm();
        refundStrategyForm.setId(1);
        refundStrategyForm.setTime(6);
        System.out.println(ticketService.updateRefundStrategy(refundStrategyForm));
    }

    @Test
    public void refundTicketsTest(){
        List<Integer> ticketsid=new ArrayList<Integer>();
        ticketsid.add(74);
        System.out.println(ticketService.refundTickets(ticketsid));
    }
}