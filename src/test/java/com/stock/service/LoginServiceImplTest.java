package com.stock.service;

import com.stock.controller.request.LoginRequest;
import com.stock.controller.response.LoginResponse;
import com.stock.dao.UserDao;
import com.stock.modal.Status;
import com.stock.modal.User;
import com.stock.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {

    @InjectMocks
    private LoginServiceImpl loginService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUser_Success() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername("john_doe");
        request.setPassword("password123");

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUserName("john_doe");
        mockUser.setActiveStatus("ACTIVE");

        when(userDao.getUser("john_doe", "password123")).thenReturn(mockUser);

        // When
        LoginResponse response = loginService.loginUser(request);

        // Then
        assertNotNull(response);
        assertEquals(Status.SUCCESS, response.getStatus());
        assertNotNull(response.getUser());
        assertEquals("john_doe", response.getUser().getUserName());

        verify(userDao, times(1)).getUser("john_doe", "password123");
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername("unknown");
        request.setPassword("wrong");

        when(userDao.getUser("unknown", "wrong")).thenReturn(null);

        // When
        LoginResponse response = loginService.loginUser(request);

        // Then
        assertNotNull(response);
        assertEquals(Status.FAILED, response.getStatus());
        assertNull(response.getUser());

        verify(userDao, times(1)).getUser("unknown", "wrong");
    }

    @Test
    void testLoginUser_Exception() {
        // Given
        LoginRequest request = new LoginRequest();
        request.setUsername("exception_user");
        request.setPassword("exception_pass");

        when(userDao.getUser(anyString(), anyString())).thenThrow(new RuntimeException("Database error"));

        // When
        LoginResponse response = loginService.loginUser(request);

        // Then
        assertNotNull(response);
        assertEquals(Status.FAILED, response.getStatus());
        assertNull(response.getUser());

        verify(userDao, times(1)).getUser("exception_user", "exception_pass");
    }
}
