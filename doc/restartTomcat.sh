#!/bin/sh

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# -----------------------------------------------------------------------------
# Start Script for the CATALINA Server
# -----------------------------------------------------------------------------
cd /mnt/apache-tomcat-9.0.6/work/Catalina
rm -rf localhost

#restart tomcat server
cd /mnt/apache-tomcat-9.0.6/bin

tomcatpido=`ps -ef | grep tomcat | grep -v grep | awk '{print $2}'`

if test -z ${tomcatpido}
then
    echo $(date +%F" "%T)"--info--:tomcatpido not exsit,so pls go on 成功" 
else
    echo $(date +%F" "%T)"--info--:tomcatpido进程存在，pid="${tomcatpido}
    kill -9 ${tomcatpido}
fi
#if [ [ $? != 0 ] ]
./startup.sh
