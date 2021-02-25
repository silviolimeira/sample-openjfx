call mvn clean install
call cd ui
pause
call mvn javafx:jlink
call target\installer\bin\installer
