#!/bin/bash

set -e

if [ $# != "1" ]; then
    echo "ERROR: you have to specify a filename for the output csv file!"
    exit 1
fi

FILENAME=$1

echo 'date;time;ip;port;type;iso_code;country' > $FILENAME

cat tarpit.log | grep -e 'Client' |\
    awk '{print $3,$4,$8,$9,$10}' |\
    sed -E "s/\('//g" | sed -E "s/'\,//g" | sed -E "s/\)//g" |\
    while read s; do
        echo $s $(geoiplookup $(echo $s | awk '{print $3}') | awk '{print $4}{for(i=5;i<=NF;i++)print $i}' | sed -E "s/\,\n/\;/" | tr '\n' '+')
    done |\
    tr ' ' ';' | tr '+' ' ' | sed -E "s/\, /\;/" >> $FILENAME

exit 0