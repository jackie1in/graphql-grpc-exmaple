import fs from 'fs';
import * as grpc from 'grpc';
import * as protoLoader from '@grpc/proto-loader';
import { options } from './option';

let packageDefinition: any = protoLoader.loadSync('Author.proto', options)

const proto: any = grpc.loadPackageDefinition(packageDefinition).sample['author'];


const API_URL = process.env.POST_API_JAVA_URL || 'localhost:8080';
console.log(API_URL)


// export default () => new proto.PostService(API_URL, credentials, options);
console.log(`post service address: ${API_URL}`)
export default new proto.AuthorService(API_URL, grpc.credentials.createInsecure());
