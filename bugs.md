# 1. Illegal unquoted character ((CTRL-CHAR, code 10)): has to be escaped using backslash to be included 

这个异常通常发生在使用 Jackson 库进行 JSON 解析或序列化的时候。具体来说，这个错误表示在 JSON 字符串中遇到了一个非法的未转义字符（在这个案例中是控制字符，代码为 10，即换行符）。

```
ObjectMapper mapper = new ObjectMapper();

mapper.configure(JsonParser.Feature.*ALLOW_UNQUOTED_CONTROL_CHARS*, true);
```

