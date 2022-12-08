import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';
import ExecutionsStackstorm from './component/executionStackstorm/ExecutionStackstorm';

const app = (portletElementID, props) => {
  const root = ReactDOM.createRoot(document.getElementById(portletElementID));
  switch (props?.type) {
    case 'listConfig':
      root.render(
        <ExecutionsStackstorm portletData={{ ...props }} />
      );
      break;
    default:
      root.render(
        <App portletData={{ ...props }} />
      );
      break;
  }
};

if (process.env.NODE_ENV === 'development') {
  app("root", {
  });
}

export default app;