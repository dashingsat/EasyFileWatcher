package com.satya.libs

import java.io.File
import java.nio.file.{FileSystems, Path, StandardWatchEventKinds, WatchEvent}
import scala.collection.JavaConversions._

/**
  * Created by Satyabrata on 6/2/2016.
  */
class EasyFileWatcher {



}


object EasyFileWatcher{


  val watchService = FileSystems.getDefault.newWatchService()


  def onCreate(fileName:String)(action : (String => Unit)) : Boolean= {



    val dir= new File(fileName).toPath

    println("Create opn")

     try{
       dir.register(watchService,StandardWatchEventKinds.ENTRY_CREATE)
     }
     catch{
       case e:Exception => {
         println(e)
         return false
       }
     }

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
        case e:Exception => return false
      }



    }

    true
  }

  def onModify(fileName:String)(action : (String => Unit)) : Boolean ={



    val dir= new File(fileName).toPath

    println("Modify opn")

    try{
      dir.register(watchService,StandardWatchEventKinds.ENTRY_MODIFY)
    }
    catch{
      case e:Exception => {
        println(e)
        return false
      }
    }

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
        case e:Exception => return false
      }



    }

    true
  }

  def onDelete(fileName:String)(action : (String => Unit)): Boolean = {


    val dir= new File(fileName).toPath

    println("Delete opn")

    try{
      println("going to register")
      dir.register(watchService,StandardWatchEventKinds.ENTRY_DELETE)
      println("registered")
    }
    catch{
      case e:Exception => {
        println(e)
        return false
      }
    }
    println("done")
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
        case e:Exception => return false
      }



    }

    true
  }
}