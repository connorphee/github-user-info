This project takes a very basic error handling strategy.
The GitHub endpoints have limited documented responses, so I decided
to use ResponseStatusException for a standard error response body. This was used
for scenarios where I felt there was not a reasonable way to continue through a failure.
In a few situations I felt supressing an error in favor of a partial success was better than a total failure.