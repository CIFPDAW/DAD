@echo off
title FXMysqlConfig - Login y Registro con MySQL
cd /d "%~dp0"
echo Iniciando FXMysqlConfig...
java "-Djava.library.path=dist/lib;dist" --module-path "dist/lib" --add-modules javafx.controls,javafx.fxml -jar "dist/FXMysqlConfig.jar"
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Si ha ocurrido algun error, compruebe que Java esta en el PATH.
    pause
)
