# 1. shiro配置

## 1. filterMap.put("/**", "oauth2"); 

```
filterMap.put("/**", "oauth2"); 这一行的意思是将所有（/** 表示匹配任何路径）的 URL 请求映射到名为 "oauth2" 的过滤器链上。这意味着当任何请求进入系统时，都会首先经过名为 "oauth2" 的过滤器进行处理。

"oauth2" 通常代表一个或多个 Shiro Filter 的组合，具体取决于你在 Shiro 配置中如何定义这个过滤器名称。例如，如果你配置 "oauth2" 是一个 OAuth2 相关的安全过滤器链，那么所有请求都需要先通过 OAuth2 的身份验证和授权才能继续执行后续的业务逻辑。

总的来说，这一段代码的作用是全局配置 Shiro 的过滤规则，强制所有请求都要经过 "oauth2" 这个过滤器链进行安全校验。
```



## 2. filterMap.put("/sys/login", "anon"); anon 是干嘛的

在 Apache Shiro 的配置中，`filterMap.put("/sys/login", "anon");` 这一行代码的意思是将 `/sys/login` 这个特定的URL路径映射到名为 "anon" 的过滤器。

"anon" 是 Shiro 内置的一个过滤器，全称为 AnonymousFilter。顾名思义，这个过滤器允许匿名访问，也就是说，当请求到达 `/sys/login` 这个路径时，Shiro 不会对用户的身份进行任何认证（登录检查），任何用户（包括未登录用户）都可以访问这个资源。

通常情况下，我们会将登录页面或者公开无需权限验证的资源路径映射到 "anon" 过滤器，以允许任何用户都能访问登录页面进行登录操作。



# 2. nginx

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
        listen       8090;
        server_name  xianmoer-frontend;
		server_tokens off;
		proxy_hide_header Server;
		add_header Content-Security-Policy "default-src 'self';
                                 object-src 'self' ;
                                 style-src 'self' 'nonce-random-value';
                                 frame-src 'self' ;
                                 base-uri 'self'; 
                                 form-action 'self';
                                 script-src 'self' 'nonce-another-random-value';
                                 connect-src 'self' https://api.iconify.design https://api.unisvg.com https://api.simplesvg.com; 
                                 img-src 'self' data:;
                                 ";
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
    }
}

```







## 1. 隐藏目录

```
	location ~ /\. {
			deny all;
		}
```

`location ~ /\. { deny all; }` 是在 Nginx 配置文件中的一段规则，用于控制对隐藏目录的访问权限。下面是详细的解释：

- `location`: Nginx 的 `location` 指令用于根据请求的 URI 匹配来执行不同的配置块。
- `~`: 波浪线表示这是一个正则表达式匹配。这意味着接下来的模式将会被当作正则表达式对待，而不是简单字符串匹配。
- `/\.`: 正则表达式模式匹配的是以点号（`.`）开始的目录或文件名。在 Unix/Linux 文件系统中，以点号开头的文件或目录被视为隐藏文件或隐藏目录。
- `{ deny all; }`: 当请求的 URI 符合上述正则表达式的匹配时，Nginx 将执行这一大括号内的配置。这里使用了 `deny all` 指令，它的作用是拒绝所有的访问请求，不允许任何客户端访问以点号开头的隐藏目录或文件。

综上所述，`location ~ /\. { deny all; }` 这段配置的主要目的是禁止所有对服务器上隐藏目录的访问，以此增加服务器的安全性，防止由于隐藏目录中的敏感信息意外暴露而引发的安全问题。



## 2. add_header

```
		add_header Content-Security-Policy "default-src 'self';
                                 object-src 'self' ;
                                 style-src 'self' 'nonce-random-value';
                                 frame-src 'self' ;
                                 base-uri 'self'; 
                                 form-action 'self';
                                 script-src 'self' 'nonce-another-random-value';
                                 ";
		add_header X-Content-Type-Options "nosniff";
		add_header Set-Cookie "SameSite=Lax; Secure; HttpOnly; Path=/";
