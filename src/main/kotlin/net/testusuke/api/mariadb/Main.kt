package net.testusuke.api.mariadb

import org.bukkit.plugin.java.JavaPlugin

class Main:JavaPlugin() {

    companion object{
        lateinit var dataBase:DataBase
    }

    override fun onEnable() {
        logger.info("load DataBase class...")
        this.saveDefaultConfig()
        dataBase = DataBase(this)
        logger.info("complete load class!")
    }
}