package com.revature.movietweeter.serviceunittests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import com.revature.movietweeter.exception.InvalidLoginException;
import com.revature.movietweeter.exception.MovieNotFoundException;
import com.revature.movietweeter.exception.InvalidRatingException;
import com.revature.movietweeter.exception.InvalidParameterException;
import com.revature.movietweeter.dao.UserDAO;
import com.revature.movietweeter.dao.ReviewDAO;
import com.revature.movietweeter.dto.SignUpDTO;
import com.revature.movietweeter.dto.AddReviewDTO;
import com.revature.movietweeter.model.User;
import com.revature.movietweeter.service.UserService;
import com.revature.movietweeter.model.Review;

public class ServiceTest {
	
	private ServiceTest sut;
	
	@Test
	public void testGetUserByUsernameAndPasswordPositive() throws SQLException, InvalidParameterException, InvalidLoginException {
		
		String pw_hash = BCrypt.hashpw("121314", BCrypt.gensalt(8));
		UserDAO mockUserDao = mock(UserDAO.class);
		
		when(mockUserDao.getUserfromUsername("artemis")).thenReturn(new User("artemis", pw_hash));
		when(mockUserDao.getUserByUserNameAndPassword("artemis", pw_hash)).thenReturn(new User("artemis", pw_hash));
		
		UserService userService = new UserService(mockUserDao);
		
		User actual = userService.getUserByUsernameAndPassword("artemis", "121314");
		
		Assertions.assertEquals(new User("artemis", pw_hash), actual);
	}
	
	@Test
	public void testGetUserByUsernameAndPasswordUsenameDoesnotExist() throws SQLException, InvalidParameterException, InvalidLoginException {
		
		String pw_hash = BCrypt.hashpw("121314", BCrypt.gensalt(8));
		UserDAO mockUserDao = mock(UserDAO.class);
		
		when(mockUserDao.getUserfromUsername(" ")).thenReturn(new User(" ", pw_hash));
		when(mockUserDao.getUserByUserNameAndPassword(" ", pw_hash)).thenReturn(new User(" ", pw_hash));
		
		UserService userService = new UserService(mockUserDao);
		
		Assertions.assertThrows(InvalidLoginException.class, () -> {
			userService.getUserByUsernameAndPassword(" ", pw_hash);
		});
	}
	
//	@Test
//	public void testGetUserByUsernameAndPasswordNegative() throws SQLException, InvalidParameterException, InvalidLoginException {
//		
//		String pw_hash = BCrypt.hashpw(" ", BCrypt.gensalt(8));
//		UserDAO mockUserDao = mock(UserDAO.class);
//		
//		UserService userService = new UserService(mockUserDao);
//		
//		Assertions.assertThrows(InvalidLoginException.class, () -> {
//			userService.getUserByUsernameAndPassword(" ", pw_hash);
//		});
//	}
	
//	@Test
//	public void testAddUserPositive() throws SQLException, InvalidParameterException, InvalidLoginException {
//		
//		UserDAO mockUserDao = mock(UserDAO.class);
//		
//		SignUpDTO dtoDao = new SignUpDTO("artemis", "121314");
//		
////		when(mockUserDao.addUser(eq(dtoDao))).thenReturn(new User("artimes", "121314"));
//		
//		
//	}

}
