#!/usr/bin/env bash
set -m

DIR=`dirname "$0"`
JARS=(
    "./out/akkahttp/2.13.10/assembly.dest/out.jar"
    "./out/akkahttp/3.2.2/assembly.dest/out.jar"
    "./out/ziohttp/2.13.10/assembly.dest/out.jar"
    "./out/ziohttp/3.2.2/assembly.dest/out.jar"
    "./out/http4s/2.13.10/assembly.dest/out.jar"
    "./out/http4s/3.2.2/assembly.dest/out.jar"
)

for JAR in "${JARS[@]}"; do
    CMD="perf stat -e 'power/energy-pkg/' --timeout 60000 -- java -jar $JAR $DIR/workload/pets-600.json &"

    for APPLY_LOAD in {false,true}; do
        for RUN_N in {1..5}; do

            if [ "$APPLY_LOAD" == "true" ]; then
                printf "$JAR - With load. Run #${RUN_N}:\n\n"

                eval $CMD

                sleep 5
                wrk -t4 -d120 -d30 http://localhost:8080/sustainability-test

                fg %1
            else
                printf "$JAR - No load. Run #${RUN_N}:\n\n"

                eval $CMD
                fg %1
            fi
        done
    done
done
