package com.ximalaya.demo.base

import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

/**
  * Description: 
  * Author: dick
  * Time: 2018/1/15 下午1:08
  */
trait IRedisAdapter {
  def userListenRedis: RedisTemplate[String, String]
  def hbaseRedis: RedisTemplate[Array[Byte], Array[Byte]]
}

@Service
class RedisAdapter extends IRedisAdapter {

  @Autowired
  @Qualifier("userListenRedisTemplate")
  val userListenRedis: RedisTemplate[String, String] = null

  @Autowired
  @Qualifier("hbaseRedisTemplate")
  val hbaseRedis: RedisTemplate[Array[Byte], Array[Byte]] = null
}
