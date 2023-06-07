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

SELECT * FROM BOARD;

COMMIT;