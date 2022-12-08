create table hisc_servicecatalog_Catalog (
	uuid_ VARCHAR(75) null,
	catalogId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	categoryId VARCHAR(75) null,
	name VARCHAR(75) null
);