package com.hope.xueling.common.controller;


import com.hope.xueling.common.domain.dto.UserDTO;
import com.hope.xueling.common.domain.vo.Result;
import com.hope.xueling.common.domain.vo.UserVO;
import com.hope.xueling.common.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.UserDatabase;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * @author 谢光益
 * @date 2026/1/20
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    //TODO 用户登录、注册、忘记密码、修改密码、修改用户信息、获取用户信息、用户注销

     //TODO 获取用户信息
    /**
     * 根据用户ID获取用户信息
     * @param
     * @return Result<UserVO> 结果对象，包含用户信息
     */
    @GetMapping("/info/{id}")
    public Result<UserVO> info(@PathVariable Long id) {
        log.info("正在获取{}用户信息", id);
        UserVO userInfo = userService.getUserInfo(id);
        log.info("用户信息获取成功: {}", userInfo);
        return Result.success(userInfo);
    }

    //TODO 编辑用户信息
    /**
     * 编辑用户信息
     * @param userDTO 用户数据传输对象
     * @return Result<String> 结果对象，包含更新成功的消息
     */
    @PostMapping("/profile")
    public Result<String> profile(@RequestBody UserDTO userDTO) {
        log.info("正在编辑用户信息: {}", userDTO);
        userService.updateUser(userDTO);
        log.info("用户信息更新成功");
        return Result.success("用户信息更新成功");
    }

    //TODO 修改密码

    //TODO 忘记密码
}
