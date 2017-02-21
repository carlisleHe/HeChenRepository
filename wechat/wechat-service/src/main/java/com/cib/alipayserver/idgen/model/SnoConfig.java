package com.cib.alipayserver.idgen.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 流水号服务配置
 * 
 * @author yelm
 * @since 2013-01-07
 */
@Entity
@Table(name = "T_ALIPAY_SNO_CFG")
public class SnoConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3013482009177072433L;


	/** 对象ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sno_id", updatable = false)
	private Integer snoId;

	/** 流水号类型 */
	@Column(name = "sno_type")
	private String snoType;

	/** 
	 * 当前最大值，为确保多台物理机同时产生流水号时互不重复，通过数据库锁来控制。<br>
	 * 对于HiLo算法，这里存放的是当前最大的高位值，低位由内存同步互斥控制 <br>
	 * 对于顺序算法，这里存放的是当前最大顺序号
	 * */
	@Column(name = "max_val")
	private Integer maxValue;

	public Integer getSnoId() {
		return snoId;
	}

	public void setSnoId(Integer snoId) {
		this.snoId = snoId;
	}

	public String getSnoType() {
		return snoType;
	}

	public void setSnoType(String snoType) {
		this.snoType = snoType;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Integer maxVal) {
		this.maxValue = maxVal;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
