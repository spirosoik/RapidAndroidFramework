#!/usr/bin/env bash
./gradlew :app:testDebug \
          :app:connectedAndroidTest \
          -PcircleCi -PdisablePreDex