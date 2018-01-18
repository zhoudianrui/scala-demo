package com.ximalaya.demo.controller

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Description: 应用接口
  * Author: dick
  * Time: 2018/1/18 上午11:16
  */
class ApplicationController @Inject()(components: ControllerComponents) extends AbstractController(components){

  /**
    * 心跳测试接口
    * @return
    */
  def isLive() = Action.async{
    Future(Ok("OK"))
  }
}
