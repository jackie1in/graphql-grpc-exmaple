## npm配置
https://segmentfault.com/a/1190000016090267?utm_source=tag-newest

# 原demo
https://medium.com/@svengau_17540/when-graphql-meets-grpc-3e9729d32e05

# proto-loader
https://github.com/grpc/grpc-node/tree/master/packages/proto-loader
https://github.com/protobufjs/protobuf.js

# apoll-sever
https://www.apollographql.com/docs/apollo-server/



# 删除镜像
```
docker-compose down --rmi all
```
# github
git clone https://github.com/silencecorner/graphql-grpc-exmaple.git
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
