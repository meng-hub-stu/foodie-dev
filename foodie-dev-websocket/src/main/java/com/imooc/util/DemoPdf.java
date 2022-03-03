package com.imooc.util;

import com.itextpdf.awt.AsianFontMapper;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mengdl
 * @date 2022/03/02
 */
public class DemoPdf {

    /**
     * 生成PDF文件
     * @param map 要往模版中填充的数据
     * @param sourceFile 模版PDF文件的路径
     * @param targetFile 填充数据以后，生成的PDF的文件路径
     * @throws IOException
     */
    private static void genPdf(HashMap map, String sourceFile, String targetFile) throws IOException {
        File templateFile = new File(sourceFile);
        fillParam(map, FileUtils.readFileToByteArray(templateFile), targetFile);
    }

    /**
     * Description: 使用map中的参数填充pdf，map中的key和pdf表单中的field对应
     */
    public static void fillParam(Map<String, String> fieldValueMap, byte[] file, String contractFileName) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(contractFileName);
            PdfReader reader = null;
            PdfStamper stamper = null;
            BaseFont base;
            try {
                reader = new PdfReader(file);
                stamper = new PdfStamper(reader, fos);
                stamper.setFormFlattening(true);
                //获取字体文件,TTF_PATH表示字体文件的路径
                base = BaseFont.createFont(AsianFontMapper.ChineseSimplifiedFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                AcroFields acroFields = stamper.getAcroFields();
                for (Object key : acroFields.getFields().keySet()) {
                    //设置字体
                    acroFields.setFieldProperty((String) key, "textfont", base, null);
                    //设置字体大小
                    acroFields.setFieldProperty((String) key, "textsize", new Float(6), null);
                }
                if (fieldValueMap != null) {
                    for (String fieldName : fieldValueMap.keySet()) {
                        //填充图片
                        if (fieldName.equals("signImage")) {
                            //这里先获取图片的byte数据
//                            byte[] bytes = PDFUtil.getImage(fieldValueMap.get(fieldName));
                            byte[] bytes = null;
                            if (bytes != null) {
                                int pageNo = acroFields.getFieldPositions(fieldName).get(0).page;
                                Rectangle signRect = acroFields.getFieldPositions(fieldName).get(0).position;
                                float x = signRect.getLeft();
                                float y = signRect.getBottom();
                                // 读图片
                                Image image = Image.getInstance(bytes);
                                // 获取操作的页面
                                PdfContentByte under = stamper.getOverContent(pageNo);
                                // 根据域的大小缩放图片
                                image.scaleToFit(signRect.getWidth(), signRect.getHeight());
                                // 添加图片
                                image.setAbsolutePosition(x, y);
                                under.addImage(image);
                                stamper.setFormFlattening(true);
                            }
                        } else {
                            //填充其它文本数据
                            acroFields.setField(fieldName, fieldValueMap.get(fieldName));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (stamper != null) {
                    try {
                        stamper.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    reader.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }


    public void testTwo() throws IOException {
        //往map中填充数据，键就是我们在生成模版的时候，里面的fieldName，value就是要填充的数据
        HashMap map = new HashMap<String, String>();
        map.put("studentStatus","1");
        map.put("submitTime", "2");
        map.put("referenceNo","3");
        map.put("branch", "4");
        //填充图片，传入的是一个图片的url地址，但实际上，是要传下图片的byte[]数据，在工具类中，会先获取图片的byte数据，然后，再往pdf里面填充，具体可以参照工具方法中的备注
        map.put("signImage", "https://ls.resource.penrose.com.cn/");
        //模版PDF文件的地址
        String sourceFile = "C:\\Users\\admin\\Desktop\\contractForm.pdf";
        //填充以后，生成的PDF文件
        String targetFile = "C:\\Users\\admin\\Desktop\\testForm_fill.pdf";
        //调用工具方法
        genPdf(map, sourceFile, targetFile);
    }

}
