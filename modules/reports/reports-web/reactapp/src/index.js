import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import MultipleConfiguration from './configuration/multiple';
import SingleConfiguration from './configuration/single';
import './index.css';

const app = (portletElementID, props) => {
  const root = ReactDOM.createRoot(document.getElementById(portletElementID));
  switch (props?.type) {
    case 'singleConfig':
      root.render(
        <SingleConfiguration portletData={{ ...props }} />
      );
      break;
    case 'multipleConfig':
      root.render(
        <MultipleConfiguration portletData={{ ...props }} />
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
    isAdmin: true,
    canEdit: true,
    // type: 'multipleConfig'
  });
}

export default app;