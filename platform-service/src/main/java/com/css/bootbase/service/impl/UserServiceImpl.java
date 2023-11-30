package com.css.bootbase.service.impl;

import com.css.bootbase.entity.User;
import com.css.bootbase.mapper.UserMapper;
import com.css.bootbase.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author jiming.jing
 * @since 2023/02/01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
