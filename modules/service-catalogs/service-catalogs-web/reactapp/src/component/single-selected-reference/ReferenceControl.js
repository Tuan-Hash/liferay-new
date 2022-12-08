import { withJsonFormsControlProps, useJsonForms } from '@jsonforms/react';
import { Reference } from './Reference';

const ReferenceControl = ({ schema, uischema, errors, required, data, path, handleChange, visible }) => {
  const ctx = useJsonForms();
  const wholeDataObject = ctx.core.data;
  return (
    <Reference
      schema={schema}
      uischema={uischema}
      errors={errors}
      required={required}
      value={data}
      visible={visible}
      path={path}
      data={wholeDataObject}
      updateValue={(newValue) => handleChange(path, newValue)}
    />
  );
} 

export default withJsonFormsControlProps(ReferenceControl);
