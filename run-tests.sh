#!/bin/bash
echo "Starting tests..."
mvn clean test

echo "Generating Allure report..."
allure generate test-results/allure-results -o allure-report --clean

echo "Tests completed! Check the following folders:"
echo "- test-results/ - Raw test results"
echo "- allure-report/ - HTML reports" 
echo "- screenshots/ - Screenshots"