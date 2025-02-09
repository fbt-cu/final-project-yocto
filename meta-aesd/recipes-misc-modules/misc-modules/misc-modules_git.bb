# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-fbt-cu.git;protocol=ssh;branch=main \
    file://ldd-misc-modules \
    file://module_load.sh \
    file://module_unload.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "0ec4ae8a8687d3b23e3ae082bf1c4e5e967785d0"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} M=${S}/misc-modules"

inherit update-rc.d

RPROVIDES:${PN} += "kernel-module-misc-modules"
# Flag package as one that uses init scripts
INITSCRIPT_PACKAGES = "${PN}" 
INITSCRIPT_NAME:${PN} = "ldd-misc-modules"
# Specify files to be packaged
FILES:${PN} += "${bindir}/module_load.sh ${bindir}/module_unload.sh"
FILES:${PN} += "${sysconfdir}/init.d/ldd-misc-modules"

do_install:append(){
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/module_load.sh ${D}${bindir}/module_load.sh
    install -m 0755 ${WORKDIR}/module_unload.sh ${D}${bindir}/module_unload.sh

    # Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/ldd-misc-modules ${D}${sysconfdir}/init.d
}
