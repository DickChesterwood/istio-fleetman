# fleeman-position-tracker
Consumes vehicle position reports from a queue. Stores them in-memory for testing.

Note: the implementation of a data store for this microservice is probably not threadsafe. I have made a (half hearted) attempt to make it threadsafe, but I'm fairly certain it is not problem free. If this were intended for battle tested production, I would work much harder at this. However, it is intended to be a quick and dirty test of a microservice and no lives will be lost in the event of a race condition. "In Real Life" we'd be using a proper datastore so all of this should be a moot point.
