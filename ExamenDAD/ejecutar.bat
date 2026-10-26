@echo off
title Examen DAD - Talleres Paco
cd /d "%~dp0"
echo Iniciando aplicacion JavaFX...
java "-Djava.library.path=dist/lib;dist" --module-path "dist/lib" --add-modules javafx.controls,javafx.fxml -jar "dist/ExamenDAD.jar"
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Si ha ocurrido algun error, compruebe que Java esta instalado en su PATH.
    pause
)
