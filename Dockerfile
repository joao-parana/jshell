FROM parana/java-jdk9:latest
#
# parana/java-jdk9 usa a versão 8 do Debian de codinome Jessie
#
MAINTAINER João Antonio Ferreira "joao.parana@gmail.com"

ENV REFRESHED_AT 2015-12-30

#
RUN mkdir /my-jshell-app
ADD my-jshell-app /my-jshell-app

WORKDIR /my-jshell-app

CMD ["/bin/bash"]
