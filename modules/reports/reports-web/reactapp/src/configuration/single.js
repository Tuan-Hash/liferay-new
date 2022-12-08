import { ClayCheckbox } from '@clayui/form';
import Autocomplete from '@mui/material/Autocomplete';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import _ from 'lodash';
import { useState } from 'react';
import './configuration.scss';

function SingleConfiguration(props) {
  const { data, selected, outputId, showFilters = false, outputStatus = '' } = props?.portletData;

  const [enableFilters, setEnableFilters] = useState(showFilters);

  let reports = [];
  _.forEach(data, category => {
    const temp = _.map(category.reports, report => {
      return {
        ...report,
        category: category.name,
        categoryId: category.id
      };
    });
    if (!_.isEmpty(temp)) {
      reports = [...reports, ...temp];
    }
  });

  let value = undefined;
  if (selected) {
    value = _.find(reports, report => report.id === selected.id && selected.categoryId);
  }

  const onChange = (e, value) => {
    if (outputId) {
      document.getElementById(outputId).value = JSON.stringify(value);
    }
  };

  return (
    <div className="report-config">
      <Autocomplete
        size='small'
        options={reports}
        autoHighlight
        onChange={onChange}
        value={value}
        getOptionLabel={(option) => `${option.category} - ${option.name} (${option.id})`}
        renderOption={(props, option) => (
          <Box component="li" sx={{ '& > img': { mr: 2, flexShrink: 0 } }} {...props}>
            {option.category} - {option.name} ({option.id})
          </Box>
        )}
        renderInput={(params) => (
          <TextField
            {...params}
            label="Choose a report"
            inputProps={{
              ...params.inputProps,
              autoComplete: 'new-password', // disable autocomplete and autofill
            }}
          />
        )}
      />
      <div className="report-config__checkbox">
        <ClayCheckbox
          checked={enableFilters}
          onChange={() => {
            setEnableFilters(!enableFilters);

            if (outputStatus) {
              document.getElementById(outputStatus).value = !enableFilters;
            }
          }}
          label="Show filters?"
        />
      </div>
    </div>
  );
}

export default SingleConfiguration;