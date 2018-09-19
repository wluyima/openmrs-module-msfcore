#!/bin/sh
set -e

if [ ! -z "$DIRS" ] 
then
  DATE=`date +%Y-%m-%d_%H-%M-%S`
  for DIR in ${DIRS//,/ } 
    do
      DIR_PAIR=(${DIR//:/ })
      7z a /backup/${DIR_PAIR[1]}-$DATE.zip ${DIR_PAIR[0]} -pJembi123
  done
else
  echo "DIRS environment not defined!"
fi
