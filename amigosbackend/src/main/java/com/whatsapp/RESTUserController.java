package com.whatsapp;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import com.whatsapp.user.User;
import com.whatsapp.user.UserDAO;

@RestController
public class RESTUserController {

	@Autowired
	UserDAO userdao;

	

	@Autowired
	ServletContext context;

	@RequestMapping(value = "/userdata" , method = RequestMethod.POST)
	public ResponseEntity<User> UserProfile(@RequestBody JSONObject data) {

		System.out.println(data);
		
		String username = data.get("Email").toString();
		
		User user = userdao.getUserByEmail(username);
		
		System.out.println(user);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "admin/allusers" , method = RequestMethod.GET)
	public ResponseEntity<List<User>> allUsers() {

		List<User> list = userdao.listUser();

		return new ResponseEntity<List<User>>(list, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/deleteuser" , method = RequestMethod.GET)
	public ResponseEntity<List<User>> deleteUser(@RequestBody String inputdata) {
		System.out.println(inputdata);

		userdao.deleteUser(Integer.parseInt(inputdata));

		List<User> list = userdao.listUser();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "/updateuser" , method = RequestMethod.POST)
	public ResponseEntity<String> updateUser(@RequestBody JSONObject data) {
		System.out.println(data);

		int userid = Integer.parseInt(data.get("UserId").toString());

		User user = userdao.getUserById(userid);

		user.setUserId(userid);
		user.setUsername(data.get("Username").toString());
		user.setPhone(data.get("Phone").toString());
		user.setCity(data.get("City").toString());
		user.setDob(data.get("DOB").toString());
		user.setGender(data.get("Gender").toString());

		userdao.updateUser(user);

		JSONObject json = new JSONObject();

		json.put("status", "Details Updated");
		System.out.println(json.toString());

		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}
	
	@RequestMapping( value = "admin/disableuser" , method = RequestMethod.POST)
	public ResponseEntity<List<User>> disableUser(@RequestBody String inputdata) {
		System.out.println(inputdata);

		User user = userdao.getUserById(Integer.parseInt(inputdata));
		user.setEnabled(false);
		userdao.updateUser(user);

		List<User> list = userdao.listUser();
		
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);

	}
	
	@RequestMapping(value = "admin/enableuser" , method = RequestMethod.POST)
	public ResponseEntity<List<User>> enableUser(@RequestBody String inputdata) {
		System.out.println("user id:"+inputdata);

		User user = userdao.getUserById(Integer.parseInt(inputdata));
		user.setEnabled(true);
		userdao.updateUser(user);

		List<User> list = userdao.listUser();
		
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);

	}
	
	@RequestMapping( value = "admin/makeadmin" ,  method = RequestMethod.POST)
	public ResponseEntity<List<User>> makeAdmin(@RequestBody String inputdata) {
		System.out.println(inputdata);
		
		User user = userdao.getUserById(Integer.parseInt(inputdata));
		user.setRole("ROLE_ADMIN");
		userdao.updateUser(user);

		List<User> list = userdao.listUser();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);

	}
	
	@RequestMapping(value= "/updatepassword" , method = RequestMethod.POST)
	public ResponseEntity<String> UserPasswordUpdate(@RequestBody JSONObject data, Principal principal) {

		System.out.println(data.get("Email"));
		System.out.println(data.get("NewPassword"));

		User user = userdao.getUserByEmail(data.get("Email").toString());
		user.setPassword(data.get("NewPassword").toString());
		userdao.updateUser(user);

		JSONObject json = new JSONObject();

		json.put("status", "Password Updated");
		System.out.println(json.toString());

		return new ResponseEntity<String>(json.toString(), HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/updateProfilePicture/" , method = RequestMethod.POST)
	public ResponseEntity<String> updateProfilePicture(MultipartHttpServletRequest request,
			HttpServletResponse response, UriComponentsBuilder ucBuilder) {

		System.out.println(request.getHeader("user"));

		System.out.println(request.getFile("file").getName());
		System.out.println(request.getFile("file").getSize());
		System.out.println(request.getFile("file").getContentType());
		System.out.println(request.getFile("file").getOriginalFilename());

		JSONObject json = new JSONObject();

		BufferedOutputStream stream = null;

		try {
			String path = context.getRealPath("/");

			System.out.println(path);

			File directory = null;

			System.out.println(request.getFile("file"));

			if (request.getFile("file").getContentType().contains("image")) {
				directory = new File(path + "\\resources\\images");

				System.out.println(directory);

				byte[] bytes = null;
				File file = null;
				bytes = request.getFile("file").getBytes();

				if (!directory.exists())
					directory.mkdirs();

				file = new File(directory.getAbsolutePath() + System.getProperty("file.separator")
						+ request.getHeader("user") + ".jpg");

				System.out.println(file.getAbsolutePath());

				stream = new BufferedOutputStream(new FileOutputStream(file));
				stream.write(bytes);
				
				json.put("status", "Image Uploaded");

				stream.close();

				/*
				 * Profile p = ps.getProfile(request.getHeader("user"));
				 * 
				 * if( p != null ) { p.setImage("resources/images/" +
				 * HashManager.generateHashCode( request.getHeader("user") +
				 * hashname[0] ) + ".jpg" );
				 * 
				 * ps.updateProfile(p);
				 * 
				 * json.put("status", "Uploaded"); json.put("imagesrc",
				 * "resources/images/" + HashManager.generateHashCode(
				 * request.getHeader("user") + hashname[0] ) + ".jpg" ); }
				 */

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println(json.toString());

		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/deleteUserImage" , method = RequestMethod.POST)
	public ResponseEntity<String> deleteUserImage(HttpServletResponse response, @RequestBody String user,
			UriComponentsBuilder ucBuilder, Principal p) {

		System.out.println(user);

		String path = context.getRealPath("/");

		System.out.println(path);

		File directory = new File(path + "resources\\images\\" + user + ".jpg");

		directory.delete();

		JSONObject json = new JSONObject(); 

		json.put("status", "Picture Deleted");

		System.out.println(json.toString());

		return new ResponseEntity<String>(json.toString(), HttpStatus.CREATED);
	}

}