#!/usr/bin/env bash
./gradlew :app:testDebug \
          :app:testUat \
          :app:connectedAndroidTest \
          -PcircleCi -PdisablePreDex