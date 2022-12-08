import {
  materialCells,
  materialRenderers
} from '@jsonforms/material-renderers';
import { JsonForms } from '@jsonforms/react';
import {
  Alert, AlertTitle, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Stack
} from '@mui/material';
import _ from "lodash";
import { useEffect, useState } from 'react';

import './App.css';
import CustomMultipleSelectLayoutRenderer, {
  customMultipleSelectLayoutTester
} from './component/multiple-selected-reference/CustomMultipleSelectLayoutRenderer';
import MultiplReferenceControl from './component/multiple-selected-reference/MultipleReferenceControl';
import MultiplReferenceTester from './component/multiple-selected-reference/MultipleReferenceTester';
import ReferenceControl from './component/single-selected-reference/ReferenceControl';
import ReferenceTester from './component/single-selected-reference/ReferenceTester';
import mockInitData from './mocks/initdata.json';
import mockSchema from './mocks/schema.json';
import mockUiSchema from './mocks/uischema.json';
const renderers = [
  ...materialRenderers,
  //register custom renderers
  { tester: ReferenceTester, renderer: ReferenceControl },
  { tester: MultiplReferenceTester, renderer: MultiplReferenceControl },
  { tester: customMultipleSelectLayoutTester, renderer: CustomMultipleSelectLayoutRenderer }
];

function App(props) {
  const { portletData } = props;
  const initData = portletData?.initData || mockInitData;
  const [data, setData] = useState(initData);
  const [errors, setErrors] = useState([]);
  const [canSubmit, setCanSubmit] = useState(false);
  const [actionAlert, setActionAlert] = useState();
  const [currentValidationMode, setCurrentValidationMode] = useState("ValidateAndHide");

  const schema = portletData?.schema || mockSchema;
  const uiSchema = portletData?.uiSchema || mockUiSchema;
  const email = portletData?.email;

  const onCancel = () => {
    window.location.href = portletData?.cancelURL || '/';
    return null;
  };

  const submitURL = portletData?.submitURL || '';
  const onSubmit = async () => {
    setCurrentValidationMode("ValidateAndShow");
    if (!_.isEmpty(errors)) return;

    const res = await fetch(submitURL, {
      method: "POST",
      headers: {
        'Content-type': 'application/json',
        'Authorization': 'Basic c3lzYWRtaW46c3lzYWRtaW4='
      },
      body: JSON.stringify({
        catalog: portletData?.catalogId || '19371349-4610-4148-9f9a-897e95a48561',
        form_data: data,
        user: email
      }),
    });

    let json = await res.json();
    if (res.ok) {
      setActionAlert({
        type: 'success',
        code: res.status,
        message: json?.message || JSON.stringify(json) || res.statusText
      });
    } else {
      setActionAlert({
        type: 'error',
        code: res.status,
        message: json?.detail || JSON.stringify(json) || res.statusText
      });
    }
  };

  useEffect(() => {
    setCanSubmit(!_.isEmpty(data));
  }, [errors, initData, data]);

  const submitAlert = () => {
    return actionAlert && actionAlert.type === 'error' && (
      <Alert severity={actionAlert.type} onClose={() => {
        setActionAlert();
      }}>
        <AlertTitle>{actionAlert.code}</AlertTitle>
        {actionAlert.message}
      </Alert>
    );
  };

  const successDialog = () => {
    return actionAlert && actionAlert.type === 'success' && (
      <Dialog
        open={true}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Success"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {actionAlert.message}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={onCancel} autoFocus>
            OK
          </Button>
        </DialogActions>
      </Dialog>
    );
  };

  return (
    <div className="App">
      {submitAlert()}
      <JsonForms
        schema={schema}
        uischema={uiSchema}
        data={data}
        renderers={renderers}
        cells={materialCells}
        refParserOptions
        validationMode={currentValidationMode}
        onChange={({ errors, data }) => {
          setErrors(errors);
          setData(data);
        }}
      />
      <div class="Action-Buttons">
        <Stack direction="row" justifyContent="flex-end" spacing={1}>
          <Button variant="outlined" onClick={onCancel}>Cancel</Button>
          <Button disabled={!canSubmit} variant="contained" color="primary" onClick={onSubmit}>Submit</Button>
        </Stack>
      </div>
      {successDialog()}
    </div>
  );
}

export default App;
