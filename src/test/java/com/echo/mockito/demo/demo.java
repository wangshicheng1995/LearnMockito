package com.echo.mockito.demo;

import com.echo.mockito.dao.UserDao;
import com.echo.mockito.exception.DAOException;
import com.echo.mockito.exception.ValidationException;
import com.echo.mockito.service.Impl.RegistrationServiceImpl;
import com.echo.mockito.util.FindUtils;
import com.echo.mockito.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.SQLException;

import static org.mockito.Mockito.*;

class demo {

    @InjectMocks
    @Spy
    private RegistrationServiceImpl registrationService;
    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register() throws Exception {
        String name = null;
        String phone = "15033248190";
        try {
            registrationService.register(name, phone);
            Assertions.fail("这里会挂掉");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ValidationException);
        }

        name = "测试";
        phone = null;
        try {
            registrationService.register(name, phone);
            Assertions.fail("这里会挂掉");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof ValidationException);
        }

        phone = "15071271412";
        MockedStatic<FindUtils> staticService = mockStatic(FindUtils.class);
        staticService.when(() -> FindUtils.getAreaCode("15071271412")).thenReturn("a");
        staticService.when(() -> FindUtils.getOperatorCode("15071271412")).thenReturn("b");
        when(userDao.save(name, phone, "Echo")).thenCallRealMethod();
        User user = registrationService.register(name, phone);
        Assertions.assertEquals("Echo", user.getRepId());

        when(userDao.save(name, phone, "Echo")).thenThrow(new SQLException());
        try {
            registrationService.register(name, phone);
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof DAOException);
        }
    }
}