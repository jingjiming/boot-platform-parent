package com.css.bootbase.mapper;

import com.css.bootbase.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author jiming.jing
 * @since 2023/02/01
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
