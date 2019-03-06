#!/usr/bin/env bash
# resolve links - $0 may be a softlink
##OUT_FILE=
##TEMP_DIR=
#PID_FILE=
if [ -z "$JAVA_OPTS" ];then
	JAVA_OPTS="-Dfile.encoding=utf8 -Dsun.jnu.encoding=UTF-8"
fi
MAIN_CLASS=com.vamsc.ais.store.TdstMain
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
APP_JAR=$APP_HOME/bin
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

if [ "$1" = "run" ];then
	shift
	cmd="$RUN_JAVA $JAVA_OPTS -classpath "$CLASSPATH" \
			  $MAIN_CLASS "$@""
	eval $cmd
	
elif [ "$1" = "start" ];then

	echo "PID_FILE=""$PID_FILE"
	if [ -f "$PID_FILE" ];then
		if [ -s "PID_FILE" ];then
			echo "Existing PID file found during start."
			if [ -r "$PID_FILE" ];then
				PID=`cat $PID_FILE`
				ps -p $PID >/dev/null 2&1
				if [ $? -eq 0 ];then
					echo "$APP_NAME appears to still be running with PID $PID. Start aborted."
					echo "If the following process is not a $APP_NAME process, remove the PID file and try again:"
					ps -f -p $PID
					exit 1
				else
					echo "Removing/clearing stale PID file."
					rm -f "$PID_FILE" >/dev/null 2>&1
					if [ $? -ne 0 ];then
						if [ -w "$PID_FILE" ];then
							cat /dev/null >"$PID_FILE"
						else
							echo "Unable to remove or clear stable PID file.Start aborted"
							exit 1
						fi
					fi
				fi
			else
				echo "Unable to read PID file,Start aborted"
				exit 1
			fi
		else
			rm -f $PID_FILE >/dev/null 2>&1
			if [ $? -ne 0 ];then
				if [ ! -w "$PID_FILE" ];then
					echo "Unable to remove or write to empty PID file.Start aborted."
					exit 1
				fi
			fi
		fi
	fi
	shift
	
	if [ ! -f "$APP_OUT" ];then
		touch "$APP_OUT"
	else
		echo "$APP_OUT exists"
	fi
	echo "Starting $APP_NAME"
	$RUN_JAVA $JAVA_OPTS -classpath "$CLASSPATH" \
	 		  $MAIN_CLASS "$@">> "$APP_OUT"	 2>&1 &
	echo "PID_FILE $PID_FILE"
	echo $! > "$PID_FILE"
	echo "$APP_NAME started"
	
elif [ "$1" = "stop" ];then
	shift
	if [ -r "$PID_FILE" ];then
		appid=`cat "$PID_FILE"`
	fi
	echo "Stopping $APP_NAME""pid is $appid"
	kill -15 $appid >/dev/null 2>&1
	if [ $? -eq 0 ];then
		echo "$APP_NAME stop sucessfully"
		rm -f "$PID_FILE"
	else
		echo "$APP_NAME stop failed"
	fi
	
elif [ "$1" = "debug" ];then
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
			  $MAIN_CLASS "-h ""$@""
	eval $cmd
fi
