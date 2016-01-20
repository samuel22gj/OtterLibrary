:: Reference: http://inthecheesefactory.com/blog/how-to-upload-library-to-jcenter-maven-central-as-dependency/en

@echo off

:: Setting the current directory to the location of the batch script.
pushd "%~dp0"

:: Build the library files
gradlew install
pause

:: Upload the library files to JFrog Bintray
gradlew bintrayUpload
pause
