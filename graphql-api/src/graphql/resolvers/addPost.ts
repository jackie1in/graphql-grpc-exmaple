
import PostClient from '../services/Post/PostClient';
import Protobuf from 'protobufjs';
let LongBits = Protobuf.util.LongBits;

const client = PostClient();

export default (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    client.addPost(params.data, function (err: any, response: any) {
      if (err) {
        return reject(err.details);
      }
      resolve({ message: "post.created", result: response});
    });
  }).then((data) => {
    let createdAt = data['result']['createdAt']
    data['result']['createdAt'] = new Date(LongBits.from(createdAt.seconds).toNumber() * 1000)
    return data;
  });
};
