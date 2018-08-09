package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
@EnableJpaRepositories("com.example.demo.repository.")
@ComponentScan("com.example.demo.controller.")
@ComponentScan("com.example.demo.service.")
@EntityScan("com.example.demo.model.")
@ComponentScan("com.example.demo.config.")
@Configuration
public class PayRollApplication {

	public static void main(String[] args) {
		SpringApplication.run(PayRollApplication.class, args);
	}
	@Value("${redis.port}")
	private int redisPort;
	@Value("${redis.host}")
	private String redisHost;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(redisHost);
		factory.setPort(redisPort);
		factory.setUsePool(true);
		return factory;
	}

	@Bean
	RedisTemplate<String, Object> redisTemplate() {
		final RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
		return template;
	}

}
