import React from 'react';
import {
  RankedTester,
  rankWith,
  isPrimitiveArrayControl,
  and,
  optionIs
} from '@jsonforms/core';

import { withJsonFormsArrayLayoutProps, useJsonForms } from '@jsonforms/react';
import { MultipleReference } from './MultipleReference';


/**
 * Default tester for a vertical layout.
 * @type {RankedTester}
 */
export const customMultipleSelectLayoutTester = rankWith(
  1000,
  and(optionIs('isMutipleSelect',true),isPrimitiveArrayControl)
);

export const CustomMultipleSelectLayoutRenderer = (props) => {
  const { uischema, schema, path, enabled, visible, renderers, cells, id, handleChange, data } = props;
  const ctx = useJsonForms();
  const wholeDataObject = ctx.core.data;

  return (
    <div>
      {visible && <MultipleReference
        schema={schema}
        uischema={uischema}
        path={path}
        values={data}
        enabled={enabled}
        renderers={renderers}
        cells={cells}
        key={id}
        data={wholeDataObject}
        updateValue={(newValue) => handleChange(path, newValue)}
      />}
    </div>
  );
}

export default withJsonFormsArrayLayoutProps(CustomMultipleSelectLayoutRenderer);
