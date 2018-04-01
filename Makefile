ifndef VERBOSE
.SILENT:
endif

VERSION = 0.0.1

default: mvn docker

test:
	mvn clean test

mvn:
	mvn -DskipTests=true clean package

docker:
	docker build -t macgenius/rundeckoptions:$(VERSION) -t macgenius/rundeckoptions:latest .

run:
	docker container run --rm -d -p 8080:8080 macgenius/rundeckoptions:latest
