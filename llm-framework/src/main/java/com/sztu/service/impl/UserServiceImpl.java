package com.sztu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sztu.dto.UserDto;
import com.sztu.dto.UserRegisterDto;
import com.sztu.entity.User;
import com.sztu.exception.LoginException;
import com.sztu.mapper.UserMapper;
import com.sztu.properties.JwtProperties;
import com.sztu.result.Result;
import com.sztu.service.UserService;
import com.sztu.utils.JwtUtil;
import com.sztu.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    public JwtProperties jwtProperties;
    @Autowired
    public UserMapper userMapper;

    @Override
    public Result<UserVo> login(UserDto userDto) {
        String studentId = userDto.getStudentId();
        String password = userDto.getPassword();
        // 查询的条件
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getStudentId, studentId);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new LoginException("学号错误");
        }
        if (!password.equals(user.getPassword())) {
            throw new LoginException("密码错误");
        }
        log.info("用户登录成功！");
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

    /**
     * 注册接口
     *
     * @param userRegisterDto 用户注册信息Dto
     * @return 注册结果
     */
    @Override
    public Result<?> register(UserRegisterDto userRegisterDto) {
        User user = User.builder()
                .studentId(userRegisterDto.getStudentId())
                .password(userRegisterDto.getPassword())
                .name(userRegisterDto.getName())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        int inserted = userMapper.insert(user);
        if (0 == inserted) {
            return Result.error("注册失败");
        }
        return Result.success("注册成功");
    }

}
