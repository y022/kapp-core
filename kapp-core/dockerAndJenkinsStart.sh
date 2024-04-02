echo "==============================package success====================================="

if docker container inspect kapp-core &>/dev/null; then

if docker container inspect --format '{{.State.Running}}' kapp-core | grep -q '^true$'; then
 echo "当前已有同名容器服务正在运行，尝试停止！！！"
 docker stop kapp-core
fi

 docker rm kapp-core
 docker rmi kapp-core

fi

docker build -t kapp-core ./

docker run -d --name kapp-core --restart=always -p 9622:9622 -e JAVA_OPTS="-Xms256m -Xmx512m"  kapp-core