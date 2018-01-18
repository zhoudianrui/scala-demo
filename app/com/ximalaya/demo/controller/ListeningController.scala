package com.ximalaya.demo.controller

import com.ximalaya.demo.service.ListeningService
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, ControllerComponents}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Description: 
  * Author: dick
  * Time: 2018/1/15 下午1:11
  */
class ListeningController @Inject()(components: ControllerComponents) extends AbstractController(components){

  @Inject
  var listeningService: ListeningService = null

  def isLive() = Action.async{ request =>
    Future(Ok("OK"))
  }

  def cleanUserData = Action.async{ request =>
    val resultFuture = listeningService.clearUserData(request)
    resultFuture.map{ value =>
      Ok(Json.toJson(value))
    }.recover{
      case e:Exception =>
        Ok("服务调用异常," + e.getMessage)
    }
  }

  def setListenTime() = Action.async{ request =>
    val resultFuture = listeningService.setListenTime(request)
    resultFuture.map{ value =>
      val temp = Json.toJson(value)
      Ok(temp)
    }.recover{
      case e: Exception =>
        Ok("服务调用异常," + e.getMessage)
    }
  }
}
