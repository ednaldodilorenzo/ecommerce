#!/bin/sh
# Hot-reload runner: compile once, keep recompiling on source changes in the
# background, and run the app with bootRun so devtools restarts it whenever
# build/classes, build/resources or the service-comons jar change.
set -e

: "${SERVICE:?SERVICE must be set}"

WATCH_TASKS=":${SERVICE}:classes :service-comons:jar"

./gradlew $WATCH_TASKS

./gradlew $WATCH_TASKS --continuous --quiet &

exec ./gradlew ":${SERVICE}:bootRun"
