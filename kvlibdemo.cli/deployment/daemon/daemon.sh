#!/usr/bin/env bash
#execute ccs by jsvc(the name is ccsd)
#use tdst application as a demo
APP_HOME=/usr/local/tdst
JAVA_HOME=/usr/local/java
PID_FILE=/var/run/tdst.pid
CLASSPATH=$APP_HOME/bin/*:$CCS_HOME/lib/*
DAEMON_CLASS=com.vamsc.ais.store.TdstDaemon
USER=ais
case $1 in
    start)
		shift
		$APP_HOME/bin/daemon \
			-user $USER
			-server \
			-home $JAVA_HOME \
			-pidfile $PID_FILE \
			-Dfile.encoding=utf8 \
			-wait 10 \
			-cp $CLASSPATH \
			-procname tdst \
			$DAEMON_CLASS "$@"
		;;
	
    stop)
		shift
		$APP_HOME/daemon \
			-user $USER
			-stop \
			-pidfile $PID_FILE \
			$DAEMON_CLASS "$@"
		;;
	*)
		arg=$1
		shift
	   	echo "error arg $arg"
		;;
esac

