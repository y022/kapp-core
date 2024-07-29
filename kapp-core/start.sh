process_name="kapp-core"

# 使用pgrep查找进程ID
Pid=$(pgrep -f $process_name)

# 检查进程是否存在
if [ -n "$Pid" ]; then
    echo "Process $process_name is running with Pid: Pid"
    kill Pid
    if kill -0 $Pid > /dev/null 2>&1; then
        echo "Process $process_name is still running"
        kill -9 Pid
    else
        echo "Process $process_name has been killed"
    fi
else
    echo "Process $process_name is not running"
fi
##
nohup /hom/app/jdk/bin/java -jar -Xms512m -Xmx512m /home/app/jar/kapp-core-v1.0.jar >> /home/logs/kapp_core_log.txt  2>1 &