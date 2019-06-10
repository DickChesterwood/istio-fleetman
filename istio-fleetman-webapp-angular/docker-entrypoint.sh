#!/bin/bash -e
j2 /templates/nginx.conf.j2 > /etc/nginx/nginx.conf
exec "$@"
