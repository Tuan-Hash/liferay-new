import React, { useEffect } from "react";
import { useTheme, styled } from '@mui/material/styles';
import { Box, Table, TableBody, TableCell, TableContainer, TablePagination, TableRow, Paper, IconButton, TextField, InputAdornment, DialogContent, Dialog, TableHead, DialogTitle, Alert, AlertTitle, Stack, Backdrop, CircularProgress, Button, DialogActions } from '@mui/material';
import Chip from '@mui/material/Chip';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import Checkbox from '@mui/material/Checkbox';

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
  '& .MuiDialogContent-root': {
    padding: theme.spacing(2),
  },
  '& .MuiDialogActions-root': {
    padding: theme.spacing(1),
  },
  '& .reference-hide': {
    display: 'none',
  },
  '& .reference-table-pagination p': {
    margin: 0,
  },
  '& .reference-backdrop ': {
    position: 'absolute',
    backgroundColor: 'white'
  },
  '& .reference-backdrop .reference-circular': {
    color: '#80808073'
  },
  '& .reference-dialog-title ': {
    position: 'relative',
    zIndex: 1202
  },
}));

const BootstrapDialogTitle = (props) => {
  const { children, onClose, ...other } = props;

  return (
    <DialogTitle sx={{ m: 0, p: 2 }} {...other} className='reference-dialog-title'>
      {children}
      {onClose ? (
        <IconButton
          aria-label="close"
          onClick={onClose}
          sx={{
            position: 'absolute',
            right: 8,
            top: 8,
            color: (theme) => theme.palette.grey[500],
          }}
        >
          <CloseIcon />
        </IconButton>
      ) : null}
    </DialogTitle>
  );
};

