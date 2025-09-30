# Base image with Playwright
FROM mcr.microsoft.com/playwright:v1.40.0-jammy

# Install Java & Maven for Allure
RUN apt-get update && apt-get install -y openjdk-17-jdk maven && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy project files
COPY . .

# Create necessary directories inside container
RUN mkdir -p /app/test-results/allure-results \
             /app/test-results/allure-report \
             /app/test-results/traces \
             /app/logs \
             /app/screenshots

# Run tests and generate Allure report
# Copy results to mounted volumes
CMD mvn verify allure:report \
        -Dallure.results.directory=/app/target/allure-results \
        -Dallure.report.directory=/app/target/allure-report && \
    echo "Copying results and report to mounted volumes..." && \
    cp -r /app/target/allure-results/* /app/test-results/allure-results/ 2>/dev/null || true && \
    cp -r /app/target/allure-report/* /app/test-results/allure-report/ 2>/dev/null || true && \
    echo "Allure results and report copied successfully" && \
    echo "Listing some files:" && \
    find /app/test-results/allure-results -name "*.json" | head -5 && \
    find /app/test-results/allure-report -name "*.html" | head -5
