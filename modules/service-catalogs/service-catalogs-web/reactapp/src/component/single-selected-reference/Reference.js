import * as React from 'react';
import { useTheme, styled } from '@mui/material/styles';
import { Box, Table, TableBody, TableCell, TableContainer, TableFooter, TablePagination, TableRow, Paper, IconButton, TextField, InputAdornment, DialogContent, Dialog, TableHead, DialogTitle, Alert, AlertTitle, Stack, Backdrop, CircularProgress, Hidden } from '@mui/material';
import FirstPageIcon from '@mui/icons-material/FirstPage';
import KeyboardArrowLeft from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRight from '@mui/icons-material/KeyboardArrowRight';
import LastPageIcon from '@mui/icons-material/LastPage';
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';

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
  '& .reference-table-pagination ': {
    width: '100%'
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

export const Reference = React.memo((props) => {
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [open, setOpen] = React.useState(false);
  const [referenceList, setReferenceList] = React.useState([]);
  const [headerTable, setHeaderTable] = React.useState([]);
  const [value, setValue] = React.useState();
  const [error, setError] = React.useState();
  const [openBackdrop, setOpenBackdrop] = React.useState(true);
  const [previousDependencyValue, setPreviousDependencyValue] = React.useState(null);
  
  const {
    uischema,
    required,
    errors,
    visible,
    data
  } = props;

  const {
    options,
    label,
  } = uischema;

  const {
    url,
    filter
  } = options;

  if(filter && visible) {
    if (previousDependencyValue == null) {
        setPreviousDependencyValue(data[filter.scope]);
    }
    else if (previousDependencyValue !== data[filter.scope]) {
        setValue();
        props.updateValue();
        setPreviousDependencyValue(data[filter.scope]);
        setOpenBackdrop(true);
        setReferenceList([]);
    }
  }

  const handleClickOpen = async () => {
    setError();
    setOpen(true);
    if (referenceList.length > 0) return;
    setOpenBackdrop(true);
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
        setError(json.error);
      }
    }
    catch (err) {
      setOpenBackdrop(false);
      setError('Failed to load data!');
    }
  };

  const handleClose = () => {
    setOpen(false);
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
        <TextField
          error={errors}
          id="standard-end-adornment"
          sx={{ width: '100%' }}
          value={value}
          label={required ? (label + '*') : label}
          helperText={errors}
          InputProps={{
            endAdornment: <InputAdornment position="end">
              <IconButton type="button" sx={{ p: '10px' }} aria-label="search" onClick={handleClickOpen} className='reference-search-icon'>
                <SearchIcon />
              </IconButton></InputAdornment>,
            readOnly: true,
          }}
          variant="standard"
        />
      </div>
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

  // Avoid a layout jump when reaching the last page with empty rows.
  const emptyRows =
    page > 0 ? Math.max(0, (1 + page) * rowsPerPage - referenceList.length) : 0;

  const handleChangePage = (
    event,
    newPage
  ) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (
    event
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  return (
    <Hidden xsUp={!visible}>
      <div className="reference">
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
            <Table sx={{ minWidth: 500 }} aria-label="custom pagination table" className={error ? 'reference-hide' : ''}>
              <TableHead>
                <TableRow>
                  {headerTable.map((header) => (
                    <TableCell align="left" style={{ fontWeight: 'bold' }}>{header.toUpperCase()}</TableCell>
                  ))}
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? referenceList.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  : referenceList
                ).map((row) => (
                  <TableRow
                    key={row[options.key]}
                    hover
                    style={{ cursor: 'pointer' }}
                    onClick={() => {
                      if (row.name !== value) {
                        props.updateValue(row[options.key]);
                        setValue(row[options.label]);
                      }
                      handleClose();
                    }}
                  >
                    {renderValues(row)}
                  </TableRow>
                )
                )}
                {emptyRows > 0 && (
                  <TableRow style={{ height: 53 * emptyRows }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    rowsPerPageOptions={[5, 10, 25, { label: 'All', value: -1 }]}
                    colSpan={3}
                    count={referenceList.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    SelectProps={{
                      inputProps: {
                        'aria-label': 'rows per page',
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                    ActionsComponent={TablePaginationActions}
                    className={referenceList.length > 0 ? 'reference-table-pagination' : 'reference-table-pagination reference-hide'}
                  />
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        </DialogContent>
      </BootstrapDialog>
    </div>
    </Hidden>
  );
})