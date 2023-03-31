# 1. 部署, 发布 到 maven 中央库

1. 注册 sonatype 账号`https://issues.sonatype.org/login.jsp`

2. 创建这么一个东西, 过程中有经办人会处理, 会花费些时间, 我当时是下午创建的,第二天早上好的; 里面 重要点是: group id -对应你spring项目里pom文件的groupid, 

   ![1643254384231](.\img\sonatype-open-source-project.png)



3. 创建GPG签名, 然后 上传公钥 , 设置有效期, 我用的是 kleopatra 这个软件

   > gpg --send-keys [用户ID] --keyserver hkp://subkeys.pgp.net

   上面命令不管用了, 这个管用, 实际操作下面的第一个能成功

   ```shell
   gpg --keyserver hkp://keyserver.ubuntu.com:11371 --send-keys 你的公钥
   gpg --keyserver hkp://pool.sks-keyservers.net:11371 --send-keys 你的公钥
   gpg --keyserver hkp://pgp.mit.edu:11371 --send-keys 你的公钥
   ```

   

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



# 2. maven 加载 配置的顺序

Maven 加载配置的顺序如下：

1. 全局配置文件 settings.xml

Maven 首先加载全局配置文件 settings.xml，它位于 Maven 安装目录的 conf 子目录中。该文件包含全局配置选项，如本地仓库位置、代理服务器、安全设置等。这些配置选项对所有 Maven 项目都生效。

1. 用户级配置文件 settings.xml

接下来，Maven 加载用户级配置文件 settings.xml，它位于用户主目录的 .m2 子目录中。用户级配置文件的作用类似于全局配置文件，不同之处在于它仅适用于当前用户的所有 Maven 项目。

1. 项目级配置文件 pom.xml

如果 Maven 在前两个步骤中找不到所需的配置选项，它将查找项目级配置文件 pom.xml。该文件是 Maven 项目的核心配置文件，包含项目的依赖关系、插件、构建配置等。在 pom.xml 文件中，您可以指定各种配置选项，如依赖版本、编译器版本、源代码目录等。

1. 命令行参数

最后，如果 Maven 在前三个步骤中找不到所需的配置选项，它将查找命令行参数中是否指定了相关选项。例如，您可以通过以下命令行参数设置 Maven 日志级别：

```lua
mvn -Dorg.slf4j.simpleLogger.defaultLogLevel=debug
```

这样，Maven 将以 debug 级别输出日志信息。

总体来说，Maven 遵循“就近原则”（the nearest definition），即优先使用最接近的配置文件中的选项。因此，如果存在多个配置文件中的相同选项，Maven 将使用最后定义的选项。



#  3. maven 加载依赖的顺序

Maven 加载依赖的顺序如下：

1. 本地仓库

Maven 首先检查本地仓库中是否已经存在所需的依赖。本地仓库通常位于用户主目录的 .m2 子目录中，它是 Maven 下载和缓存所有依赖项的地方。如果本地仓库中已经存在所需的依赖，Maven 将直接使用它，不会重新下载。

1. 远程仓库

如果本地仓库中不存在所需的依赖，Maven 将检查配置的远程仓库（或中央仓库）中是否有该依赖。Maven 遍历所有配置的远程仓库，按照顺序依次查询，直到找到所需的依赖为止。如果远程仓库中存在所需的依赖，Maven 将下载并缓存它到本地仓库中。

1. 依赖传递

当 Maven 下载并缓存所需的依赖后，它将检查这些依赖的传递依赖关系。也就是说，Maven 将查找这些依赖所依赖的其他依赖，并按照相同的顺序加载它们。这个过程可能会涉及到多层依赖传递，因此 Maven 需要按照正确的顺序加载依赖，以保证所有依赖都能够正确地加载和使用。

总体来说，Maven 的依赖加载顺序遵循“依赖传递”和“就近原则”。也就是说，Maven 首先尝试在本地仓库中查找所需的依赖，如果找不到再去远程仓库查找。对于多个远程仓库，Maven 按照配置的顺序依次查询，直到找到所需的依赖为止。在加载依赖的过程中，Maven 还会根据依赖传递关系和就近原则确定依赖的顺序。



# 4. maven 命令

```
mvn -v //查看版本 
mvn archetype:create //创建 Maven 项目 
mvn compile //编译源代码 
mvn test-compile //编译测试代码 
mvn test //运行应用程序中的单元测试 
mvn site //生成项目相关信息的网站 
mvn package //依据项目生成 jar 文件 
mvn install //在本地 Repository 中安装 jar 
mvn -Dmaven.test.skip=true //忽略测试文档编译 
mvn clean //清除目标目录中的生成结果 
mvn clean compile //将.java类编译为.class文件 
mvn clean package //进行打包 
mvn clean test //执行单元测试 
mvn clean deploy //部署到版本仓库 
mvn clean install //使其他项目使用这个jar,会安装到maven本地仓库中 
mvn archetype:generate //创建项目架构 
mvn dependency:list //查看已解析依赖 
mvn dependency:tree com.xx.xxx //看到依赖树 
mvn dependency:analyze //查看依赖的工具 
mvn help:system //从中央仓库下载文件至本地仓库 
mvn help:active-profiles //查看当前激活的profiles 
mvn help:all-profiles //查看所有profiles 
mvn help:effective -pom //查看完整的pom信息

```

