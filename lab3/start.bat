@echo off
javac -sourcepath .\src -d .\bin .\src\Main.java
java -classpath .\bin Main
pause