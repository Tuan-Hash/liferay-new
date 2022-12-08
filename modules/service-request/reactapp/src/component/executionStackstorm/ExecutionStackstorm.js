import React, { useEffect, useRef } from "react";
import moment from 'moment';
import { Alert, AlertTitle, Stack, Grid, ListItemText, ListItemIcon, ListItem, List, IconButton, Collapse, Box } from '@mui/material';
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import KeyboardArrowRightIcon from "@mui/icons-material/KeyboardArrowRight";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import CircleIcon from '@mui/icons-material/Circle';
import CancelIcon from '@mui/icons-material/Cancel';

export default function CollapsibleTable(props) {
  const [childrenList, setChildrenList] = React.useState([]);
  const [openCollapse, setOpenCollapse] = React.useState(false);
  const {portletData} = props;
  const [error, setError] = React.useState();

  const getData = async (id) => {
    const url = `${portletData.tenantUrl}/executions/${id}/children`;
    let returnData = [];
    try {
      const res = await fetch(url, {
        method: "GET",
        headers: {
          'Content-type': 'application/json',
          'Authorization': 'Basic c3lzYWRtaW46c3lzYWRtaW4=',
        }
      });
      let json = await res.json();
      if (res.ok) {
        returnData = json.data.results;
      } else {
        setError(json.error);
      }
    }
    catch (err) {
      setError('Failed to load data!');
    }
    return returnData;
  }

  function Row(props) {
    const { row } = props;
    const [openModule, setOpenModule] = React.useState(false);
    const [rowChildren, setRowChildren] = React.useState([]);
    const [isClicked, setIsClicked] = React.useState(false);

    function checkStatus(statusChildren) {
      if (statusChildren == "paused" || statusChildren == "pending") {
        return (
          <ListItemIcon style={{ minWidth: 30, color: "grey" }}>
            <CircleIcon className="request-list-item-icon"/>
          </ListItemIcon>
        );
      }

      if (statusChildren == "failed") {
        return (
          <ListItemIcon style={{ minWidth: 30, color: "red" }}>
            <CancelIcon className="request-list-item-icon"/>
          </ListItemIcon>
        );
      }

      if (statusChildren == "canceled") {
        return (
          <ListItemIcon style={{ minWidth: 30, color: "orange" }}>
            <CancelIcon className="request-list-item-icon"/>
          </ListItemIcon>
        );
      }

      if (statusChildren == "running") {
        return (
          <ListItemIcon style={{ minWidth: 30, color: "grey" }}>
            <CircleIcon className="request-list-item-icon"/>
          </ListItemIcon>
        );
      }

      if (statusChildren == "succeeded") {
        return (
          <ListItemIcon style={{ minWidth: 30, color: "green" }}>
            <CheckCircleIcon className="request-list-item-icon" />
          </ListItemIcon>
        );
      }
    }

    return (
      <React.Fragment>
        <ListItem style={{ flexWrap: "wrap" }} className="request-list-item">
          {checkStatus(row.status)}
          <IconButton
            aria-label="expand row"
            size="small"
            className={row.children.length > 0 ? "" : "request-hide"}
            onClick={async () => {
              if (!isClicked) {
                let dataChildren = []
                await getData(row.id).then(data => dataChildren = data);
                setRowChildren(dataChildren);
                setIsClicked(true);
              }
              setOpenModule(!openModule);
            }}
            disabled={row.children.length > 0 ? false : true}
            style={{ color: "black" }}
          >
            {!openModule ? <KeyboardArrowRightIcon className="request-list-item-icon" /> : <KeyboardArrowDownIcon className="request-list-item-icon" />}
          </IconButton>
          <ListItemText className="request-list-item-text" primary={moment(row.start_time).format('YY-MM-DD HH:MM:SS')} style={{ maxWidth: "15%" }} />
          <ListItemText className="request-list-item-text" primary={row.name} />
          <Collapse in={openModule} timeout="auto" unmountOnExit style={{ width: "100%" }}>
            <Box style={{ marginLeft: 20 }} >
              <List>
                {rowChildren.map((childrenRow) => {
                  return (
                    <Row
                      row={childrenRow}
                    />
                  )
                })}
              </List>
            </Box>
          </Collapse>
        </ListItem>
      </React.Fragment>
    );
  }

  useEffect(() => {
    if (portletData.executionId) {
      getData(portletData.executionId).then(data => setChildrenList(data));
    }
  }, []);

  return (
    <Grid container spacing={2}>
      <Grid item sm={12}>
      <div className={portletData.executionId ? "request-execution-list" : "request-disable"} style={{fontWeight: 600}} onClick={() => {
        if (!portletData.executionId) {
          return;
        }
        setOpenCollapse(!openCollapse)
      }}>
          <span >Execution in Stackstorm</span>
          {!openCollapse ? <KeyboardArrowRightIcon className={portletData.executionId ? "request-list-item-icon" : "request-hide"}/> : <KeyboardArrowDownIcon className={portletData.executionId ? "request-list-item-icon" : "request-hide"}/>}
        </div>
        <Collapse in={openCollapse} timeout="auto" unmountOnExit style={{ width: "100%" }}>
        <Stack sx={{ width: '100%' }} spacing={2} className={error ? '' : 'request-display-none'}>
            <Alert severity="error">
              <AlertTitle>Error</AlertTitle>
              {error}
            </Alert>
          </Stack>
          <List>
            {childrenList.map((row) => {
              return (
                <Row key={row.name} row={row} />
              )
            })}
          </List>
        </Collapse>
      </Grid>
    </Grid>
  );
}
