
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
       
insert into board values(board_seq.nextval, 'user1', '월급날', '어쩌고 쩌저고', sysdate, 0, '/img/루피1.jpg');
insert into board values(board_seq.nextval, 'user1', '버그 없이 한번에 될때 내표정', '어쩌고 쩌저고', sysdate, 0, '/img/루피2.jpg');
insert into board values(board_seq.nextval, 'user2', '주말까지...', '어쩌고 쩌저고', sysdate, 0, '/img/루피3.jpg');
INSERT INTO BOARD(BOARD_NO, USER_ID ,TITLE ,CONTENT, REG_DATE, VIEWS, IMG) VALUES(BOARD_SEQ.NEXTVAL,'1', '1', '1', SYSDATE, 0, '1');
SELECT * FROM BOARD;

COMMIT;

INSERT INTO BOARD(BOARD_NO, USER_ID ,TITLE ,CONTENT, REG_DATE, VIEWS, IMG)
VALUES(BOARD_SEQ.NEXTVAL, 'USER3', '테스트', '내용', SYSDATE, 0, '/img/test.jpg');

SELECT USER_ID, TITLE, CONTENT

UPDATE BOARD SET TITLE = '제목', USER_ID = '수정', CONTENT = '수정', IMG = '수정' WHERE BOARD_NO = 1;

ROLLBACK;