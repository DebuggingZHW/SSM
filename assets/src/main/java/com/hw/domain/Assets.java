package com.hw.domain;

public class Assets {
    private Integer userid;

    private String username;

    private String asset;

    private String assetnum;
    
    private String assetinfo;

    public Integer getUserid() {
        return userid;
    }

    public String getAssetinfo() {
		return assetinfo;
	}

	public void setAssetinfo(String assetinfo) {
		this.assetinfo = assetinfo;
	}

	public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset == null ? null : asset.trim();
    }

    public String getAssetnum() {
        return assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum == null ? null : assetnum.trim();
    }
}