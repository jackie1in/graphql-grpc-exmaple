# docker/docker-compose启动
## 版本(docker/docker-compose)
![版本](./asset/dokcerversion.jpg)
## 下载
```
git clone https://github.com/silencecorner/graphql-grpc-exmaple.git
```
## 运行
```
docker-compose up -d
```
访问地址 http://localhost:4000/graphql
## 停止删除镜像
### 停止
```
docker-compose down
```
### 停止删除镜像
```
docker-compose down --rmi all
```

## 说明
docker最低版本为17.06.0才支持stage构建
# npm配置
https://segmentfault.com/a/1190000016090267?utm_source=tag-newest

# 原demo
https://medium.com/@svengau_17540/when-graphql-meets-grpc-3e9729d32e05

# proto-loader
https://github.com/grpc/grpc-node/tree/master/packages/proto-loader
---
https://github.com/protobufjs/protobuf.js

# apoll-sever
https://www.apollographql.com/docs/apollo-server/

## grphql-api

### dev
```
npm run dev
```
### production
```
npm run production
```
# graphql操作
```
mutation {
 addPost(data: { title: "helloooo",body: "这是一个body" }) {
   message
   result { _id title body }
 }
}
```

```
query{
  listPosts(page:1,limit:20){
    count
    page
    limit
    nodes{
      _id
      title
      body
    }
  }
}
```
# proto文件管理
## 注意事项
- proto文件名规范
> 本身编译时是支持文件名相同，只要的package不同就行，但是会觉得很怪，使用gradle插件编译的时候会出bug
## 编辑npmrc
添加下面几项
```
registry=http://product.beidougx.com.cn/nexus/content/groups/npm-all
email=hilin2333@gamil.com
always-auth=false
_auth=YWRtaW46YWRtaW4xMjM=
```

## 发布
```
npm publish --registry http://product.beidougx.com.cn/nexus/content/repositories/npm.local
```
## java中使用
### 正式环境 
直接通过npm的tgz文件下载，解压编译，可以通过npm的版本管理来做版本控制，当然本身也是有git仓库的
- [ ] 文件下载task
- [ ] 解压task
- [ ] 编译task
### 开发环境
- [X] 直接使用原始文件，支持发布到npm仓库
- [ ] 使用git仓库
- [ ] 切换版本task
## nodejs中
### 正式环境 
- [x] install发布protos版本
### 开发环境
- [ ] npm link(maybe)