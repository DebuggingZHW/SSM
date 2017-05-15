package com.hw.IDao;

import java.util.List;

import com.hw.domain.Assets;

public interface AssetsMapper {
    Integer deleteByPrimaryKey(int userid);

    int insert(Assets assets);

    int insertSelective(Assets assets);

    Assets selectByPrimaryKey(int userid);
    
    List<Assets> selectByName(String username);

    int updateByPrimaryKeySelective(Assets assets);

    int updateByPrimaryKey(Assets assets);
    
    List<Assets> selectAllUser();
    
    boolean update(Assets assets);
}