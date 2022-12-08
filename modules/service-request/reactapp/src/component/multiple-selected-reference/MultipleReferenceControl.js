import { withJsonFormsControlProps, useJsonForms } from '@jsonforms/react';
import { MultipleReference } from './MultipleReference';

const MultipleReferenceControl = (props) =>{
  const { uischema, schema, path, visible, errors, required, handleChange, data } = props;
  const ctx = useJsonForms();
  const wholeDataObject = ctx.core.data;
  return (
    <MultipleReference
      schema={schema}
      uischema={uischema}
      errors={errors}
      path={path}
      values={data}
      required={required}
      data={wholeDataObject}
      visible={visible}
      updateValue={(newValue) => {
        handleChange(path, newValue);
      }}
    />
  );
} 

export default withJsonFormsControlProps(MultipleReferenceControl);
