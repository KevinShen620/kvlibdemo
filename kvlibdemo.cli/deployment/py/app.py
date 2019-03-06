#!/usr/bin/env python
import os
import sys
import subprocess
import shutil
envvars = {}
if os.path.exists('env.py'):
    import env
    envvars = env.__dict__


def get_java():
    JAVA_HOME = envvars.get('JAVA_HOME')
    if JAVA_HOME:
        jhome = JAVA_HOME
    else:
        jhome = os.getenv('JAVA_HOME', '/usr/local/java')
    java = jhome + os.sep + 'bin' + os.sep + 'java';
    if os.path.exists(java) and os.access(java, os.X_OK):
        return jhome, java
    raise Exception('not found java')

def get_app_home():
    APP_HOME = envvars.get('APP_HOME')
    if APP_HOME:
        return APP_HOME
    fpath = sys.argv[0]
    return os.path.dirname(os.path.realpath(fpath))
        

def get_class_path(app_home):    
    CLASS_PATH = envvars.get('CLASSPATH')
    if CLASS_PATH:
        return CLASS_PATH
    lib_path = app_home + os.sep + 'lib'
    if os.path.isdir(lib_path):
        return lib_path + os.sep + '*'
    return NONE
    
def get_main_class():
    MAIN_CLASS = envvars.get('MAIN_CLASS')
    if not MAIN_CLASS:
        raise Exception('main-class has not been defined')
    return MAIN_CLASS

def get_app_name():
    pyfile = sys.argv[0]
    filename = os.path.basename(pyfile)
    if filename.endswith('.py'):
        return filename[0:len(filename) - 3]
    elif filename.endswith('.pyc'):
        return filename[0:len(filename) - 4]
    return filename

java = get_java()
app_home = get_app_home()
class_path = get_class_path(app_home)
main_class = get_main_class()
app_name = get_app_name()

def kill_old_pro():
    pidfile = get_pid_file()
    if not os.path.exists(pidfile):
        return
    print 'found pid file %s' % pidfile
    with file(pidfile) as f:
        for line in f:
            try:
                str_pid = line.strip()
                pid = int(line)
                print 'kill %d' % pid
                kill_process(pid)
            except:
                raise Exception('error pid file %s,pid must be a number', pidfile)
    os.remove(pidfile)


def kill_process(pid):
    str_pid = str(pid)
    child = subprocess.Popen(['ps', str_pid], stdout=subprocess.PIPE)
    child.wait()
    fails = []
    if child.stdout:
        for info in child.stdout:
            pname = info.split()[-1]
            if pname.endswith(main_class):
                print 'stop %s' % main_class
                try:
                    subprocess.check_call(['kill', str_pid])
                except:
                    print 'error when stop process %s with id %s' % (main_class, str_pid)

def get_app_out():
    out = envvars.get('APP_OUT')
    if out:
        return out
    return app_name + ".out"

def touchfile(fpath):
    if not os.path.exists(fpath):
        f = open(fpath, 'w')
        f.close()

def get_pid_file():
    PID_FILE = envvars.get('PID_FILE')
    if PID_FILE:
        return PID_FILE
    pidfilename = app_name + '.pid'
    pypath = sys.argv[0]
    pidfile = os.path.dirname(pypath) + os.sep + pidfilename
    return pidfile
        
def run(daemon, args=None):
    java_home, java = get_java()
    print 'Using JAVA_HOME %s' % java_home
    app_home = get_app_home()
    print 'Using Application Home %s' % app_home
    classpath = get_class_path(app_home)
    print 'Using CLASSPATH:%s' % class_path
    out = get_app_out()
    lst = list()
#     if classpath:
#         lst.append('CLASSPATH=$CLASSPATH:%s' % classpath)
#         lst.append('export CLASSPATH')
    lst.append('java')
    lst.append(main_class)
    if args:
        lst.extend(args)
    # class path environments 
    if classpath:
        os.environ['CLASSPATH'] = classpath
    if daemon:
        touchfile(out)
        lst.append('>>"' + out + '" 2>&1 &')
        cmd = ' '.join(lst)
        print 'execute cmd %s' % cmd
        child = subprocess.Popen(cmd, shell=True)
        print 'get pid %d' % child.pid
        fpid = get_pid_file()
        with open(fpid, 'a') as f:
            # if shell=True,the child.pid is not the correct pid 
            # I haven't figure out how to fix it,it's quite common
            # that the really pid is child.pid+1
            f.write(str(child.pid + 1) + os.linesep)
    else:
        try:
            subprocess.check_call(lst)
        except:
            print "%s stopped" % app_name


if __name__ == '__main__':
    argv = sys.argv
    if len(argv) > 1:
        first = argv[1]
        if first == 'start':
            pid = run(True, argv[2:])
        elif first == 'run':
            run(False, argv[2:])
        elif first == 'stop':
            kill_old_pro()
        else:
            run(False, argv[1:])
    else:
        run(False)
    
