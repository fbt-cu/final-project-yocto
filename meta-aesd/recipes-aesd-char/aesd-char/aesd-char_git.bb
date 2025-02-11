# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "gitsm://git@github.com/cu-ecen-aeld/assignments-3-and-later-fbt-cu.git;protocol=ssh;branch=main \
           file://0001-Update-makefile.patch \
           file://0002-Fix-errors.patch \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "77e55c427987e3abb98c8f40e681ad62c4ec9276"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
EXTRA_OEMAKE += "-C ${STAGING_KERNEL_DIR} M=${S}/aesd-char-driver"
inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "aesd-char-driver_init"

RPROVIDES:${PN} += "kernel-module-aesd-char"
FILES:${PN} += "${bindir}/aesdchar_load ${bindir}/aesdchar_unload"
FILES:${PN} += "${sysconfdir}/init.d/aesd-char-driver_init"

do_install:append () {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/aesdchar_load ${D}${bindir}/aesdchar_load
    install -m 0755 ${WORKDIR}/aesdchar_unload ${D}${bindir}/aesdchar_unload

    # Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesd-char-driver_init ${D}${sysconfdir}/init.d
}
