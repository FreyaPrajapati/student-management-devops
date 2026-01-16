pipeline {
    agent any

    environment {
        SONAR_TOKEN = credentials('SONAR_TOKEN')
    }

    stages {

        stage('Clone Repository') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/FreyaPrajapati/student-management-devops.git'
            }
        }

        stage('Build Java') {
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

        stage('Debug Workspace') {
            steps {
                bat '''
                echo ===== CURRENT FILES =====
                dir
                echo ===== BUILD CONTENT =====
                dir build
                '''
            }
        }

        stage('JUnit Testing') {
            steps {
                bat '''
                echo ===== Running JUnit Tests =====

                echo ===== Compiling Test File =====
                javac -cp "lib\\junit-4.13.2.jar;lib\\hamcrest-core-1.3.jar;build" ^
                -d build src\\test\\StudentServiceTest.java

                echo ===== Running JUnitCore =====
                java -cp "build;lib\\junit-4.13.2.jar;lib\\hamcrest-core-1.3.jar" ^
                org.junit.runner.JUnitCore test.StudentServiceTest
                '''
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                bat '''
                echo ===== Running SonarCloud Scan =====
                "C:\\Users\\Admin\\sonar-scanner-cli-8.0.1.6346-windows-x64\\sonar-scanner-8.0.1.6346-windows-x64\\bin\\sonar-scanner.bat" ^
                -Dsonar.organization=freyaprajapati ^
                -Dsonar.projectKey=FreyaPrajapati_student-management-devops ^
                -Dsonar.sources=src ^
                -Dsonar.java.binaries=build ^
                -Dsonar.host.url=https://sonarcloud.io ^
                -Dsonar.token=%SONAR_TOKEN%
                '''
            }
        }

        stage('Docker Build') {
            steps {
                bat '''
                echo ===== Docker Build =====
                docker build -t student-management-app .
                '''
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline Finished"
        }
        failure {
            echo "❌ Pipeline Failed"
        }
    }
}
