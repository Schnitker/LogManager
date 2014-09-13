@echo off
cd /d %~dp0
call gradlew cleanEclipse eclipse
pause
