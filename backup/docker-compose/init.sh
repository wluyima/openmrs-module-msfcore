#!/bin/sh

stty -echo
read -p 'Zip file password: ' ZIP_FILE_PASSWORD
stty echo
echo
if [ ! -z "$ZIP_FILE_PASSWORD" ]
then
rm docker-compose*
wget https://raw.githubusercontent.com/edrisse/openmrs-module-msfcore/master/backup/docker-compose/docker-compose.yml
wget https://raw.githubusercontent.com/edrisse/openmrs-module-msfcore/master/backup/docker-compose/docker-compose.override.yml
wget https://raw.githubusercontent.com/edrisse/openmrs-module-msfcore/master/backup/docker-compose/docker-compose.prod.yml
echo $ZIP_FILE_PASSWORD > zip_file_password.txt
mkdir backup
else
  echo "Please specify a password for the zip file"
fi
