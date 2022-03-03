package com.imooc.controller;

import com.imooc.server.WebSocketServer;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * 手动推送消息到前端
 * @author Mengdl
 * @date 2022/02/22
 */
@RestController
public class DemoController {

    @GetMapping("index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }

    @GetMapping("page")
    public ModelAndView page(){
        return new ModelAndView("websocket");
    }

    @RequestMapping("/push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return ResponseEntity.ok("MSG SEND SUCCESS");
    }

    public static void main(String[] args) throws ParseException {
//        Date date = DateUtils.parseDate("2022-02-26 23:59:59", "yyyy-MM-dd HH:mm:ss");
//        System.out.println(date);
//        LocalDateTime now = LocalDateTime.now();
//        long day = ChronoUnit.DAYS.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now());
//        System.out.println(day);
//        if (now.isBefore(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())){
//            System.out.println(123);
//        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        System.out.println(sdf.format(mondayDate) + " 00:00:00");
    }

}
