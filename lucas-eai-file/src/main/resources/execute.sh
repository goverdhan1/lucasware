if [ "x$JAVA_MIN_MEM" = "x" ]; then
    JAVA_MIN_MEM=128M
    export JAVA_MIN_MEM
fi
         
if [ "x$JAVA_MAX_MEM" = "x" ]; then
    JAVA_MAX_MEM=512M
    export JAVA_MAX_MEM
fi

export DEFAULT_JAVA_OPTS="-Xms$JAVA_MIN_MEM -Xmx$JAVA_MAX_MEM -Duser.timezone="GMT" -Dcom.sun.management.jmxremote"

java $DEFAULT_JAVA_OPTS -Djava.endorsed.dirs="../lib/endorsed" -DLUCAS_ENCRYPTION_PASSWORD=Hn4UKcorcdoQIFyby1BAs7ZQVmBm+NRk -jar ../lib/lucas-eai-file-1.0.0.CI-SNAPSHOT.jar
