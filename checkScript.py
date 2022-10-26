import os
import subprocess

iRet=subprocess.call('./gradlew :app:build :javaModule:build :kotlinModule:build',shell = True)
if iRet:
    print("gradlew :app:check success >>>>>>")