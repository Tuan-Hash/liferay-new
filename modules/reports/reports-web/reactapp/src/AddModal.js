import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, { ClayCheckbox, ClayInput } from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayModal from '@clayui/modal';
import _ from 'lodash';
import { useEffect, useState } from 'react';

const PBI_CLIENT_VAL = "pbi-client";
const EMBED_URL_VAL = "embed-url";

const URL_TYPES = [
  {
    value: PBI_CLIENT_VAL,
    name: "PBI client"
  }, 
  {
    value: EMBED_URL_VAL,
    name: "Embed URL"
  }
];

const AddForm = (props) => {
  const { data = {}, onChange } = props;

  const isNew = _.isEmpty(data);

  const [formValues, setFormValues] = useState(
    isNew
    ?
    { 
      width: 3,
      height: 2,
      urlType: PBI_CLIENT_VAL,
      aspectRatio: false
    } 
    : 
    {
      urlType: typeof data?.urlType === "undefined" ? EMBED_URL_VAL : data?.urlType,
      aspectRatio: typeof data?.aspectRatio === "undefined" ? false : data?.aspectRatio,
      ...data 
    });

  const onValueChange = (type, value) => {
    setFormValues({
      ...onValidate({ values: formValues, type, value }),
      [type]: type === "width" || type === "height" ? parseInt(value) : value
    });
  };

  const onValidate = ({ values, type, value }) => {
    if (isNew && type === "urlType" && value === EMBED_URL_VAL) {
      values["groupId"] = "";
      values["reportId"] = "";
    }

    if (isNew && type === "urlType" && value === PBI_CLIENT_VAL) {
      values["url"] = "";
    }

    if (!isNew && type === "urlType" && value === PBI_CLIENT_VAL && data?.urlType === PBI_CLIENT_VAL) {
      values["url"] = "";
      values["groupId"] = data?.groupId;
      values["reportId"] = data?.reportId;
    }

    if (!isNew && type === "urlType" && value === EMBED_URL_VAL && data?.urlType === EMBED_URL_VAL) {
      values["url"] = data?.url;
      values["groupId"] = "";
      values["reportId"] = "";
    }

    return values;
  };

  useEffect(() => {
    onChange && onChange(formValues);
  }, [formValues]);

  const urlTitle = URL_TYPES.find(({ value }) => value === formValues["urlType"])?.name;

  return (
    <ClayForm>
      <ClayForm.Group className={formValues.title ? "" : "has-error"}>
        <ClayInput placeholder="Title" type="text" value={formValues.title}
          onChange={(e) => onValueChange('title', e.target.value)} />
      </ClayForm.Group>
      {
        formValues.urlType === EMBED_URL_VAL
          ? 
          <ClayForm.Group className={formValues.url ? "" : "has-error"}>
            <ClayInput placeholder="URL" type="text" value={formValues.url}
              onChange={(e) => onValueChange('url', e.target.value)} />
          </ClayForm.Group> 
          : 
          <>
            <ClayForm.Group className={formValues.groupId ? "" : "has-error"}>
              <ClayInput placeholder="Group ID" type="text" value={formValues.groupId}
                onChange={(e) => onValueChange('groupId', e.target.value)} />
            </ClayForm.Group>
            <ClayForm.Group className={formValues.reportId ? "" : "has-error"}>
              <ClayInput placeholder="Report ID" type="text" value={formValues.reportId}
                onChange={(e) => onValueChange('reportId', e.target.value)} />
            </ClayForm.Group>
          </>
      }
      <ClayForm.Group>
        <ClayLayout.Row>
          <ClayLayout.Col size={6}>
            <ClayInput.Group className={formValues.width ? "" : "has-error"}>
              <ClayInput.GroupItem prepend>
                <ClayInput placeholder="Width" type="number" value={formValues.width}
                  onChange={(e) => onValueChange('width', e.target.value)} />
              </ClayInput.GroupItem>
              <ClayInput.GroupItem prepend shrink>
                <ClayInput.GroupText>{"units"}</ClayInput.GroupText>
              </ClayInput.GroupItem>
            </ClayInput.Group>
          </ClayLayout.Col>
          <ClayLayout.Col size={6}>
            <ClayInput.Group className={formValues.height ? "" : "has-error"}>
              <ClayInput.GroupItem prepend>
                <ClayInput placeholder="Height" type="number" value={formValues.height}
                  onChange={(e) => onValueChange('height', e.target.value)} />
              </ClayInput.GroupItem>
              <ClayInput.GroupItem shrink prepend>
                <ClayInput.GroupText>{"units"}</ClayInput.GroupText>
              </ClayInput.GroupItem>
            </ClayInput.Group>
          </ClayLayout.Col>
        </ClayLayout.Row>
      </ClayForm.Group>
      <ClayForm.Group>
        <ClayLayout.Row>
          <ClayLayout.Col size={6}>
            <ClayDropDown
              closeOnClick
              trigger={<ClayButton className="choose-url-type" displayType={null}>{`Choose URL type:  ${urlTitle}`}</ClayButton>}
            >
              <ClayDropDown.ItemList items={URL_TYPES}>
                {(item) => (
                  <ClayDropDown.Item 
                    key={item.value} 
                    textvalue={item.value}
                    active={formValues["urlType"] === item.value}
                    onClick={() => onValueChange("urlType", item.value)}
                  >
                    {item.name}
                  </ClayDropDown.Item>
                )}
              </ClayDropDown.ItemList>
            </ClayDropDown>
          </ClayLayout.Col>
          <ClayLayout.Col size={6}>
            <ClayCheckbox
              checked={formValues.aspectRatio}
              onChange={(e) => onValueChange('aspectRatio', e.target.checked)}
              label="Keep aspect ratio?" />
          </ClayLayout.Col>
        </ClayLayout.Row>
      </ClayForm.Group>
    </ClayForm>
  );
};

