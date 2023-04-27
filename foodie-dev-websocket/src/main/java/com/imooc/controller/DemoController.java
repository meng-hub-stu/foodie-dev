package com.imooc.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Table;
import com.imooc.websocket.WebSocketServer;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * 手动推送消息到前端
 * @author Mengdl
 * @date 2022/02/22
 */
@RestController
public class DemoController {

    private static final ExecutorService executorService = SpringUtil.getBean(ExecutorService.class);

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

    public static void main(String[] args) throws Exception {
//        Date date = DateUtils.parseDate("2022-02-26 23:59:59", "yyyy-MM-dd HH:mm:ss");
//        System.out.println(date);
//        LocalDateTime now = LocalDateTime.now();
//        long day = ChronoUnit.DAYS.between(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now());
//        System.out.println(day);
//        if (now.isBefore(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())){
//            System.out.println(123);
//        }

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar cal = Calendar.getInstance();
//        cal.setFirstDayOfWeek(Calendar.MONDAY);
//        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
//        if(dayWeek==1){
//            dayWeek = 8;
//        }
//        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
//        Date mondayDate = cal.getTime();
//        System.out.println(sdf.format(mondayDate) + " 00:00:00");
//        Date date = new Date();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
//        String format = sf.format(date);
//        System.out.println(format);

//        ByteArrayOutputStream baos = new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE);
//        Document document = new Document(PageSize.A4);
//        PdfWriter writer = PdfWriter.getInstance(document, baos);
//        writer.setViewerPreferences(PdfWriter.AllowPrinting  | PdfWriter.PageLayoutSinglePage);
//        BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
//        Font font = new Font(bf, 12, Font.NORMAL);
//        document.open();
//        Paragraph p = new Paragraph("你好", font);
//        document.add(p);
//        document.add(new Paragraph("Test2"));
//        Table table = new Table(2, 3);
//        table.addCell(new Phrase("我好", font));
//        table.addCell("C2R1");
//        table.addCell("C1R2");
//        table.addCell("C2R2");
//        Cell c = (Cell) table.getElement(0, 0);
//        c.setVerticalAlignment("Middle");
//        c.setBackgroundColor(new Color(255, 0, 0));
//        c.setHorizontalAlignment("Center");
//        document.add(table);
//        document.close();
//        baos.writeTo(new FileOutputStream("F:\\test.pdf"));

//
//
//
//    finally {
//        try {
//            if (bos != null) {
//                bos.close();
//            }
//            if (reader != null) {
//                reader.close();
//            }
//            if (out != null) {
//                out.close();
//            }
//            if (pdDocument != null) {
//                pdDocument.close();
//            }
//            if (stamper != null) {
//                stamper.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("关闭流异常");
//        }


//     System.out.println("main函数开始执行");
//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("===task start===");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("===task finish===");
//            }
//        });
//
//        thread.start();
//        executorService.submit(()->{
//            System.out.println("测试");
//        });
//  System.out.println("main函数执行结束");
    }


}
