SUMMARY = "Extra user accounts"
DESCRIPTION = "Set up user accounts for non-root usera"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

EXCLUDE_FROM_WORLD = "1"
INHIBIT_DEFAULT_DEPS = "1"

ALLOW_EMPTY:${PN} = "1"

FILESEXTRAPATHS:prepend := "${THISDIR}/sudo:"

SRC_URI:append = " \
    file://sudoers.extra \
"

# We explicitly require pam
inherit features_check

REQUIRED_DISTRO_FEATURES = "pam"

do_install:append () {
    install -m 0755 -d ${D}${sysconfdir}/sudoers.d
    install -m 0440 ${WORKDIR}/sudoers.extra ${D}${sysconfdir}/sudoers.d/50-ierturk
}

inherit useradd

USERADD_PACKAGES = "${PN}"

GROUPADD_PARAM:${PN} = "ierturk"
USERADD_PARAM:${PN} = "-G adm,sudo,users,plugdev,audio,video,dialout,input,docker -m -d /home/ierturk -P password ierturk"

pkg_postinst_ontarget_${PN} () {
    if [ ! -e /etc/.passwd_changed ]; then
        passwd -e ierturk
        touch /etc/.passwd_changed
    fi
}

