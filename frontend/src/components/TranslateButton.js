import React, { useState } from 'react';
import Button from '@mui/material/Button';
import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { Box } from '@mui/material';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const TranslateButton = ({ language, fileUploaded, fileName, fileContent, setTranslateFile }) => {
  const [open, setOpen] = useState(false);
  const [serverity, setServerity] = useState('');
  const [text, setText] = useState('');

  const [loading, setLoading] = useState(false);

  const States = {
    FILE_NOT_EXIST: "file_not_exist",
    WRONG_FILE: "wrong_file",
    SUCCESS: "success",
    FILE_TRANSFER_ERROR: "file_transfer_error",
    FILE_TRANSLATE_ERROR: "file_translate_error"
  }

  const handleClick = () => {
    if (!fileUploaded){
      setState(States.FILE_NOT_EXIST)
      return;
    } 

    if (loading) return;
    
    let file = fileName.split('.').pop();

    if (file !== 'yml' && file !== 'json') {
      setState(States.WRONG_FILE)
      return;
    }

    handleDownload();
  };

  const setState = (state) => {
    switch (state) {
      case States.FILE_NOT_EXIST:
        setServerity('error');
        setText('File not added!');
        setOpen(true);
        break;
      case States.WRONG_FILE:
        setServerity('warning');
        setText('Incorrect file! Acceptable formats .yml .json');
        setOpen(true);
        break;
      case States.SUCCESS:
        setServerity('success');
        setText('Correctly translated file!');
        setOpen(true);
        break;
      case States.FILE_TRANSFER_ERROR:
        setServerity('error');
        setText('Error during file transfer!');
        setOpen(true);
        break; 
        case States.FILE_TRANSLATE_ERROR:
          setServerity('error');
          setText('Error during file translation!');
          setOpen(true);
          break; 
      default:
        setOpen(false);
        break;
    }
  }

  const handleDownload = async () => {
    setLoading(true);
    try {
      const formData = new FormData();
      formData.append('file', new Blob([fileContent], { type: 'text/yaml' }), fileName);
      formData.append('language', language);
      
      const response = await fetch('http://localhost:8080/api/translate', {
        method: 'POST',
        body: formData,
      });

      setLoading(false);

      console.log(response);

      if (response.ok) {
        const blob = await response.blob();

        setTranslateFile(blob);
        setState(States.SUCCESS)
      } else {
        setState(States.FILE_TRANSFER_ERROR)
        console.error('Error during file transfer');
      }
    } catch (error) {
      setState(States.FILE_TRANSLATE_ERROR)
      setLoading(false);
      console.error('Error while processing a file: ', error);
    }
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') return;
    
    setState()
  };

  return (
    <Box >
      <Button 
        variant="outlined" 
        disabled={!fileUploaded || loading}
        onClick={handleClick}>
        Translate
      </Button>
      
      <Snackbar 
        open={open} 
        autoHideDuration={3000} 
        onClose={handleClose}>

        <Alert 
          onClose={handleClose} 
          severity={serverity}
          sx={{ width: '100%' }}>

          {text}
        </Alert>
      </Snackbar>
    </Box>
  );
}

export default TranslateButton;