package com.newland.alipay.proxy;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 通过散列值定位微信服务节点
 * 
 * @author hongye
 * @since 2014-07-21
 */
@Entity
@Table(name="t_alipay_node")
public class AlipayNode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9031500738377986505L;

	public AlipayNode(){
		
	}
	
   public AlipayNode(String nodeHost ){
	   this.nodeHost = nodeHost;
	}
   
	 /**
    * TODO
    */
   @Id 
   @Column(name="node_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer nodeId;
	
   /**
    * 节点名（服务器名）
    */
    @Column(name="node_name")
	private String nodeName;
	
    /**
     * 节点（服务器）IP 参与服务算法计算
     */
    @Column(name="node_host")
    private String nodeHost;
	
    /**
     * 节点（服务器）端口
     */
    @Column(name="node_port")
	private String nodePort;
	
    /**
     * 节点（服务器）mac地址
     */
    @Column(name="node_MAC_address")
	private String nodeMACAddress;
	
    /**
     *
     */
    @Column(name="node_context")
	private String nodeContext;
    
    /**
     * 更新时间
     */
    @Column(name="upd_time")
    private Date updTime;
	

	public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeHost() {
		return nodeHost;
	}

	public void setNodeHost(String nodeHost) {
		this.nodeHost = nodeHost;
	}

	public String getNodePort() {
		return nodePort;
	}

	public void setNodePort(String nodePort) {
		this.nodePort = nodePort;
	}

	public String getNodeMACAddress() {
		return nodeMACAddress;
	}

	public void setNodeMACAddress(String nodeMACAddress) {
		this.nodeMACAddress = nodeMACAddress;
	}

	public String getNodeContext() {
		return nodeContext;
	}

	public void setNodeContext(String nodeContext) {
		this.nodeContext = nodeContext;
	}

	
	
	public boolean equals(Object obj) {
		if (obj instanceof AlipayNode) {
			return ((AlipayNode) obj).nodeHost.equals(this.nodeHost);
		}
		
		return false;
	}
	
	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public int hashCode() {
		return this.nodeHost.hashCode();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
