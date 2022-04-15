package com.echo.mockito.service.Impl;

import com.echo.mockito.dao.SalesDao;
import com.echo.mockito.dao.UserDao;
import com.echo.mockito.exception.DAOException;
import com.echo.mockito.exception.ValidationException;
import com.echo.mockito.service.RegistrationService;
import com.echo.mockito.util.FindUtils;
import com.echo.mockito.entity.SalesRep;
import com.echo.mockito.entity.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author echo
 * @version 1.0
 * @date 2022/4/4 15:27
 */
public class RegistrationServiceImpl implements RegistrationService {

    SalesDao salesDao = new SalesDao();
    UserDao userDao = new UserDao();

    @Override
    public User register(String name, String phone) throws Exception {
        // 参数校验
        if (name == null || name.length() == 0) {
            throw new ValidationException("number 不能为空");
        }
        if (phone == null || !isValid(phone)) {
            throw new ValidationException("phone 格式错误");
        }
        // 获取手机号归属地编号和运营商编号 然后通过编号找到区域内是 SalesRep
        String areaCode = FindUtils.getAreaCode(phone);
        String operatorCode = FindUtils.getOperatorCode(phone);

        User user;
        try {
            SalesRep rep = salesDao.findRep(areaCode, operatorCode);

            // 最后创建用户，落盘，然后返回
            user = userDao.save(name, phone, rep.getRepId());
        } catch (SQLException e) {
            throw new DAOException("SQLException thrown " + e.getMessage());
        }
        return user;
    }

    private boolean isValid(String phone) {
        String pattern = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
        boolean flag = phone.matches(pattern);
        return flag;
    }

}
