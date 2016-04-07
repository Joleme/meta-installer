#
# Copyright (C) 2012-2015 Wind River Systems, Inc.
#            1) Skip login
#            2) While serail starting, it invokes screen attach
#               after bash started
#
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://serial-getty@.service \
           file://serial-screen-anaconda.sh \
"

do_install_append() {
	install -d ${D}${sysconfdir}/profile.d
	install -m 644 ${WORKDIR}/serial-screen-anaconda.sh ${D}${sysconfdir}/profile.d/
}