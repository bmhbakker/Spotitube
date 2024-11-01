CALL clean.bat
CALL mvn package -f pom.xml
CALL docker build -t spotitube-img .
ECHO Docker image built successfully