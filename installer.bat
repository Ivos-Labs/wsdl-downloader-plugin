

@echo off


ECHO Installing wsdl-downloader-mave-plugin
SET /p FILE=wsdl-downloader-maven-plugin-1.0.0.jar
SET /p GROUP_ID=com.ivoslabs
SET /p ARTIFACT_ID=wsdl-downloader-maven-plugin
SET /p VERSION=1.0.0:
 

CALL mvn install:install-file -Dfile="%FILE%" -DgroupId=%GROUP_ID% -DartifactId=%ARTIFACT_ID% -Dversion=%VERSION% -Dpackaging=jar

ECHO Installed file=%FILE%  as  groupId=%GROUP_ID%  artifactId=%ARTIFACT_ID%  version=%VERSION%

SET /p exit=Press Enter to exit


