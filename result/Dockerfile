FROM node:5.11.0-slim

ADD package.json /package.json

WORKDIR /

RUN npm install -g nodemon

RUN npm config set registry http://registry.npmjs.org
RUN npm install && npm ls

ADD . /app

WORKDIR /app

ENV PORT 80
EXPOSE 80

CMD ["node", "server.js"]
