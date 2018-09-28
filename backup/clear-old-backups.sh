#!/bin/sh

FILES_TO_DELETE=$(find /backup ! -mtime -7 )
for FILE in $FILES_TO_DELETE
do
        rm $FILE
        echo "Removed $FILE"
done
