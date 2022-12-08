import ClayAlert from '@clayui/alert';
import ClayButton from "@clayui/button";
import "@clayui/css/lib/css/atlas.css";
import ClayForm, { ClayCheckbox, ClayToggle } from "@clayui/form";
import ClayIcon from "@clayui/icon";
import ClayLayout from '@clayui/layout';
import { useModal } from '@clayui/modal';
import { ClayTooltipProvider } from '@clayui/tooltip';
import _ from "lodash";
import { useEffect, useState } from "react";
import Responsive from "react-grid-layout";
import { SizeMe } from 'react-sizeme';

import { models } from 'powerbi-client';
import AddModal from "./AddModal";
import './App.scss';
import Filters from './filters/Filters';
import localspritemap from './icons.svg';
import PowerBiClient from './PowerBiClient';
import './react-grid-layout-styles.css';
import './react-resizable-styles.css';

const liferay = window.Liferay;
const spritemap = liferay?.Icons.spritemap || localspritemap;

const defaultProps = {
  className: "layout",
  cols: 12,
  draggableHandle: '.btn-move',
  margin: [20, 20],
  containerPadding: [0, 0],
  compactType: null,
};

function App(props) {
  const {
    isAdmin, 
    canEdit, 
    saveURL, 
    namespace, 
    outputId, 
    originSchema = [], 
    customSchema = [],
    getConfigURL,
    showFilters,
    outputStatus
  } = props?.portletData;

  const schema = isAdmin || _.isEmpty(customSchema) ? originSchema : _.map(originSchema, item => {
    const customItem = _.find(customSchema, i => _.isEqual(i.content, item.content));
    if (!_.isEmpty(customItem)) {
      return customItem;
    }

    return item;
  });

  const [toastItems, setToastItems] = useState([]);
  const [layout, setLayout] = useState(schema);
  const [isEdit, setIsEdit] = useState(false);
  const [isChanged, setIsChanged] = useState(false);
  const [isResetOrigin, setIsResetOrigin] = useState(false);
  const [isPreview, setIsPreview] = useState(true);
  const [editNode, setEditNode] = useState({});
  const [embeddedReports, setEmbeddedReports] = useState([]);
  const [isSelectAll, setIsSelectAll] = useState(false);

  const { observer, onOpenChange, open } = useModal();

  useEffect(() => {
    setIsChanged(!_.isEqual(schema, layout));
    setIsResetOrigin(!_.isEqual(originSchema, layout));

    if (outputId) {
      document.getElementById(outputId).value = JSON.stringify(layout);
    }

    if (outputStatus) {
      document.getElementById(outputStatus).value = JSON.stringify(showFilters);
    }
  }, [layout, originSchema, outputId, schema, outputStatus]);

  useEffect(() => {
    !open && setEditNode({});
  }, [open]);

  useEffect(() => {
    setIsSelectAll(embeddedReports.every(({ isSelected }) => isSelected.newValue));
  }, [embeddedReports]);

  const onLayoutChange = (changedLayout) => {
    const _changedLayout = _.map(changedLayout, i => {
      return _(i).omitBy(_.isUndefined).omitBy(_.isNull).value();
    });

    setLayout(_.map(_changedLayout, (item, idx) => {
      return {
        ...item,
        content: layout[idx].content
      }
    }));
  }

  const onResize = (l, oldItem, newItem, placeholder) => {
    const content = _.find(layout, item => item.i === oldItem.i).content;
    if (!content.aspectRatio) return;

    const changeCoef = oldItem.w / oldItem.h;
    if (Math.abs(newItem.h - oldItem.h) < Math.abs(newItem.w - oldItem.w)) {
      placeholder.h = newItem.h = newItem.w / changeCoef;
    } else {
      placeholder.w = newItem.w = newItem.h * changeCoef;
    }
  };

  const onEdit = (node) => {
    onOpenChange(true);
    setEditNode({ i: node.i, ...node.content, width: node.w, height: node.h });
  };

  const onSubmit = (node) =>  _.isEmpty(editNode) ? onAdd(node) : onUpdate(node);

  const onAdd = (node) => {
    let idx = 0;
    let x = 0;
    let y = 0;
    
    if (!_.isEmpty(layout)) {
      idx = parseInt(_.maxBy(layout, i => parseInt(i.i)).i) + 1;
      x = (idx * 3) % 12;
      y = _.maxBy(layout, i => i.y).y;
    }

    const newLayout = [...layout, {
      x: x,
      y: y,
      w: node.width,
      h: node.height,
      i: idx.toString(),
      static: false,
      moved: false,
      content: onBuildLayoutContent(node)
    }];

    setLayout(newLayout);
  };

  const onUpdate = (node) => {
    const newLayout = [...layout];
    
    newLayout[parseInt(node.i)] = {
      ...newLayout[parseInt(node.i)],
      w: node.width,
      h: node.height,
      content: onBuildLayoutContent(node)
    };

    setLayout(newLayout);
  };

  const onDelete = (idx) => {
    setLayout(_.reject(layout, { i: idx.toString() }));
    setEditNode({});
  }

  const onCancel = () => {
    setIsEdit(false);
    setLayout(schema);
    setIsPreview(true);
  };

  const onReset = () => {
    setLayout(originSchema);
  };

  const onSaveLayout = async () => {
    setIsEdit(false);
    setIsPreview(true);

    let formData = new FormData();
    formData.append(namespace + "customSchema", JSON.stringify(layout));

    const res = await fetch(saveURL, {
      method: "POST",
      body: formData,
    });

    if (res.ok) {
      window.location.reload();
    } else {
      setToastItems([{
        displayType: "danger",
        title: "Error",
        message: "Internal Server Error"
      }]);
    }
  };

  const onSelectAll = (isSelected) => {
    const _embeddedReports = embeddedReports.reduce((results, embeddedReport) => [
      ...results,
      {
        ...embeddedReport,
        isSelected: {
          oldValue: embeddedReport.isSelected,
          newValue: isSelected
        }
      }
    ], []); 

    setEmbeddedReports(_embeddedReports);
  }

  const onBuildLayoutContent = ({ title, url, aspectRatio, groupId, reportId, urlType }) => {
    return {
      title,
      url,
      groupId,
      reportId,
      aspectRatio,
      urlType
    };
  }

  const filterEmbeddedReports = (embeds) => embeds.filter(({ isSelected }) => isSelected.newValue);

  const updateEmbeddedReport = async ({ embed, bookmarkName, filter }) => {
    try {
      const bookmarks = await embed.bookmarksManager.getBookmarks();
      const bookmark = bookmarks.find(({ displayName }) => displayName === bookmarkName);

      await embed.bookmarksManager.apply(bookmark?.name);
    } catch (error) {
      console.log("error: apply bookmark failed");
    }

    await embed.updateFilters(
      models.FiltersOperations.ReplaceAll, 
      [filter]
    );
  }

  const generateDOM = () => {
    return _.map(layout, (item, index) => {
      return (
        <div key={item.i} className={"item " + (isAdmin || isEdit ? "item-edit" : "")}>
          <span className="btn-action btn-move">
            <ClayIcon spritemap={spritemap} symbol="move" />
          </span>
          {isAdmin && 
            <span className="btn-action btn-remove" onClick={() => onEdit(item)}>
              <ClayIcon spritemap={spritemap} symbol="info-circle" />
            </span>
          }
          <div className="content">
            {generateContent({ isPreview, index,...item?.content })}
          </div>
        </div>
      );
    });
  }

  const actionButtons = () => {
    const results = [];

    if (isAdmin) {
      results.push(
        <ClayButton displayType="secondary" size="sm" onClick={onReset} disabled={!isChanged}>
          Reset
        </ClayButton>);
    } else {
      results.push(
        <ClayButton displayType="secondary" size="sm" onClick={onCancel} disabled={!isChanged && !isEdit}>
          Cancel
        </ClayButton>
      );

      results.push(
        <ClayButton displayType="secondary" size="sm" onClick={onReset} disabled={!isResetOrigin}>
          Reset to Origin
        </ClayButton>);

      results.push(
        <ClayButton displayType="primary" size="sm" onClick={onSaveLayout} disabled={!isChanged}>
          Save
        </ClayButton>
      );
    }

    return results;
  };

  const generateContent = ({ isPreview, index, title, url, aspectRatio, urlType, groupId, reportId } = {}) => {
    const renderIframe = () => {
      switch (urlType) {
        case "embed-url":
          return <iframe title={url} src={url} className="iframe" />;
        case "pbi-client": {
          const embedKey = `${index}_${title}`;
          const embedIndex = embeddedReports.findIndex(embeddedReport => embeddedReport?.embedKey === embedKey);
          const isEmbedSelected = embeddedReports[embedIndex]?.isSelected.newValue || false;

          return (
            <div className="pbi-client">
              {showFilters && 
                <div className={`
                  pbi-client__checkbox 
                  ${embeddedReports[embedIndex] !== -1 ? "d-block" : "d-none"}
                `}>
                  <ClayCheckbox
                    checked={isEmbedSelected}
                    onChange={(e) => {
                      const embeddedReport = {
                        ...embeddedReports[embedIndex],
                        isSelected: {
                          oldValue: isEmbedSelected,
                          newValue: e.target.checked
                        } 
                      }

                      setEmbeddedReports(
                        embeddedReports.map((embedded, index) => embedIndex !== index ? embedded : embeddedReport)
                      );
                    }}
                  />
                </div>
              }
              <PowerBiClient
                namespace={namespace}
                getConfigURL={getConfigURL}
                groupId={groupId}
                reportId={reportId}
                setEmbeddedReport={(embed) => {
                  setEmbeddedReports([...embeddedReports, { 
                    embed,
                    embedKey,
                    isSelected: {
                      oldValue: false,
                      newValue: false
                    }
                  }])
                }}
              />
            </div>
          );
        }
        default:
          return;  
      }
    }
    
    return isPreview
      ? renderIframe()
      : (
        <div className="minitext">
          <span>{title}</span>
          <span>{url}</span>
          <span>lockAspectRatio: {aspectRatio ? 'true' : 'false'}</span>
        </div>
      )
  }

  return (
    <div className="app">
      <ClayLayout.ContainerFluid className={isAdmin ? 'c-py-3' : 'c-p-0'}>
        <ClayLayout.Row justify="between" className="header">
          <ClayLayout.Col size={12}>
            {showFilters && 
              <Filters
                embeddedReports={filterEmbeddedReports(embeddedReports)}
                isSelectAll={isSelectAll}
                selectAll={onSelectAll}
                updateEmbeddedReport={updateEmbeddedReport}
              />
            }
          </ClayLayout.Col>
        </ClayLayout.Row>
        
        <ClayLayout.Row justify="between" className="header">
          <ClayLayout.Col size={5}>
            {isAdmin &&
              <ClayButton displayType="primary" size="sm" onClick={() => onOpenChange(true)}>
                Add Chart
              </ClayButton>
            }
          </ClayLayout.Col>
          <ClayLayout.Col size={2}>
            <ClayForm.Group>
              {(isAdmin || isEdit) && 
                <ClayToggle
                  toggled={isPreview}
                  spritemap={spritemap}
                  onToggle={setIsPreview}
                  label={
                    <>
                      Preview? 
                      <ClayTooltipProvider>
                        <span data-tooltip-align="right" title="Disable Preview mode for good display performance.">
                          <ClayIcon spritemap={spritemap} symbol="question-circle-full" />
                        </span>
                      </ClayTooltipProvider>
                    </>
                  }
                />
              }
            </ClayForm.Group>
          </ClayLayout.Col>
          <ClayLayout.Col size={5}>
            <div className="action-button form-group">
              {!isEdit && !isAdmin ?
                canEdit && (
                  <ClayButton 
                    displayType="unstyled" 
                    onClick={() => setIsEdit(true)} 
                    style={{
                      position: 'absolute',
                      right: 20,
                    }}
                >
                  <ClayIcon spritemap={spritemap} symbol="move" />
                </ClayButton>
                ) : (
                  <ClayButton.Group spaced>
                    {actionButtons()}
                  </ClayButton.Group>
                )}
            </div>
          </ClayLayout.Col>
        </ClayLayout.Row>

        <ClayLayout.Row justify="between" className="layout">
          <ClayLayout.Col size={12}>
            <SizeMe>
              {({ size }) => (
                <Responsive
                  {...defaultProps}
                  width={size.width}
                  rowHeight={_.floor((size.width + 24) / defaultProps.cols) - defaultProps.margin[0]}
                  layout={layout}
                  onLayoutChange={onLayoutChange}
                  onResize={onResize}
                >
                  {generateDOM()}
                </Responsive>
              )}
            </SizeMe>

          </ClayLayout.Col>
        </ClayLayout.Row>
      </ClayLayout.ContainerFluid>

      <ClayAlert.ToastContainer>
        {_.map(toastItems, item => (
          <ClayAlert
            autoClose={5000}
            key={item.message}
            onClose={() => {
              setToastItems(prevItems =>
                _.filter(prevItems, i => i.message !== item.message)
              );
            }}
            spritemap={spritemap}
            displayType={item.displayType}
            title={item.title}
          >
            {item.message}
          </ClayAlert>
        ))}
      </ClayAlert.ToastContainer>

      <AddModal
        spritemap={spritemap}
        observer={observer}
        open={open}
        data={editNode}
        onOpenChange={onOpenChange}
        onSubmit={onSubmit}
        onDelete={onDelete}
      />
    </div>
  );
}

export default App;