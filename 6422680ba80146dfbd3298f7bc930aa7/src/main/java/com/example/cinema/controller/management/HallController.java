package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallInfoForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
@RequestMapping("/hall")
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping("/all")
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @PostMapping("/addHall")
    public ResponseVO addHall(@RequestBody HallInfoForm hallInfoForm){return hallService.addOneHall(hallInfoForm);}

    @PostMapping("/changeHallInfo")
    public ResponseVO modifyHallInfo(@RequestBody HallVO hallVO){return  hallService.changeHallInfo(hallVO);}

    @PostMapping("/deleteHallInfo/{hallID}")
    public ResponseVO deleteHall(@PathVariable int hallID){return hallService.deleteHallInfo(hallID);}

}
