@echo off
echo Creating required folders...
mkdir test-results 2>nul
mkdir allure-report 2>nul
mkdir logs 2>nul

echo Folders created!
echo.
echo Now run the tests using:
echo   docker-compose up
pause