create index IX_27778671 on hisc_servicecatalog_Catalog (name[$COLUMN_LENGTH:75$]);
create index IX_DA513DA6 on hisc_servicecatalog_Catalog (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_BE38F5A8 on hisc_servicecatalog_Catalog (uuid_[$COLUMN_LENGTH:75$], groupId);