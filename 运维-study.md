# 1. java -jar 启动命令

1. linux 启动命令

   ```shell
   nohup java -Dserver.port=8086  -Dspring.config.additional-location=./application-dev.yml -jar ./springboot.jar > nohup.out 2>&1 &
   
   ```

2. "2>&1 "

```
2>&1 是一条 shell 命令中的重定向语句，表示将标准错误输出（stderr）重定向到标准输出（stdout）。

在 Linux/Unix 系统中，每个进程都有三个默认的标准文件描述符：

标准输入（stdin），文件描述符为 0，通常对应键盘输入；
标准输出（stdout），文件描述符为 1，通常对应屏幕输出；
标准错误输出（stderr），文件描述符为 2，通常用于输出程序运行时的错误信息。
通过 2>&1 这条重定向命令，可以将标准错误输出（2）重定向到标准输出（1）上，这意味着两者会被合并到同一个输出流中，并且最终都输出到默认的控制台。这样做的好处是方便查看和调试程序的运行结果和错误信息
```

 3. ">"

    ```
    > 是一种用于将命令的标准输出重定向到文件的 shell 操作符。
    
    在 Linux/Unix 中，每个进程都可以打开三个标准文件描述符：
    
    标准输入（stdin），文件描述符为 0，通常对应键盘输入；
    标准输出（stdout），文件描述符为 1，通常对应屏幕输出；
    标准错误输出（stderr），文件描述符为 2，通常用于输出程序运行时的错误信息。
    > 这个操作符的作用是将标准输出（stdout）写入到指定的文件中，例如：
    
    $ command > output.txt
    这条命令会执行一个叫做 command 的命令，并将其标准输出写入到 output.txt 文件中。如果该文件不存在，则创建它；如果存在，则覆盖原文件中的内容。如果不想覆盖原文件中的内容，可以使用追加操作符 >>，例如：
    
    $ command >> output.txt
    这条命令也会执行一个叫做 command 的命令，并将其标准输出追加写入到 output.txt 文件中，不会覆盖原文件中的内容。
    ```

    

 4. 




# 2. jar包

打包之后 生成了 三个文件夹 boot-inf,  meta-inf, org,  分析下这三个文件夹

好的，以下是对这三个文件夹更详细的分析：

1. `META-INF` 文件夹：这个文件夹通常包含了 JAR 文件的元数据信息。其中，`MANIFEST.MF` 文件是必须包含的，它包含了 JAR 文件的版本号、作者、依赖关系等信息。此外，JAR 文件还可以包含其他的元数据文件，例如签名文件、LICENSE.txt 等。这些文件可以帮助用户了解 JAR 文件的来源、授权、使用限制等信息。
2. `BOOT-INF` 文件夹：这个文件夹通常包含了 JAR 文件的启动类和依赖项，用于在运行时启动应用程序。如果使用 Spring Boot 等框架进行打包，则会将应用程序的启动类和依赖项打包到 `BOOT-INF` 文件夹中。在 Spring Boot 应用程序启动时，JVM 会首先加载 `BOOT-INF` 文件夹中的依赖项，然后再加载应用程序的主类。
3. `org` 文件夹：这个文件夹包含了 JAR 文件中的所有类文件和资源文件，按照 Java 包的命名规范进行组织。这个文件夹通常是最大的文件夹，占据了 JAR 文件的大部分空间。其中，类文件以 `.class` 结尾，资源文件可以是任意类型的文件，例如 `.xml`、`.properties`、`.txt` 等。在使用 JAR 文件时，应用程序会从 `org` 文件夹中读取需要的类和资源文件，然后进行加载和使用。

需要注意的是，这三个文件夹只是 JAR 文件中的一部分，具体的组织方式和内容取决于使用的构建工具和打包方式。例如，在使用 `jar` 命令手动打包时，生成的 JAR 文件中只包含一个 META-INF 文件夹和一个 org 文件夹，其中包含了所有的类和资源文件。而在使用 Maven 或 Gradle 等构建工具打包时，则会根据配置生成不同的文件夹和文件。



# 3. winsw



## WinSW使用说明

2023/11/22 10:47:02



### 使用说明

- 前言
- 下载
- 配置介绍
- 示例
- - jar包启动示例
- 安装服务



# 前言

由于使用windows自动的自启方法，不管是将程序启动服务放到开机自启文件夹中，还是创建任务计划程序，都没有很好的实现程序的开机自启效果，而WinSW很好的解决了这个问题。

# 下载

