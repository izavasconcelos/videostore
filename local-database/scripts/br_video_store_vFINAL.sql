/*
  Description
  -----------
  --  CREATE TABLE movies
  --  CREATE TABLE users
  --  CREATE TABLE rented_movie
  --  INSERT DATA INTO table movies
*/
--#############################################################################
-------------------------------------------------------------------------------
--  CREATE TABLE movies and INSERT data into movies
-------------------------------------------------------------------------------
DO $$
    BEGIN
        IF EXISTS(SELECT * FROM information_schema.tables where table_name = 'movies') THEN
            RAISE NOTICE 'table movies already exists.';
        ELSE
            CREATE TABLE movies (
            	id serial PRIMARY KEY NOT NULL,
            	title VARCHAR NOT NULL UNIQUE,
            	director VARCHAR NOT NULL,
            	available INTEGER NOT NULL,
            	unavailable INTEGER DEFAULT 0
            );

            RAISE NOTICE 'table movies created.';

            INSERT INTO movies (id, title, director, available, unavailable) VALUES(1, 'Filme 7', 'Tarja Turunen', 2, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(2, 'Filme 8', 'Alissa', 1, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(3, 'Filme 9', 'Floor Jansen', 1, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(4, 'Filme 6', 'Angela Gossow', 9, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(5, 'Filme 1', 'Ozzy Osbourne', 3, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(6, 'Filme 2', 'Axl Rose', 10, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(7, 'Filme 3', 'Duff', 1, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(8, 'Filme 4', 'James', 2, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(9, 'Filme 5', 'Lita Ford', 4, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(10, 'Filme 10', 'David Coverdale', 8, 0);
            INSERT INTO movies (id, title, director, available, unavailable) VALUES(11, 'Filme 121', 'Teste', 1, 0);

            RAISE NOTICE 'insert data into table movies.';
        END IF;
    END
$$;

-------------------------------------------------------------------------------
--  CREATE TABLE users
-------------------------------------------------------------------------------
DO $$
    BEGIN
        IF EXISTS(SELECT * FROM information_schema.tables where table_name = 'users') THEN
            RAISE NOTICE 'table users already exists.';
        ELSE
            CREATE TABLE users (
            	id serial NOT NULL PRIMARY KEY,
            	email VARCHAR(100) NOT NULL UNIQUE,
            	name VARCHAR(150) NOT NULL,
            	password VARCHAR(20) NOT NULL,
            	logged BOOLEAN NOT NULL
            );

            RAISE NOTICE 'table users created.';

        END IF;
    END
$$;

-------------------------------------------------------------------------------
--  CREATE TABLE rented_movie
-------------------------------------------------------------------------------
DO $$
    BEGIN
        IF EXISTS(SELECT * FROM information_schema.tables where table_name = 'rented_movie') THEN
            RAISE NOTICE 'table rented_movie already exists.';
        ELSE
            CREATE TABLE rented_movie (
            	email VARCHAR(100) NOT NULL,
            	id_movie INTEGER NOT NULL
            );

            RAISE NOTICE 'table rented_movie created.';

        END IF;
    END
$$;
