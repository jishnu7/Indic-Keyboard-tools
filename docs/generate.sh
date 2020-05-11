#!/bin/sh
# Generate documentation

set -e

generate() {
    KEYBOARD=$1
    java -jar build/libs/docs.jar "../../java/res/xml/kbd_$KEYBOARD.xml" "../../docs/generated/$KEYBOARD.json"
}

getKeyboardFileNames() {
    # https://stackoverflow.com/a/41965460/589184
    find '../../java/res/xml/' -name 'kbd_*' -exec basename {} .xml ';'
}


./gradlew build

mkdir -p ../../docs/generated

getKeyboardFileNames | while read -r kbd
do
  keyboardName=${kbd#kbd_}
  echo "Processing $keyboardName"
  generate "$keyboardName"
done
