import postClient from '../../services/postClient';

const listPosts: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    postClient.listPosts(params.request, function(err: any, response: any) {
      if (err) {
        return reject(err);
      }
      resolve(response);
    });
  });
};
const posts: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    postClient.listPosts({authorId : root.id, ...params.request}, function(err: any, response: any) {
      if (err) {
        return reject(err);
      }
      resolve(response);
    });
  });
};

export { listPosts, posts };