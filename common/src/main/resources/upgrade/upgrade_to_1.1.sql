use happy_reading;

/*2020-01-09*/
alter table book drop column  free_chapter_index;

update sysconfig set version=1.1,releasedate=NOW();
commit;