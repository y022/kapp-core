#!/bin/bash

process_name="kapp-core" # 替换为你要检查的进程名
# 使用pgrep查找进程ID
pid=$(pgrep -f $process_name)

# 检查进程是否存在
if [ -n "$pid" ]; then
    echo "Process $process_name is running with PID: $pid"
    kill $pid
    if kill -0 $pid > /dev/null 2>&1; then
        echo "Process $process_name is still running"
        # 尝试更强制的方式杀死进程
        kill -9 $pid
    else
        echo "Process $process_name has been killed"
    fi
else
    echo "Process $process_name is not running"
fi

java -jar -Xms256 -Xmx 512m kapp-core-v1.0.jar