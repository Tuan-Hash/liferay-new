import ClayButton from "@clayui/button";
import ClayDatePicker from '@clayui/date-picker';
import ClayIcon from "@clayui/icon";
import ClayLayout from "@clayui/layout";
import { useEffect, useState } from "react";
import './Filters.scss';
import { ADVANCED_FILTER, REPORT_BUTTONS } from "./ReportConstants";
import { calculateDistanceBetweenTwoDays, REPORT_DATES } from "./ReportDateTime";

const spriteMap = window.Liferay?.Icons.spritemap;

const advancedFilter = ADVANCED_FILTER;

const Filters = (props) => {
	const { isSelectAll, selectAll, embeddedReports, updateEmbeddedReport } = props;

	const getReportDate = (changedType) => REPORT_DATES.find(({ type }) => type === changedType);

	const getReportFilter = () => {
		switch (reportDate.type) {
			case "customDate": {
				const [fromReportDate, toReportDate] = reportDate.value.split(" - ");

				const fromDate = new Date(fromReportDate).toISOString();
				const toDate = new Date(new Date(toReportDate).setHours(23, 59, 59)).toISOString();
				
				advancedFilter.conditions = [
					{
						operator: "GreaterThanOrEqual",
						value: fromDate
					},
					{
						operator: "LessThan",
						value: toDate
					}
				]
				break;
			}
			default:
				advancedFilter.conditions = [{ operator: "LessThan", value: reportDate.value }];
				break;
		}

		return advancedFilter;	
	}

	const applyReportFilter = async ({ embed }) => {
		try {
			const newReportFilter = getReportFilter();
			const oldReportFilters = await embed.getFilters();

			const oldReportFilter = oldReportFilters.find(oldFilter => 
				JSON.stringify(newReportFilter) === JSON.stringify(oldFilter)
			)

			// don't apply filter if it is the same filters
			if (oldReportFilter) {
				return;
			}

			await updateEmbeddedReport({ 
				embed, 
				bookmarkName: reportDate.displayName, 
				filter: newReportFilter 
			});
		} catch (error) {
			console.log("error" + JSON.stringify(error));
		}
	}

	const [customDate, setCustomDate] = useState(null);
	const [reportDate, setReportDate] = useState(getReportDate("3M"));

	useEffect(() => {
		const applyReportFilters = () => {	
			embeddedReports.forEach(async (embeddedReport) => 
				await applyReportFilter(embeddedReport)
			);
		};

		applyReportFilters();
	}, [reportDate, embeddedReports]);

	const onValueChange = (changedType) => {
		const reportDate = getReportDate(changedType);
		setCustomDate(null);
		setReportDate(reportDate);
	};

	const onDateChange = (value) => {
		setCustomDate(value);
		setReportDate({ type: "customDate", value, displayName: "BM Custom" });
	};

	const getRangeDays = () => {
		const [fromDate, toDate] = (customDate || " - ").split(" - ");

		if (fromDate === toDate) {
			return "1 day";
		}

		const distanceBetweenDays = calculateDistanceBetweenTwoDays(new Date(toDate), new Date(fromDate));
		return `${distanceBetweenDays} days`;
	}

	return (
		<ClayLayout.ContainerFluid className="report-filters">
			<ClayLayout.Row justify="between" className="header">
				<ClayLayout.Col size={12}>
					<ClayButton.Group spaced>
						<ClayDatePicker
							dateFormat="MM/dd/yyyy"
							onChange={(value) => onDateChange(value)}
							placeholder="Custom"
							range
							spritemap={spriteMap}
							value={customDate}
							footerElement={() => <span>{getRangeDays()}</span>}
							years={{ start: 2000, end: 2099 }}
						/>
						{REPORT_BUTTONS.map(({ type, name, icon }, index) => (
							<ClayButton
								key={index}
								className="report-filters__button" 
								displayType={"secondary"}
								onClick={() => onValueChange(type)}
							>
								{icon && <ClayIcon className="report-filters__icon" spritemap={spriteMap} symbol={icon} />}
								{name}
							</ClayButton>
						))}
						<ClayButton
							className="report-filters__button" 
							displayType={"secondary"}
							onClick={()=> selectAll(!isSelectAll)}
						>
							{isSelectAll ? 'Deselect All' : 'Select All'}
						</ClayButton>
					</ClayButton.Group>
				</ClayLayout.Col>
			</ClayLayout.Row>
		</ClayLayout.ContainerFluid>
	);
}

export default Filters;