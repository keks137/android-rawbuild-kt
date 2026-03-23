#!/bin/bash
set -xe

SDK=~/android_sdk
PLATFORM=$SDK/platforms/android-29
BUILD_TOOLS=$SDK/build-tools/29.0.3

kotlinc src/*.kt -d classes/ \
    -cp $PLATFORM/android.jar \
    -include-runtime

mkdir -p dex
$BUILD_TOOLS/d8 \
    --lib $PLATFORM/android.jar \
    classes/bubble/keks/androtest/*.class \
    --output dex/

# Package
$BUILD_TOOLS/aapt2 link  -o base.apk \
    --manifest AndroidManifest.xml \
    -I $PLATFORM/android.jar \
    --min-sdk-version 29

cp base.apk app.apk
zip -uj app.apk dex/classes.dex

$BUILD_TOOLS/zipalign -f 4 app.apk app-aligned.apk
$BUILD_TOOLS/apksigner sign --ks debug.keystore --ks-pass pass:android app-aligned.apk
