#!/bin/sh

if [ -z "${QUER_HOME}" ]; then
  export QUER_HOME="$(cd "`dirname "$0"`"/..; pwd)"
fi

echo $QUER_HOME

MAIN_CLASS=iotdb.data.generation.DataQuery

CLASSPATH=""
for f in ${QUER_HOME}/lib/*.jar; do
  CLASSPATH=${CLASSPATH}":"$f
done

echo ${CLASSPATH}


if [ -n "$JAVA_HOME" ]; then
    for java in "$JAVA_HOME"/bin/amd64/java "$JAVA_HOME"/bin/java; do
        if [ -x "$java" ]; then
            JAVA="$java"
            break
        fi
    done
else
    JAVA=java
fi


exec "$JAVA" -Duser.timezone=GMT+8 -Dlogback.configurationFile=${QUER_HOME}/conf/logback.xml  -cp "$CLASSPATH" "$MAIN_CLASS" "$@"

exit $?