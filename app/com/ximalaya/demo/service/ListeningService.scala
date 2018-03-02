package com.ximalaya.demo.service

import com.ximalaya.demo.base.Core
import java.text.SimpleDateFormat
import java.util.Date
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import play.api.mvc.RequestHeader
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Description: 
  * Author: dick
  * Time: 2018/1/15 下午1:14
  */
@Service
class ListeningService {

  @Autowired
  val core: Core = null

  def clearUserData(rq:RequestHeader): Future[Boolean] = {
    val userIdOption = rq.getQueryString("userId")
    if(!userIdOption.isEmpty) {
      val key = "point:user_daily_check_in_account:" + userIdOption.getOrElse("")
      Future{
        core.redis.userListenRedisTemplate.delete(key)
      }.map{ _ =>
        true
      }.recover{
        case e:Exception => false
      }
    } else {
      Future(false)
    }
  }

  def setListenTime(rq: RequestHeader): Future[Boolean] = {
    val timeOption = rq.getQueryString("time")
    val userIdOption = rq.getQueryString("userId")
    if (!userIdOption.isEmpty && !timeOption.isEmpty) {
      val userIdBytes = toBytes(userIdOption.get.toLong)
      val dateBytes = toBytes(new SimpleDateFormat("yyyyMMdd").format(new Date()).toInt)
      val result = new Array[Byte](12)
      System.arraycopy(userIdBytes, 0, result, 0, userIdBytes.length)
      System.arraycopy(dateBytes, 0, result, userIdBytes.length, dateBytes.length)
      val value = toBytes(timeOption.get.toInt)
      Future {
        core.redis.hbaseRedis.opsForValue().set(result, value)
      }.map{ _ =>
        val setResult = intArrayByte2Int(core.redis.hbaseRedis.opsForValue().get(result))
        setResult == timeOption.get.toInt
      }.recover {
        case e: Exception =>
          false
      }
    } else {
      Future(false)
    }
  }

  def getListenTime(rq: RequestHeader): Future[Int] = {
    val userIdOption = rq.getQueryString("userId")
    if (!userIdOption.isEmpty) {
      val userIdBytes = toBytes(userIdOption.get.toLong)
      val dateBytes = toBytes(new SimpleDateFormat("yyyyMMdd").format(new Date()).toInt)
      val result = new Array[Byte](12)
      System.arraycopy(userIdBytes, 0, result, 0, userIdBytes.length)
      System.arraycopy(dateBytes, 0, result, userIdBytes.length, dateBytes.length)
      Future {
        core.redis.hbaseRedis.opsForValue().get(result)
      }.map(intArrayByte2Int(_))
        .recover {
        case e: Exception =>
          -1
      }
    } else {
      Future(-1)
    }
  }

    /**
      * 长整型转换成字节数组
      * @param number
      * @return
      */
    private[this] def toBytes(number: Long): Array[Byte] = {
      var value = number
      val b = new Array[Byte](8)
      var i = 7
      while (i >= 0) {
        b(i) = value.toByte
        value >>>= 8
        i = i - 1
      }
      b
    }

    /**
      * 整型转换成字节数组
      * @param number
      * @return
      */
    private[this] def toBytes(number: Int): Array[Byte] = {
      var value = number
      val b = new Array[Byte](4)
      var i = 3
      while (i >= 0) {
        b(i) = value.toByte
        value >>>= 8
        i = i - 1
      }
      b
    }

    /**
      * 整型字节数组转换成整数
      * @param bytes
      * @return
      */
    private[this] def intArrayByte2Int(bytes: Array[Byte]): Int = {
      if(bytes.length != 4){
        -1
      } else {
        val highest = bytes(0) << 3 * 8
        val secondHigh = (bytes(1) & 0xFF) << 2 * 8
        val secondLowest = (bytes(2) & 0xFF) << 8
        val lowest = bytes(3) & 0xFF
        highest | secondHigh | secondLowest | lowest
      }
    }
}
