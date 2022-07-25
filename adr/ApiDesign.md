The API chosen for this project is:

GET /v1/user-info/{username}

In this case, the resource is user-info, and the singular endpoint
referenced above is a lookup by unique identifier, in this case a username.

This was chosen over something like GET /v1/user-info?username={username}
because we aren't querying a collection, rather attempting to lookup a resource
by unique ID. A common test I run to decide on path-param versus query param is
the response I would expect back in the case of not finding anything.
In this case I would expect a 404 response, which fits a path
param, versus a query param that may return a 200 with an empty body.

V1 was also added as a first step in versioning.