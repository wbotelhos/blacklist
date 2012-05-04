#!/bin/sh

scp -i ~/mockr.pem ~/workspace/blacklist/blacklist-client/sql.sql ec2-user@177.71.186.176:/home/ec2-user/Development/apache-tomcat-7.0.23/webapps/blacklist-client && \
ssh -i ~/mockr.pem ec2-user@177.71.186.176 'mysql -D r7 -u r7 -prrrrrrr < /home/ec2-user/Development/apache-tomcat-7.0.23/webapps/blacklist-client/sql.sql'
