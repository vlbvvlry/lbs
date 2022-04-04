@echo off
javac -sourcepath .\src -d .\bin .\src\com\parasort\Main.java
java -classpath .\bin com.parasort.Main
pause