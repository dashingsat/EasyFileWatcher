# README #


### This is simple scala library which can watch your Directories and you can set handlers very easily to implement subsequent actions ###

### Syntax ###
      EasyFileWatcher.onCreate(<fileName>)(<Function which takes a string argument and returns Unit > )

### As an example ###
     EasyFileWatcher.onCreate("/home/satya/Documents/fw/")(fileName => saveToGoogleDrive(fileName))
     EasyFileWatcher.onDelete("/home/satya/Documents/fw/")(fileName => notify(fileName))
     EasyFileWatcher.onModify("/home/satya/Documents/fw/")(fileName => notify(fileName))