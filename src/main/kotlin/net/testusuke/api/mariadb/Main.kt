package net.testusuke.api.mariadb

import org.bukkit.plugin.java.JavaPlugin

class Main:JavaPlugin() {

    companion object{
        lateinit var dataBase:DataBase
    }

    override fun onEnable() {
        //  Logger
        logger.info("load DataBase class...")
        //  class
        dataBase = DataBase(this)
        //  Logger
        logger.info("complete load class!")
    }
}