package com.example.ithelper.system.controller;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.common.utils.UserTools;
import com.example.ithelper.system.dao.AccountDataRepository;
import com.example.ithelper.system.entity.AccountData;
import com.example.ithelper.system.entity.Menu;
import com.example.ithelper.system.entity.Role;
import com.example.ithelper.system.entity.User;
import com.example.ithelper.system.entity.vo.VueRouter;
import com.example.ithelper.system.servie.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AccountDataRepository accountDataRepository;

    @GetMapping
    public CommonResponse login() {
        return new CommonResponse().message("请先登录");
    }

    @PostMapping
    public CommonResponse doLogin(@RequestBody Map map) throws CommonException {
        System.out.println("LoginController.login()");
        System.out.println(map.get("username") + "  " + map.get("password") + " " + map.get("finger"));
        String username = map.get("username").toString();
        String password = map.get("password").toString();

        //把用户跟主机指纹绑定保存到redis
        String finger = map.get("finger").toString();
        if (finger != null) {
            stringRedisTemplate.opsForValue().set("itHelper." + username, finger);
        }

        User user = userService.getUserByUsername(username);

        //判断用户账号是否被禁用
        if(user.getStatus().equals("0")){
            System.out.println("账号被禁用");
            return new CommonResponse().message(CommonErrorMsg.USER_IS_DISABLED.getErrMsg())
                    .code(CommonErrorMsg.USER_IS_DISABLED.getErrCode());
        }

        //检测用户密码是否正常
        String encryptPwd = UserTools.encryptPassword(password, username);
        if (!user.getPassword().equals(encryptPwd)) {
            return new CommonResponse().message(CommonErrorMsg.USER_LOGIN_FAIL.getErrMsg())
                    .code(CommonErrorMsg.USER_LOGIN_FAIL.getErrCode());
        }

        Date date = new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_TIME);

        //生成token
        String token = JWTUtil.sign(username, encryptPwd);

        Date lastLoginTime = user.getLastLoginTime();
        userService.updateLastLoginTime(user);

        //生成返回前端数据
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", username);
        userInfo.put("role",user.getRole().getRoleName());
        userInfo.put("token", token);
        userInfo.put("exp_time", date);
        userInfo.put("lastLoginTime",lastLoginTime);
        if (user.getDept().getDeptName().contains("关务")){
            List<AccountData> orgList = accountDataRepository.findAllByName("组织");
            userInfo.put("org",UserTools.getUserOrg(orgList.get(0).getContent()));
        } else {
            HashMap<String,Object> map1 = new HashMap<>();
            map1.put("label",user.getDept().getDeptName());
            map1.put("value",user.getDept().getDeptName());
            ArrayList<Map> list = new ArrayList<Map>();
            list.add(map1);
            userInfo.put("org",list);
        }

        return new CommonResponse().message(CommonErrorMsg.SUCCESS.getErrMsg())
                .data(userInfo)
                .code(CommonErrorMsg.SUCCESS.getErrCode());
    }

    //生成前端路由
    @PostMapping("getRoutes")
    public List getRoutes(@RequestBody Map map) throws CommonException {
        System.out.println("getRoutes");
        if (map == null) {
            throw new CommonException(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrMsg(),
                    CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode());
        }

        User user = userService.getUserByUsername(map.get("username").toString());
        Role userRole = user.getRole();
        Set<Menu> menuSet = userRole.getMenus();
        ArrayList<VueRouter> routerList = new ArrayList<>();

        //第一层目录
        for (Menu menu : menuSet) {
            if (menu.getParentId() == 0 && menu.getType().equals("0")) {
                ArrayList<VueRouter> childList = new ArrayList<>();
                VueRouter vueRouter = new VueRouter();
                vueRouter.setPath(menu.getPath());
                vueRouter.setName(menu.getMenuName());
                vueRouter.setComponent(menu.getComponent());
                vueRouter.setIcon(menu.getIcon());
                vueRouter.setMenuId(menu.getMenuId());
                vueRouter.setParentId(menu.getParentId());
                vueRouter.setChildren(childList);
                routerList.add(vueRouter);
            }
        }

        //第二层目录
        for (Menu menu : menuSet) {
            if (menu.getParentId() != 0 && menu.getType().equals("0")) {
                VueRouter child = new VueRouter();
                child.setPath(menu.getPath());
                child.setName(menu.getMenuName());
                child.setComponent(menu.getComponent());
                child.setIcon(menu.getIcon());
                child.setParentId(menu.getParentId());
                for (VueRouter v : routerList) {
                    if (v.getMenuId() == child.getParentId()) {
                        v.getChildren().add(child);
                    }
                }
            }
        }
        Collections.sort(routerList);
        //System.out.println(routerList);
        return routerList;
    }
}
