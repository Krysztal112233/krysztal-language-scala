#!/bin/bash

if ! command -v jq &> /dev/null; then
    exit 1
fi

if ! command -v parallel &> /dev/null; then
    exit 1
fi

if [ ! -f "supported.json" ]; then
    exit 1
fi

jq -r '.[]' supported.json | \
parallel -j 4 --halt now,fail=1 \
    "echo 'Processing version: {}'; SCALA3_VERSION={} ./gradlew build&&./gradlew publishMods"
