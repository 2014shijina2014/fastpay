#author cuijun luke 201804  pre-shell
cd $WORKSPACE
#ls
echo "###############update weixinpay_config.properties file now##################"
cp ~/weixinpay_config.properties fast-pay-service/src/main/resources/weixinpay_config.properties

###############################################################################################
# post-shell
BUILD_ID=dontKillMeTomcatMaster
#author cuijun luke 201804
echo "start check WORKSPACE dir on jenkins server"
cd $WORKSPACE
ls
echo $ref

tomcatpid=`ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`

if test -z ${tomcatpid}
then
    echo $(date +%F" "%T)"--info--:tomcatpid not exsit,so pls go on 成功" 
else
    echo $(date +%F" "%T)"--info--:tomcatpid进程存在，pid="${tomcatpid}
    kill -9 ${tomcatpid}
fi

netstat -ntlp 
#clear tomcat cache
cd /mnt/apache-tomcat-9.0.6/work/Catalina
rm -rf localhost
#clear tomcat old wars
cd /mnt/apache-tomcat-9.0.6/webapps
rm -rf fast-pay-web-boss
rm -rf fast-pay-web-gateway
rm -rf fast-pay-web-merchant
rm -rf fast-pay-web-sample-shop
rm -f fast-pay-web-boss.war 
rm -f fast-pay-web-gateway.war
rm -f fast-pay-web-merchant.war
rm -f fast-pay-web-sample-shop.war
ls

#copy newest wars
cp $WORKSPACE/fast-pay-web-boss/target/fast-pay-web-boss.war  /mnt/apache-tomcatwars/
cp $WORKSPACE/fast-pay-web-gateway/target/fast-pay-web-gateway.war /mnt/apache-tomcatwars/
cp $WORKSPACE/fast-pay-web-merchant/target/fast-pay-web-merchant.war /mnt/apache-tomcatwars/
cp $WORKSPACE/fast-pay-web-sample-shop/target/fast-pay-web-sample-shop.war /mnt/apache-tomcatwars/

cd /mnt/apache-tomcatwars/
ls
cp /mnt/apache-tomcatwars/* /mnt/apache-tomcat-9.0.6/webapps/

cd /mnt/apache-tomcat-9.0.6/webapps
ls -l

cd /mnt/apache-tomcat-9.0.6/bin
nohup ./startup.sh &

sleep 2s
netstat -ntlp
ps -ef | grep tomcat

sleep 5s
#cp ~/weixinpay_config.properties $fastpay/fast-pay-service/src/main/resources/weixinpay_config.properties

exit 0

#######################################################
BUILD_ID=dontKillMeTomcatMaster0
#clear tomcat cache it诸葛亮
#post-shell execution
cd /mnt/apache-tomcat-9.0.6/work/Catalina
rm -rf localhost

#restart tomcat server
cd /mnt/apache-tomcat-9.0.6/bin
./restartTomcat.sh

netstat -ntlp
exit 0