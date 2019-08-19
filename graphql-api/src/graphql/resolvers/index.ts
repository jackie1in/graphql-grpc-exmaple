import { addPost, updatePost } from './mutation/post';
import { listPosts, posts } from './query/post';
import { author, getAuthor } from './query/author';
import { addAuthor } from './mutation/author';
import {DateTimeScalar} from '../scalar'

const resolvers: any  = {
  DateTime: DateTimeScalar,
  Mutation: {
    addPost,
    updatePost,
    addAuthor
  },
  Query: {
    getAuthor,
    listPosts
  },
  Author: {
    posts
  },
  Post: {
    author
  }
};

export default resolvers;
