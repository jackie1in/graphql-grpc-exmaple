FROM node:10.16.0-alpine AS build

USER root

WORKDIR /home/node/compile/

COPY schema /home/node/schema/
COPY protos /home/node/protos/

COPY graphql-api /home/node/compile/

RUN rm -rf node_modules && yarn run install:offline
RUN yarn run build

FROM node:10.16.0-alpine
USER node

WORKDIR /home/node/app/
COPY --from=build /home/node/compile/build ./build
COPY --from=build /home/node/compile/node_modules ./node_modules
ENTRYPOINT exec node ./build/server.js
EXPOSE 4000
