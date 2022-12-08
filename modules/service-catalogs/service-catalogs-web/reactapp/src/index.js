import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const hiscProps = window.Liferay?.Hisc;
const email = window.Liferay.ThemeDisplay.getUserEmailAddress();

const root = ReactDOM.createRoot(document.getElementById(hiscProps?.portletElementID || 'root'));
root.render(
  <React.StrictMode>
    <App portletData={{...hiscProps?.portletDataProps, email}} />
  </React.StrictMode>
);
