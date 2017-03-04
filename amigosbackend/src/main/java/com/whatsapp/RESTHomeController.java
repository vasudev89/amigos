package com.whatsapp;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.blog.Blog;
import com.whatsapp.blog.BlogDAO;
import com.whatsapp.blog.BlogData;
import com.whatsapp.blog.BlogDataDAO;
import com.whatsapp.user.User;
import com.whatsapp.user.UserDAO;

@RestController
public class RESTHomeController {

	@Autowired
	UserDAO userdao;

	@Autowired
	BlogDAO blogdao;
	
	@Autowired
	BlogDataDAO blogdatadao;


	@RequestMapping(value = "/adduser" , method = RequestMethod.POST)
	public ResponseEntity<String> addUser(@RequestBody JSONObject data, Principal p) {
		System.out.println(data);

		JSONObject json = new JSONObject();
		
		List<User> listuser = userdao.listUser();

		boolean usermatch = false;

		for (User u : listuser) {
			if (u.getEmail().equals(data.get("Email").toString() )) {
				usermatch = true;
				break;
			}
		}
		if (usermatch == false) {
			User u = new User();
			
			u.setCity(data.get("City").toString());
			u.setCpassword(data.get("ConfirmPassword").toString());
			u.setDob(data.get("Date").toString());
			u.setEmail(data.get("Email").toString());
			u.setEnabled(true);
			u.setGender(data.get("Gender").toString());
			u.setPassword(data.get("Password").toString());
			u.setPhone(data.get("Phone").toString());
			u.setRole("ROLE_USER");
			u.setUsername(data.get("Username").toString());
			
			userdao.addUser(u);
			
			json.put("msg", "User Added Successfully");
			
		} else {
			json.put("msg", "User Already Exists");
		}
		
		
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/loginuser" , method = RequestMethod.POST)
	public ResponseEntity<String> loginUser(@RequestBody JSONObject data, Principal p) {
		System.out.println(data);

		JSONObject json = new JSONObject();
		
		List<User> listuser = userdao.listUser();

		boolean usermatch = false;

		for (User u : listuser) {
			if (u.getEmail().equals(data.get("Email").toString() ) && u.getPassword().equals(data.get("Password").toString() )) {
				usermatch = true;
				break;
			}
		}
		if (usermatch == false) {
			json.put("msg", "Invalid Login");
			
		} else {
			
			json.put("msg", "Login Successful");
			
		}
		
		
		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}

		

}