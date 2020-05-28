package net.testusuke.api.mariadb

import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class DataBase(private val plugin: JavaPlugin) {

    //  接続設定
    private var host: String? = null
    private var user: String? = null
    private var pass: String? = null
    private var port: String? = null
    private var db: String? = null

    init {
        plugin.logger.info("データベースを読み込みます。")
        loadConfig()
        loadClass()
        testConnect()
        plugin.logger.info("データベースを読み込みました。")
    }

    /**
     * データベースへの接続設定を読み込みます。
     */
    fun loadConfig() {
        val config = plugin.config
        host = config.getString("database.host")
        user = config.getString("database.user")
        pass = config.getString("database.pass")
        port = config.getString("database.port")
        db = config.getString("database.db")
    }

    private fun loadClass() {
        try {
            Class.forName("org.mariadb.jdbc.Driver")
            plugin.logger.info("クラスを読み込みました")
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * MariaDBの [Connection] を取得します
     * @return [Connection]
     */
    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection("jdbc:mariadb://$host:$port/$db", user, pass)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * MariaDBへの接続をテストします。
     * @return [Boolean] 成功: true / 失敗: false
     */
    private fun testConnect() {
        plugin.logger.info("接続テスト中...")
        if (getConnection() != null) {
            plugin.logger.info("接続に成功しました！")
        } else {
            plugin.logger.info("接続に失敗しました。")
        }
    }
}