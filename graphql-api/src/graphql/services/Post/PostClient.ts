import fs from 'fs';
import * as grpc from 'grpc';
import * as protoLoader from '@grpc/proto-loader';

const fsReadDir = (dir: string, suffix: string): string[] => {
  let files = fs.readdirSync(dir)
  let tempFiles: string[] = [];
  if (suffix) {
    files.forEach(file => {
      if (file.endsWith(suffix)) {
        file = dir + file
        tempFiles.push(file)
      }
    })
  } else {
    files.forEach(file => {
      file = dir + file
      tempFiles.push(file)
    })
  }
  return tempFiles;
}
const files = fsReadDir(process.env.PWD + '/node_modules/protos/', '.proto')
console.log(JSON.stringify(files))
let packageDefinition: any = protoLoader.loadSync(files)

const proto: any = grpc.loadPackageDefinition(packageDefinition).sample;

const credentials: any = grpc.credentials.createSsl(
  fs.readFileSync(__dirname + '/cert/ca.crt'),
  fs.readFileSync(__dirname + '/cert/client.key'),
  fs.readFileSync(__dirname + '/cert/client.crt')
);

const interceptorAuth: any = (options: any, nextCall: any) =>
  new grpc.InterceptingCall(nextCall(options), {
    start: function (metadata, listener, next) {
      metadata.add('x-api-key', 'myapikey');
      next(metadata, listener);
    }
  });

const API_URL = process.env.POST_API_JAVA_URL || 'localhost:8080';
console.log(API_URL)
const options: any = {
  'grpc.ssl_target_name_override': API_URL,
  interceptors: [interceptorAuth]
};

// export default () => new proto.PostService(API_URL, credentials, options);
export default () => new proto.PostService(API_URL, grpc.credentials.createInsecure());
