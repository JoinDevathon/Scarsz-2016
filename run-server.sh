#!/bin/bash
cd server
while true; do
	cp ../target/DevathonProject-Pipette.jar plugins/DevathonProject-Pipette.jar
	java -jar spigot.jar
done