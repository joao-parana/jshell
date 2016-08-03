FROM parana/java-jdk9:latest
#
# parana/java-jdk9 usa a versão 8 do Debian de codinome Jessie
#
MAINTAINER João Antonio Ferreira "joao.parana@gmail.com"

ENV REFRESHED_AT 2016-08-03

RUN apt-get update && apt-get install -y vim && apt-get clean

#
RUN mkdir /my-jshell-app
ADD my-jshell-app /my-jshell-app

WORKDIR /my-jshell-app

CMD ["/bin/bash"]
