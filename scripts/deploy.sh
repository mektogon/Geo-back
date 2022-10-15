#!/usr/bin/env bash

mvn clean package

echo "Copying files... Please wait."

scp -r -i ~/.ssh/id_rsa \
  target/Mobile-map-0.0.1-SNAPSHOT.war \
  changeMeToServerAddress:/folderToCopy

echo "Restart server... Please wait."

ssh -i ~/.ssh/id_rsa changeMeToServerAddress << EOF
pgrep java | xargs kill -9
nohup java -jar Mobile-map-0.0.1-SNAPSHOT.war > log.txt &
EOF

echo "The server is running. Bye!"
