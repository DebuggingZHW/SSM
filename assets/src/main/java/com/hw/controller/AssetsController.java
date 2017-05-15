package com.hw.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hw.domain.Assets;
import com.hw.service.IAssetsService;

@Controller
@RequestMapping("/user")

@RunWith(SpringJUnit4ClassRunner.class) //junit测试需要，表示继承了SrpingJunit4ClassRunner类
@ContextConfiguration(locations={"classpath:spring-mybatis.xml"})
public class AssetsController {
	@Autowired
	private IAssetsService assetsService;
	
	private static Logger logger = Logger.getLogger(Test.class);
	
	/* 获取所有用户列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllAssets")
	public String getAllAssets(HttpServletRequest request, Model model) {
		List<Assets> assets = this.assetsService.getAllUsers();
		model.addAttribute("userList",assets);
		request.setAttribute("userList", assets);
		return "/allAssets";
	}
	/* 获取个人用户列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPersonalAssets")
	public String getPersonalAssets(HttpServletRequest request, Model model) {
		String username = "hanwei";
		List<Assets> assets = this.assetsService.getAssetsByName(username);
		model.addAttribute("personList",assets);
		request.setAttribute("personList", assets);
		return "/PersonalAssets";
	}
	/*
	 * 跳转到添加用户界面
	 * @return
	 */
	@RequestMapping("/toAddAssets")
	public String toAddAssets(HttpServletRequest request,Assets assets) {
		String id = request.getParameter("id");
		if ("mine".equals(id)) {
			assets.setUsername("管理员("+request.getRemoteAddr()+")");
		}

		return "/addAssets";
	}
	/*
	 * 跳转到添加个人用户界面
	 * @return
	 */
	@RequestMapping("/toAddPersonalAssets")
	public String toAddPersonalAssets(HttpServletRequest request,Assets assets) {
		String id = request.getParameter("id");
		if ("mine".equals(id)) {
			assets.setUsername("hanwei");
		}
		
		return "/PersonalAddAssets";
	}
	/*
	 * 添加用户并重定向
	 * @param assets
	 * @param model
	 * @return
	 */
	@RequestMapping("/addAssets")
	public String addAssets(Assets assets,Model model,HttpServletRequest request) {
		assetsService.insert(assets);
		return "redirect:/user/getAllAssets";
		
	}
	/*
	 * 添加个人用户并重定向
	 * @param assets
	 * @param model
	 * @return
	 */
	@RequestMapping("/personalAddAssets")
	public String personalAddAssets(Assets assets,Model model,HttpServletRequest request) {
		assetsService.insert(assets);
		return "redirect:/user/getPersonalAssets";
		
	}
	/*
	 * 编辑用户材料
	 * @param assets
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateAssets")
	public String updateAssets(Assets assets,HttpServletRequest request,Model model) {
		if (assetsService.update(assets) > -1) {
			assets = assetsService.getAssetsById(assets.getUserid());
			request.setAttribute("assets", assets);
			model.addAttribute("assets", assets);		
			return "redirect:/user/getAllAssets";
		} else {
			return "/error";
		}
	}
	/*
	 * 编辑用户材料
	 * @param assets
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePersonalAssets")
	public String updatePersonalAssets(Assets assets,HttpServletRequest request,Model model) {
		if (assetsService.update(assets) > -1) {
			assets = assetsService.getAssetsById(assets.getUserid());
			request.setAttribute("assets", assets);
			model.addAttribute("assets", assets);		
			return "redirect:/user/getPersonalAssets";
		} else {
			return "/error";
		}
	}
	/*
	 * 根据id查询单个用户
	 * @param request
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/getUser")
	public String getUser(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		Integer userid = Integer.parseInt(id);
		request.setAttribute("assets",this.assetsService.getAssetsById(userid));
		model.addAttribute("assets", this.assetsService.getAssetsById(userid));

		return "/editUser";
	}
	/*
	 * 根据id查询单个个人用户
	 * @param request
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/getPersonalUser")
	public String getPersonalUser(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		Integer userid = Integer.parseInt(id);
		request.setAttribute("assets",this.assetsService.getAssetsById(userid));
		model.addAttribute("assets", this.assetsService.getAssetsById(userid));

		return "/PersonalEditUser";
	}
	/*
	 * 删除用户
	 * @param request
	 * @param userid
	 * @param model
	 * @return
	 */
	@RequestMapping("/delUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response) {
		String id = request.getParameter("id");
		Integer userid = Integer.parseInt(id);
		String result = "{\"result\":\"error\"}"; 
		if (assetsService.delete(userid) > -1) {
			result = "{\"result\":\"success\"}";
		}
		response.setContentType("application/json");
        try {  
            PrintWriter out = response.getWriter();  
            out.write(result);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	/*
	 * 导出管理员版Excel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/Export")
	public void export(HttpServletRequest request,Model model,HttpServletResponse response) throws IOException {

		List<Assets> assets = this.assetsService.getAllUsers();
		//创建webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet，对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("资产管理表");
		//在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		//创建单元格，设置表头
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("姓名ID");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("资产");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("资产数目");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("资产明细");
		cell.setCellStyle(style);
		
//		String result = "{\"result\":\"error\"}"; 
		
		for (int i = 0; i < assets.size(); i ++) {
			row = sheet.createRow((int)i + 1);
			Assets ass = (Assets)assets.get(i);
			//创建单元格，并设置值
			row.createCell((short) 0).setCellValue((Integer)ass.getUserid());
			row.createCell((short) 1).setCellValue((String)ass.getUsername());
			row.createCell((short) 2).setCellValue((String)ass.getAsset());
			row.createCell((short) 3).setCellValue((String)ass.getAssetnum());
			row.createCell((short) 4).setCellValue((String)ass.getAssetinfo());
		}
		
		String fileName = "导出.xls";
		fileName = new String(fileName.getBytes("UTF-8"),"GBK");
		response.reset();
		response.setHeader("Content-Dispostion", "attachment;filename="+fileName);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream outputStream = response.getOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
		
		try {
//			FileOutputStream outputStream = new FileOutputStream(new File(fileName));
			bufferedOutputStream.flush();
			wb.write(bufferedOutputStream);
			bufferedOutputStream.close();
			
			/*result = "{\"result\":\"success\"}";
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
            out.write(result);
			outputStream.close();*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/*
	 * 导出个人版Excel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/personalExport")
	public void personalExport(HttpServletRequest request,Model model,HttpServletResponse response) throws IOException {
		String username = "hanwei";
		List<Assets> assets = this.assetsService.getAssetsByName(username);
		//创建webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet，对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("资产管理表");
		//在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		//创建单元格，设置表头
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("姓名ID");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("资产");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("资产数目");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("资产明细");
		cell.setCellStyle(style);
		
		for (int i = 0; i < assets.size(); i ++) {
			row = sheet.createRow((int)i + 1);
			Assets ass = (Assets)assets.get(i);
			//创建单元格，并设置值
			row.createCell((short) 0).setCellValue((Integer)ass.getUserid());
			row.createCell((short) 1).setCellValue((String)ass.getUsername());
			row.createCell((short) 2).setCellValue((String)ass.getAsset());
			row.createCell((short) 3).setCellValue((String)ass.getAssetnum());
			row.createCell((short) 4).setCellValue((String)ass.getAssetinfo());
		}
		
		String fileName = "个人.xls";
		fileName = new String(fileName.getBytes("UTF-8"),"GBK");
		response.reset();
		response.setHeader("Content-Dispostion", "attachment;filename="+fileName);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		OutputStream outputStream = response.getOutputStream();
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
		
		try {
			bufferedOutputStream.flush();
			wb.write(bufferedOutputStream);
			bufferedOutputStream.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
