BUILD_ID=dontKillMe
#authror luke 诸葛亮 201804
echo "start start vesta code now ......................."
netstat -ntlp
cd $WORKSPACE
cd vesta-rest-netty 
cd target
ls

nettypid=`ps -ef | grep netty | grep -v grep | awk '{print $2}'`

if test -z ${nettypid}
then
    echo $(date +%F" "%T)"--info--:nettypid not exsit,so pls go on 成功" 
else
    echo $(date +%F" "%T)"--info--:nettypid进程存在，pid="${nettypid}
    kill -9 ${nettypid}
fi

netstat -ntlp

tar xzvf vesta-rest-netty-0.0.1-bin.tar.gz

#cat vesta-rest-netty-0.0.1/bin/server.sh

cd vesta-rest-netty-0.0.1/bin 
chmod 755 *

sed -i "s/8088/8089/" server.sh

nohup ./start.sh  &


netstat -ntlp
#if [ $? -eq 0 ]
#then
#    echo " build is  sucessed"
#    netstat -ntlp
#else
#    echo " build is  failed"
#fi
#cat server.sh
#nohup ./start.sh  &
#sleep 2s

echo " deploy vesta has been done！"
exit 0