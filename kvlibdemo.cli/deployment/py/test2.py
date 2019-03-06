#!/usr/bin/env python
import subprocess
import os
if __name__ == '__main__':
    JAVA_HOME = '/usr/local/java8'
    MAIN_CLASS = 'kevsn.libdemo.jopt.KApplication'
    APP_HOME = '/Users/kevin/Desktop/klibdemo-1.0'
    os.environ['CLASSPATH'] = APP_HOME + '/lib/*'
    
    cmd = '%s/bin/java %s >>/dev/null 2>&1 &' % (JAVA_HOME, MAIN_CLASS)
#     cmd2 = ['%s/bin/java' % JAVA_HOME, MAIN_CLASS]
    child = subprocess.Popen(cmd, shell=True)
#     child = subprocess.Popen(cmd2)
#     cmd = ['%s/bin/java' % JAVA_HOME, MAIN_CLASS, '>>', 'dev/null', '2>>&1', '&']
#     child = subprocess.Popen(cmd, shell=True)
    subprocess.check_call('echo $!', shell=True)
    print child.pid
    print 'running'
    

        
    
