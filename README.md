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
           mariadb
    mysql -u root -h dockerhost.local -p # Informar senha xpto
    mysql> show databases;
    mysql> use test;
    mysql> show tables; 
    java -jar target/myJshellAppLib.jar

Rodando com JShell no conteiner

    ./build-jshell
    docker run -i -t --rm --name jshell \
           --link mysql_db:mysql \
           parana/jshell bash

    # Verificando a existencia de persistence.xml
    jar -tvf target/myJshellAppLib.jar META-INF/persistence.xml
    # Testando o JAR existente na console
    java -jar target/myJshellAppLib.jar
    # Testando o JShell
    jshell
    /classpath target/myJshellAppLib.jar 
    import br.com.joaoparana.*
    App app = new App()
    String[] args = {}
    App.main(args)
    
