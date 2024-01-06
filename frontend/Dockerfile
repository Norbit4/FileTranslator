FROM node:20 as build

WORKDIR /frontend

COPY package*.json ./

RUN npm install -g npm@9.3.1

COPY . .

RUN npm run build

FROM nginx:alpine

COPY --from=build /frontend/build /usr/share/nginx/html

EXPOSE 3000

CMD ["nginx", "-g", "daemon off;"]