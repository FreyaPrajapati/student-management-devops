pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN')
        SONAR_ORG   = "freyaprajapati"
        SONAR_KEY   = "FreyaPrajapati_student-management-devops"
        SONAR_HOST  = "https://sonarcloud.io"
        SCANNER     = "C:\\Users\\Admin\\sonar-scanner-cli-8.0.1.6346-windows-x64\\sonar-scanner-8.0.1.6346-windows-x64\\bin\\sonar-scanner.bat"
        IMAGE_NAME  = "student-management-app"
        CONTAINER_NAME = "student-management-container"
    }

    stages {

        stage('1) Clean Workspace') {
            steps {
                echo "Cleaning old workspace..."
                deleteDir()
            }
        }

        stage('2) Clone Repository') {
            steps {
                echo "Cloning GitHub repository..."
                git branch: 'main',
                    url: 'https://github.com/FreyaPrajapati/student-management-devops.git'
            }
        }

        stage('3) Verify Tools') {
            steps {
                bat '''
                echo ===== Checking Java Version =====
                java -version

                echo ===== Checking Docker Version =====
                docker --version

                echo ===== Checking Git Version =====
                git --version
                '''
            }
        }

        stage('4) Build / Compile Java Project') {
            steps {
                bat '''
                echo ===== Cleaning build folder =====
                if exist build rmdir /s /q build
                mkdir build

                echo ===== Compiling Java files =====
                javac -Xlint:unchecked -Xlint:deprecation -d build src\\model\\*.java src\\service\\*.java src\\ui\\*.java src\\Main.java

                echo ===== Build Completed Successfully =====
                '''
            }
        }

        stage('5) Smoke Test (Run Main)') {
            steps {
                bat '''
                echo ===== Running basic smoke test =====
                java -cp build Main
                '''
            }
        }

        stage('6) SonarCloud Analysis') {
            steps {
                bat """
                echo ===== Running SonarCloud Scan =====
                \"${SCANNER}\" ^
                -Dsonar.organization=${SONAR_ORG} ^
                -Dsonar.projectKey=${SONAR_KEY} ^
                -Dsonar.sources=src ^
                -Dsonar.java.binaries=build ^
                -Dsonar.host.url=${SONAR_HOST} ^
                -Dsonar.token=%SONAR_TOKEN%
                """
            }
        }

        stage('7) Docker Build Image') {
            steps {
                bat '''
                echo ===== Building Docker Image =====
                docker build -t %IMAGE_NAME% .
                '''
            }
        }

        stage('8) Docker Run Container') {
            steps {
                bat '''
                echo ===== Removing old container if exists =====
                docker rm -f %CONTAINER_NAME% >nul 2>&1

                echo ===== Running container =====
                docker run -d --name %CONTAINER_NAME% %IMAGE_NAME%

                echo ===== Docker Container Running =====
                docker ps
                '''
            }
        }

    }

    post {
        success {
            echo "✅ PIPELINE SUCCESSFUL! Build + Sonar + Docker completed."
        }

        failure {
            echo "❌ PIPELINE FAILED! Check console logs for the failed stage."
        }

        always {
            echo "📌 Pipeline Finished. Cleaning stopped containers (optional)."
            bat '''
            docker rm -f %CONTAINER_NAME% >nul 2>&1
            '''
        }
    }
}
