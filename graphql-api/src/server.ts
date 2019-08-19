import express from 'express';
import { ApolloServer } from 'apollo-server-express';
import { schema as schemaPublic } from './graphql';

const server = new ApolloServer({ schema: schemaPublic, playground: {
  settings: {
    'editor.theme': 'light',
  }
},
});

const app = express();
server.applyMiddleware({ app });
const port = process.env.PORT || 4001
app.listen({ port: port }, () =>
  console.log(`🚀 Server ready at http://localhost:${port}${server.graphqlPath}`)
);