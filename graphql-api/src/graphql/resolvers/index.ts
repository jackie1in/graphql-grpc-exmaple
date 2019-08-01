import addPost from './addPost';
import listPosts from './listPosts';
import {DateTimeScalar} from '../scalar'

const resolvers: any  = {
  DateTime: DateTimeScalar,
  Mutation: {
    addPost
  },
  Query: {
    listPosts
  },
};

export default resolvers;
