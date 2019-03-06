#!/usr/bin/env python
import subprocess
import os
if __name__ == '__main__':
    JAVA_HOME = '/usr/local/java8'
    MAIN_CLASS = 'kevsn.libdemo.jopt.KApplication'
    APP_HOME = '/Users/kevin/Desktop/klibdemo-1.0'
    os.environ['CLASSPATH'] = APP_HOME + '/lib/*'
    cmd2 = JAVA_HOME + '/bin/java ' + MAIN_CLASS + " &"
    
    print child.pid

        
    
