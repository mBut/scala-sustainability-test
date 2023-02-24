#!/usr/bin/env bash

DIR=`dirname "$0"`

JARS=(
    "$DIR/out/circe/2.13.10/assembly.dest/out.jar"
    "$DIR/out/circe/3.2.2/assembly.dest/out.jar"
)

WORKLOADS=(
    "$DIR/workload/pets-1000.json"
    "$DIR/workload/pets-10000.json"
    "$DIR/workload/pets-100000.json"
)

for JAR in "${JARS[@]}"; do
    for WORKLOAD in "${WORKLOADS[@]}"; do
        for RUN_N in {1..5}; do
            printf "$JAR - Run #${RUN_N}:\n\n"
            perf stat -e 'power/energy-pkg/' -- java -jar $JAR $WORKLOAD
        done
    done
done
