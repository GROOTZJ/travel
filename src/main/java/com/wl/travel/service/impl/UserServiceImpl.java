package com.wl.travel.service.impl;

import com.wl.travel.dao.UserDao;
import com.wl.travel.entity.UserEntity;
import com.wl.travel.service.UserService;
import com.wl.travel.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserVo get(Long id) {
        UserEntity entity = userDao.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(entity, userVo);
        return userVo;
    }
}
