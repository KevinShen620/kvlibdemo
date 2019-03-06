#!/usr/bin/env bash
# resolve links - $0 may be a softlink
##OUT_FILE=
##TEMP_DIR=
#PID_FILE=
if [ -z "$JAVA_OPTS" ];then
	JAVA_OPTS="-Dfile.encoding=utf8 -Dsun.jnu.encoding=UTF-8"
fi
MAIN_CLASS=com.vamsc.ais.store.dataimport.ImportToHbaseMain
APP_NAME=dataimport
JAVA_HOME=/usr/local/java
DEBUG_PORT=9000	
function flowlink(){
	program_file=$1
	#-h
    # -h file True if file exists and is a symbolic link
    #
	while [ -h "$program_file" ]; do
		ls=`ls -ld "$program_file"`
		link=`expr "$ls" : '.*-> \(.*\)$'`
		if expr "$link" : '/.*' > /dev/null; then
			program_file="$link"
		else
			program_file=`dirname "$program_file"`/"$link"
		fi
	done
	echo $program_file
}
APP_SHELL=`flowlink $0`
APP_DIR=`dirname "$APP_SHELL"`

[ -z "$APP_HOME" ] && APP_HOME=`cd "$APP_DIR/.." >/dev/null; pwd`
echo "Using APP_HOME:$APP_HOME"
if [ -r "$APP_HOME/bin/setenv.sh" ]; then
	"$APP_HOME/bin/setenv.sh"
fi

APP_JAR=$APP_HOME/bin/*

if [ ! -z "$CLASSPATH" ] ; then
	CLASSPATH="$CLASSPATH"":"
fi
CLASSPATH="$CLASSPATH"$APP_JAR
if [ -d $APP_HOME/lib ];then
	CLASSPATH="$CLASSPATH"":""$APP_HOME/lib/*"
fi
if [ -d $APP_HOME/config ];then
	CLASSPATH="$CLASSPATH:$APP_HOME/config"
fi

if [ -z "$OUT_FILE" ]; then
	if [ ! -d "$APP_HOME"/logs ];then
		mkdir "$APP_HOME"/logs
	fi
	APP_OUT="$APP_HOME"/logs/$APP_NAME.out
fi


if [ ! -z $JAVA_HOME ];then
	RUN_JAVA=$JAVA_HOME/bin/java
elif [ ! -z $JRE_HOME ];then
	RUN_JAVA=$JRE_HOME/bin/java
elif which java>/dev/null 2>&1;then
	RUN_JAVA=java
else
	echo "Cannot found Java"
	exit 1
fi

if [ -z $MAIN_CLASS ];then
	echo "Unknown MAIN_CLASS"
	exit 1
fi

if [ -z $PID_FILE ];then
	PID_FILE="$APP_HOME""/bin/"$APP_NAME".pid"
fi


if [ "$1" = "debug" ];then
	shift
	if [ -z "$DEBUG_PORT" ];then
		$DEBUG_PORT=9000
		JAVA_OPTS="$JAVA_OPTS ""-Xdebug -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=y"
		cmd="$RUN_JAVA $JAVA_OPTS -classpath "$CLASSPATH" \
			  $MAIN_CLASS "$@""
		eval $cmd
	fi
else
	cmd="$RUN_JAVA $JAVA_OPTS -classpath "$CLASSPATH" \
			  $MAIN_CLASS "$@""
	eval $cmd
fi
