CREATE TABLE todolist(
id number constraint todolist_id_pk PRIMARY KEY,
completed number DEFAULT 0,
todoname VARCHAR2(100) NOT NULL);

CREATE SEQUENCE todo_id_seq
 START WITH 1
 INCREMENT By 1
 NOCACHE
 NOCYCLE;

INSERT INTO todolist VALUES(todo_id_seq.nextval, 0, '여행');

COMMIT;

SELECT * FROM todolist;