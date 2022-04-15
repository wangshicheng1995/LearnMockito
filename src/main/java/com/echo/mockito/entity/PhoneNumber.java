package com.echo.mockito.entity;

import com.echo.mockito.exception.ValidationException;

/**
 * @author echo
 * @version 1.0
 * @date 2022/3/27 16:23
 */
public class PhoneNumber {
    private final String number;
    private final String pattern = "^0?[1-9]{2,3}-?\\d{8}$";

    public String getNumber() {
        return number;
    }

    //在含参构造器中进行参数校验
    public PhoneNumber (String number) throws ValidationException {
        if (number == null) {
             throw new ValidationException("number 不能为空");
        } else if (isValid(number)) {
            throw new ValidationException("number 格式错误");
        }
        this.number = number;
    }

    private boolean isValid(String number) {
        return number.matches(pattern);
    }

    private static String getAreaCode(String number) {
        //具体实现逻辑
        return "a";
    }

    private static String getOperatorCode(String number) {
        //具体实现逻辑
        return "b";
    }
}
