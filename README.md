# User Info Service

### Starting the Project

#### Starting with Docker
*Requires Docker installation (https://docs.docker.com/get-docker/)*

##### Building the image:

Run the following commands in order:

```shell
git clone git@github.com:connorphee/github-user-info.git 
cd github-user-info
./mvnw (or mvnw on Windows) clean install
docker build -t github-user-info:0.0.1 .
docker run -p 8080:8080 github-user-info:0.0.1
```

#### Starting Manually

Run the following commands in order:

```shell
git clone git@github.com:connorphee/github-user-info.git
cd user-info-service
./mvnw (or mvnw on Windows) clean install
java -jar target/user-info-0.0.1-SNAPSHOT.jar
```

### Architecture Decision Records (ADR)
This project stores architecture decision records in the [adr](adr) directory. These files
share thoughts, tradeoffs and context that went into the development
of the project.

### Interacting with the Service
After starting the service, you can use this curl command to test the API,
replacing octocat with any user or non-user
```shell
curl http://localhost:8080/v1/user-info/octocat
```

To test caching, you can check logs in stdout, and also shorten the fixedRate in [CacheEvictor.java](src/main/java/com/connorphee/githubuserinfo/scheduled/CacheEvictor.java)
to see the differing request lengths.

### Outstanding Issues / Discussion Points

- Repository "url" param is pulling from the wrong fieldI
- Documentation needs refinement
- Logging needs refinement
- Tests need refinement
- Consider pagination for repositories https://docs.github.com/en/rest/overview/resources-in-the-rest-api#pagination
- Fine-grained cache invalidation is probably needed
- Backoff in case of rate limiting (caching will help this but if requests are all
for new users it could become a problem)