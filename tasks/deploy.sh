#!/usr/bin/env bash
set -euo pipefail

KEY="spring-boot-app-key.pem"
HOST="ec2-54-226-179-87.compute-1.amazonaws.com"
USER="ec2-user"
JAR_LOCAL="target/tasks-0.0.1-SNAPSHOT.jar"
JAR_REMOTE="tasks-0.0.1-SNAPSHOT.jar"
APP_PORT="9000"

echo "==> Building jar..."
./mvnw clean package

echo "==> Uploading jar to EC2..."
scp -i "$KEY" "$JAR_LOCAL" "$USER@$HOST:~/$JAR_REMOTE"

echo "==> Restarting app on EC2..."
ssh -i "$KEY" "$USER@$HOST" bash -lc "'
  set -euo pipefail

  echo \"Loading environment variables...\"
  if [ -f ~/.env ]; then
    set -a
    source ~/.env
    set +a
  else
    echo \"ERROR: ~/.env file not found\" >&2
    exit 1
  fi

  echo \"Stopping anything on port $APP_PORT (if running)...\"
  PID=\$(sudo lsof -t -iTCP:$APP_PORT -sTCP:LISTEN || true)
  if [ -n \"\$PID\" ]; then
    sudo kill -TERM \$PID || true
    sleep 2
    sudo kill -KILL \$PID || true
  fi

  echo \"Starting app...\"
  nohup java -jar ~/$JAR_REMOTE > ~/tasks.log 2>&1 &

  sleep 2
  echo \"Now running:\"
  ps -ef | grep \"java -jar ~/$JAR_REMOTE\" | grep -v grep || true

  echo \"Last logs:\"
  tail -n 30 ~/tasks.log
'"

echo "==> Done."
echo "Check logs with:"
echo "ssh -i \"$KEY\" $USER@$HOST 'tail -f ~/tasks.log'"
