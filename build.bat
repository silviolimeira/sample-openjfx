call mvn clean install
call cd ui
echo "Java 11, Open JavaFX 15"
echo Para Executar
pause
call mvn javafx:jlink
call target\installer\bin\installer
