@echo off

echo .
echo *******************************************
echo *  MySQL Batch Subroutine                 *
echo *  FusionSpy fusionspystudio@gmail.com    *
echo *  Copyright 2009 - 2020                  *
echo *  http://www.fusionspy.com               *
echo *******************************************

rem MYSQL_HOME:  本地MySQL的安装路径,一般是 "C:\Program Files\MySQL\MySQL Server 5.0" 注意用引号引起有空格的目录
rem MYSQL_HOST:  mysql 服务器的ip地址,可以是本地,也可以是远程
rem MYSQL_PORT:  mysql 服务器的端口,缺省为 3306
rem MYSQL_USER:  具有操作数据库权限的用户名,如 root
rem MYSQL_PASSWORD:  具有操作数据库权限的用户密码,如 root,如果为空请保留
rem MYSQL_SYSDB: 要连接的数据名,这里用的 mysql.
rem beacon_mysql.sql: 要执行的脚本文件,这里为 beacon_mysql.sql
rem mysql 后面的应该放在一行.

set MYSQL_HOME="C:\Program Files\MySQL\MySQL Server 5.0"
set MYSQL_HOST=localhost
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASSWORD=root
set MYSQL_SYSDB=mysql
set MYSQL_BEACONDB=beacon_db_v5
set PATH=%MySQL_HOME%\bin;%PATH%
mysql --host=%MYSQL_HOST% --port=%MYSQL_PORT% --user=%MYSQL_USER% --password=%MYSQL_PASSWORD% %MYSQL_SYSDB%<beacon_mysql.sql
mysql --host=%MYSQL_HOST% --port=%MYSQL_PORT% --user=%MYSQL_USER% --password=%MYSQL_PASSWORD% --default-character-set=utf8 beacon_db_v5<monitor.sql

pause