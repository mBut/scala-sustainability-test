#!/usr/bin/env bash
set -m

JAR="${1}"

DIR=`dirname "$0"`
CMD="perf stat -e 'power/energy-pkg/' --timeout 60000 -- java -jar $JAR ./workload/pets-600.json &"

for APPLY_LOAD in {false,true}; do
    for RUN_N in {1..3}; do

        if [ "$APPLY_LOAD" == "true" ]; then
            for LOAD_RATE in {20,40,60}; do
                printf "Load rate = ${LOAD_RATE}. Run #${RUN_N}:\n\n"

                eval $CMD

                sleep 5
                wrk2 -R 40 -d 30 -t 4 http://localhost:8080/sustainability-test

                fg %1
            done
        else
            printf "No load. Run #${RUN_N}:\n\n"

            eval $CMD
            fg %1
        fi
    done
done
