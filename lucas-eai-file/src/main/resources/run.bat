@echo off
setlocal



set DEFAULT_JAVA_OPTS=-server -Xmx512M -Duser.timezone="GMT" -Dcom.sun.management.jmxremote

set BOOT_OPTS=-Djava.endorsed.dirs="%JRE_HOME%\lib\endorsed"

java %JAVA_OPTS% %BOOT_OPTS% -jar -DLUCAS_ENCRYPTION_PASSWORD=Hn4UKcorcdoQIFyby1BAs7ZQVmBm+NRk ../lib/lucas-eai-file-1.0.0.CI-SNAPSHOT.jar

endlocal