package com.newland.alipay.post.model;

import java.io.Serializable;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

public class Groups implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用于查询返回的分组列表
	 */
	private List<Group> groups;

	private Group group;
	
	private String openid;
	
	private String togroupid;
	
	private String groupid;
	/**
	 * 用于查询返回的分组列表
	 * @return
	 */
	@JSON(name = "group")
	public List<Group> getGroups() {
		return groups;
	}
    @JSON(name = "groups", deserialize = false)
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
    /**
     * 用于发送请求的对象
     * @return
     */
    @JSON(name = "group")
	public Group getGroup() {
		return group;
	}
    @JSON(name = "group", deserialize = false)
	public void setGroup(Group group) {
		this.group = group;
	}
    @JSON(name = "openid")
	public String getOpenid() {
		return openid;
	}
    @JSON(name = "openid", deserialize = false)
	public void setOpenid(String openid) {
		this.openid = openid;
	}
    @JSON(name = "to_groupid")
	public String getTogroupid() {
		return togroupid;
	}
    @JSON(name = "to_groupid", deserialize = false)
	public void setTogroupid(String togroupid) {
		this.togroupid = togroupid;
	}
    @JSON(name = "groupid")
	public String getGroupid() {
		return groupid;
	}
    @JSON(name = "groupid", deserialize = false)
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

}
