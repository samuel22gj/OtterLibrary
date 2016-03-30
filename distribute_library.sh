#!/usr/bin/env bash

# Reference: http://inthecheesefactory.com/blog/how-to-upload-library-to-jcenter-maven-central-as-dependency/en

# Setting the current directory to the location of the shell script.
cd "$(dirname "$0")"

# Build the library files
./gradlew install

echo ""
read -p "Distribute library to JFrog Bintray [y/n]? " -n 1 ans
echo ""
echo ""

if [[ $ans = "y" ]]; then
    # Upload the library files to JFrog Bintray
    ./gradlew bintrayUpload
fi
