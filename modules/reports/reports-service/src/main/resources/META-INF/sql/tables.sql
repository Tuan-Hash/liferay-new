create table hisc_report_Category (
	uuid_ VARCHAR(75) null,
	categoryId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(500) null,
	icon VARCHAR(75) null
);

create table hisc_report_Report (
	uuid_ VARCHAR(75) null,
	reportId LONG not null,
	categoryId LONG not null,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	name VARCHAR(75) null,
	description VARCHAR(500) null,
	icon VARCHAR(75) null,
	sources STRING null,
	primary key (reportId, categoryId)
);