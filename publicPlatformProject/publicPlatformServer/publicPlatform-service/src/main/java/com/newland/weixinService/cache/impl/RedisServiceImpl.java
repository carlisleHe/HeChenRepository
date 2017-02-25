package com.newland.weixinService.cache.impl;

import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.intensoft.exception.AppBizException;
import com.newland.weixinService.cache.CacheService;


public class RedisServiceImpl implements CacheService<byte[], byte[]>,InitializingBean {
	
	private String hostName = "168.3.61.103";
	
	private int port = 6379;
	
	private String password;
	
	private JedisPool pool;
	
	private int timeout = 60;
	
	private JedisPoolConfig poolConfig;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (poolConfig == null){
			poolConfig = new JedisPoolConfig();			
		}
		this.pool = new JedisPool(poolConfig, hostName, port, timeout, password);
	}

	@Override
	public byte[] get(byte[] key) throws AppBizException {
		Jedis jedis = this.pool.getResource();
		byte[] value = jedis.get(key);
		this.pool.returnResource(jedis);
		return value;
	}

	@Override
	public void put(byte[] key, byte[] value, boolean lock) throws AppBizException {
		Jedis jedis = this.pool.getResource();
		jedis.set(key, value);
		this.pool.returnResource(jedis);
	}
	
	@Override
	public void put(byte[] key, byte[] value, int timeout, boolean lock) throws AppBizException {
		Jedis jedis = this.pool.getResource();
		jedis.set(key, value);
		jedis.expire(key, timeout);
		this.pool.returnResource(jedis);
	}

	@Override
	public byte[] remove(byte[] key) throws AppBizException {
		Jedis jedis = this.pool.getResource();
		byte[] value = jedis.get(key);
		jedis.del(key);
		this.pool.returnResource(jedis);
		return value;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public void expire(byte[] key, int timeout) throws AppBizException {
		Jedis jedis = this.pool.getResource();
		jedis.expire(key, timeout);
		this.pool.returnResource(jedis);
		
	}

}
