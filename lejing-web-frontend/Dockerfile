# 设置基础镜像
FROM nginx:1.22
# 定义作者
MAINTAINER Weasley "1432689025@qq.com"
# 将dist文件中的内容复制到 /usr/share/nginx/html/ 这个目录下面
COPY dist/ /usr/share/nginx/html/
# 将配置文件中的内容复制到 /etc/nginx 这个目录下面(增加自己的代理及一些配置)
RUN rm -rf /etc/nginx/nginx.conf
COPY nginx/nginx.conf /etc/nginx/nginx.conf
RUN echo 'echo init ok!!'
