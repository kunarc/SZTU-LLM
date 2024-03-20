package com.sztu.web;

import com.sztu.dto.UserDto;

import com.sztu.result.Result;
import com.sztu.service.UserService;
import com.sztu.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
