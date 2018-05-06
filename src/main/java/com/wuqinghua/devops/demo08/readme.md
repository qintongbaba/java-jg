

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

8. 分别启动zookeeper
- 启动:zkServer.sh start
- 状态:zkServer.sh status
	
9. zookeeper的进程为

```shell
	[root@client01 data]# jps
	1829 Jps
	1775 QuorumPeerMain
```

#### 3. zookeeper使用

```shell
	zkCli.sh  # 进入zookeeper的客户端
	查找 : ls /     ls /zookeeper
	创建并赋值 : create /wqh hadoop
	获取 : get /wqh
	设值 : set /wqh hello
	递归删除节点 ： rmr /wqh
	删除指定节点 : delete /path/child
	创建节点有两种：  短暂（ephemeral） 持久（persistent）   
```

#### 4.cfg配置
```
	tickTime: 服务器之间或客户端与服务器之间维持心跳的时间间隔,
			也就是每隔 tickTime时间就会发送一个心跳。
	dataDir:存储内存中数据库快照的位置,顾名思义就是 Zookeeper 保存数据的目录,默认情况下,Zookeeper 将写数据的日志文件也保存在这个目录里。
	clientPort: 这个端口就是客户端连接 Zookeeper 服务器的端口,Zookeeper 会监听这个端口,接受客户端的访问请求。
	initLimit: 这个配置项是用来配置 Zookeeper 接受客户端初始化连接时最长能忍受多少个心跳时间间隔数,
			  当已经超过 10 个心跳的时间(也就是 tickTime)长度后 Zookeeper 服务器还没有收到客户端的返回信息,
			 那么表明这个客户端连接失败。总的时间长度就是10*2000=20 秒。
	syncLimit: 这个配置项标识 Leader 与 Follower 之间发送消息,请求和应答时间长度,
			最长不能超过多少个 tickTime 的时间长度,总的时间长度就是 5*2000=10 秒
	server.A = B:C:D : A表示这个是第几号服务器,
					B 是这个服务器的 ip 地址;
					C 表示的是这个服务器与集群中的 Leader 服务器交换信息的端口;
					D 表示的是万一集群中的 Leader 服务器挂了,需要一个端口来重新进行选举,选出一个新的 Leader
```


