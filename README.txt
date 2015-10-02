Prerequisites
=============
1. Java 1.8
2. Maven 3
3. Hadoop 2.6.0
4. Hive 0.9.0

Setup Java (Ubuntu OS)
======================
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

* java -version to verify the java installation

Setup Maven (Ubuntu OS)
=======================
sudo apt-get install maven

* mvn -version to verify the maven installation

Enable Passwordless SSH login for Hadoop (Ubuntu OS)
====================================================
1. In order to install open ssh server please run the follwing command
sudo apt-get install openssh-server

2. Generate SSH key
ssh-keygen -t rsa -P ""

3. Add generated key to authorized keys file
cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys

4. Check passwordless ssh login by
ssh localhost

Setup Hadoop 2.6.0
==================
1. Download hadoop 2.6.0 version (binary) from https://hadoop.apache.org/releases.html

2. Extract it
tar -zxvf hadoop-2.6.0.tar.gz

3. Open ~/.bashrc and add HADOOP_HOME variable
export HADOOP_HOME=<path/to/hadoop>/hadoop-2.6.0
export PATH=$HADOOP_HOME/bin

4. Open hadoop-env.sh and change JAVA_HOME
vi hadoop-2.6.0/etc/hadoop/hadoop-env.sh
export JAVA_HOME=<path/tp/jdk>/jdk1.8.0_31

5. create temp folder for hadoop and add that into core-site.xml
vi hadoop-2.6.0/etc/hadoop/core-site.xml

Add following configuration to core-site.xml
--------------------------------------------
<configuration>
 <property>
  <name>hadoop.tmp.dir</name>
  <value><path/to/hadoop/temp>/hadoop-temp</value>
  <description>A base for other temporary directories.</description>
 </property>

 <property>
  <name>fs.default.name</name>
  <value>hdfs://localhost:54310</value>
  <description>The name of the default file system.  A URI whose
  scheme and authority determine the FileSystem implementation.  The
  uri's scheme determines the config property (fs.SCHEME.impl) naming
  the FileSystem implementation class.  The uri's authority is used to
  determine the host, port, etc. for a filesystem.</description>
 </property>
</configuration>

6. Add following configuration to mapred-site.xml
First change the mapred-site.xml.template name to mapred-site.xml
-----------------------------------------------------------------
cp hadoop-2.6.0/etc/hadoop/mapred-site.xml.template hadoop-2.6.0/etc/hadoop/mapred-site.xml

Add following configuration to mapred-site.xml
----------------------------------------------
<configuration>
 <property>
  <name>mapred.job.tracker</name>
  <value>localhost:54311</value>
  <description>The host and port that the MapReduce job tracker runs
  at.  If "local", then jobs are run in-process as a single map
  and reduce task.
  </description>
 </property>
</configuration>

7. Create two directories for name node and data node

8. Add following configuration to hdfs-site.xml
<configuration>
 <property>
  <name>dfs.replication</name>
  <value>1</value>
  <description>Default block replication.
  The actual number of replications can be specified when the file is created.
  The default is used if replication is not specified in create time.
  </description>
 </property>
 <property>
   <name>dfs.namenode.name.dir</name>
   <value>file:<path/to>/hadoop-namenode</value>
 </property>
 <property>
   <name>dfs.datanode.data.dir</name>
   <value>file:<path/to>/hadoop-datanode</value>
 </property>
</configuration>

9. Run following command to format namenode
hadoop namenode -format

10. Run hadoop
Change directory to hadoop-2.6.0/sbin

Run following command to start dfs and yarn
sh start-all.sh

Setup Hive
==========

Setup Database
==============
1. Login to mysql using root username and password

2. In order create user and database run the following command
mysql > source /path/to/SQL/create-db.sql

3. To create tables run the following command
mysql > source /path/to/SQL/create-tables-with-seed-data.sql

Build Project
=============
1. Change directory to project

2. Run following command to build the project
mvn clean install

Run DAS System
==============
1. Once build successful navigate to das-executor directory
 cd das-executor/target

2. Unzip das-executor.zip
unzip das-executor.zip

3. Navigate to unzipped directory
cd das-executor/bin

4. Run following command to execute DAS
sh das-executor start - start in background
sd das-executor console - start in current terminal

5. To check the logs navigate to logs and tail
cd das-executor/logs
tail -f das-trace.log




