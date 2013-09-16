require conf/license/openpli-gplv2.inc
require softcam.inc
inherit gitpkgv
inherit cmake

DESCRIPTION = "OScam ${PV} Open Source Softcam"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

BRANCH = "master"
SRCREV = "99553b60dacce7d245fc9f46ed31cf42d68dd161"
PV = "svn8917"
PKGV = "${PV}"
SRC_URI = "git://git.cuci.nl/oscam;protocol=git;branch=${BRANCH};tag=${SRCREV}"
PR = "r0"

DEPENDS = "libusb openssl"

S = "${WORKDIR}/git"
CAMNAME = "oscam"
CAMSTART = "/usr/bin/oscam --config-dir /etc/tuxbox/config/oscam --daemon --pidfile /tmp/oscam.pid --restart 2 --utf8"
CAMSTOP = "kill \`cat /tmp/oscam.pid\` 2> /dev/null"

SRC_URI += " \
	file://oscam.conf \
	file://oscam.server \
	file://oscam.srvid \
	file://oscam.user \
	file://oscam.provid"

CONFFILES = "/etc/tuxbox/config/oscam/oscam.conf /etc/tuxbox/config/oscam/oscam.server /etc/tuxbox/config/oscam/oscam.srvid /etc/tuxbox/config/oscam/oscam.user /etc/tuxbox/config/oscam/oscam.provid"

FILES_${PN} = "/usr/bin/oscam /etc/tuxbox/config/oscam/* /etc/init.d/softcam.oscam"

EXTRA_OECMAKE += "\
	-DOSCAM_SYSTEM_NAME=Tuxbox \
	-DWEBIF=1 \
	-DWITH_STAPI=0 \
	-DHAVE_LIBUSB=1 \
	-DSTATIC_LIBUSB=1 \
	-DWITH_SSL=1 \
	-DHAVE_PCSC=0"

do_install() {
	install -d ${D}/etc/tuxbox/config/oscam
	install -m 0644 ${WORKDIR}/oscam.* ${D}/etc/tuxbox/config/oscam/
	install -d ${D}/usr/bin
	install -m 0755 ${S}/oscam ${D}/usr/bin
}
