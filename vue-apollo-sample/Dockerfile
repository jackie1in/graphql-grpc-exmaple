FROM node:10.16.0-alpine AS build

USER root

WORKDIR /home/node/compile/
RUN apk add python
COPY schema /home/node/schema/

COPY vue-apollo-sample /home/node/compile/

RUN  yarn run install:offline

ARG VUE_APP_GRAPHQL_HTTP

RUN VUE_APP_GRAPHQL_HTTP=${VUE_APP_GRAPHQL_HTTP} yarn run build

FROM nginx:1.17.1-alpine

WORKDIR /usr/share/nginx/html
COPY --from=build /home/node/compile/dist/ .
ENTRYPOINT exec nginx -g 'daemon off;'
EXPOSE 8080
