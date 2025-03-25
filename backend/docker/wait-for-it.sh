#!/usr/bin/env bash
# Espera hasta que un host:puerto esté disponible

host="$1"
shift
port="$1"
shift
cmd="$@"

until nc -z "$host" "$port"; do
  echo "Esperando a que $host:$port esté disponible..."
  sleep 2
done

exec $cmd
