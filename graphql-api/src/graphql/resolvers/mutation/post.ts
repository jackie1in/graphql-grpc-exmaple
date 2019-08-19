
import postClient from '../../services/postClient';


const addPost: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    let request = {...params.request, authorId: 1}
    postClient.addPost(request, function (err: any, response: any) {
      if (err) {
        return reject(err.details);
      }
      resolve(response);
    });
  });
};

const updatePost: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    postClient.updatePost(params.request, function (err: any, response: any) {
      if (err) {
        return reject(err.details);
      }
      resolve(response);
    });
  });
};


export { addPost, updatePost };
