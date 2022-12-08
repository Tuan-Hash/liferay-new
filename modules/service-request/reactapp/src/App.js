import {
  materialCells,
  materialRenderers
} from '@jsonforms/material-renderers';
import { JsonForms } from '@jsonforms/react';
import { useEffect, useState } from 'react';

import './App.css';
import mockInitData from './mocks/initdata.json';
import mockSchema from './mocks/schema.json';
import mockUiSchema from './mocks/uischema.json';
import ReferenceControl from './component/single-selected-reference/ReferenceControl';
import ReferenceTester from './component/single-selected-reference/ReferenceTester';
import MultiplReferenceControl from './component/multiple-selected-reference/MultipleReferenceControl';
import MultiplReferenceTester from './component/multiple-selected-reference/MultipleReferenceTester';
import CustomMultipleSelectLayoutRenderer, {
  customMultipleSelectLayoutTester,
} from './component/multiple-selected-reference/CustomMultipleSelectLayoutRenderer';


const renderers = [
  ...materialRenderers,
  { tester: ReferenceTester, renderer: ReferenceControl },
  { tester: MultiplReferenceTester, renderer: MultiplReferenceControl },
  { tester: customMultipleSelectLayoutTester, renderer: CustomMultipleSelectLayoutRenderer }
];

function App(props) {
  const { portletData } = props;
  const initData = portletData?.initData || mockInitData;
  const [data, setData] = useState(initData);
  const [errors, setErrors] = useState([]);
  const schema = portletData?.schema || mockSchema;
  const uiSchema = portletData?.uiSchema || mockUiSchema;

  useEffect(() => {}, [errors, initData, data]);

  return (
    <div className="App">
      <JsonForms
        readonly={true}
        validationMode='NoValidation'
        schema={schema}
        uischema={uiSchema}
        data={data}
        renderers={renderers}
        cells={materialCells}
        refParserOptions
        onChange={({ errors, data }) => {
          setErrors(errors);
          setData(data);
        }}
      />
  </div>
  );
}

export default App;
