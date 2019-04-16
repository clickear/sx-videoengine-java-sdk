#!/bin/sh

# check for root user
if [ "$(id -u)" -ne 0 ] ; then
	echo "You must run this script as root. Sorry!"
	exit 1
fi

# copy include header files to /usr/include
cp -rv ./include/*   /usr/include

# copy lib files to /usr/lib
cp -rv ./lib/* /usr/lib


# 安装opengel
#apt update
#apt -y install autoconf automake build-essential libass-dev libfreetype6-dev \
#  git libsdl2-dev libtheora-dev libtool libva-dev libvdpau-dev libvorbis-dev libxcb1-dev libxcb-shm0-dev \
#  libxcb-xfixes0-dev pkg-config texinfo wget zlib1g-dev \
#  mesa-common-dev libosmesa6-dev libglu1-mesa-dev libglfw3 libglew-dev