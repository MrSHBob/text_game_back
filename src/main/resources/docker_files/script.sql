create database text_games_db;

SET GLOBAL time_zone = '+7:00';
commit;

use text_games_db;
CREATE TABLE USER_TBL(
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         USER_NAME VARCHAR(50),
                         PASSWORD VARCHAR(50),
                         ROLE VARCHAR(50),
                         CREATED DATETIME(3),
                         LAST_UPDATE_DATE DATETIME(3),
                         VERSION INT,
                         UNIQUE KEY unique_user_name (USER_NAME)
);

CREATE TABLE SCREEN_TBL(
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           CREATED DATETIME(3),
                           LAST_UPDATE_DATE DATETIME(3),
                           VERSION INT,
                           NAME VARCHAR(30),
                           TYPE VARCHAR(30),
                           DESCRIPTION VARCHAR(500),
                           ILLUSTRATION_LINK VARCHAR(200)
);

CREATE TABLE PLOT_BUTTONS_TBL(
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 CREATED DATETIME(3),
                                 LAST_UPDATE_DATE DATETIME(3),
                                 VERSION INT,
                                 SCREEN_ID BIGINT,
                                 NAME VARCHAR(30),
                                 TYPE VARCHAR(30),
                                 ACTIONS VARCHAR(300),
                                 DESCRIPTION VARCHAR(500),
                                 FOREIGN KEY (SCREEN_ID)
                                     REFERENCES SCREEN_TBL(id)
);

CREATE TABLE GAME_STATE_TBL(
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               CREATED DATETIME(3),
                               LAST_UPDATE_DATE DATETIME(3),
                               VERSION INT,
                               OWNER_ID BIGINT,
                               ACTIVE_SCREEN_ID BIGINT,
                               CHAR_NAME VARCHAR(30),
                               CHAR_RACE VARCHAR(30),
                               CHAR_CLASS VARCHAR(30),
                               CHAR_STRENGTH INT,
                               CHAR_AGILITY INT,
                               CHAR_ENDURANCE INT,
                               CHAR_INTELLIGENCE INT,
                               TAB_MAP_PIC_LINK VARCHAR(200),
                               TAB_CHAR_PIC_LINK VARCHAR(200),
                               FOREIGN KEY (OWNER_ID)
                                   REFERENCES USER_TBL(id),
                               FOREIGN KEY (ACTIVE_SCREEN_ID)
                                   REFERENCES SCREEN_TBL(id)
);

CREATE TABLE BUTTON_ACTION_TBL(
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  CREATED DATETIME(3),
                                  LAST_UPDATE_DATE DATETIME(3),
                                  VERSION INT,
                                  DESCRIPTION VARCHAR(100),
                                  TYPE CHAR(3),
                                  IF_CODE VARCHAR(30),
                                  DO_CODE VARCHAR(30),
                                  ELSE_CODE VARCHAR(30)
);
commit;
