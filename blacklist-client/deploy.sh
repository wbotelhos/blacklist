#!/bin/sh

ant -f ~/workspace/blacklist/blacklist-client/ant/build.xml																&& \
cd ~/workspace/blacklist/blacklist-client/target/war																	&& \
scp -i ~/blacklist.pem blacklist-client.war ec2-user@FILTERED:/home/ec2-user/Development/apache-tomcat-7.0.23/webapps	&& \
ssh -i ~/blacklist.pem ec2-user@FILTERED '/home/ec2-user/Development/apache-tomcat-7.0.23/bin/shutdown.sh && cd /home/ec2-user/Development/apache-tomcat-7.0.23/webapps && rm -rf blacklist-client && mv blacklist-client.war blacklist-client.zip && unzip -q blacklist-client.zip -d blacklist-client && /home/ec2-user/Development/apache-tomcat-7.0.23/bin/startup.sh && rm blacklist-client.zip'
