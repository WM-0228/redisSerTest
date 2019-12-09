/**   
 * 
 * @Title: Redis.java 
 * @Prject: wangming-redis-test
 * @Package: com.wangming.test 
 * @Description: TODO
 * @author: WM  
 * @date: 2019年12月9日 上午8:49:27 
 * @version: V1.0   
 */
package com.wangming.test;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wangming.entity.User;
import com.wangming.utils.UserUtils;

/** 
 * @ClassName: Redis 
 * @Description: TODO
 * @author:WM 
 * @date: 2019年12月9日 上午8:49:27  
 */
@RunWith(SpringJUnit4ClassRunner.class)
//加载reids.xml文件
@ContextConfiguration("classpath:redis.xml")
public class RedisSer {

	@Autowired
	private RedisTemplate redisTemplate;
	
	//使用JDK系列化方式保存5万个user随机对象到Redis，并计算耗时
	@Test
	public void redisJDKSer(){
		//计算开始时间
		long start = System.currentTimeMillis();
		for (int i = 1; i <= 50000; i++) {
			//创建user对象
			User user = new User();
			//给id赋值
			user.setId(i);
			//给姓名赋值
			user.setName(UserUtils.getName());
			//给姓别赋值
			user.setGender(UserUtils.getSex());
			//给电话赋值
			user.setPhoneNum(UserUtils.getPhone());
			//给生日赋值
			user.setBirthday(UserUtils.getBirthday());
			//给邮箱赋值
			user.setEmail(UserUtils.getMail());
			//调用操控redis数据库的类   存储数据到数据库中
			redisTemplate.opsForValue().set("user"+i, user);
		}
		//计算结束时间
		long end = System.currentTimeMillis();
		//打印系列化方式
		System.out.println("采用jdk的序列化方式");
		//打印保存数量
		System.out.println("保存数据共五万条");
		//打印消耗时常
		System.out.println("所耗时间"+(end-start)+"毫秒");
	}
	
	//5.使用JSON系列化方式保存5万个user随机对象到Redis，并计算耗时（18分）
	@Test
	public void redisJSONSer(){
		long start = System.currentTimeMillis();
		for (int i = 1; i <= 50000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(UserUtils.getName());
			user.setGender(UserUtils.getSex());
			user.setPhoneNum(UserUtils.getPhone());
			user.setBirthday(UserUtils.getBirthday());
			user.setEmail(UserUtils.getMail());
			redisTemplate.opsForValue().set("user"+i, user);
		}
		long end = System.currentTimeMillis();
		System.out.println("采用JSON的序列化方式");
		System.out.println("保存数据共五万条");
		System.out.println("所耗时间"+(end-start)+"毫秒");
	}
	
	
	//使用Redis的Hash类型保存5万个user随机对象到Redis，并计算耗时
	@Test
	public void redisHashSer(){
		long start = System.currentTimeMillis();
		for (int i = 1; i <= 50000; i++) {
			User user = new User();
			user.setId(i);
			user.setName(UserUtils.getName());
			user.setGender(UserUtils.getSex());
			user.setPhoneNum(UserUtils.getPhone());
			user.setBirthday(UserUtils.getBirthday());
			user.setEmail(UserUtils.getMail());
			redisTemplate.opsForHash().put("users"+i,"user"+i,user.toString());
		}
		long end = System.currentTimeMillis();
		System.out.println("采用Hash的序列化方式");
		System.out.println("保存数据共五万条");
		System.out.println("所耗时间"+(end-start)+"毫秒");
	}
	
}
