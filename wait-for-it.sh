#!/usr/bin/env bash
# wait-for-it.sh

set -e

host="$1"
shift
port="$1"
shift

echo "Aguardando $host:$port ficar disponível..."
until nc -z "$host" "$port"; do
  echo "Aguardando $host:$port..."
  sleep 1
done

echo "Conexão TCP estabelecida, verificando se o Postgres está aceitando conexões..."

# Agora espera que o Postgres aceite conexões SQL
until PGPASSWORD=$POSTGRES_PASSWORD psql -h "$host" -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c '\q' > /dev/null 2>&1; do
  echo "Esperando o banco aceitar conexões SQL..."
  sleep 1
done

echo "Banco de dados pronto! Iniciando aplicação..."

exec "$@"