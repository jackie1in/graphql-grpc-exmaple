
import authorClient  from '../../services/authorClient';

const addAuthor: Function = (root: any, params: any) => {
  return new Promise((resolve: any, reject: any) => {
    authorClient.addAuthor(params.request, function (err: any, response: any) {
      if (err) {
        return reject(err.details);
      }
      resolve(response);
    });
  });
};
export {addAuthor}
