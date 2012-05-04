#!/bin/sh

ant -f ~/workspace/blacklist/blacklist-server/ant/build.xml																&& \
cd ~/workspace/blacklist/blacklist-server/target/war																	&& \
scp -i ~/blacklist.pem blacklist-server.war ec2-user@FILTERED:/home/ec2-user/Development/apache-tomcat-7.0.23/webapps	&& \
ssh -i ~/blacklist.pem ec2-user@FILTERED '/home/ec2-user/Development/apache-tomcat-7.0.23/bin/shutdown.sh && cd /home/ec2-user/Development/apache-tomcat-7.0.23/webapps && rm -rf blacklist-server && mv blacklist-server.war blacklist-server.zip && unzip -q blacklist-server.zip -d blacklist-server && /home/ec2-user/Development/apache-tomcat-7.0.23/bin/startup.sh && rm blacklist-server.zip'
