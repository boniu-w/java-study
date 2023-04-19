1. linux 启动命令

   ```shell
   nohup java -Dserver.port=8086  -Dspring.config.additional-location=./application-dev.yml -jar ./springboot.jar > nohup.out 2>&1 &
   
   ```






# 2. jar包

打包之后 生成了 三个文件夹 boot-inf,  meta-inf, org,  分析下这三个文件夹

好的，以下是对这三个文件夹更详细的分析：

1. `META-INF` 文件夹：这个文件夹通常包含了 JAR 文件的元数据信息。其中，`MANIFEST.MF` 文件是必须包含的，它包含了 JAR 文件的版本号、作者、依赖关系等信息。此外，JAR 文件还可以包含其他的元数据文件，例如签名文件、LICENSE.txt 等。这些文件可以帮助用户了解 JAR 文件的来源、授权、使用限制等信息。
2. `BOOT-INF` 文件夹：这个文件夹通常包含了 JAR 文件的启动类和依赖项，用于在运行时启动应用程序。如果使用 Spring Boot 等框架进行打包，则会将应用程序的启动类和依赖项打包到 `BOOT-INF` 文件夹中。在 Spring Boot 应用程序启动时，JVM 会首先加载 `BOOT-INF` 文件夹中的依赖项，然后再加载应用程序的主类。
3. `org` 文件夹：这个文件夹包含了 JAR 文件中的所有类文件和资源文件，按照 Java 包的命名规范进行组织。这个文件夹通常是最大的文件夹，占据了 JAR 文件的大部分空间。其中，类文件以 `.class` 结尾，资源文件可以是任意类型的文件，例如 `.xml`、`.properties`、`.txt` 等。在使用 JAR 文件时，应用程序会从 `org` 文件夹中读取需要的类和资源文件，然后进行加载和使用。

需要注意的是，这三个文件夹只是 JAR 文件中的一部分，具体的组织方式和内容取决于使用的构建工具和打包方式。例如，在使用 `jar` 命令手动打包时，生成的 JAR 文件中只包含一个 META-INF 文件夹和一个 org 文件夹，其中包含了所有的类和资源文件。而在使用 Maven 或 Gradle 等构建工具打包时，则会根据配置生成不同的文件夹和文件。



