package com.example.ithelper.system.servie.Impl;

import com.example.ithelper.common.handler.CommonException;
import com.example.ithelper.common.jwt.JWTUtil;
import com.example.ithelper.common.response.CommonErrorMsg;
import com.example.ithelper.common.response.CommonResponse;
import com.example.ithelper.common.utils.UserTools;
import com.example.ithelper.system.dao.UserRepository;
import com.example.ithelper.system.entity.Dept;
import com.example.ithelper.system.entity.Role;
import com.example.ithelper.system.entity.User;
import com.example.ithelper.system.entity.vo.UserVo;
import com.example.ithelper.system.servie.DeptService;
import com.example.ithelper.system.servie.RoleService;
import com.example.ithelper.system.servie.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DeptService deptService;

    @Override
    public Map getUsers(Map map) {

        String filter = (String) map.get("filter");

        String keyWord = (String) map.get("keyWord");

        //每页显示的分录数
        int rowsPerPage = (int) map.get("rowsPerPage");

        //第几页，分页是从0开始
        int page = (int) map.get("page");

        //分录总数
        int rowsNumber = 0;

        List<User> userList = null;

        //排序
        String sortBy = (String) map.get("sortBy");

        Sort sort = new Sort(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page - 1, rowsPerPage, sort);
        switch (filter) {
            case "all":
                rowsNumber = userRepository.findAll().size();
                userList = userRepository.findAll(pageable).getContent();
                break;
            case "username":
                rowsNumber = userRepository.findAll((Specification<User>) (root, criteriaQuery, criteriaBuilder)
                        -> criteriaBuilder.like(root.get("username"), "%" + keyWord + "%")).size();
                userList = userRepository.findAllByUsernameContaining(keyWord, pageable);
                break;
            case "role":
                Role role = roleService.getRoleByName(keyWord);
                if (role == null) {
                    return new CommonResponse().code(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode())
                            .message(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrMsg());
                } else {
                    rowsNumber = userRepository.findAllByRole(role).size();
                    userList = userRepository.findAllByRole(role, pageable);
                }
                break;
            case "branch":
                Dept dept = deptService.getDeptByName(keyWord);
                System.out.println(dept);
                if (dept == null) {
                    return new CommonResponse().code(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode())
                            .message(CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrMsg());
                } else {
                    rowsNumber = userRepository.findAllByDept(dept).size();
                    userList = userRepository.findAllByDept(dept, pageable);
                    System.out.println("模糊查询dept：" + rowsNumber);
                }
                break;
            case "status":
                System.out.println("状态过滤");
                String status = keyWord.equals("启用") ? User.STATUS_ENABLE : User.STATUS_DISABLE;
                rowsNumber = userRepository.findAllByStatus(status).size();
                userList = userRepository.findAllByStatus(status, pageable);
                break;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("rowsNumber", rowsNumber);
        hashMap.put("rowsPerPage", rowsPerPage);
        hashMap.put("page", page);
        hashMap.put("data", UserTools.UserToUserVo(userList));

        return hashMap;
    }

    @Override
    public UserVo getUser(Long id) throws CommonException {
        UserVo userVo = new UserVo();
        try {
            User user = userRepository.getOne(id);
            BeanUtils.copyProperties(user, userVo);
            userVo.setDeptName(user.getDept().getDeptName());
            userVo.setRoleName(user.getRole().getRoleName());
        } catch (EntityNotFoundException e) {
            int code = CommonErrorMsg.USER_NOT_EXIST.getErrCode();
            String msg = CommonErrorMsg.USER_NOT_EXIST.getErrMsg();
            throw new CommonException(msg, code);
        }
        return userVo;
    }

    @Override
    public User getUserByUsername(String username) throws CommonException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            int code = CommonErrorMsg.USER_NOT_EXIST.getErrCode();
            String msg = CommonErrorMsg.USER_NOT_EXIST.getErrMsg();
            throw new CommonException(msg, code);
        }
        return user;
    }


    @Override
    public User saveUser(Map userMap) throws CommonException {
        User user = new User();
        try {
            user.setUsername((String) userMap.get("username"));
            String encryptPwd = UserTools.encryptPassword(userMap.get("password").toString(), userMap.get("username").toString());
            user.setPassword(encryptPwd);
            user.setRole(roleService.getRoleByName((String) userMap.get("role")));
            user.setDept(deptService.getDeptByName((String) userMap.get("dept")));
            user.setStatus("1");
            user.setCreatedBy(userMap.get("creator").toString());
            user.setUpdatedBy(userMap.get("Modifier").toString());
        } catch (Exception e) {
            int code = CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrCode();
            String msg = CommonErrorMsg.PARAMETER_VALIDATION_ERROR.getErrMsg();
            throw new CommonException(msg, code);
        }
        return userRepository.save(user);
    }


    @Override
    public User updateUser(Map userInfo) throws CommonException {

        User user;
        try {
            long id = Long.valueOf(userInfo.get("id").toString());
            user = userRepository.getOne(id);
            if (!ObjectUtils.isEmpty(userInfo.get("password"))) {
                String encryptPwd = UserTools.encryptPassword(userInfo.get("password").toString(), userInfo.get("username").toString());
                user.setPassword(encryptPwd);
            }
            user.setRole(roleService.getRoleByName(userInfo.get("role").toString()));
            user.setDept(deptService.getDeptByName(userInfo.get("dept").toString()));
            user.setStatus(userInfo.get("status").toString());
            user.setUpdatedBy(userInfo.get("Modifier").toString());
        } catch (EntityNotFoundException e) {
            int code = CommonErrorMsg.USER_NOT_EXIST.getErrCode();
            String msg = CommonErrorMsg.USER_NOT_EXIST.getErrMsg();
            throw new CommonException(msg, code);
        }
        return userRepository.save(user);
    }

    @Override
    public void updateLastLoginTime(User user) {
        user.setLastLoginTime(new Date());
        userRepository.save(user);
    }

    @Override
    public String updatePassword(String username, String password) {

        User user = userRepository.findByUsername(username);
        String encryptPwd = UserTools.encryptPassword(password, username);
        user.setPassword(encryptPwd);
        userRepository.save(user);
        return JWTUtil.sign(username,encryptPwd);
    }
}