```



`add_header Set-Cookie "sessionid=...; SameSite=Lax; Secure; HttpOnly; Path=/";` 是 Nginx 服务器中用于设置 HTTP 响应头中 `Set-Cookie` 的一条指令。这条指令用于向客户端（通常是浏览器）发送一个新的或更新现有的 cookie。这里是对各个参数的详细解释：

1. `sessionid=...`: 这里是 cookie 的名称及其值。`sessionid` 是一个常见的 cookie 名称，用于存储用户的会话 ID。`...` 表示具体的会话 ID 值，此处省略了具体内容，实际应用中应该是唯一的会话标识符。
2. `SameSite=Lax`: 这是一个安全属性，指示浏览器在发送这个 cookie 时遵循 SameSite 策略。`Lax` 表示在跨站（Cross-site）请求的情况下，只有 top-level navigation（即 GET 请求）才会携带此 cookie，而不会在跨站的子资源请求（如图片、iframe、Ajax 请求等）中携带。这有助于防止跨站请求伪造（CSRF）攻击，同时仍允许一些安全的跨站交互。
3. `Secure`: 这是一个安全标志，表示该 cookie 只能在 HTTPS 安全连接上传输。如果设置了 `Secure`，浏览器将不会在 HTTP 连接上发送此 cookie，从而增加了 cookie 在网络传输过程中的安全性。
4. `HttpOnly`: 这也是一个安全标志，它告诉浏览器不应让 JavaScript 通过 `document.cookie` API 访问此 cookie。这意味着即使网页存在 XSS（跨站脚本攻击）漏洞，攻击者也无法通过脚本窃取带有 `HttpOnly` 标志的 cookie。
5. `Path=/`: 这指定了 cookie 生效的路径范围。这里的 `Path=/` 表示 cookie 适用于服务器上的所有路径。当浏览器向服务器发送请求时，只要请求路径位于指定的路径之下，就会把此 cookie 发送给服务器。

综上所述，这段 Nginx 配置指令是在为客户端设置一个名为 `sessionid` 的 cookie，它在 Lax SameSite 策略下工作，只能通过 HTTPS 传输，且不能被 JavaScript 访问，应用于服务器的所有路径。这些都是为了加强 session cookie 的安全性和隐私保护措施。



SameSite 属性是用来防止 CSRF 攻击的一种 Cookie 安全属性，它可以设置为三个值：`Strict`、`Lax` 或 `None`。目前的推荐做法是：

- 对于大多数网站，优先考虑设置为 `Lax`，因为它既可以防止跨站请求伪造（CSRF），又能保持对导航到目标网站的部分 GET 请求的支持（例如，链接跳转）。
- 对于那些需要在跨站上下文中（例如嵌入在 iframe 中）进行无痕登录或操作的网站，可以考虑设置为 `None`，但这需要同时设置 `Secure` 属性（即仅限 HTTPS）以提高安全性。

以下是基于不同的 Web 服务器配置 SameSite 属性的示例：



`X-Content-Type-Options` 是一个 HTTP 响应头，用于帮助防止 MIME 类型混淆攻击，即浏览器在遇到资源的实际内容与服务器声明的 MIME 类型不匹配时，不会尝试去猜测资源的真实类型并据此执行。正确设置此响应头可以增强网页安全性。为了确保安全，你应该将其设置为 `nosniff`。

要在不同服务器上设置 `X-Content-Type-Options`，可以按照以下方式操作：



```
# 在 server 或 location 块中添加如下配置：

# 禁止任何情况下发送 Referer 信息
add_header Referrer-Policy no-referrer;

# 默认情况下仅在同源请求或同等安全级别（HTTPS 到 HTTPS）时发送 Referer，降级（HTTPS 到 HTTP）时不发送
add_header Referrer-Policy no-referrer-when-downgrade;

# 其他可选策略还包括：
# - same-origin：只在请求的目标与当前页面同源时发送 Referer。
# - origin：不论是否同源，都只发送源信息（scheme + host + port）。
# - strict-origin：与 `origin` 类似，但在不安全的上下文中不发送（如从 HTTPS 到 HTTP）。
# - origin-when-cross-origin：同源请求发送完整 Referer，跨源请求发送源信息。
# - unsafe-url：总是发送完整的 URL 作为 Referer，包括查询字符串和片段标识符（最不安全的选择）。

# 选择适合您需求的策略，例如：
add_header Referrer-Policy same-origin;

# 如果要全局应用，可以在 http 或 server 块中添加：
http {
    include mime.types;
    default_type application/octet-stream;
    sendfile on;
    keepalive_timeout 65;

    # 添加 Referrer Policy
    add_header Referrer-Policy same-origin;
    # ...
}
```

