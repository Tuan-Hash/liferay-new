import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import { metadata, workflow } from './mock';

const hiscProps = window['Liferay']?.HiscWorkflow;

const root = ReactDOM.createRoot(
  document.getElementById(hiscProps?.portletElementID || 'root') as HTMLElement
);

const portletData = hiscProps?.portletDataProps || { metaSource: metadata, workflowSource: workflow };
root.render(
  <React.StrictMode>
    <App portletData={portletData} />
  </React.StrictMode>
);
