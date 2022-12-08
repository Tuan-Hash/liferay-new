import React, { useState, useEffect } from "react";

import { Checkbox, FormControlLabel } from "@mui/material";
import { ChevronRight, ExpandMore } from "@mui/icons-material";
import { TreeItem, TreeView } from "@mui/lab";

const leafIds = (node) => {
  return !node.children || node.children.length === 0
    ? [node.value]
    : node.children.map(leafIds).flat();
}

const getExpanded = (node, checked = []) => {
  if (!node.children || !node.children.length) {
    return checked.includes(node.value) ? [node.value] : [];
  }

  const children = node.children.map(i => getExpanded(i, checked)).flat();
  return children.length ? [node.value, ...children] : [];
}

const reduceChecked = (node, checked = []) => {
  if (!node.children || !node.children.length) {
    return checked.includes(node.value) ? [node.value] : [];
  }

  const children = node.children.map(i => reduceChecked(i, checked)).flat();
  return children.length === node.children.length ? [node.value] : children;
}

const expandChecked = (node, checked = []) => {
  if (checked.includes(node.value)) {
    if (!node.children || !node.children.length) {
      return [node.value];
    }

    return node.children.map(childNode => childNode.value);
  }

  if (node.children && node.children.length > 0) {
    return node.children.map(i => expandChecked(i, checked)).flat();
  }

  return [];
}

export const CheckboxTree = (props) => {
  const { nodes, onChecked } = props;

  const initChecked = nodes.map(node => expandChecked(node, props.checked || [])).flat();
  const [checked, setChecked] = useState(initChecked);

  const initExpanded = props.expanded || nodes.map(node => getExpanded(node, checked)).flat();
  const [expanded, setExpanded] = useState(initExpanded);

  useEffect(() => {
    onChecked && onChecked(nodes.map(node => reduceChecked(node, checked)).flat());
  }, [checked]);

  const handleCheck = (node, newValue) => {
    const value = checked.includes(node.value);
    if (!node.children || node.children.length === 0) {
      if (value === newValue) return;
      setChecked(
        newValue
          ? [...checked, node.value]
          : checked.filter(id => id !== node.value)
      );
    } else {
      const ids = leafIds(node);
      const remaining = checked.filter(id => !ids.includes(id));
      setChecked(newValue ? [...remaining, ...ids] : remaining);
    }
  };

  function TreeNode({ node }) {
    const isChecked = leafIds(node).every(id => checked.includes(id));
    const isIndeterminate =
      !isChecked && leafIds(node).some(id => checked.includes(id));
    const onChange = () => {
      handleCheck(node, !isChecked);
    };

    return (
      <TreeItem
        key={node.value}
        nodeId={node.value}
        label={
          <FormControlLabel
            color="white"
            style={{ display: "flex", alignItems: "center" }}
            control={
              <Checkbox
                checked={isChecked}
                onChange={onChange}
                onClick={e => e.stopPropagation()}
                indeterminate={isIndeterminate}
              />
            }
            label={node.label}
            onClick={e => e.stopPropagation()}
          />
        }
      >
        {node.children && <TreeNodes nodes={node.children} />}
      </TreeItem>
    );
  }

  function TreeNodes({ nodes }) {
    return (
      <>
        {nodes.map(node => (
          <TreeNode node={node} key={node.label} />
        ))}
      </>
    );
  }

  return (
    <TreeView
      defaultCollapseIcon={<ExpandMore />}
      defaultExpandIcon={<ChevronRight />}
      defaultEndIcon={<ChevronRight style={{ visibility: "hidden" }} />}
      expanded={expanded}
      onNodeToggle={(_, nodes) => setExpanded(nodes)}
      disableSelection
    >
      <TreeNodes nodes={nodes} />
    </TreeView>
  );
}
