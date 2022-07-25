To prevent rate-limiting and to improve performance of the service, caching is necessary. For this service I leveraged Spring's @Cacheable functionality.
This will use a Concurrent HashMap under the hood by default. There could be some concerns around memory issues if the system
grew too large, but works for a small scale. This functionality does not allow for a set time-to-live for eviction, so
I used a scheduled job to do this. It is not the most efficient, but some refresh is needed in case user or repo data is updated.
In an ideal world this is event driven and GitHub would push updates to this service, where we could then invalidate any
existing entries for the changed data.

Given more time I would likely reach for a tool like Redis that allows more configuration and control to build this functionality.