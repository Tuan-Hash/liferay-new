import { models } from 'powerbi-client';
import { EmbedType, PowerBIEmbed } from 'powerbi-client-react';
import React, { useEffect, useState } from 'react';
import './PowerBiClient.css';

const DEFAULT_REPORT_CONFIG = {
	type: EmbedType.Report,
	id: undefined,
	embedUrl: undefined,
	accessToken: undefined,
	tokenType: models.TokenType.Embed,
	permissions: models.Permissions.All,
	viewMode: models.ViewMode.View,
	settings: {
		panes: {
			filters: {
				expanded: false,
				visible: false
			},
			pageNavigation: {
				visible: false
			}
		},
		background: models.BackgroundType.Transparent,
	}
};

const PowerBiClient = (props) => {
	const { namespace, getConfigURL, groupId, reportId, setEmbeddedReport } = props;

	const [reportConfig, setReportConfig] = useState(DEFAULT_REPORT_CONFIG);

	useEffect(() => {
		const fetchReportConfig = async () => {
			let formData = new FormData();
			
			formData.append(namespace + "groupId", groupId);
			formData.append(namespace + "reportId", reportId);

			const result = await fetch(getConfigURL, {
				method: "POST",
				body: formData,
			});


			if (!result.ok) {
				console.error("Failed to fetch config for report.");
				setReportConfig(DEFAULT_REPORT_CONFIG);
				return;
			}

			const json = await result.json();
			const _reportConfig = JSON.parse(JSON.stringify(json || ""))?.[0];
			
			if (typeof _reportConfig === 'undefined' ||  _reportConfig === "" || _reportConfig === "null") {
				console.error("Failed to fetch config for report.");
				setReportConfig(DEFAULT_REPORT_CONFIG);
				return;
			}

			setReportConfig({
				...reportConfig,
				id: JSON.parse(_reportConfig)?.id,
				embedUrl: JSON.parse(_reportConfig)?.embedUrl,
				accessToken: JSON.parse(_reportConfig)?.embedToken.token
			});
		}

		fetchReportConfig();
	}, [groupId, reportId]);

	const eventHandlersMap = new Map([
		['loaded', (e) => console.log(`Report ${reportId} loaded`)],
		['rendered', () => console.log(`Report ${reportId} rendered`)],
		['error', (e) => console.log(e.detail)]
	]);

	return (
		<div className="power-bi-client">
			{reportConfig?.embedUrl ? 
				<PowerBIEmbed
					embedConfig={reportConfig}
					eventHandlers={eventHandlersMap}
					cssClassName={"embed-container"}
					getEmbeddedComponent={(embeddedReport) => setEmbeddedReport(embeddedReport)}
				/> 
				: "" 
			}
		</div>
	);
}

export default PowerBiClient;