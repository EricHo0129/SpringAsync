package com.eric.springasync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Profile {
	
	private Long pid;
    private String userName;
    private String avatarFileId;
    private String introduction;
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAvatarFileId() {
		return avatarFileId;
	}
	public void setAvatarFileId(String avatarFileId) {
		this.avatarFileId = avatarFileId;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
    
    @Override
    public String toString() {
    	 return "User [pid="+pid+", userName=" + userName + ", avatarFileId="+avatarFileId+", introduction=" + introduction + "]";
    }


}
