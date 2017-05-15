package com.hw.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hw.IDao.AssetsMapper;
import com.hw.domain.Assets;
import com.hw.service.IAssetsService;

@Service
public class AssetsServiceImpl implements IAssetsService{
	@Resource
	private AssetsMapper assetsMapper;
	@Override
	public Assets getAssetsById(Integer userid) {  
		// TODO 自动生成的方法存根
		return this.assetsMapper.selectByPrimaryKey(userid);
	}
	@Override
	public List<Assets> getAssetsByName(String username) {
		// TODO 自动生成的方法存根
		return this.assetsMapper.selectByName(username);
	}
	@Override
	public List<Assets> getAllUsers() {
		// TODO 自动生成的方法存根
		return this.assetsMapper.selectAllUser();
	}
	@Override
	public void insert(Assets assets) {
		// TODO 自动生成的方法存根
		this.assetsMapper.insert(assets);
	}
	@Override
	public Integer update(Assets assets) {
		// TODO 自动生成的方法存根
		return assetsMapper.updateByPrimaryKeySelective(assets);
	}
	@Override
	public Integer delete(int userid) {
		// TODO 自动生成的方法存根
		return assetsMapper.deleteByPrimaryKey(userid);
	}

}
