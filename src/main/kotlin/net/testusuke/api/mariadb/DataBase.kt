package net.testusuke.api.mariadb

import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class DataBase(private val plugin: JavaPlugin) {
    //  接続設定
    private var host: String? = null
    private var user: String? = null
    private var password: String? = null
    private var port: String? = null
    private var databaseName: String? = null

    var isAvailable = false

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
        password = config.getString("database.pass")
        port = config.getString("database.port")
        databaseName = config.getString("database.db")
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
            DriverManager.getConnection("jdbc:mariadb://$host:$port/$databaseName", user, password)
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    /**
     * [Connection] を取得し、処理終了後に自動で閉じます
     * @param run [Connection] に対して実行する処理
     * @param R 処理の戻り値
     * @return [R]?
     */
    inline fun <R> useConnection(run: Connection.() -> R): R? {
        return getConnection()?.use(run)
    }

    /**
     * [Statement] を取得し、処理終了後に自動で閉じます
     * @param run [Statement] に対して実行する処理
     * @param R 処理の戻り値
     * @return [R]?
     */
    inline fun <R> useStatement(run: Statement.() -> R): R? {
        return useConnection { createStatement().use(run) }
    }

    /**
     * MariaDBへの接続をテストします。
     * @return [Boolean] 成功: true / 失敗: false
     */
    private fun testConnect() {
        plugin.logger.info("接続テスト中...")
        isAvailable = if (useConnection { } != null) {
            plugin.logger.info("接続に成功しました！")
            true
        } else {
            plugin.logger.info("接続に失敗しました。")
            false
        }
    }
}