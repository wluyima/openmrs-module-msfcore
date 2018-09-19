#docker run --rm --name msf-backup -e DIRS=/code:code_files -e 'SCHEDULE=*/1 * * * *' -v backup:/backup msf-openmrs-backup

FROM openmrs/cron-backup

ADD backup.sh /code
ADD restore.sh /code
RUN apk update
RUN apk add p7zip

CMD ["/bin/bash" , "cron-backup.sh"]
