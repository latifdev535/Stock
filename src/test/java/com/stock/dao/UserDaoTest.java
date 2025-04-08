package com.stock.dao;

import com.stock.modal.User;
import com.stock.util.SQLQueryConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

import static com.stock.util.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserDaoTest {

    @InjectMocks
    private UserDao userDao;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUser_SuccessfulLogin() {
        // Given
        String username = "testUser";
        String password = "testPass";

        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUserName(username);
        mockUser.setActiveStatus("ACTIVE");

        when(jdbcTemplate.queryForObject(anyString(),
                any(MapSqlParameterSource.class),
                any(RowMapper.class)))
            .thenReturn(mockUser);

        // When
        User result = userDao.getUser(username, password);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(username, result.getUserName());
        assertEquals("ACTIVE", result.getActiveStatus());

        verify(jdbcTemplate, times(1)).queryForObject(anyString(),
                any(MapSqlParameterSource.class),
                any(RowMapper.class));
    }

    @Test
    void testGetUser_UserNotFound() {
        // Given
        String username = "invalidUser";
        String password = "wrongPass";

        when(jdbcTemplate.queryForObject(anyString(),
                any(MapSqlParameterSource.class),
                any(RowMapper.class)))
            .thenThrow(new RuntimeException("User not found"));

        // When
        User result = userDao.getUser(username, password);

        // Then
        assertNull(result);
        verify(jdbcTemplate, times(1)).queryForObject(anyString(),
                any(MapSqlParameterSource.class),
                any(RowMapper.class));
    }
}