const AddModal = (props) => {
  const { observer, onOpenChange, open,
    spritemap, data = {}, onSubmit, onDelete
  } = props;

  const [formValues, setFormValues] = useState({});
  const [isValid, setIsValid] = useState(false);

  const isNew = _.isEmpty(data);

  useEffect(() => {
    const isValid = formValues.title && formValues.width && formValues.height;

    if (formValues.urlType === EMBED_URL_VAL) {
      setIsValid(formValues.url && isValid);
      return;
    }

    setIsValid(formValues.groupId && formValues.reportId && isValid);
  }, [formValues]);

  const handleOnSubmit = () => {
    onOpenChange(false);

    if (!isNew && formValues["urlType"] === EMBED_URL_VAL && data?.urlType === PBI_CLIENT_VAL) {
      formValues["groupId"] = "";
      formValues["reportId"] = "";
    }

    if (!isNew && formValues["urlType"] === PBI_CLIENT_VAL && data?.urlType === EMBED_URL_VAL) {
      formValues["url"] = "";
    }

    onSubmit && onSubmit(formValues);
  };

  const handleOnDelete = () => {
    onOpenChange(false);
    onDelete && onDelete(data.i);
  };

  return (
    <>
      {open && (
        <ClayModal
          className="report__add-modal"
          observer={observer}
          size="md"
          spritemap={spritemap}
        >
          <ClayModal.Header>{isNew ? "Add" : "Update"}</ClayModal.Header>
          <ClayModal.Body>
            <AddForm data={data} onChange={setFormValues} />
          </ClayModal.Body>
          <ClayModal.Footer
            first={!isNew &&
              <ClayButton.Group spaced>
                <ClayButton displayType="danger" onClick={handleOnDelete}>
                  {"Delete"}
                </ClayButton>
              </ClayButton.Group>
            }
            last={
              <ClayButton onClick={handleOnSubmit} disabled={!isValid}>
                {isNew ? "Add" : "Update"}
              </ClayButton>
            }
          />
        </ClayModal>
      )}
    </>
  );
};

export default AddModal;