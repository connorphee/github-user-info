Null handling is something I try to focus on early in projects, as I believe it can be simplified
if efforts and patterns are set up front. By preventing null from getting past the boundaries of the system
you can prove that null is not a valid or possible state of data,
eliminating the need for constant null checks. In this project null is stopped at the boundaries like this:

From GitHub: If we do not get a response, or a non-successful response we throw. 

From Cache: Our cache implementation returns Optional

From Http Requests to GET /v1/user-info/{username}: username is a required parameter,
Spring would disallow a null value prior to the request entering the endpoint