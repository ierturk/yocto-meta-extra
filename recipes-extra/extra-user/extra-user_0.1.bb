SUMMARY = "Example recipe for using inherit useradd"
DESCRIPTION = "This recipe serves as an example for using features from useradd.bbclass"
SECTION = "examples"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

S = "${WORKDIR}"

EXCLUDE_FROM_WORLD = "1"

inherit useradd

USERADD_PACKAGES = "${PN}"

GROUPADD_PARAM:${PN} = "-g 988 render; -g 1000 ierturk"
USERADD_PARAM:${PN} = "-G adm,sudo,users,plugdev,audio,video,dialout,input,docker,render -p 1nyv3S0bODNy2 -u 1000 -d /home/ierturk -r -s /bin/bash ierturk"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"


pkg_postinst_ontarget_${PN} () {
    if [ ! -e /etc/.passwd_changed ]; then
        passwd -e ierturk
        touch /etc/.passwd_changed
    fi

    if [ -e /mnt/etc/nvidia-container-runtime/host-files-for-container.d/libcublas.csv ]; then
       /mnt/etc/nvidia-container-runtime/host-files-for-container.d/libcublas.csv \
        /mnt/etc/nvidia-container-runtime/libcublas.csv
    fi
}
