SET SERVER_PORT = 5090

SET ADMIN_PASSWORD=toor
SET TARGET_ADDRESS_IP=127.0.0.1
SET TARGET_ADDRESS_MAC=FF:FF:45:FF:FF:FF

call mvn clean package

cd target

java -jar wake-on-lan-0.0.1-SNAPSHOT.jar

PAUSE
