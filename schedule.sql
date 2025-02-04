CREATE TABLE `USER` (
                        `ID`	Long	NOT NULL,
                        `NAME`	String	NULL,
                        `EMAIL`	String	NULL
);

CREATE TABLE `SCHEDULE` (
                            `ID`	Long	NOT NULL,
                            `WRITER_ID`	Long	NOT NULL,
                            `CONTENTS`	varchar(200)	NOT NULL,
                            `PASSWORD`	String	NULL,
                            `CREATED_DATE`	DATE	NULL,
                            `REWRITE_DATE`	DATE	NULL
);

ALTER TABLE `USER` ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                                         `ID`
    );

ALTER TABLE `SCHEDULE` ADD CONSTRAINT `PK_SCHEDULE` PRIMARY KEY (
                                                                 `ID`,
                                                                 `WRITER_ID`
    );

ALTER TABLE `SCHEDULE` ADD CONSTRAINT `FK_USER_TO_SCHEDULE_1` FOREIGN KEY (
                                                                           `WRITER_ID`
    )
    REFERENCES `USER` (
                       `ID`
        );