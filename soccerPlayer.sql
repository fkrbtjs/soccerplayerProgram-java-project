-- soccerplayer 데이터베이스 생성

drop database if exists soccerplayerDB;
create database soccerplayerDB;



-- soccerplayer 데이터베이스 사용

use soccerplayerDB;



-- soccerplayer 테이블 생성

CREATE TABLE soccerplayer (
    name CHAR(10) NOT NULL,
    team CHAR(10) NOT NULL,
    goal INT NOT NULL,
    assist INT NOT NULL,
    foul INT NOT NULL,
    point INT NULL,
    rate INT NULL,
    constraint pk_soccerplayer_name primary key(name)
);

-- 인덱스 생성


alter table soccerplayer drop index idx_soccerplayer_name;
create index idx_soccerplayer_name on soccerplayer(name);







-- soccerplayer 입력 프로시져 생성

delimiter //
create procedure
procedure_insert_soccerplayer(
   IN in_name char(10),
    IN in_team char(10),
    In in_goal int,
    In in_assist int,
    In in_foul int
    )
    begin
    DECLARE in_point int default 0;
    SET in_point = in_goal + in_assist;
    
    insert into soccerplayer(name,team,goal,assist,foul) values(in_name,in_team,in_goal,in_assist,in_foul);
    UPDATE soccerplayer set point = in_point where name = in_name;
    end //
    delimiter ;



-- soccerplayer 수정 프로시져 생성

delimiter //
create procedure
procedure_update_soccerplayer(
   IN in_name char(10),
    IN in_team char(10),
    In in_goal int,
    In in_assist int,
    In in_foul int
    )
    begin
    DECLARE in_point int default 0;
    SET in_point = in_goal + in_assist;
    
    UPDATE soccerplayer set name=in_name, team = in_team ,goal=in_goal,assist=in_assist,foul=in_foul,point = in_point where name = in_name;
    end //
    delimiter ;



-- soccerplayer 삭제 프로시져 생성

delimiter //
create procedure
procedure_delete_soccerplayer(
   IN in_name char(10)
    )
    begin
    DECLARE in_point int default 0;
    delete from soccerplayer where name = in_name;
    end //
    delimiter ;



-- soccerplayer 삭제 테이블 생성

create table deleteSoccerPlayer(
      name char(10) not null primary key,
        team char(10) not null,
        goal int not null,
        assist int not null,
        foul int not null,
        point int not null,
        rate int null default 0,
        deleteDate datetime
    );



-- soccerplayer 수정 테이블 생성

create table updateSoccerPlayer(
      name char(10) not null primary key,
        team char(10) not null,
        goal int not null,
        assist int not null,
        foul int not null,
        point int not null,
        rate int null default 0,
        updateDate datetime
    );
-- soccerplayer 삭제 트리거 생성
 
   delimiter // 
    create trigger trg_deleteSoccerPlayer
    after delete
    on soccerPlayer
    for each row
    begin
    insert into `deletesoccerplayer` values(old.name,old.team,old.goal,old.assist,old.foul,old.point,old.rate,now());
    end //
    delimiter ;



-- soccerplayer 수정 트리거 생성

delimiter // 
    create trigger trg_updateSoccerPlayer
    after update
    on soccerPlayer
    for each row
    begin
    insert into `updatesoccerplayer` values(old.name,old.team,old.goal,old.assist,old.foul,old.point,old.rate,now());
    end //
    delimiter ;
