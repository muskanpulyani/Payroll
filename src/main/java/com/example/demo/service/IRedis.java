package com.example.demo.service;

public interface IRedis {
	public Object getValue(String key);
	public void deleteValue(String key);
	public void setValue(String key, Integer value);

}
