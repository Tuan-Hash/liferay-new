import { Text } from '@clayui/core';
import { ClayCheckbox } from '@clayui/form';
import { Input } from '@mui/material';
import { useState } from 'react';
import { CheckboxTree } from './CheckboxTree';
import './configuration.scss';

const prepareTreeData = (item = {}, path = '') => {
  const newPath = (path ? path + '.' : '') + item.id;
  if (!item.reports || !item.reports.length) {
    return {
      value: newPath,
      label: item.name
    };
  }

  return {
    value: newPath,
    label: item.name,
    children: item.reports.map(i => prepareTreeData(i, newPath))
  };
};

function MultipleConfiguration(props) {
  const { data = [], selected = [], outputId = '', showFilters = false, outputStatus = '' } = props?.portletData;

  const [value, setValue] = useState('');
  const [enableFilters, setEnableFilters] = useState(showFilters);

  const nodes = data.map(i => prepareTreeData(i));

  return (
    <div className="report-config">
      <Input type='hidden' value={value} name={outputId} />
      <Input type='hidden' value={enableFilters} name={outputStatus} />

      <div className="multi-config__checkbox-tree">
        <Text>Choose report</Text>
        <CheckboxTree
          nodes={nodes}
          checked={selected}
          onChecked={values => setValue(JSON.stringify(values))} 
        />
      </div>
      
      <div className="report-config__checkbox multi-config__checkbox">
        <ClayCheckbox
          checked={enableFilters}
          onChange={() => setEnableFilters(!enableFilters)}
          label="Show filters?"
        />
      </div>
    </div>
  );
}

export default MultipleConfiguration;