FROM mcr.microsoft.com/playwright:v1.40.0-jammy

# Install Java, Maven, and Allure CLI 2.30.0
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven wget unzip && \
    # Install Allure CLI 2.30.0
    wget https://github.com/allure-framework/allure2/releases/download/2.30.0/allure-2.30.0.zip && \
    unzip allure-2.30.0.zip -d /opt/ && \
    ln -s /opt/allure-2.30.0/bin/allure /usr/bin/allure && \
    # Verify installation
    allure --version

WORKDIR /app
COPY . .

RUN mkdir -p test-results/allure-results allure-report logs screenshots

# Run tests and generate Allure report
CMD mvn clean verify && \
    allure generate test-results/allure-results -o allure-report --clean && \
    echo "=== Allure 2.30.0 Report Generated ===" && \
    echo "Check the 'allure-report' folder on your Windows machine"