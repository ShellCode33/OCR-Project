#!/bin/bash
cd "$(dirname "$0")"

if [ ! -d out ]; then
  mkdir out;
fi

#clean
rm jar_ocrplugin.jar
rm -rf out/*

#compile
javac -cp "imagej-1.47.jar" -d out src/fr/iut/ocr/*.java

cd out

#package
jar cvfM ../jar_ocrplugin.jar * ../plugins.config
cp ../jar_ocrplugin.jar $HOME/.imagej/plugins/

#run
DEBUG=1 imagej
