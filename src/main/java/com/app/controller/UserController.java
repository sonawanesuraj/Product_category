package com.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.app.dto.ErrorResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDto;
import com.app.entities.UserEntity;
import com.app.repository.UserRepository;
import com.app.serviceInterface.UserInterface;
import com.app.util.PasswordValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserInterface userInterface;

	@PostMapping()
	public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception, DataIntegrityViolationException {

		String email = userDto.getEmail();
		String password = userDto.getPassword();

		if (PasswordValidator.isValidforEmail(email)) {

			if (PasswordValidator.isValid(password)) {

				UserEntity databaseName = userRepository.findByEmailContainingIgnoreCase(email);
				if (databaseName == null) {
					userInterface.addUser(userDto);
					return new ResponseEntity<>(new SuccessResponseDto("User Created", "userCreated", "data added"),
							HttpStatus.CREATED);

				} else {
					return new ResponseEntity<>(
							new ErrorResponseDto("User Email Id Already Exist", "userEmailIdAlreadyExist"),
							HttpStatus.BAD_REQUEST);
				}
			} else {

				return ResponseEntity.ok(new ErrorResponseDto(
						"Password should have Minimum 8 and maximum 50 characters, at least one uppercase letter, one lowercase letter, one number and one special character and No White Spaces",
						"Password validation not done"));
			}

		} else {
			return new ResponseEntity<>(new ErrorResponseDto("please check Email is not valid ", "Enter valid email"),
					HttpStatus.BAD_REQUEST);
		}

	}
}
