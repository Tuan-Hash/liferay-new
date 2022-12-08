import { CircularProgress, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Grid from '@mui/material/Grid';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Paper from '@mui/material/Paper';
import { useEffect, useRef, useState } from 'react';
import './App.css';
import './App.scss';

function not(a, b) {
  return a.filter((value) => b.indexOf(value) === -1);
}

function intersection(a, b) {
  return a.filter((value) => b.indexOf(value) !== -1);
}

function App(props) {
  const { namespace, submitURL, fetchCatalogsURL, initData } = props?.portletData;

  const leftRef = useRef("leftRef");
  const rightRef = useRef("rightRef");

  const [checked, setChecked] = useState([]);
  const [left, setLeft] = useState([]);
  const [right, setRight] = useState([]);
  const [actionAlert, setActionAlert] = useState();
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState({});

  const hasCatalogs = !!(JSON.parse(initData)?.results || []).length;

  useEffect(() => {
    setData(JSON.parse(initData) || {});
    setLeft(JSON.parse(initData)?.results || []);
  }, []);

  const onCancel = () => {
    window.parent.location.reload();
    return null;
  };

  const onSubmit = async () => {
    await setLoadingState(async () => {
      let formData = new FormData();
      formData.append(namespace + "catalogs", JSON.stringify(right));
  
      const result = await fetch(submitURL, {
        method: "POST",
        body: formData
      });

      await result.json();
  
      if (result?.ok) {
        setActionAlert({
          type: 'success',
          code: result?.status,
          message: "Service catalogs has been cloned successfully"
        });
      } else {
        setActionAlert({
          type: 'error',
          code: result?.status,
          message: "Service catalog has been cloned failed"
        });
      }
    });
  }

  const setLoadingState = async (execFunc) => {
    setLoading(true);
    await execFunc();
    setLoading(false);
  }
  
  const leftChecked = intersection(checked, left);
  const rightChecked = intersection(checked, right);

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  const handleAllRight = () => {
    setRight(right.concat(left));
    setLeft([]);
  };

  const handleCheckedRight = () => {
    setRight(right.concat(leftChecked));
    setLeft(not(left, leftChecked));
    setChecked(not(checked, leftChecked));
  };

  const handleCheckedLeft = () => {
    setLeft(left.concat(rightChecked));
    setRight(not(right, rightChecked));
    setChecked(not(checked, rightChecked));
  };

  const handleAllLeft = () => {
    setLeft(left.concat(right));
    setRight([]);
  };

  const renderRootCatalogs = (items, ref) => (
    <Paper className="app__transfer-list-wrapper" ref={ref}> 
      <List className="app__transfer-list-container" dense component="div" role="list">
        {items.map((item) => {
          const labelId = `transfer-list-item-${item.id}-label`;

          return (
            <ListItem
              key={item.id}
              role="listitem"
              button
              onClick={handleToggle(item)}
            >
              <ListItemIcon>
                <Checkbox
                  checked={checked.indexOf(item) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{
                    'aria-labelledby': labelId,
                  }}
                />
              </ListItemIcon>
              <ListItemText id={labelId}>
                {`${item?.category.name}_${item.name}`}
              </ListItemText>
            </ListItem>
          );
        })}
        <ListItem />
      </List>
    </Paper>
  );

  const renderButtons = () => (
    <Grid container direction="column" alignItems="center">
      <Button
        sx={{ my: 0.5 }}
        variant="outlined"
        size="small"
        onClick={handleAllRight}
        disabled={left.length === 0}
        aria-label="move all right"
      >
        ≫
      </Button>
      <Button
        sx={{ my: 0.5 }}
        variant="outlined"
        size="small"
        onClick={handleCheckedRight}
        disabled={leftChecked.length === 0}
        aria-label="move selected right"
      >
        &gt;
      </Button>
      <Button
        sx={{ my: 0.5 }}
        variant="outlined"
        size="small"
        onClick={handleCheckedLeft}
        disabled={rightChecked.length === 0}
        aria-label="move selected left"
      >
        &lt;
      </Button>
      <Button
        sx={{ my: 0.5 }}
        variant="outlined"
        size="small"
        onClick={handleAllLeft}
        disabled={right.length === 0}
        aria-label="move all left"
      >
        ≪
      </Button>
    </Grid>
  );

  const successDialog = () => {
    return actionAlert?.type === 'success' && (
      <Dialog
        open={true}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          Success
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

  return !hasCatalogs 
    ? <div class="app app__no-data">No Service catalogs found!</div> 
    : (
      <Grid className={loading ? "App app app--loading" : "App app"} container justifyContent="center" alignItems="center">
        <CircularProgress className="app__progress-bar" hidden={!loading} />
        <Grid container  spacing={2} columns={12} className="app__transfer-list">
          <Grid item xs={5} className="app__transfer-list-left">
            <p class="app__transfer-list-title">Available</p>
            {renderRootCatalogs(left, leftRef)}
          </Grid>
          <Grid item xs={2} className="app__transfer-list-middle">
            {renderButtons()}
          </Grid>
          <Grid item xs={5} className="app__transfer-list-right">
            <p class="app__transfer-list-title">Selected</p>
            {renderRootCatalogs(right, rightRef)}
          </Grid>
        </Grid>
        <Grid container xs={12} justifyContent="flex-end" alignItems="center" className="app__buttons Action-Buttons">
          <Button variant="outlined" onClick={onCancel}>Cancel</Button>
          <Button disabled={right.length === 0} variant="contained" color="primary" onClick={onSubmit}>Submit</Button>
        </Grid>
        {successDialog()}
      </Grid>
    );
}

export default App;