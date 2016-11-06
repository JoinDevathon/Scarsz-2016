#!/bin/bash
cd server
while true; do
	cp ../target/DevathonProject-Pipette.jar plugins/DevathonProject-Pipette.jar
	java -Xmx8G -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar spigot.jar nogui
done