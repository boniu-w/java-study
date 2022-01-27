# 1. 部署, 发布 到 maven 中央库

1. 注册 sonatype 账号`https://issues.sonatype.org/login.jsp`

2. 创建这么一个东西, 过程中有经办人会处理, 会花费些时间, 我当时是下午创建的,第二天早上好的; 里面 重要点是: group id -对应你spring项目里pom文件的groupid, 

   ![1643254384231](.\img\sonatype-open-source-project.png)



3. 创建GPG签名, 然后 上传公钥 , 设置有效期, 我用的是 kleopatra 这个软件

   > gpg --send-keys [用户ID] --keyserver hkp://subkeys.pgp.net

4. 编辑 项目所用 maven 的 settings.xml , 其中 server.id  填 固定的 ossrh , 原因还没搞懂

   ```xml
   	<server>
   	  <id>ossrh</id>
   	  <username>sonatype用户名</username>
         <password>sonatype密码</password>
   	</server>
   ```

5. 编辑 项目的pom.xml

   

   ```xml
   <!-- tag: 表示 分支名; connection, url 按照格式 写 -->
       <scm>
           <tag>master</tag>
           <connection>scm:git:git@github.com:boniu-w/test.git</connection>
           <url>git@github.com:boniu-w/test.git</url>
       </scm>
   
   <!-- 下面内容写成固定的 -->
       <distributionManagement>
           <snapshotRepository>
               <id>ossrh</id>
               <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
           </snapshotRepository>
           <repository>
               <id>ossrh</id>
               <url>https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/</url>
           </repository>
       </distributionManagement>
   
   <build>
           <plugins>
    <!-- 源码包插件, 非必须 -->
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-source-plugin</artifactId>
                   <version>3.0.1</version>
                   <executions>
                       <execution>
                           <id>attach-sources</id>
                           <phase>package</phase>
                           <goals>
                               <goal>jar-no-fork</goal>
                           </goals>
                       </execution>
                   </executions>
                   <configuration>
                       <attach>true</attach>
                   </configuration>
               </plugin>
   
               <!-- Nexus Staging Maven 插件是将组件部署到 OSSRH 并将它们发布到中央存储库的推荐方法。要配置它，只需将插件添加到您的 Maven pom.xml-->
               <plugin>
                   <groupId>org.sonatype.plugins</groupId>
                   <artifactId>nexus-staging-maven-plugin</artifactId>
                   <version>1.6.7</version>
                   <extensions>true</extensions>
                   <configuration>
                       <serverId>ossrh</serverId>
                       <nexusUrl>https://s01.oss.sonatype.org/</nexusUrl>
                       <autoReleaseAfterClose>true</autoReleaseAfterClose>
                   </configuration>
               </plugin>
   
               <!-- gpg 插件, Maven GPG 插件用于对具有以下配置的组件进行签名-->
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-gpg-plugin</artifactId>
                   <version>1.5</version>
                   <executions>
                       <execution>
                           <id>sign-artifacts</id>
                           <phase>verify</phase>
                           <goals>
                               <goal>sign</goal>
                           </goals>
                       </execution>
                   </executions>
               </plugin>
             </plugins>
        </build>
   ```

   6. 然后 发布, 

   ​     deploy 日志: 

