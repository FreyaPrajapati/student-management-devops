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
                echo ===== Java Version =====
                java -version

                echo ===== Docker Version =====
                docker --version

                echo ===== Git Version =====
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
                javac -d build src\\model\\*.java src\\service\\*.java src\\ui\\*.java src\\Main.java

                echo ===== BUILD SUCCESS =====
                '''
            }
        }

        stage('5) SonarCloud Analysis') {
            steps {
                bat """
                echo ===== Running SonarCloud Analysis =====
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

        stage('6) Docker Build Image') {
            steps {
                bat '''
                echo ===== Building Docker Image =====
                docker build -t %IMAGE_NAME% .
                '''
            }
        }
    }

    post {
        success {
            echo "✅ PIPELINE SUCCESSFUL: Git + Build + Sonar + Docker"
        }

        failure {
            echo "❌ PIPELINE FAILED"
        }

        always {
            echo "📌 Cleaning docker containers (safe)"
            bat '''
            docker rm -f %CONTAINER_NAME% >nul 2>&1 || echo No container to remove
            '''
        }
    }
}
