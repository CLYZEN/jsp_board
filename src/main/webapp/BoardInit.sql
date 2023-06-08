
DROP TABLE BOARD;

CREATE TABLE BOARD(
    BOARD_NO NUMBER PRIMARY KEY,
    USER_ID VARCHAR2(50) NOT NULL,
    TITLE VARCHAR2(255) NOT NULL,
    CONTENT CLOB NOT NULL,
    REG_DATE DATE NOT NULL,
    VIEWS NUMBER NOT NULL,
    IMG VARCHAR2(255)
);

DROP SEQUENCE BOARD_SEQ;

CREATE SEQUENCE BOARD_SEQ
      increment by 1
       start with 1
       minvalue 1
       maxvalue 9999
       nocycle
       nocache
       noorder;
       
insert into board values(board_seq.nextval, 'user1', '���޳�', '��¼�� ¼����', sysdate, 0, '/img/����1.jpg');
insert into board values(board_seq.nextval, 'user1', '���� ���� �ѹ��� �ɶ� ��ǥ��', '��¼�� ¼����', sysdate, 0, '/img/����2.jpg');
insert into board values(board_seq.nextval, 'user2', '�ָ�����...', '��¼�� ¼����', sysdate, 0, '/img/����3.jpg');
INSERT INTO BOARD(BOARD_NO, USER_ID ,TITLE ,CONTENT, REG_DATE, VIEWS, IMG) VALUES(BOARD_SEQ.NEXTVAL,'1', '1', '1', SYSDATE, 0, '1');
SELECT * FROM BOARD;

COMMIT;

INSERT INTO BOARD(BOARD_NO, USER_ID ,TITLE ,CONTENT, REG_DATE, VIEWS, IMG)
VALUES(BOARD_SEQ.NEXTVAL, 'USER3', '�׽�Ʈ', '����', SYSDATE, 0, '/img/test.jpg');

SELECT USER_ID, TITLE, CONTENT

UPDATE BOARD SET TITLE = '����', USER_ID = '����', CONTENT = '����', IMG = '����' WHERE BOARD_NO = 1;

ROLLBACK;