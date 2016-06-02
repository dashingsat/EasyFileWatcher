package com.satya.libs

import java.io.File
import java.nio.file._

import scala.collection.JavaConversions._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Satyabrata on 6/2/2016.
  */
class EasyFileWatcher {

}


object EasyFileWatcher{


  def onCreate(fileName:String)(action : (String => Unit)) : Future[Boolean]= {

    val watchService = FileSystems.getDefault.newWatchService()

  Future{
    val dir= new File(fileName).toPath

    try{
      dir.register(watchService,StandardWatchEventKinds.ENTRY_CREATE)
    }
    catch{
      case e:Exception => {
        println(e)
        return Future(false)
      }
    }

    executeAction(watchService)(action)

    true
  }


  }

  def onModify(fileName:String)(action : (String => Unit)) : Future[Boolean]  ={

    Future{
      val watchService = FileSystems.getDefault.newWatchService()

      val dir= new File(fileName).toPath



      try{
        dir.register(watchService,StandardWatchEventKinds.ENTRY_MODIFY)
      }
      catch{
        case e:Exception => {
          println(e)
          return Future(false)
        }
      }

      executeAction(watchService)(action)

      true
    }


  }

  def onDelete(fileName:String)(action : (String => Unit)): Future[Boolean] = {

    Future{
      val watchService = FileSystems.getDefault.newWatchService()

      val dir= new File(fileName).toPath

      try{

        dir.register(watchService,StandardWatchEventKinds.ENTRY_DELETE)

      }
      catch{
        case e:Exception => {
          println(e)
          return Future(false)
        }
      }

      executeAction(watchService)(action)

      true
    }
  }

  def executeAction(watchService : WatchService )(action : String => Unit ) : Unit ={

    while(true){

      try{
        val key= watchService.take()

        for(event  <- key.pollEvents()){

          val pathEvent :WatchEvent[Path]= event.asInstanceOf[WatchEvent[Path]]

          if(pathEvent.kind() != StandardWatchEventKinds.OVERFLOW)
            action(pathEvent.context().toString)
        }

        key.reset()

      }
      catch{
        case e:Exception => println(e)
      }



    }

  }


}