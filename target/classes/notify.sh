#!/usr/bin/env bash

export DISPLAY=:0.0
AUTH=$(echo $1 | tr _ \ )
TXT=$(echo $2 | tr _ \ )
if [ -z "$3" ]; then
    notify-send "$AUTH" "$TXT"
else
    notify-send -i "$3" "$AUTH" "$TXT"
fi