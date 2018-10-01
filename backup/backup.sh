#!/bin/sh
set -e

if [ ! -z "$DIRS" ] 
then
  DATE=`date +%Y-%m-%d_%H-%M-%S`
  for DIR in ${DIRS//,/ } 
    do
      DIR_PAIR=(${DIR//:/ })
      ZIP_FILE_PASSWORD=$(</run/secrets/zip_file_password)
      if [ ! -z $ZIP_FILE_PASSWORD ]
      then
        zip --password $ZIP_FILE_PASSWORD -r /backup/${DIR_PAIR[1]}-$DATE.zip ${DIR_PAIR[0]}
      else
        echo "Zip file password cannot be empty"
      fi
  done
else
  echo "DIRS environment not defined!"
fi