```log
E:\java-1.8.0\bin\java.exe -Dmaven.multiModuleProjectDirectory=D:\java-project\test -Dmaven.home=E:\apache-maven-3.5.4 -Dclassworlds.conf=E:\apache-maven-3.5.4\bin\m2.conf "-Dmaven.ext.class.path=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.2\plugins\maven\lib\maven-event-listener.jar" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.2\lib\idea_rt.jar=54982:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.2\bin" -Dfile.encoding=UTF-8 -classpath E:\apache-maven-3.5.4\boot\plexus-classworlds-2.5.2.jar org.codehaus.classworlds.Launcher -Didea.version=2021.2.3 --update-snapshots -s E:\apache-maven-3.5.4\conf\settings.xml -DskipTests=true deploy
[INFO] Scanning for projects...
[INFO] Inspecting build with total of 1 modules...
[INFO] Installing Nexus Staging features:
[INFO]   ... total of 1 executions of maven-deploy-plugin replaced with nexus-staging-maven-plugin
[INFO] 
[INFO] -----------------------< io.github.boniu-w:test >-----------------------
[INFO] Building test 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:3.1.0:resources (default-resources) @ test ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 4 resources
[INFO] Copying 196 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:compile (default-compile) @ test ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 209 source files to D:\java-project\test\target\classes
[WARNING] /D:/java-project/test/src/main/java/wg/application/controller/Test.java: 某些输入文件使用了未经检查或不安全的操作。
[WARNING] /D:/java-project/test/src/main/java/wg/application/controller/Test.java: 有关详细信息, 请使用 -Xlint:unchecked 重新编译。
[INFO] 
[INFO] --- maven-resources-plugin:3.1.0:testResources (default-testResources) @ test ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory D:\java-project\test\src\test\resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.0:testCompile (default-testCompile) @ test ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 7 source files to D:\java-project\test\target\test-classes
[WARNING] /D:/java-project/test/src/test/java/wg/application/TestApplicationTests.java: D:\java-project\test\src\test\java\wg\application\TestApplicationTests.java使用了未经检查或不安全的操作。
[WARNING] /D:/java-project/test/src/test/java/wg/application/TestApplicationTests.java: 有关详细信息, 请使用 -Xlint:unchecked 重新编译。
[INFO] 
[INFO] --- maven-surefire-plugin:2.22.1:test (default-test) @ test ---
[INFO] Tests are skipped.
[INFO] 
[INFO] --- maven-jar-plugin:3.1.1:jar (default-jar) @ test ---
[INFO] Building jar: D:\java-project\test\target\test-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-source-plugin:3.0.1:jar-no-fork (attach-sources) @ test ---
[INFO] Building jar: D:\java-project\test\target\test-0.0.1-SNAPSHOT-sources.jar
[INFO] 
[INFO] --- maven-gpg-plugin:1.5:sign (sign-artifacts) @ test ---
[INFO] 
[INFO] --- maven-install-plugin:2.5.2:install (default-install) @ test ---
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT.jar to C:\Users\wg\.m2\repository\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.jar
[INFO] Installing D:\java-project\test\pom.xml to C:\Users\wg\.m2\repository\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.pom
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT-sources.jar to C:\Users\wg\.m2\repository\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT-sources.jar
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT.jar.asc to C:\Users\wg\.m2\repository\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.jar.asc
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT.pom.asc to C:\Users\wg\.m2\repository\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.pom.asc
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT-sources.jar.asc to C:\Users\wg\.m2\repository\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT-sources.jar.asc
[INFO] 
[INFO] --- nexus-staging-maven-plugin:1.6.7:deploy (injected-nexus-deploy) @ test ---
[INFO] Performing deferred deploys (gathering into "D:\java-project\test\target\nexus-staging\deferred")...
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT.jar to D:\java-project\test\target\nexus-staging\deferred\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.jar
[INFO] Installing D:\java-project\test\pom.xml to D:\java-project\test\target\nexus-staging\deferred\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.pom
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT-sources.jar to D:\java-project\test\target\nexus-staging\deferred\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT-sources.jar
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT.jar.asc to D:\java-project\test\target\nexus-staging\deferred\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.jar.asc
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT.pom.asc to D:\java-project\test\target\nexus-staging\deferred\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT.pom.asc
[INFO] Installing D:\java-project\test\target\test-0.0.1-SNAPSHOT-sources.jar.asc to D:\java-project\test\target\nexus-staging\deferred\io\github\boniu-w\test\0.0.1-SNAPSHOT\test-0.0.1-SNAPSHOT-sources.jar.asc
[INFO] Deploying remotely...
[INFO] Bulk deploying locally gathered artifacts from directory: 
[INFO]  * Bulk deploying locally gathered snapshot artifacts
Downloading from ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1-sources.jar.asc
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1-sources.jar.asc (235 B at 181 B/s)
Downloading from ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/maven-metadata.xml
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml (643 B at 156 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/maven-metadata.xml
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/maven-metadata.xml (281 B at 204 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1-sources.jar
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1-sources.jar (91 MB at 5.8 MB/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml (857 B at 474 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.jar
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.jar (91 MB at 6.9 MB/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.pom
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.pom (16 kB at 12 kB/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml (1.2 kB at 681 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.pom.asc
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.pom.asc (235 B at 492 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml (1.4 kB at 997 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.jar.asc
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/test-0.0.1-20220127.031452-1.jar.asc (235 B at 500 B/s)
Uploading to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml
Uploaded to ossrh: https://s01.oss.sonatype.org/content/repositories/snapshots/io/github/boniu-w/test/0.0.1-SNAPSHOT/maven-metadata.xml (1.6 kB at 687 B/s)
[INFO]  * Bulk deploy of locally gathered snapshot artifacts finished.
[INFO] Remote deploy finished with success.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 01:19 min
[INFO] Finished at: 2022-01-27T11:15:38+08:00
[INFO] ------------------------------------------------------------------------
[WARNING] The requested profile "test" could not be activated because it does not exist.

Process finished with exit code 0

```



 发布成功后, 可看到

  ![](.\img\repositories-snapshots-io-gihub-boniu-w-test-0.0.1.png)

  其中 test-0.0.1-20220127.031452-1.jar 就是 我们的包, sources.jar 是源码包, 







# 2. gpg

```
gpg --list-keys
```

  

|                 |          |      |
| --------------- | -------- | ---- |
| gpg --list-keys | 密钥列表 |      |
| gpg --gen-key   | 生成密钥 |      |
|                 |          |      |

