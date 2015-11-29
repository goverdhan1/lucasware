#!/bin/sh

# Added comment
#-- Set your JAVA_HOME ----#
export JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home
export PATH=$JAVA_HOME/bin:$PATH

# Pass this as a sytem prop instead
#export LUCAS_ENCRYPTION_PASSWORD=Hn4UKcorcdoQIFyby1BAs7ZQVmBm+NRk

CP=
for i in `ls ../lib/*.jar`
do
  CP=$CP:$i
done

echo $CP
java -cp ".:$CP"  -DLUCAS_ENCRYPTION_PASSWORD=Hn4UKcorcdoQIFyby1BAs7ZQVmBm+NRk com.lucas.alps.benchmark.Benchmark