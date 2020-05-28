# MariaDBConnection
This plugin is able to return Connection to MariaDB

**・How To Use?**  
**1. download jar**  
url: https://www.dropbox.com/sh/l0jaehsazkbj1h0/AADAYaJ9k5xhedhqHWRUYSC1a?dl=0  
**2. you add jar to dependency.**  
create 'libs' directory to your project and add jar there.  
Next,you write gradle.
Gradle  
```
compile fileTree(dir: 'libs', include: '*.jar')
```
**3. you have to write url to your plugin.yml.**  
url: https://github.com/testusuke/MariaDBConnection  
**4. import net.testusuke.api.mariadb**  
```
val db:DataBase = net.testusuke.api.mariadb.Main.dataBase
```
**5. get connection to MariaDB**  
```
val connection = db.getConnection()
```
**6. Use the connection in your way.**  
