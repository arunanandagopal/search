-- Enable LTree
-- Execute this with in tmdb and using superuser access
CREATE EXTENSION IF NOT EXISTS LTREE WITH SCHEMA public;

-- Create table
CREATE TABLE IF NOT EXISTS directory (
  path ltree PRIMARY KEY
);

-- Truncate table
TRUNCATE TABLE directory;

-- Create index
CREATE INDEX IF NOT EXISTS tree_path_idx ON directory USING gist (path);