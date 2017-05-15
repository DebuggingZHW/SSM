package com.hw.service;

import java.util.List;

import com.hw.domain.Assets;

public interface IAssetsService {
	public Assets getAssetsById(Integer userid);
	
	public List<Assets> getAssetsByName(String username);
	
	public List<Assets> getAllUsers();
	
	public void insert(Assets assets);
	
	public Integer update(Assets assets);
	
	public Integer delete(int userid);
}
