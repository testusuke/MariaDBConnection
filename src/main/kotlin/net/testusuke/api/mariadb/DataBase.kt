package net.testusuke.api.mariadb

import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataBase(private val plugin:JavaPlugin) {

    //  Connect information
    private var host: String? = null
    private var user: String? = null
    private var pass: String? = null
    private var port: String? = null
    private var db: String? = null

    init {
        //  Logger
        plugin.logger.info("DataBaseを読み込みます。")
        //  load config
        loadConfig()
        //  クラスローダー
        loadClass()
        //  Test Connect
        testConnect()
        //  Logger
        plugin.logger.info("DataBaseを読み込みました。")
    }

    /**
     * DBへの接続設定を読み込みます。
     */
    fun loadConfig() {
        host = plugin.config.getString("database.host")
        user = plugin.config.getString("database.user")
        pass = plugin.config.getString("database.pass")
        port = plugin.config.getString("database.port")
        db = plugin.config.getString("database.db")
    }

    private fun loadClass() {
        try {
            Class.forName("org.mariadb.jdbc.Driver")
            plugin.logger.info("Load class.")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            plugin.logger.info("DataBase connection class not found!")
        }
    }

    /**
     * MariaDBのコネクション取得function
     * @return Connection
     */
    fun getConnection(): Connection? {
        val connection: Connection
        connection = try {
            DriverManager.getConnection("jdbc:mariadb://$host:$port/$db", user, pass)
        } catch (e: SQLException) {
            e.printStackTrace()
            return null
        }
        return connection
    }

    /**
     * MariaDBへの接続をテストします。
     * 成功:true 失敗:false
     * @return Boolean
     */
    private fun testConnect(): Boolean? {
        plugin.logger.info("接続テスト中....")
        if (getConnection() == null) {
            plugin.logger.info("接続に失敗しました。")
            return false
        }
        plugin.logger.info("接続に成功しました！")
        return true
    }
}