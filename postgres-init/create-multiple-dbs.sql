\set ON_ERROR_STOP on
SELECT 'CREATE DATABASE "products-service"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'products-service')\gexec

SELECT 'CREATE DATABASE "orders-service"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'orders-service')\gexec

SELECT 'CREATE DATABASE "payments-service"'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'payments-service')\gexec