
  CREATE TABLE DISTANCE 
   (	"FROMCITY" VARCHAR2(60 BYTE), 
	"TOCITY" VARCHAR2(60 BYTE), 
	"DISTANCE" NUMBER(8,0)
   );

Insert into DISTANCE (FROMCITY,TOCITY,DISTANCE) values ('Tolyatti','Samara',120000);
Insert into DISTANCE (FROMCITY,TOCITY,DISTANCE) values ('Tolyatti','Moskva',1110000);
Insert into DISTANCE (FROMCITY,TOCITY,DISTANCE) values ('Moskva','Samara',1000000);
