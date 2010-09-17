#!/bin/bash
# depend.sh dir cc args

DIR="$1"

shift 1

CC="$1"

shift 1

case "$DIR" in
"" | ".")
$CC -MM -MG "$@" | sed -e 's/^\(.*\).o:/\1.d \1.o:/' > temp
;;
*)
$CC -MM -MG "$@" | sed -e "s@^\(.*\).o:@$DIR/\1.d $DIR\1.o:@" > temp
;;
esac

cat temp

sed -e 's/^[^:]*://' -e 's/^ *//' -e 's/\\$//' -e 's/$/:/' temp

rm temp