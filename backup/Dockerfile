FROM openmrs/cron-backup

ADD backup.sh /code
ADD restore.sh /code
RUN apk update
RUN apk add p7zip

CMD ["/bin/bash" , "cron-backup.sh"]
