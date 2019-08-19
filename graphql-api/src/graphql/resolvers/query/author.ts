import authorClient  from '../../services/authorClient';

const author: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    authorClient.getAuthor({id : root.authorId}, function(err: any, response: any) {
      if (err) {
        return reject(err);
      }
      resolve(response);
    });
  });
};

const getAuthor: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    authorClient.getAuthor(params.request, function(err: any, response: any) {
      if (err) {
        return reject(err);
      }
      resolve(response);
    });
  });
};

export {author, getAuthor}