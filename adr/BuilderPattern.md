I chose to use a builder from Lombok when creating the final response for
GET /v1/user-info/{username}.I chose this over a factory or constructor because:
- A constructor or factory would either need to be coupled to the responses from the two GitHub
APIs or require a large amount of parameters. I didn't want to be coupled to the responses, so it seemed like requiring
many params to populate the object was inevitable. With that being the case, a fluid interface with a builder seemed more
appropriate.