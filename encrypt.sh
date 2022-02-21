#!/usr/bin/env bash

password=$1
content=$2

echo -n "$content" | openssl enc -aes-256-cbc \
 -base64 -A \
 -md sha256 \
 -pass pass:$password