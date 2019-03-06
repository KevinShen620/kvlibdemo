FROM k-ubuntu:16.04
MAINTAINER Kevin
RUN apt-get update && apt-get install -y nginx
RUN echo 'Hi,I am in your container' \
	>/usr/share/nginx/html/index.html
EXPOSE 80
