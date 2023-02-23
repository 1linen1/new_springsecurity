package com.atyjh.demo.dao;

import com.atyjh.demo.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户id查询权限信息
     *
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(@Param("userId") Long userId);
    
}