FROM node:10.16.0-alpine AS build

USER root

WORKDIR /usr/src/app

COPY . /usr/src/app/

# ARG NODE_ENV=development
# ENV NODE_ENV=${NODE_ENV}

# RUN apk --no-cache add python make g++
RUN npm run build

FROM node:10.16.0-alpine
USER node
RUN mkdir /home/node/app/
WORKDIR /home/node/app/
COPY --from=build /usr/src/app/build ./build
COPY --from=build /usr/src/app/node_modules ./node_modules
ENTRYPOINT exec node ./build/server.js
EXPOSE 4000
