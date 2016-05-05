SUMMARY = "The anaconda package"
DESCRIPTION = "The anaconda package"
HOMEPAGE = "http://fedoraproject.org/wiki/Anaconda"
LICENSE = "GPLv2"
SECTION = "devel"

LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "e2fsprogs gettext intltool libarchive virtual/libx11 libnl1 \
           pango python rpm slang zlib dbus iscsi-initiator-utils audit \
           lvm2 system-config-keyboard-native libuser util-linux \
           libnewt libxcomposite gtk+ curl libarchive"

DEPENDS += "libxklavier glade \
            "

S = "${WORKDIR}/git"

# Disabled networkmanager...
DEPENDS += "networkmanager"

RDEPENDS_${PN} = "e2fsprogs e2fsprogs-e2fsck e2fsprogs-mke2fs \
                   e2fsprogs-tune2fs e2fsprogs-resize2fs \
                   ntfsprogs xfsprogs btrfs-tools nfs-utils-client \
                   parted dosfstools gzip libarchive lvm2 \
                   squashfs-tools openssh python python-misc python-modules python-dbus \
                   nspr nss python-nss python-pyparted python-pyblock \
                   cracklib-python system-config-keyboard system-config-keyboard-base \
                   system-config-date pykickstart libnewt-python dmraid lvm2 \
                   python-cryptsetup firstboot python-iniparse libnl1\
                   dmidecode python-meh libuser-python libuser \
                   libreport-python localedef device-mapper device-mapper-multipath \
                   python-pygobject python-rpm python-urlgrabber\
                   libgnomecanvas grub usermode tigervnc keybinder \
                   gtk-engine-clearlooks gtk-theme-clearlooks \
                   tzdata tzdata-misc tzdata-posix tzdata-right tzdata-africa \
                   tzdata-americas tzdata-antarctica tzdata-arctic tzdata-asia \
                   tzdata-atlantic tzdata-australia tzdata-europe tzdata-pacific \
                   module-init-tools smartpm util-linux efibootmgr \
                   ca-certificates xfsprogs-fsck xfsprogs-mkfs isomd5sum \
                   btrfs-tools ntfs-3g iproute2 mdadm shadow chkconfig \
                   util-linux-swaponoff util-linux-uuidgen python-blivet \
                   xrandr glibc-charmaps glibc-localedatas python-ipy \
                   python-pytz python-langtable libpwquality-python \
                   python-ntplib libgnomekbd libtimezonemap \
                   procps python-prctl \
                "

RDEPENDS_${PN} += "networkmanager libnmutil libnmglib libnmglib-vpn \
                   network-manager-applet \
"

SRC_URI = "git://github.com/rhinstaller/anaconda;protocol=https;branch=rhel7-branch \
           file://smartinstall.py \
           file://wrlinux.py \
           file://widgets-Makefile.am-do-not-compile-doc.patch \
           file://utils-Makefile.am-do-not-compile-dd.patch \
           file://scripts-run-anaconda-replace-usr-bin-bash-with-bin-s.patch \
           file://tweak-native-language-support.patch \
           file://drop-selinux-module.patch \
          "

SRCREV = "1e5f44b5fd76489bbd95dba4e04f30939a71426b"

FILES_${PN}-dbg += "${libexecdir}/anaconda/.debug ${PYTHON_SITEPACKAGES_DIR}/pyanaconda/.debug"
FILES_${PN}-staticdev += "${PYTHON_SITEPACKAGES_DIR}/pyanaconda/_isys.a"
FILES_${PN} = "/lib ${libdir} ${sysconfdir} ${bindir} ${sbindir} ${libexecdir} ${datadir} ${PYTHON_SITEPACKAGES_DIR}/pyanaconda ${PYTHON_SITEPACKAGES_DIR}/log_picker"
FILES_${PN}-misc = "/usr/lib"
PACKAGES += "${PN}-misc"
RDEPENDS_${PN}-misc += "bash python"

EXTRA_OECONF += "--disable-selinux \
         --with-sysroot=${PKG_CONFIG_SYSROOT_DIR} \
"

#USE_NLS = "no"

inherit autotools-brokensep gettext pythonnative pkgconfig gobject-introspection

do_configure_prepend() {
	( cd ${S}; ${S}/autogen.sh --noconfigure)
}

do_compile_prepend() {
	( cd ${S}; make po-empty)
}

addtask do_setupdistro after do_patch before do_configure
do_setupdistro() {
	cp ${WORKDIR}/wrlinux.py ${S}/pyanaconda/installclasses/
	cp ${WORKDIR}/smartpayload.py ${S}/pyanaconda/packaging/
}