function TablePaginationActions(props) {
  const theme = useTheme();
  const { count, page, rowsPerPage, onPageChange } = props;

  const handleFirstPageButtonClick = (
    event
  ) => {
    onPageChange(event, 0);
  };

  const handleBackButtonClick = (event) => {
    onPageChange(event, page - 1);
  };

  const handleNextButtonClick = (event) => {
    onPageChange(event, page + 1);
  };

  const handleLastPageButtonClick = (event) => {
    onPageChange(event, Math.max(0, Math.ceil(count / rowsPerPage) - 1));
  };

  return (
    <Box sx={{ flexShrink: 0, ml: 2.5 }}>
      <IconButton
        onClick={handleFirstPageButtonClick}
        disabled={page === 0}
        aria-label="first page"
      >
        {theme.direction === 'rtl' ? <LastPageIcon /> : <FirstPageIcon />}
      </IconButton>
      <IconButton
        onClick={handleBackButtonClick}
        disabled={page === 0}
        aria-label="previous page"
      >
        {theme.direction === 'rtl' ? <KeyboardArrowRight /> : <KeyboardArrowLeft />}
      </IconButton>
      <IconButton
        onClick={handleNextButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="next page"
      >
        {theme.direction === 'rtl' ? <KeyboardArrowLeft /> : <KeyboardArrowRight />}
      </IconButton>
      <IconButton
        onClick={handleLastPageButtonClick}
        disabled={page >= Math.ceil(count / rowsPerPage) - 1}
        aria-label="last page"
      >
        {theme.direction === 'rtl' ? <FirstPageIcon /> : <LastPageIcon />}
      </IconButton>
    </Box>
  );
}

export const MultipleReference = React.memo((props) => {
  const [open, setOpen] = React.useState(false);
  const [referenceList, setReferenceList] = React.useState([]);
  const [headerTable, setHeaderTable] = React.useState([]);
  const [valueList, setValueList] = React.useState([]);
  const [error, setError] = React.useState();
  const [openBackdrop, setOpenBackdrop] = React.useState(true);
  const [selected, setSelected] = React.useState([]);
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);

  const handleSelectAllClick = (event) => {
    if (event.target.checked) {
      const newSelected = referenceList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((n, key) => n.name);
      setSelected(newSelected);
      return;
    }
    setSelected([]);
  };

  const handleClick = (event, name) => {
    const selectedIndex = selected.indexOf(name);
    let newSelected = [];

    if (selectedIndex === -1) {
      newSelected = newSelected.concat(selected, name);
    } else if (selectedIndex === 0) {
      newSelected = newSelected.concat(selected.slice(1));
    } else if (selectedIndex === selected.length - 1) {
      newSelected = newSelected.concat(selected.slice(0, -1));
    } else if (selectedIndex > 0) {
      newSelected = newSelected.concat(
        selected.slice(0, selectedIndex),
        selected.slice(selectedIndex + 1),
      );
    }
    setSelected(newSelected);
  };

  const {
    uischema,
    required,
    errors,
    data,
    path,
    values
  } = props;

  const {
    options,
    label,
  } = uischema;

  const {
    url,
    filter
  } = options;

  useEffect(() => {
    const fetchData = async () => {
      if (values != undefined) {
        if (options.label != options.key) {
          let list = [];
        for (const value of data[path]) {
          let urlApi = `${url}?${options.key}=${value}`;
          try {
            const res = await fetch(urlApi, {
              method: "GET",
              headers: {
                'Content-type': 'application/json',
                'Authorization': 'Basic c3lzYWRtaW46c3lzYWRtaW4=',
              }
            });
            let json = await res.json();
            if (res.ok) {
              if (json.length > 0) {
                list.push(json[0][options.label]);
                setError();
              }
              else {
                setError("No data were found " + path.toUpperCase() + '!');
              }
            }
            else {
              setError(json.error);
            }
          }
          catch (err) {
            setError('Failed to load data ' + path.toUpperCase() + '!');
          }
        }
        setValueList(list);
        }
        else {
          setValueList(data[path]);
        }
      }
    }
    fetchData();
  }, []);

  const handleClickOpen = async () => {
    setOpen(true);
    let urlApi = url;
    if (filter) {
      urlApi = `${url}?${filter.key}=${data[filter.scope]}`;
    }
    
    try {
      const res = await fetch(urlApi, {
        method: "GET",
        headers: {
          'Content-type': 'application/json',
          'Authorization': 'Basic c3lzYWRtaW46c3lzYWRtaW4=',
        }
      });
      let json = await res.json();
      if (res.ok) {
        setOpenBackdrop(false);
        if (json.length > 0) {
          setReferenceList(json);
          setHeaderTable(Object.keys(json[0]));
        }
        else {
          setError("No data were found!");
          setReferenceList(json);
        }
        
      } else {
        setOpenBackdrop(false);
      }
    }
    catch (err) {
      setOpenBackdrop(false);
      setError('Failed to load data!');
    }
  };

  const handleClose = () => {
    setSelected(valueList)
    setOpen(false);
  };

  const handleSave = () => {
    setValueList(selected);
    setOpen(false);
    props.updateValue(selected);
  };

  const handleDelete = (select) => {
    let arr = [...selected]
    let index = selected.indexOf(select);
    arr.splice(index, 1);
    setSelected(arr);
    setValueList(arr);
  };


  const renderValues = (obj) => {
    let values = Object.values(obj);
    return values.map((value) => {
      return <TableCell align="left">{value.toString()}</TableCell>
    })
  }

  function CustomizedInputBase() {
    return (
      <div>
         <Stack sx={{ width: '100%' }} spacing={2} className={error ? '' : 'reference-hide'} style={{ marginBottom: 10 }}>
          <Alert severity="error">
            <AlertTitle>Error</AlertTitle>
            {error}
          </Alert>
        </Stack>
        <TextField
          className={error ? 'reference-hide' : ''}
          error={errors}
          id="standard-end-adornment"
          sx={{ width: '100%' }}
          value={null}
          label={required ? (label + '*') : label}
          helperText={errors}
          InputProps={{
            endAdornment: <InputAdornment position="end">
              <IconButton type="button" sx={{ p: '10px' }} aria-label="search" onClick={handleClickOpen} className='reference-search-icon' disabled>
                <SearchIcon />
              </IconButton></InputAdornment>,
            disabled: true,
            startAdornment: (valueList.length === 0 ? null :
              <InputAdornment position="start">
                {valueList.map(select => {
                  return (
                    <Chip style={{marginRight: 5, height: 25 }} label={select} variant="outlined"/>
                  )
                })}
              </InputAdornment>
            ),
          }}
          variant="standard"
        />
      </div>
    );
  }

  function EnhancedTableHead(props) {
    const { onSelectAllClick, order, orderBy, numSelected, rowCount } =
      props;
    return (
      error ? null :
      <TableHead>
        <TableRow>
          <TableCell padding="checkbox">
            <Checkbox
              color="primary"
              indeterminate={numSelected > 0 && numSelected < rowCount}
              checked={rowCount > 0 && numSelected === rowCount}
              onChange={onSelectAllClick}
              inputProps={{
                'aria-label': 'select all desserts',
              }}
            />
          </TableCell>
          {headerTable.map((headCell) => (
            <TableCell
              key={headCell.id}
              align={headCell.numeric ? 'right' : 'left'}
              padding={headCell.disablePadding ? 'none' : 'normal'}
              sortDirection={orderBy === headCell.id ? order : false}
            >
              {headCell.toUpperCase()}
            </TableCell>
          ))}
        </TableRow>
      </TableHead>
    );
  }
  
  function SimpleBackdrop() {
    return (
      <div>
        <Backdrop
          sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
          open={openBackdrop}
          className="reference-backdrop"
        >
          <CircularProgress color="inherit" className="reference-circular" />
        </Backdrop>
      </div>
    );
  }

  // Avoid a layout jump when reaching the last page with empty referenceList.
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const isSelected = (name) => selected.indexOf(name) !== -1;

  // Avoid a layout jump when reaching the last page with empty referenceList.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - referenceList.length) : 0;

  return (
    <div className='reference'>
      <CustomizedInputBase></CustomizedInputBase>
      <BootstrapDialog
        onClose={handleClose}
        aria-labelledby="customized-dialog-title"
        open={open}
        fullWidth='true'
        maxWidth='md'
        className='reference-dialog'
      >
        <BootstrapDialogTitle id="customized-dialog-title" onClose={handleClose}>
          {label}
        </BootstrapDialogTitle>
        <SimpleBackdrop></SimpleBackdrop>
        <DialogContent style={{ minHeight: 100 }}>
          <Stack sx={{ width: '100%' }} spacing={2} className={error ? '' : 'reference-hide'}>
            <Alert severity="error">
              <AlertTitle>Error</AlertTitle>
              {error}
            </Alert>
          </Stack>
          <TableContainer component={Paper}>
            <Table
              sx={{ minWidth: 750 }}
              aria-labelledby="tableTitle"
            >
              <EnhancedTableHead
                numSelected={selected.length}
                onSelectAllClick={handleSelectAllClick}
                rowCount={referenceList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).length}
              />
              <TableBody>
                {referenceList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row, index) => {
                  const isItemSelected = isSelected(row.name);
                  const labelId = `enhanced-table-checkbox-${index}`;

                  return (
                    error ? null :
                    <TableRow
                      hover
                      onClick={(event) => handleClick(event, row.name)}
                      role="checkbox"
                      aria-checked={isItemSelected}
                      tabIndex={-1}
                      key={row.name}
                      selected={isItemSelected}
                    >
                      <TableCell padding="checkbox">
                        <Checkbox
                          color="primary"
                          checked={isItemSelected}
                          inputProps={{
                            'aria-labelledby': labelId,
                          }}
                        />
                      </TableCell>
                      {renderValues(row)}
                    </TableRow>
                  );
                })}
                {emptyRows > 0 && (
                  <TableRow
                  >
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={referenceList.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
            SelectProps={{
              inputProps: {
                'aria-label': 'rows per page',
              },
              native: true,
            }}
            className={referenceList.length > 0 ? 'reference-table-pagination' : 'reference-table-pagination reference-hide'}
            ActionsComponent={TablePaginationActions}
          />
        </DialogContent>
        <DialogActions className={error ? 'reference-hide' : ''}>
          <Button variant="contained" onClick={handleClose} style={{textTransform:'none'}}>
            Cancel
          </Button>
          <Button variant="contained" onClick={handleSave} style={{textTransform:'none'}}>
            Apply
          </Button>
        </DialogActions>
      </BootstrapDialog>
    </div>
  );
})
