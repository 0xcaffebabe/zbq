

self.addEventListener('fetch', function(event) {
    if (event.request.mode == 'navigate') {
        console.log('Handling fetch event for', event.request.url);
        console.log(event.request);
        event.respondWith(
            fetch(event.request).catch(function(exception) {
                // The `catch` is only triggered if `fetch()` throws an exception,
                // which most likely happens due to the server being unreachable.
                console.error(
                    'Fetch failed; returning offline page instead.',
                    exception
                );
                return caches.open(OFFLINE_CACHE).then(function(cache) {
                    return cache.match('/');
                });
            })
        );
    } else {
        // It’s not a request for an HTML document, but rather for a CSS or SVG
        // file or whatever…
        event.respondWith(
            caches.match(event.request).then(function(response) {
                return response || fetch(event.request);
            })
        );
    }

});

