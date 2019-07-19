FROM node:10.16.0-alpine AS build

USER root

WORKDIR /home/node/compile/

COPY . /home/node/compile/

RUN  yarn run install:offline

RUN yarn run build

FROM nginx:1.17.1-alpine

WORKDIR /usr/share/nginx/html
COPY --from=build /home/node/compile/dist/ .
ENTRYPOINT exec nginx -g 'daemon off;'
EXPOSE 8080
