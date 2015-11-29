REM Set your JAVA_HOME appropriately
SET JAVA_HOME=C:\PROGRA~1\java\jre6

REM You may also need to set unlimited strength JCE extensions for the JRE here.
REM See the readme that comes with the distribution here: 
REM http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

SET PATH=%JAVA_HOME%/bin;%PATH

REM Because of WinCE limitations, now pass this as a system property using the -D flag on the cli
REM SET LUCAS_ENCRYPTION_PASSWORD=Hn4UKcorcdoQIFyby1BAs7ZQVmBm+NRk

set JARS=
set CP=
for %%i in (..\lib\*.jar) do call cappend.bat %%i
set CP=%JARS%

%JAVA_HOME%\bin\java -cp ".:%CP%"  -DLUCAS_ENCRYPTION_PASSWORD=Hn4UKcorcdoQIFyby1BAs7ZQVmBm+NRk com.lucas.alps.benchmark.Benchmark
