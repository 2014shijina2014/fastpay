BUILD_ID=dontKillMeTomcatMaster
#author cuijun luke 201804
echo "start check WORKSPACE dir on jenkins server"
cd $WORKSPACE
ls

tomcatpid=`ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`

if test -z ${tomcatpid}
then
    echo $(date +%F" "%T)"--info--:tomcatpid not exsit,so pls go on �ɹ�" 
else
    echo $(date +%F" "%T)"--info--:tomcatpid���̴��ڣ�pid="${tomcatpid}
    kill -9 ${tomcatpid}
fi

netstat -ntlp 

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

exit 0