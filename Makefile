run: container
	./gradlew bootRun
container:
	docker-compose up -d
