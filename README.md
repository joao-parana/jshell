# JShell with Java 9

## Rodando no MAC OSX com Boot2Docker.

Premissas:

    /etc/hosts com entrada para dockerhost.local apontando para 192.168.59.103
    ping -c 3 dockerhost.local 
    contêiner: mariadb
    database: test 
    user: test
    passwd: test

Comandos no Terminal

    mvn install && mvn assembly:single  
    export MYSQL_PORT_3306_TCP_ADDR=dockerhost.local
    # Parando e removendo o contêiner MySQL caso já exista
    docker stop mysql_db ; docker rm  mysql_db
    export MYSQL_ROOT_PASSWORD=xpto
    docker run -d --name mysql_db -p 3306:3306 \
           -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
           -e MYSQL_DATABASE=test \
           -e MYSQL_USER=test \
           -e MYSQL_PASSWORD=test \
           mariadb bash
    mysql -u root -h dockerhost.local -p # Informar senha xpto
    mysql> show databases;
    mysql> use test;
    mysql> show tables; 
    java -jar target/myJshellAppLib.jar

Rodando com JShell no conteiner

    
    
