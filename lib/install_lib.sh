#!/bin/sh

# check for root user
if [ "$(id -u)" -ne 0 ] ; then
	echo "You must run this script as root. Sorry!"
	exit 1
fi

cp -v libnvpipe.so   /usr/lib

