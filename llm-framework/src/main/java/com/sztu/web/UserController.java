package com.sztu.web;

import com.sztu.dto.PasswordDto;
import com.sztu.dto.UserDto;

import com.sztu.dto.UserRegisterDto;
import com.sztu.result.Result;
import com.sztu.service.UserService;
import com.sztu.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<UserVo> login(@RequestBody UserDto userDto) {
        log.info("登录用户信息：{}", userDto);
        return userService.login(userDto);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody UserRegisterDto userRegisterDto) {
        log.info("注册用户信息：{}", userRegisterDto);
        return userService.register(userRegisterDto);
    }

    @PostMapping("/password")
    public Result<?> changePassword(@RequestBody PasswordDto passwordDto) {
        log.info("修改密码信息: {}", passwordDto);
        return userService.changePassword(passwordDto);
    }

}
