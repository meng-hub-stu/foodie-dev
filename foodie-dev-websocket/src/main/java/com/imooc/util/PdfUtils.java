package com.imooc.util;

import com.alibaba.fastjson.JSON;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mengdl
 * @date 2022/03/04
 */
public class PdfUtils {

	private static final String DATA_MAP = "dataMap";
	private static final String IMG_MAP = "imgMap";

	/**
	 * 删除文件夹
	 * @param filePath 路径
	 */
	public static void delFilePath(String filePath){
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			return ;
		}
		if (dirFile.isFile()) {
			dirFile.delete();
			return ;
		} else {
			for (File file : dirFile.listFiles()) {
				file.delete();
			}
		}
		dirFile.delete();
	}

	/**
	 * 下载文件
	 * @param response 响应请求
	 * @param srcSource 文件地址
	 */
	public static void downloadFile(HttpServletResponse response, String srcSource){
		File file = new File(srcSource);
		// 取得文件名。
		String fileName = file.getName();
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);
			response.reset();
			response.setCharacterEncoding("UTF-8");
			// 设置强制下载不打开
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
			response.setHeader("Content-Length", String.valueOf(file.length()));

			byte[] b = new byte[1024];
			int len;
			while ((len = fis.read(b)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
			response.flushBuffer();
			fis.close();
		}catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 通过模板生成pdf
	 *
	 * @param text         文本字段
	 * @param img          照片
	 * @param templatePath 模板路径
	 * @param savePath     存储的文件夹路径
	 * @return resultPath 最后存储的路径
	 */
	public static String createTemplatePdf(Object text, Object img, String templatePath, String savePath) {
		Map map1 = JSON.parseObject(JSON.toJSONString(text), Map.class);
		Map map2 = JSON.parseObject(JSON.toJSONString(img), Map.class);
		Map<String, Object> data = new HashMap(2);
		data.put(DATA_MAP, map1);
		data.put(IMG_MAP, map2);
		String dateFolder = DateFormatUtils.format(new Date(), "yyyyMMdd");
		String folderPath = savePath + File.separator + dateFolder;
		//创建上传文件目录
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String fileName = "1" + "_" + DateFormatUtils.format(new Date(), "yyyyMMddhhmmss") + ".pdf";
		String resultPath = folderPath + File.separator + fileName;
		PdfReader reader = null;
		FileOutputStream out = null;
		ByteArrayOutputStream bos = null;
		PdfStamper stamper = null;
		PDDocument pdDocument = null;
		try {
			pdDocument = PDDocument.load(new File(templatePath));
			int pages = pdDocument.getNumberOfPages();
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
			out = new FileOutputStream(resultPath);
			reader = new PdfReader(templatePath);
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			for (Object key : form.getFields().keySet()) {
				//设置字体
				form.setFieldProperty((String) key, "textfont", bf, null);
				//设置字体大小
				form.setFieldProperty((String) key, "textsize", new Float(10), null);
			}
			//文字类的内容处理
			Map<String, String> dataMap = (Map<String, String>) data.get(DATA_MAP);
			if (dataMap != null) {
				for (String key : dataMap.keySet()) {
					String value = dataMap.get(key);
					form.setField(key, value);
				}
			}
			//图片类的内容处理
			Map<String, String> imgMap = (Map<String, String>) data.get(IMG_MAP);
			if (imgMap != null) {
				for (String key : imgMap.keySet()) {
					String value = imgMap.get(key);
					String imgPath = value;
					int pageNo = form.getFieldPositions(key).get(0).page;
					Rectangle signRect = form.getFieldPositions(key).get(0).position;
					float x = signRect.getLeft();
					float y = signRect.getBottom();
					//根据路径读取图片
					Image image = Image.getInstance(imgPath);
					//获取图片页面
					PdfContentByte under = stamper.getOverContent(pageNo);
					//图片大小自适应
					image.scaleToFit(signRect.getWidth(), signRect.getHeight());
					//添加图片
					image.setAbsolutePosition(x, y);
					under.addImage(image);
				}
			}
			// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
			stamper.setFormFlattening(true);
			stamper.close();
			Document doc = new Document();
			PdfCopy copy = new PdfCopy(doc, out);
			doc.open();
			for (int i = 1; i <= pages; i++) {
				PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), i);
				copy.addPage(importPage);
			}
			doc.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (reader != null) {
					reader.close();
				}
				if (out != null) {
					out.close();
				}
				if (pdDocument != null) {
					pdDocument.close();
				}
				if (stamper != null) {
					stamper.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultPath;
	}

	@Data
	@Builder
	@ApiModel(value = "合同对象", description = "Pact对象")
	public static class Pact {
		/**
		 * 双包无忧服务合同头部资料
		 */
		@ApiModelProperty(value = "合同编号")
		private String pactNo;
		@ApiModelProperty(value = "经销商")
		private String dealer;
		@ApiModelProperty(value = "经销商代码")
		private String dealerCode;
		@ApiModelProperty(value = "经销商地址")
		private String dealersAddress;
		@ApiModelProperty(value = "所属公司")
		private String company;
		/**
		 * 客户资料
		 */
		@ApiModelProperty(value = "客户姓名")
		private String customer;
		@ApiModelProperty(value = "身份证号")
		private String identity;
		@ApiModelProperty(value = "联系电话")
		private String mobile;
		@ApiModelProperty(value = "联系地址")
		private String customerAddress;
		@ApiModelProperty(value = "邮政编码")
		private String postalCode;
		@ApiModelProperty(value = "电子邮箱")
		private String email;
		/**
		 * 车辆信息
		 */
		@ApiModelProperty(value = "生产厂商")
		private String manufacturer;
		@ApiModelProperty(value = "车型")
		private String carModel;
		@ApiModelProperty(value = "购买总价（含税）")
		private String buyPrice;
		@ApiModelProperty(value = "车辆识别代码")
		private String carCode;
		@ApiModelProperty(value = "发动机号")
		private String engineNo;
		@ApiModelProperty(value = "购买日期")
		private String buyDate;
		/**
		 * 双包无忧服务合同资料
		 */
		@ApiModelProperty(value = "双保计划")
		private String protectPlan;
		@ApiModelProperty(value = "服务合同价格")
		private String contractPrice;
		@ApiModelProperty(value = "延保服务生效日期")
		private String extendWarrantyStartDate;
		@ApiModelProperty(value = "延保服务结束日期")
		private String extendWarrantyEndDate;
		@ApiModelProperty(value = "保修期限")
		private String warrantyDate;
		@ApiModelProperty(value = "免费保养生效日期")
		private String maintenanceStartDate;
		@ApiModelProperty(value = "免费保养结束日期")
		private String maintenanceEndDate;
		@ApiModelProperty(value = "忠诚客户维修优惠服务生效日期")
		private String reducedStartDate;

		@ApiModelProperty(value = "销售顾问")
		private String salesConsultant;
		@ApiModelProperty("产品")
		private String product;
		@ApiModelProperty(value = "开票日期")
		private String invoiceDate;
	}

	/**
	 * 合同中存在的照片
	 */
	@Data
	@Builder
	public static class PactImg {
		@ApiModelProperty(value = "第一张图片")
		private String imgOne;
		@ApiModelProperty(value = "第二张图片")
		private String imgTwo;
	}

}
