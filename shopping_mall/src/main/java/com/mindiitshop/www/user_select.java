package com.mindiitshop.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

//user table(select, insert, update, delete)
@Repository("userselect")
public class user_select {
	@Resource(name="template")//dbconfig에 있는 template 이름
	private SqlSessionTemplate tm;
	
	public ArrayList<Object> search_id(String uname, String uemail) {
		ArrayList<Object> onedata = new ArrayList<Object>();
		Map<String, String> keycode = new HashMap<String, String>();
		keycode.put("part", "1");
		keycode.put("uname", uname);
		keycode.put("uemail", uemail);
		
		user_dao dao = tm.selectOne("Shopping_mall.search",keycode);
		System.out.println(dao.getUid());
		return onedata;
	}
}
