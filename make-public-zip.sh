#!/bin/bash

set -eux

TMP_DIR=`mktemp -d`/

rsync -a \
--exclude=*.iml \
--exclude=.gradle \
--exclude=/local.properties \
--exclude=/.idea/caches \
--exclude=/.idea/libraries \
--exclude=/.idea/modules.xml \
--exclude=/.idea/workspace.xml \
--exclude=/.idea/navEditor.xml \
--exclude=/.idea/assetWizardSettings.xml \
--exclude=.DS_Store \
--exclude=/app/build \
--exclude=/captures \
--exclude=.externalNativeBuild \
--exclude=.cxx \
--exclude=local.properties \
--exclude=/.git \
--exclude=make-public-zip.sh \
--exclude=.gitlab-ci.yml \
--exclude=public.zip \
./ $TMP_DIR/

cat <<EOF > $TMP_DIR/app/src/main/java/com/example/pizzapay/Config.kt
package com.example.pizzapay

class Config {

    companion object {
        // Server backend's url
        const val BASE_URL: String = "https://your-server-url.example.com/pizzapay/api/v1"

        // As set in your AndroidManifest.xml
        const val DEEPLINK_SCHEME: String = "pizzapay"

        // As configured during onboarding
        const val REDIRECT_URI: String = "pizzapay://confirm-pay"
    }

}
EOF

cd $TMP_DIR

zip -r public.zip .

cd -

mv $TMP_DIR/public.zip .

rm -rf $TMP_DIR
