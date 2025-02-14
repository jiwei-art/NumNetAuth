# 一键登录取号完整工程示例

该项目为GetMobile的完整工程示例。

该示例**无法在线调试**，如需调试可下载到本地后替换 [AK](https://usercenter.console.aliyun.com/#/manage/ak) 以及参数后进行调试。

## 运行条件

- 下载并解压需要语言的代码;


- 在阿里云帐户中获取您的 [凭证](https://usercenter.console.aliyun.com/#/manage/ak) 并通过它替换下载后代码中的 ACCESS_KEY_ID 以及 ACCESS_KEY_SECRET;

- 执行对应语言的构建及运行语句

## 执行步骤

下载的代码包，在根据自己需要更改代码中的参数和 AK 以后，可以在**解压代码所在目录下**按如下的步骤执行：

- *最低要求Java 8*
```sh
mvn clean package
java -jar target/sample-1.0.0-jar-with-dependencies.jar
```
## 使用的 API

-  GetMobile：本接口用于号码认证服务端一键登录取号，成功取得号码后会将号码返回。 更多信息可参考：[文档](https://next.api.aliyun.com/document/Dypnsapi/2017-05-25/GetMobile)

## API 返回示例

*实际输出结构可能稍有不同，属于正常返回；下列输出值仅作为参考，以实际调用为准*


- JSON 格式 
```js
{
  "Code": "OK",
  "Message": "请求成功",
  "RequestId": "8906582E-6722",
  "GetMobileResultDTO": {
    "Mobile": "13900001234"
  }
}
```

