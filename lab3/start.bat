@echo off
javac -sourcepath .\src -d .\bin .\src\Main.java
java -Xmx4g -classpath .\bin Main
pause