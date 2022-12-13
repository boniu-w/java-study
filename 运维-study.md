1. linux 启动命令

   ```shell
   nohup java -Dserver.port=8086  -Dspring.config.additional-location=./application-dev.yml -jar ./springboot.jar > nohup.out 2>&1 &
   
   ```

   