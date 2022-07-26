SUMMARY = "Add non-system user"
DESCRIPTION = "This recipe add non-system user with sudo privileges"
SECTION = "extra"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

# do_install() {
# 	install -dm 755 -o ierturk -g ierturk ${D}/home/ierturk
# }


EXCLUDE_FROM_WORLD = "1"
INHIBIT_DEFAULT_DEPS = "1"

ALLOW_EMPTY:${PN} = "1"

inherit useradd

USERADD_PACKAGES = "${PN}"

USERADD_PARAM:${PN} = "-u 1000 -U -p 1nyv3S0bODNy2 -m -d /home/ierturk -G sudo ierturk;"

pkg_postinst_ontarget:${PN} () {
    if [ ! -e /etc/.passwd_changed ]; then
        passwd -e ierturk
        touch /etc/.passwd_changed
    fi
}
