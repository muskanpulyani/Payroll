package com.example.demo.serviceImpl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.service.IRedis;

@Service
@Configuration
public class RedisImpl implements IRedis{
	@Autowired
	private RedisTemplate<String, Object> template;

	@Override
	public Object getValue(String key) {
		return template.opsForValue().get(key);
	}

	@Override
	public void setValue(String key, Integer value) {
		template.opsForValue().set(key, value);
        template.expire(key, 24, TimeUnit.HOURS);
		
	}

	@Override
	public void deleteValue(String key) {
		Set<String> keys = new HashSet<>();
        keys.add(key);
        template.opsForValue().getOperations().delete(keys);
		
	}

}
