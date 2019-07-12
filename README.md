# docker/docker-compose启动
## 版本(docker/docker-compose)
![版本](./asset/dokcerversion.jpg)
## 下载
```
git clone https://github.com/silencecorner/graphql-grpc-exmaple.git
```
## 运行
```
docker-compose up
```
访问地址 http://localhost:4000/graphql
## 删除镜像
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
 addPost(data: { title: "helloooo" }) {
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
