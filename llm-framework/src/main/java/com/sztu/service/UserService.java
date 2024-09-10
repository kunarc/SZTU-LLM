package com.sztu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sztu.dto.PasswordDto;
import com.sztu.dto.UserDto;
import com.sztu.dto.UserRegisterDto;
import com.sztu.entity.User;
import com.sztu.result.Result;
import com.sztu.vo.UserVo;

public interface UserService extends IService<User> {
    Result<UserVo> login(UserDto userDto);

    Result<?> register(UserRegisterDto userRegisterDto);

    Result<?> changePassword(PasswordDto passwordDto);
}
