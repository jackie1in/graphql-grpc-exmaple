import PostClient from '../services/Post/PostClient';
import Protobuf from 'protobufjs';
let LongBits = Protobuf.util.LongBits;

const  client = PostClient();

interface Params {
  _id: string;
}

export default (root: any, params: Params) => {
  return new Promise((resolve: any, reject: any) => {
    client.listPosts(params, function(err: any, response: any) {
      if (err) {
        return reject(err);
      }
      resolve(response);
    });
  }).then((data) => {
    data['nodes'].forEach(element => {
      element.createdAt = new Date(LongBits.from(element.createdAt).toNumber() * 1000)
    });
    return data;
  });
};
