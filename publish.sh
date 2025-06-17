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

# 使用jq提取版本列表并通过parallel并发执行
jq -r '.[]' supported.json | \
parallel -j 4 --halt now,fail=1 \
    "echo '正在处理版本: {}'; SCALA3_VERSION={} ./gradlew publishMods"
