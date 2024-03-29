# 1. 数据类型



| .proto Type | Notes                                                        | C++ Type | Java/Kotlin Type[1] | Python Type[3]                  | Go Type | Ruby Type                      | C# Type    | PHP Type          | Dart Type |
| ----------- | ------------------------------------------------------------ | -------- | ------------------- | ------------------------------- | ------- | ------------------------------ | ---------- | ----------------- | --------- |
| double      |                                                              | double   | double              | float                           | float64 | Float                          | double     | float             | double    |
| float       |                                                              | float    | float               | float                           | float32 | Float                          | float      | float             | double    |
| int32       | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint32 instead. | int32    | int                 | int                             | int32   | Fixnum or Bignum (as required) | int        | integer           | int       |
| int64       | Uses variable-length encoding. Inefficient for encoding negative numbers – if your field is likely to have negative values, use sint64 instead. | int64    | long                | int/long[4]                     | int64   | Bignum                         | long       | integer/string[6] | Int64     |
| uint32      | Uses variable-length encoding.                               | uint32   | int[2]              | int/long[4]                     | uint32  | Fixnum or Bignum (as required) | uint       | integer           | int       |
| uint64      | Uses variable-length encoding.                               | uint64   | long[2]             | int/long[4]                     | uint64  | Bignum                         | ulong      | integer/string[6] | Int64     |
| sint32      | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int32s. | int32    | int                 | int                             | int32   | Fixnum or Bignum (as required) | int        | integer           | int       |
| sint64      | Uses variable-length encoding. Signed int value. These more efficiently encode negative numbers than regular int64s. | int64    | long                | int/long[4]                     | int64   | Bignum                         | long       | integer/string[6] | Int64     |
| fixed32     | Always four bytes. More efficient than uint32 if values are often greater than 228. | uint32   | int[2]              | int/long[4]                     | uint32  | Fixnum or Bignum (as required) | uint       | integer           | int       |
| fixed64     | Always eight bytes. More efficient than uint64 if values are often greater than 256. | uint64   | long[2]             | int/long[4]                     | uint64  | Bignum                         | ulong      | integer/string[6] | Int64     |
| sfixed32    | Always four bytes.                                           | int32    | int                 | int                             | int32   | Fixnum or Bignum (as required) | int        | integer           | int       |
| sfixed64    | Always eight bytes.                                          | int64    | long                | int/long[4]                     | int64   | Bignum                         | long       | integer/string[6] | Int64     |
| bool        |                                                              | bool     | boolean             | bool                            | bool    | TrueClass/FalseClass           | bool       | boolean           | bool      |
| string      | A string must always contain UTF-8 encoded or 7-bit ASCII text, and cannot be longer than 232. | string   | String              | str/unicode[5]                  | string  | String (UTF-8)                 | string     | string            | String    |
| bytes       | May contain any arbitrary sequence of bytes no longer than 232. | string   | ByteString          | str (Python 2) bytes (Python 3) | []byte  | String (ASCII-8BIT)            | ByteString | string            | List      |





# 2. 生成代码



python生产代码

```
python -m grpc_tools.protoc -I./grpc_sme/protos/assessment/defect/corrosion/ --python_out=./grpc_sme/service/assessment/defect/corrosion/asmeb31g --grpc_python_out=./grpc_sme/service/assessment/defect/corrosion/asmeb31g ./grpc_sme/protos/assessment/defect/corrosion/asmeb31g.proto
```



java 生产代码, 使用protobuf 插件