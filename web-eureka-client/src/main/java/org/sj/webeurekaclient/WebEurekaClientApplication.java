package org.sj.webeurekaclient;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;

@SpringBootApplication
@EnableFeignClients
@RestController
@Log
public class WebEurekaClientApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(WebEurekaClientApplication.class, args);
	}

	
	@Autowired
	UserRepo userRepo;

	@GetMapping("/list-users/user/getAllUsers/")
	public String getAllUsers() {
		return userRepo.getAllUsers();
	}

	@PostMapping("/create-user/user/addUser")
	public String addUser(@RequestBody String user) {
		return userRepo.addUser(user);
	}
	
	@GetMapping("/profile-file-upload/user/getUser/{id}")
	public String getUser(@PathVariable (value="id") UUID id) {
		return userRepo.getUser(id);
	}
		
	@PatchMapping("profile-file-upload/user/updateUser/{id}")
	public String updateUser(@PathVariable (value="id") UUID id, @RequestBody String receivedUser) {
		return userRepo.updateUser(id,receivedUser);
	}
		
	}; 
	

	
	
	
	
	@FeignClient("ms-api-gateway")
	interface UserRepo {
		
		String user="/user";
		
		@GetMapping(user+"/getAllUsers")
		String getAllUsers();
		
		@PostMapping(value =user+"/addUser", consumes = "application/json")
		public String addUser(String user); 
		
		@GetMapping(user+"/getUser/{id}")
		public String getUser(@PathVariable (value="id") UUID id); /*{
			log.log(Level.INFO, "Get user {0}",id);
			return userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("UserRepo","id",id));
		}*/

		/*
		 * @GetMapping("/getAllUsers")
		 * 
		 * @ResponseBody public List<UserRepo> getAllUsers() { log.log(Level.INFO,
		 * "Get All Users" ); return userRepo.findAll(); }
		 */

		
		@PatchMapping(value =user+"/updateUser/{id}", consumes = "application/json")
		public String updateUser(@PathVariable (value="id") UUID id, @RequestBody String receivedUser); /*{
			log.log(Level.INFO, "Update User with ID {0} and details as {1}",new Object[] {id, receivedUser});
			UserRepo savedUser=userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("UserRepo","id",id));
			savedUser.setPoaFileID(receivedUser.getPoaFileID());
			savedUser.setPoiFileID(receivedUser.getPoiFileID());
			userRepo.save(savedUser);
			return savedUser;
		}*/
		
		
	}

