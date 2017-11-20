FEATURE_PACKAGES_installer-support = "packagegroup-installer-support"
IMAGE_FEATURES_append = " installer-support"
EXTRA_IMAGEDEPENDS += "grub grub-efi"

EXTRA_IMAGEDEPENDS += "glibc-locale"

# Generate filesystem images for image copy install
IMAGE_FSTYPES += "ext4"

IMAGE_POSTPROCESS_COMMAND_append = " emit_image_env;"

inherit distro_features_check
REQUIRED_DISTRO_FEATURES = "systemd ldconfig"

python emit_image_env () {
    localdata = bb.data.createCopy(d)

    # Export DISTRO for installer build
    localdata.setVarFlag("DISTRO", "unexport", "")

    dumpfile = d.expand("${TOPDIR}/installersupport_${PN}")
    with open(dumpfile , "w") as f:
        bb.data.emit_env(f, localdata, True)
}

python __anonymous () {
    if not bb.utils.contains("PACKAGE_CLASSES", "package_rpm", True, False, d):
        raise bb.parse.SkipPackage('Target build requires RPM packages to be the default in PACKAGE_CLASSES.')
}