WinSW下载地址
注意：不同版本，有些配置是不一样的，比如我用的这个版本`log mode`就弃用了rotate模式，推荐使用`roll`模式。
这里我用的版本是： `v2.12.0`。
![在这里插入图片描述](https://img-blog.csdnimg.cn/0c5cc7b6cd8e4d089e4c5ee0b9125117.png)

# 配置介绍

配置的话其实在下载的那两个xml文件中就有介绍到，翻译着看也能懂的，这里再介绍一下比较常用的。

- **sample-allOptions.xml**
  包含此版本的全部配置说明，文档不长，介绍的也很详细。
- **sample-minimal.xml**
  给出一个最简单的示例配置，临时用用就够了。

| 参数             | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| id               | 安装windows服务后的服务ID，必须是唯一的，展示在任务管理器的服务列表中 |
| name             | 服务名称，也必须是唯一的，显示在打开服务的列表中             |
| description      | 服务描述                                                     |
| env              | 环境变量，可在顶层配置多个                                   |
| startmode        | 启动模式，取值：Automatic（自动）、Manual（手动）、Boot（针对驱动程序）、 System（针对驱动程序），默认Automatic |
| delayedAutoStart | 是否启用延迟启动模式，值为true或者false，需要stratmode设置为Automatic |
| depend           | 指定此服务依赖的其他服务的ID，仅在依赖的服务运行时此服务才可运行，可以使用多个标签指定多个依赖 |
| log mode         | 日志存储模式：append（默认，一直累积）、none（不存）、reset（启动就删除之前的）、roll、roll-by-time（参考下访说明） |
| logpath          | 日志路径，%BASE%代表相对路径，也就是当前目录                 |
| executable       | 要执行的命令，如启动命令java、nginx.exe                      |
| arguments        | 命令执行参数，如指定虚拟机参数，配置文件路径、-jar xxx.jar等。 |
| stopexecutable   | 指定当请求停止服务时要执行的命令或可执行文件                 |
| stoparguments    | 当请求停止服务时启动另一个进程时的参数                       |



具体步骤: 

1. 把winsw 改成 你的jar包的名字, 后缀别改, 还是 exe
2. 把jar包复制到当前目录下
3. 在当前目录下配置xml

xml 配置示例: 

```xml
<service>
  <id>xianmoer-backend</id>
  <name>xianmoer-backend</name>
  <description>xianmoer app</description>
  <executable>java</executable>
  <arguments>-jar %BASE%/process_pipe_inspection_and_assessment_system_xian.jar --spring.config.location=%BASE%\application-xianmoer.yml</arguments>
  <logmode>rotate</logmode>
  <priority>Normal</priority>
  <stoptimeout>45 sec</stoptimeout>
  <stopparentprocessfirst>false</stopparentprocessfirst>
  <startmode>Automatic</startmode>
  <logpath>%BASE%\logs</logpath>
  <waithint>60 sec</waithint>
  <sleeptime>10 sec</sleeptime>
</service>
```





# 4. nginx 配置

## 配置样例

```

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;
    
    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;
	gzip on;
	gzip_comp_level 9;
	gzip_min_length 10k;
	gzip_proxied any;
	gzip_vary on;
	gzip_types
    	application/atom+xml
    	application/javascript
    	application/json
    	application/ld+json
    	application/manifest+json
    	application/rss+xml
    	application/vnd.geo+json
    	application/vnd.ms-fontobject
    	application/x-font-ttf
    	application/x-web-app-manifest+json
    	application/xhtml+xml
    	application/xml
    	font/opentype
    	image/bmp
    	image/svg+xml
    	image/x-icon
    	text/cache-manifest
    	text/css
    	text/plain
    	text/vcard
    	text/vnd.rim.location.xloc
    	text/vtt
    	text/x-component
    	text/x-cross-domain-policy;	


	client_max_body_size 1000M;
	server_tokens off;
	
    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html;
            index  index.html index.htm;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

    server {
        listen       8090 443 ssl;
        server_name  xianmoer-frontend;
		
		ssl_certificate ./xianmoer.crt;
        ssl_certificate_key ./xianmoer.key;
		ssl_session_timeout 5m;
        ssl_protocols TLSv1 TLSv1.1 TLSv1.2; 
        ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:HIGH:!aNULL:!MD5:!RC4:!DHE; 
        ssl_prefer_server_ciphers on;
	
		server_tokens off;
		proxy_hide_header Server;
		add_header Content-Security-Policy "default-src 'self';
                                 object-src 'self' ;
                                 style-src 'self' 'unsafe-inline';
                                 frame-src 'self' ;
                                 base-uri 'self'; 
                                 form-action 'self';
                                 script-src 'self'  'unsafe-eval' 'sha256-s9RoBFqqRTKEz03aMkaEGLy7X3nJuyOZ8mzkjwCekYA=' ;
								 connect-src 'self' https://api.iconify.design https://api.unisvg.com https://api.simplesvg.com;
                                 img-src 'self' data:";
		add_header X-Content-Type-Options "nosniff";
		add_header Set-Cookie "SameSite=Lax; Secure; HttpOnly; Path=/";
		add_header Referrer-Policy same-origin;
        
		root D:\xianmoer-assessment\front-end\20231121\dist;

        location /basic-api {
			
            proxy_pass http://127.0.0.1:9950/api;
        }

        location /file/xianmoer {
            proxy_set_header Host 127.0.0.1:9000;
            proxy_pass http://127.0.0.1:9000/xianmoer;
        }

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
		
		location ~ /\. {
			deny all;
		}
		
		location / {
            try_files $uri $uri/ =404;
        }
    }
}

```



## 1. 开启ssl

### 1. 生成自签名证书

```
openssl genrsa -out xianmoer.key 2048
openssl req -new -key xianmoer.key -out xianmoer.csr
openssl x509 -req -days 36500 -in xianmoer.csr -signkey xianmoer.key -out xianmoer.crt
```



```
server {
    listen 443 ssl;
    server_name example.com;

    ssl_certificate d:/具体的路径/xianmoer.crt;
    ssl_certificate_key d:/具体的路径/xianmoer.key;

    # 其他配置项
}
```

