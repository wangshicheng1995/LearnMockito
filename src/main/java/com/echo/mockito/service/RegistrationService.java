package com.echo.mockito.service;

import com.echo.mockito.exception.ValidationException;
import com.echo.mockito.entity.User;

public interface RegistrationService {
    User register(String name, String phone) throws Exception;
}
