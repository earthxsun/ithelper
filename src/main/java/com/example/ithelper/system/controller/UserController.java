package com.example.ithelper.system.controller;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.system.servie.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("getUsers")
    public CommonResponse getUsers(@RequestBody Map map) {

        System.out.println(map);
        return new CommonResponse(userService.getUsers(map));
    }

    @PutMapping
    public CommonResponse getUser(@RequestBody Map map) throws CommonException {
        //long id = Long.valueOf(map.get("id").toString());
        if (map.containsKey("id")){
            long id = Long.valueOf(map.get("id").toString());
            return new CommonResponse(userService.getUser(id));
        }

        if (map.containsKey("username")){
            String usernmae = map.get("username").toString();
            return new CommonResponse(userService.getUserByUsername(usernmae));
        }
        return new CommonResponse(new CommonException(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrMsg(),
                CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode()));
    }

    @PostMapping
    public CommonResponse saveUser(@RequestBody Map userMap) throws CommonException {
        String methods = userMap.get("methods").toString();
        if (methods.equals("add")) {
            userService.saveUser(userMap);
        } else {
            userService.updateUser(userMap);
        }

        return new CommonResponse().message(CommonErrorMsg.SUCCESS.getErrMsg()).code(CommonErrorMsg.SUCCESS.getErrCode());
    }

    @PostMapping("updatePwd")
    public CommonResponse updatePassword(@RequestBody Map userMap){
        String username = userMap.get("username").toString();
        String password = userMap.get("password").toString();
        String currentUser = JWTUtil.getUsername(SecurityUtils.getSubject().getPrincipal().toString());
        if (username.equals(currentUser) && password != null){
            return new CommonResponse(userService.updatePassword(username,password));
        } else {
            return new CommonResponse().message(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrMsg())
                    .code(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode());
        }
    }
}
