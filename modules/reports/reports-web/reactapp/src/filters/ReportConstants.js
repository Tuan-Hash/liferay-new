import { models } from 'powerbi-client';

export const REPORT_BUTTONS = [
	{
		"type": "yesterday",
		"name": "Yesterday"
	},
	{
		"type": "7D",
		"name": "7D"
	},
	{
		"type": "30D",
		"name": "30D"
	},
	{
		"type": "3M",
		"name": "3M"
	},
	{
		"type": "6M",
		"name": "6M"
	},
	{
		"type": "12M",
		"name": "12M"
	}
];

export const ADVANCED_FILTER = {
    $schema: "http://powerbi.com/product/schema#advanced",
    target: {
        table: "Date",
        column: "Date"
    },
    filterType: models.FilterType.Advanced,
    logicalOperator: "And",
    conditions: []
};