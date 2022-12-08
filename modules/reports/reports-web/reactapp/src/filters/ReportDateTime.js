const currDate = new Date();

const getDateTimestamp = (type, value) => {
	if (type === "date") {
		return new Date(currDate.setDate(currDate.getDate() - value)).toISOString();
	}

	if (type === "month") {
		return new Date(currDate.setMonth(currDate.getMonth() - value)).toISOString();
	}

	return "";
}

export const REPORT_DATES = [
	{
		"type": "yesterday",
		"value": getDateTimestamp("date", 1),
		"displayName": "BM YTD"
	},
	{
		"type": "7D",
		"value": getDateTimestamp("date", 7),
		"displayName": "BM 7D"
	},
	{
		"type": "30D",
		"value": getDateTimestamp("date", 30),
		"displayName": "BM 30D"
	},
	{
		"type": "3M",
		"value": getDateTimestamp("month", 3),
		"displayName": "BM 3M"
	},
	{
		"type": "6M",
		"value": getDateTimestamp("month", 6),
		"displayName": "BM 6M"
	},
	{
		"type": "12M",
		"value": getDateTimestamp("month", 12),
		"displayName": "BM 12M"
	}
];

export const calculateDistanceBetweenTwoDays = (fromDate, toDate) => {
	const difference = fromDate.getTime() - toDate.getTime();
	return Math.ceil(difference / (1000 * 3600 * 24));
}
