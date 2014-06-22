#!/bin/bash
# Author: Jishnu Mohan <jishnu7@gmail.com>
# 04-03-2014

location="/home/jishnu/git/IndicKeyboard"
src="kashmiri"

if [ $# -lt 1 ]
then
    echo "Usage : $0 language type"
    exit
fi
case "$2" in
    inscript)
        files=('kbd_'$src'_inscript.xml' 'keyboard_layout_set_'$src'_inscript.xml'
            'rows_'$src'_inscript.xml'
            'rowkeys_'$src'_inscript1.xml' 'rowkeys_'$src'_inscript2.xml'
            'rowkeys_'$src'_inscript3.xml' 'rowkeys_'$src'_inscript4.xml')
        ;;
    *)
        files=('kbd_$src.xml' 'keyboard_layout_set_$src.xml'
            'rows_$src.xml' 'rowkeys_$src1.xml'
            'rowkeys_$src2.xml' 'rowkeys_$src3.xml')
        ;;
esac

cd $location/java/res/xml/
for i in "${files[@]}"
do
    echo "copying... ${i/$src/$1}"
    sed "s/$src/${1}/g" $i > ${i/$src/$1}
done
