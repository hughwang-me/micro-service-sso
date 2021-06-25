create table hugh_menu
(
    id     int auto_increment
        primary key,
    pid    int          null,
    level  int          null,
    name   varchar(255) null,
    type   int          null,
    remark varchar(255) null
);

INSERT INTO hugh.hugh_menu (id, pid, level, name, type, remark) VALUES (1, -1, 1, '根目录', 1, '文件夹');
INSERT INTO hugh.hugh_menu (id, pid, level, name, type, remark) VALUES (2, 1, 2, '子目录', 1, '文件夹');
INSERT INTO hugh.hugh_menu (id, pid, level, name, type, remark) VALUES (3, 1, 2, '子目录', 1, '文件夹');
INSERT INTO hugh.hugh_menu (id, pid, level, name, type, remark) VALUES (4, 2, 3, 'AAA', 2, '文件');
INSERT INTO hugh.hugh_menu (id, pid, level, name, type, remark) VALUES (5, 2, 3, 'BBB', 3, '文件');
INSERT INTO hugh.hugh_menu (id, pid, level, name, type, remark) VALUES (6, 3, 3, 'CCC', 3, '文件');