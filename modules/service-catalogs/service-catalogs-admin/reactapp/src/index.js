import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';

const app = (portletElementID, props) => {
  const root = ReactDOM.createRoot(document.getElementById(portletElementID));
  root.render(
    <App portletData={{ ...props }} />
  );
};

export default app;