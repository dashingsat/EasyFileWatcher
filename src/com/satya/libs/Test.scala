package com.satya.libs

/**
  * Created by sdas on 6/2/2016.
  */
object Test {

  def main(args: Array[String]) {


    EasyFileWatcher.onCreate("/home/satya/Documents/fw/")(fileName => println(fileName))

    EasyFileWatcher.onDelete("/home/satya/Documents/fw/")(fileName => {

      println("FILE TO be deleted :"+ fileName)
    })

    while(true){

    }
  }
}
