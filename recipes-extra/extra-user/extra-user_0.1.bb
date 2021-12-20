SUMMARY = "Add non-system user"
DESCRIPTION = "This recipe add non-system user with sudo privileges"
SECTION = "extra"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

RDEPENDS:${PN} = "nvidia-docker"

# do_install() {
# 	install -dm 755 -o ierturk -g ierturk ${D}/home/ierturk
# }


EXCLUDE_FROM_WORLD = "1"
INHIBIT_DEFAULT_DEPS = "1"

ALLOW_EMPTY:${PN} = "1"

inherit useradd

USERADD_PACKAGES = "${PN}"

GROUPADD_PARAM:${PN} = "-g 988 render;"
USERADD_PARAM:${PN} = "-u 1000 -U -p 1nyv3S0bODNy2 -m -d /home/ierturk -G adm,sudo,users,plugdev,audio,video,dialout,input,docker,render ierturk;"

pkg_postinst_ontarget:${PN} () {
    if [ ! -e /etc/.passwd_changed ]; then
        passwd -e ierturk
        touch /etc/.passwd_changed
    fi

    if [ -e /etc/nvidia-container-runtime/host-files-for-container.d/libcublas.csv ]; then
       mv /etc/nvidia-container-runtime/host-files-for-container.d/libcublas.csv \
        /etc/nvidia-container-runtime/libcublas.csv
    fi
}
