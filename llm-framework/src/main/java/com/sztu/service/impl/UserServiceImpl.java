package com.sztu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.dto.UserDto;
import com.sztu.entity.User;
import com.sztu.exception.LoginException;
import com.sztu.mapper.UserMapper;
import com.sztu.properties.JwtProperties;
import com.sztu.result.Result;
import com.sztu.service.UserService;
import com.sztu.utils.JwtUtil;
import com.sztu.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    public JwtProperties jwtProperties;
    @Override
    public Result<UserVo> login(UserDto userDto) {
        String studentId = userDto.getStudentId();
        String password = userDto.getPassword();
        User user = this.getBaseMapper().selectOne(new QueryWrapper<User>().eq("student_Id", studentId));
        if (user == null) {
            throw new LoginException("学号错误");
        }
        if (!password.equals(user.getPassword())) {
            throw new LoginException("密码错误");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        Map<String, Object> claims = new HashMap<>();
        claims.put("USER_ID", user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        userVo.setToken(token);
        return Result.success(userVo);
    }
}
