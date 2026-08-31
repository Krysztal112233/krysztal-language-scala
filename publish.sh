#!/usr/bin/env bash
set -euo pipefail

./gradlew clean build
./gradlew publishMods --stacktrace
