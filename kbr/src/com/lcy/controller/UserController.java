package com.lcy.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lcy.pojo.User;
import com.lcy.service.UserService;
import com.lcy.util.ExcelUtil;
/**
 * 
* @ClassName: UserController 
* @Description: 
* @author admin 
* @date 2018年3月7日 下午5:09:59
 */
@Controller
@RequestMapping("user")
public class UserController {
	@Autowired(required=true)
	@Qualifier("userService")
	private UserService userService;
	@RequestMapping("/user.htm")
	public String TestToJson(HttpServletRequest request,HttpServletResponse response) {
		List<User> users=userService.findUsers(null);
		request.setAttribute("userList", users);
		return "userList";
	}
	
	@RequestMapping("/deleteUser.htm")
	@ResponseBody
	public String  deleteUser(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("id") Integer id) {
	    userService.deleteUser(id);
		return "1";
	}
	
	@RequestMapping("/updateUser.htm")
	public String  updateUser(HttpServletRequest request,HttpServletResponse response,@ModelAttribute("id") Integer id) {
		List<User> users=userService.findUsers(id);
		User user=users.get(0);
	    request.setAttribute("user", user);
	    return "updateUser";
	}
	
	@RequestMapping("/Hello")
	@ResponseBody
	public String Helle() {
		return "Hello SSM";
	}
	@RequestMapping("/addUser.htm")
	@ResponseBody
	public String insertUser() {
		User user=new User();
		user.setAge(18);
		user.setName("李四");
		int idKey=userService.insertUser(user);
	    return String.valueOf(user.getId()) ;
	}
	@RequestMapping("/trueUpdateUser.htm")
	public String updateUser(HttpServletRequest request,HttpServletResponse response, @ModelAttribute User user) {
		userService.updateUser(user);
		return "redirect:/user/user.htm"; 
	}
	/*
	 * 批量查询用户列表并导出excel
	 */
	@RequestMapping("/findUser.htm")
	@ResponseBody
	public String findUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String codedFileName = java.net.URLEncoder.encode("用户信息-" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "UTF-8");
        // 生成提示信息
        response.setContentType("application/vnd.ms-excel");
        // 设置响应头
        response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls"); 
		List<Map<String,Object>> userList=userService.findUser();
		ExcelUtil excelUtil=new ExcelUtil();
		excelUtil.setHeads( new String[] {"序号","姓名","年龄"});
		excelUtil.setNames( new String[] {"id","name","age"});
		OutputStream outputStream=response.getOutputStream();
		excelUtil.write(userList, outputStream, "第一页");
		return  "操作成功";
	}
   /**
    * 
   * @author admin
   * @Description:用户信息批量导入 
   * @param:request,respone
   * @return String
   * @throws IOException
   * @date 2018年3月7日 下午5:10:06
    */
	@RequestMapping("/importUser.htm")
	public String importUser(HttpServletRequest request,HttpServletResponse response) throws IOException {
		String filePath="D:\\用户信息-20180307142549.xlsx";
		FileInputStream fileIn = new FileInputStream(filePath);
		ExcelUtil excelUtil=new ExcelUtil();
		excelUtil.setNames( new String[] {"name","age"});
		List<Map<String,Object>>list=excelUtil.read(fileIn);
		userService.importUser(list);
		return "redirect:/user/user.htm"; 

	}
}
