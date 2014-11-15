#!/bin/bash

# avoid virtual desktop jumps at mac terminal on test run
export JAVA_TOOL_OPTIONS='-Djava.awt.headless=true'

./gradlew clean :unit:test

echo "test reports: $(pwd)/unit/build/reports/tests/index.html"
