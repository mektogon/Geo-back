url="http://localhost:8080/actuator/health"
status_code=$(curl -s -o /dev/null -I -w %{http_code} ${url})
path_to_logs_app="/path/log.txt"
path_to_report_healthcheck="/path/healthcheck.txt"
if [[ "$status_code" != 200 ]] ; then
  echo $(date) Current status: $status_code. The application is not working correctly. A restart is in progress. >> $path_to_report_healthcheck
  pgrep java | xargs kill -9
  nohup java -jar Mobile-map-0.0.1-SNAPSHOT.war >> $path_to_logs_app &
else
  echo $(date) The app is working! >> $path_to_report_healthcheck
fi