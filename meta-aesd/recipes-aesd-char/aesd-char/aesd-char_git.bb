# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8ed1a118f474eea5e159b560c339329b \
                    file://assignment-autotest/LICENSE;md5=cde0fddafb4332f35095da3d4fa989dd \
                    file://assignment-autotest/Unity/LICENSE.txt;md5=b7dd0dffc9dda6a87fa96e6ba7f9ce6c"

SRC_URI = "gitsm://git@github.com/cu-ecen-aeld/assignments-3-and-later-fbt-cu.git;protocol=ssh;branch=main \
            file://aesd-char-driver_init \
            file://aesdchar_load \
            file://aesdchar_unload "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "4c9e264855532b4aba5b505255c6d5df21c95d3a"

S = "${WORKDIR}/git/aesd-char-driver"

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
