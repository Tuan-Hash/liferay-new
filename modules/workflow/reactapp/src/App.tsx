import Canvas from './st2flow-canvas';
import './App.css';
import { st2Data } from './data';
import { useState } from 'react';
import { delay } from 'lodash';

function App(props) {
  const { portletData } = props;
  const metaSource = portletData?.metaSource || '';
  const workflowSource = portletData?.workflowSource || 'version: 1.0\r\ntasks:\r\n';
  const data = st2Data({ metaSource, workflowSource });
  const [firstTime, setFirstTime] = useState(0);
  delay(() => { setFirstTime(1) }, 0);
  return (
    <div className="App">
      <Canvas className="canvas" {...data}></Canvas>
    </div>
  );
}

export default App;
