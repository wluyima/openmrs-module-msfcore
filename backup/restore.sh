#!/bin/sh
set -e

if [ ! -z "$DIRS" ] 
then
  for DIR in ${DIRS//,/ } 
    do
      DIR_PAIR=(${DIR//:/ })
      echo 'Clearing directory '${DIR_PAIR[0]}
      cd ${DIR_PAIR[0]}
      rm -rf * #removes all files but leaves hidden directories
      find . -type d -name '.[^.]*' -prune -exec rm -rf {} + #removes the hidden directories
      ZIP_FILE_PASSWORD=$(</run/secrets/zip_file_password)
      if [ ! -z $ZIP_FILE_PASSWORD ]
      then
        7z x /backup/${DIR_PAIR[1]}-$1.zip -p$ZIP_FILE_PASSWORD -o/
      else
        echo "Zip file password cannot be empty"
      fi
  done
else
  echo "DIRS environment not defined!"
fi
