

### zookeeper
#### 1.zookeeper的应用场景
- 配置管理
1. 数据量比较小
2. 数据内容的运行时动态发生改变
3. 集群中各个节点共享信息，配置一致
- 集群管理
1. 希望知道当前集群中究竟有多少个机器
2. 对集群中每天集群的运行时状态进行数据收集
3. 对集群中每台集群进行上下线操作
- 发布与订阅
- 数据库切换
- 分布式日志收集


#### 2.zookeeper的安装
1. 一共3个节点（zk服务器集群规模不能少于3个节点），要求服务器之前时间保持一致（client01，client02，client03）
2. 解压zookeeper-3.4.11.tar.gz   tar -zxvf zookeeper-3.4.11.tar.gz
3. 进行重命名  mv zookeeper-3.4.11 zookeeper
4. 修改环境变量
```shell
  vi /etc/profile
  export ZOOKEEPER_HOME=/usr/local/zookeeper
  export PATH=$PATH:$ZOOKEEPER_HOME/bin
``` 
5. 刷新环境变量  source /etc/profile
6. 修改zookeeper配置文件

```shell
	cd /usr/local/zookeeper/conf
	mv zoo_sample.cfg zoo.cfg
	
	# 修改两处
	# (1)
		dataDir = /usr/local/zookeeper/data
	# (2) 最后添加
		server.0=172.16.88.144:2888:3888
		server.1=172.16.88.145:2888:3888
		server.2=172.16.88.146:2888:3888
```
7. 添加服务器标识

```shell
	# 在zookeeper中创建data目录
	mkdir data
	# 在data中创建文件myid并填写内容为0
	vi myid
```

8.分别启动zookeeper
	启动:zkServer.sh start
	状态:zkServer.sh status


