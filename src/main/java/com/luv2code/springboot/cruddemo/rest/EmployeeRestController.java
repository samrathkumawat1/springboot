package com.luv2code.springboot.cruddemo.rest;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springboot.cruddemo.dao.EmployeeRepository;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.entity.EmployeeDetails;
import com.luv2code.springboot.cruddemo.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	// expose "/employees" and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	// add mapping for GET /employees/{employeeId}
	
	@GetMapping("/employees/{employeeId}")
	public Optional<Employee> getEmployee(@PathVariable int employeeId) {
		
		Optional<Employee> theEmployee = employeeRepository.findById(employeeId);
		
		if (theEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theEmployee.setId(0);
		
		employeeRepository.save(theEmployee);
		
		return theEmployee;
	}
	
	// add mapping for PUT /employees - update existing employee
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		
		employeeRepository.save(theEmployee);
		
		return theEmployee;
	}
	
	// add mapping for DELETE /employees/{employeeId} - delete employee

	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Optional<Employee> tempEmployee = employeeRepository.findById(employeeId);
		
		// throw exception if null
		
		if (tempEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		employeeRepository.deleteById(employeeId);
		
		return "Deleted employee id - " + employeeId;
	}

	
	// add mapping for POST /employees - add new employee
	
		@PostMapping("/employee/register")
		public void registerEmployee(@RequestBody Employee theEmployee) {
			
			// also just in case they pass an id in JSON ... set id to 0
			// this is to force a save of new item ... instead of update
			
			System.out.print("emp details"+theEmployee.getEmail());
			
			String password= theEmployee.getPassword();
			try {
				System.out.println("given password"+password+"--");
				String strongPassword = generateStorngPasswordHash(password);
				System.out.println("encrapted  password"+strongPassword+"--");
				
				theEmployee.setPassword(strongPassword);
				
			}
			catch(Exception e)
			{
				System.out.println("erorr-------");
			}
			
			theEmployee.setId(0);
			
			employeeRepository.save(theEmployee);	
			
		
		}
		

		@PostMapping("/employee/login")
		public ResponseEntity<Employee> loginEmployee(@RequestBody Employee emp ) {
			
			
			// also just in case they pass an id in JSON ... set id to 0
			// this is to force a save of new item ... instead of update
			String email= emp.getEmail();
			String password =emp.getPassword();
			System.out.println(" email-->"+email);
			System.out.println(" Password-->"+password);
			
				
			
			try {
				Employee theEmployee = employeeRepository.findByemail(email);
				
				if(!theEmployee.equals("null"))
				{String storedPassword = theEmployee.getPassword();
			
				System.out.println(" storedPassword-->"+storedPassword);
				
				boolean validation = validatePassword(password, storedPassword);
				System.out.println("password is correct or not: -->"+validation+"");
				
				if(validation)
					{	System.out.println("password is correct -->"+validation+"");
							System.out.println("password is correct -->"+new ResponseEntity<>(theEmployee, HttpStatus.OK)+"");
					
				
					return new ResponseEntity<>(theEmployee, HttpStatus.OK);
				}
				else
				{
					System.out.println("password is incurrect -->"+validation+"");
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				}
				else
				{
					System.out.println("password is incurrect -->"+"");
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage()+"");
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
			
		}
		
		
		private static String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
		{
			int iterations = 1000;
			char[] chars = password.toCharArray();
			byte[] salt = getSalt().getBytes();
			
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return iterations + ":" + toHex(salt) + ":" + toHex(hash);
					
		}
		
		private static String getSalt() throws NoSuchAlgorithmException
		{
			SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
			byte[] salt = new byte[16];
			sr.nextBytes(salt);
			return salt.toString();
		}
		
		private static String toHex(byte[] array) throws NoSuchAlgorithmException
		{
			BigInteger bi = new BigInteger(1, array);
			String hex = bi.toString(16);
			int paddingLength = (array.length * 2) - hex.length();
			if(paddingLength > 0)
			{
				return String.format("%0"  +paddingLength + "d", 0) + hex;
			}else{
				return hex;
			}
		}
		
		private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
		{
			byte[] bytes = new byte[hex.length() / 2];
			for(int i = 0; i<bytes.length ;i++)
			{
				bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
			}
			return bytes;
		}

		
		private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException
		{
			String[] parts = storedPassword.split(":");
			int iterations = Integer.parseInt(parts[0]);
			byte[] salt = fromHex(parts[1]);
			byte[] hash = fromHex(parts[2]);
			
			PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
			
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			
			byte[] testHash = skf.generateSecret(spec).getEncoded();
			
			int diff = hash.length ^ testHash.length;
			
			for(int i = 0; i < hash.length && i < testHash.length; i++)
			{
				diff |= hash[i] ^ testHash[i];
			}
			
			return diff == 0;
		}
		
}










