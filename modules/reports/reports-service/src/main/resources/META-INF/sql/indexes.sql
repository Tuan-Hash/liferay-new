create index IX_828FA6A0 on hisc_report_Category (name[$COLUMN_LENGTH:75$]);
create index IX_7C478F55 on hisc_report_Category (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_7A5E717 on hisc_report_Category (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_D13A2C16 on hisc_report_Report (name[$COLUMN_LENGTH:75$]);
create index IX_1EB793CB on hisc_report_Report (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_FC18910D on hisc_report_Report (uuid_[$COLUMN_LENGTH:75$], groupId);