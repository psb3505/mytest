drop table jobhist IF EXISTS;

drop table emp IF EXISTS;

drop table dept IF EXISTS;

drop table dual IF EXISTS;

drop sequence empseq IF EXISTS;

CREATE SEQUENCE empseq START WITH 8000;


CREATE TABLE dual (
    dummy        VARCHAR(1) PRIMARY KEY
);

CREATE TABLE dept (
    dept_no          NUMERIC(2) NOT NULL,
    dept_name        VARCHAR(14),
    loc             VARCHAR(13),
    CONSTRAINT dept_pk PRIMARY KEY (dept_no),
    CONSTRAINT dept_name_uq UNIQUE (dept_name)
);

CREATE TABLE emp (
    emp_no          NUMERIC(4) NOT NULL,
    emp_name        VARCHAR(10),
    job             VARCHAR(9),
    mgr             NUMERIC(4),
    hire_date       DATE,
    sal             NUMERIC(7),
    comm            NUMERIC(7),
    dept_no         NUMERIC(2),
	CONSTRAINT emp_pk PRIMARY KEY (emp_no),
	CONSTRAINT emp_sal_ck CHECK (sal > 0),
	CONSTRAINT emp_ref_dept_fk FOREIGN KEY (dept_no) REFERENCES dept(dept_no)
);

CREATE TABLE jobhist (
    emp_no           NUMERIC(4) NOT NULL,
    start_date       DATE NOT NULL,
    end_date         DATE,
    job             VARCHAR(9),
    sal             NUMERIC(7),
    comm            NUMERIC(7),
    dept_no          NUMERIC(2),
    chg_desc         VARCHAR(80),
    CONSTRAINT jobhist_pk PRIMARY KEY (emp_no, start_date),
    CONSTRAINT jobhist_ref_emp_fk FOREIGN KEY (emp_no)
        REFERENCES emp(emp_no) ON DELETE CASCADE,
    CONSTRAINT jobhist_ref_dept_fk FOREIGN KEY (dept_no)
        REFERENCES dept (dept_no) ON DELETE SET NULL,
	CONSTRAINT jobhist_date_chk CHECK (start_date <= end_date)
);


-- dual 
INSERT INTO dual VALUES ('X');

--  Load the 'dept' table
--
INSERT INTO dept VALUES (10,'ACCOUNTING','NEW YORK');
INSERT INTO dept VALUES (20,'RESEARCH','DALLAS');
INSERT INTO dept VALUES (30,'SALES','CHICAGO');
INSERT INTO dept VALUES (40,'OPERATIONS','BOSTON');
--
--  Load the 'emp' table
--
INSERT INTO emp VALUES (7369,'SMITH','CLERK',7902,'1980-12-17',800,NULL,20);
INSERT INTO emp VALUES (7499,'ALLEN','SALESMAN',7698,'1981-02-20',1600,300,30);
INSERT INTO emp VALUES (7521,'WARD','SALESMAN',7698,'1981-02-22',1250,500,30);
INSERT INTO emp VALUES (7566,'JONES','MANAGER',7839,'1981-04-02',2975,NULL,20);
INSERT INTO emp VALUES (7654,'MARTIN','SALESMAN',7698,'1981-09-28',1250,1400,30);
INSERT INTO emp VALUES (7698,'BLAKE','MANAGER',7839,'1981-05-01',2850,NULL,30);
INSERT INTO emp VALUES (7782,'CLARK','MANAGER',7839,'1981-06-09',2450,NULL,10);
INSERT INTO emp VALUES (7788,'SCOTT','ANALYST',7566,'1987-04-19',3000,NULL,20);
INSERT INTO emp VALUES (7839,'KING','PRESIDENT',NULL,'1981-11-17',5000,NULL,10);
INSERT INTO emp VALUES (7844,'TURNER','SALESMAN',7698,'1981-09-08',1500,0,30);
INSERT INTO emp VALUES (7876,'ADAMS','CLERK',7788,'1987-05-23',1100,NULL,20);
INSERT INTO emp VALUES (7900,'JAMES','CLERK',7698,'1981-12-03',950,NULL,30);
INSERT INTO emp VALUES (7902,'FORD','ANALYST',7566,'1981-12-03',3000,NULL,20);
INSERT INTO emp VALUES (7934,'MILLER','CLERK',7782,'1982-01-23',1300,NULL,10);
--
--  Load the 'jobhist' table
--
INSERT INTO jobhist VALUES (7369,'1980-12-17',NULL,'CLERK',800,NULL,20,'New Hire');
INSERT INTO jobhist VALUES (7499,'1981-02-20',NULL,'SALESMAN',1600,300,30,'New Hire');
INSERT INTO jobhist VALUES (7521,'1981-02-22',NULL,'SALESMAN',1250,500,30,'New Hire');
INSERT INTO jobhist VALUES (7566,'1981-04-02',NULL,'MANAGER',2975,NULL,20,'New Hire');
INSERT INTO jobhist VALUES (7654,'1981-09-28',NULL,'SALESMAN',1250,1400,30,'New Hire');
INSERT INTO jobhist VALUES (7698,'1981-05-01',NULL,'MANAGER',2850,NULL,30,'New Hire');
INSERT INTO jobhist VALUES (7782,'1981-06-09',NULL,'MANAGER',2450,NULL,10,'New Hire');
INSERT INTO jobhist VALUES (7788,'1987-04-19','1988-04-12','CLERK',1000,NULL,20,'New Hire');
INSERT INTO jobhist VALUES (7788,'1988-04-13','1989-05-04','CLERK',1040,NULL,20,'Raise');
INSERT INTO jobhist VALUES (7788,'1990-05-05',NULL,'ANALYST',3000,NULL,20,'Promoted to Analyst');
INSERT INTO jobhist VALUES (7839,'1981-11-17',NULL,'PRESIDENT',5000,NULL,10,'New Hire');
INSERT INTO jobhist VALUES (7844,'1981-09-08',NULL,'SALESMAN',1500,0,30,'New Hire');
INSERT INTO jobhist VALUES (7876,'1987-05-23',NULL,'CLERK',1100,NULL,20,'New Hire');
INSERT INTO jobhist VALUES (7900,'1981-12-03','1983-01-14','CLERK',950,NULL,10,'New Hire');
INSERT INTO jobhist VALUES (7900,'1983-01-15',NULL,'CLERK',950,NULL,30,'Changed to Dept 30');
INSERT INTO jobhist VALUES (7902,'1981-12-03',NULL,'ANALYST',3000,NULL,20,'New Hire');
INSERT INTO jobhist VALUES (7934,'1982-01-23',NULL,'CLERK',1300,NULL,10,'New Hire');

commit;