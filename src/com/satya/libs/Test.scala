package com.satya.libs

/**
  * Created by sdas on 6/2/2016.
  */
object Test {

  def main(args: Array[String]) {

    EasyFileWatcher.onDelete("C:\\Users\\sdas\\Documents\\fw")(fileName => {

      println(fileName)

    })
  }


  EasyFileWatcher.onCreate("C:\\Users\\sdas\\Documents\\fw")(fileName => println(fileName))
}
