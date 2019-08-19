import * as path from 'path';
export const options = {
    keepCase: false,
    longs: String,
    enums: String,
    defaults: true,
    oneofs: true,
    includeDirs: [path.resolve(process.cwd(), './node_modules/protos')],
}