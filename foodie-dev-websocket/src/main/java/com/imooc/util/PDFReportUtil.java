package com.imooc.util;

import com.google.common.collect.Maps;
import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Mengdl
 * @date 2022/03/02
 */
@Slf4j
public class PDFReportUtil {



    /**
     * 根据模板生成pdf
     *@param pdfName 文件名
     * @param data Map(String,Object)
     * @return 文件保存全路径文件
     */
    public static String createPDF(String pdfName, Map<String, Object> data) {
        PdfReader reader = null;
        AcroFields s = null;
        PdfStamper ps = null;
        ByteArrayOutputStream bos = null;
        String realPath = "D:\\temp\\pdf\\bbb";
        String dateFolder = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String folderPath = realPath + File.separator + dateFolder;
        //创建上传文件目录
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        String fileName = pdfName + "_" + DateFormatUtils.format(new Date(), "yyyyMMddhhmmss") + ".pdf";
        String savePath = folderPath + File.separator + fileName;
        try {
            String file = "D:\\temp\\pdf\\comfirmTemplate.pdf";
            //设置字体
            reader = new PdfReader(file);
            bos = new ByteArrayOutputStream();
            ps = new PdfStamper(reader, bos);
            s = ps.getAcroFields();
            BaseFont bfChinese = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            s.addSubstitutionFont(bfChinese);
            for (String key : data.keySet()) {
                if (data.get(key) != null) {
                    s.setField(key, data.get(key).toString());
                }
            }
            ps.setFormFlattening(true);
            ps.close();
            FileOutputStream fos = new FileOutputStream(savePath);
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
            return savePath;
        } catch (IOException | DocumentException e) {
            log.error("读取文件异常");
            e.printStackTrace();
            return "";
        } finally {
            try {
                bos.close();
                reader.close();
            } catch (IOException e) {
                log.error("关闭流异常");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Object> map = Maps.newConcurrentMap();
        map.put("1", "测试");
        map.put("2", "fei测试");
        createPDF("1", map);
    }

}
