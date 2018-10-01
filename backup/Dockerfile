FROM openmrs/cron-backup

ADD backup.sh /code
ADD restore.sh /code
ADD clear-old-backups.sh /code
ADD cron-backup.sh /code
RUN apk update
RUN apk add p7zip zip

CMD ["/bin/bash" , "cron-backup.sh"]
