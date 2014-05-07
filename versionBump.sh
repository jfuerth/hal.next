#!/bin/sh

# Use this script to bump the version accross all POMs, after starting a new release branch.

PROGNAME=`basename "$0"`

if [ "$#" -ne 1 ]; then
    echo "Illegal number of arguments. Use '$PROGNAME <version>'"
else
    mvn versions:set -DnewVersion=$1 -P build
    find . -name pom.xml.versionsBackup -exec rm {} \;
fi
