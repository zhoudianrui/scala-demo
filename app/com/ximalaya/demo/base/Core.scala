package com.ximalaya.demo.base

import java.util.Properties
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.stereotype.Service

/**
  * Description: 
  * Author: dick
  * Time: 2018/1/15 上午11:37
  */
@Service
class Core {

  @Autowired
  @Qualifier("yamlProperties")
  private[this] val properties: Properties = null

  lazy val settings = new Settings(properties)

  @Autowired
  val redis: IRedisAdapter = null
}

class Settings(val properties: Properties) {
  def apply(key: String): String = {
    properties.get(key) match {
      case null => throw new RuntimeException(s"""Settings not found with key "$key"""")
      case x => x.toString
    }
  }

  def apply(key: String, defaultValue: String = "") = {
    properties.get(key) match {
      case null => defaultValue
      case x => x.toString
    }
  }
}
