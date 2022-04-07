@echo off
javac -sourcepath .\src -d bin .\src\com\arragen\Main.java
java -classpath .\bin com.arragen.Main
pause