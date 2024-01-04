import React, { useState } from 'react';
import { Button } from '@mui/material';
import './App.css';
import { Container } from '@mui/system';
import { styled } from '@mui/material/styles';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CustomizedSnackbars from './components/Info';

const VisuallyHiddenInput = styled('input')({
  clip: 'rect(0 0 0 0)',
  clipPath: 'inset(50%)',
  height: 1,
  overflow: 'hidden',
  position: 'absolute',
  bottom: 0,
  left: 0,
  whiteSpace: 'nowrap',
  width: 1
});

function App() {
  const [fileUploaded, setFileUploaded] = useState(false);
  const [fileName, setFileName] = useState('');
  const [fileContent, setFileContent] = useState('');

  const handleFileChange = (event) => {
    const file = event.target.files[0];

    if (file) {
      setFileName(file.name);
      setFileUploaded(true);

      const reader = new FileReader();
      reader.onload = (e) => {
        const content = e.target.result;
        setFileContent(content);
      };

      // Odczytuje zawartość pliku jako tekst
     reader.readAsText(file);
    } else {
      setFileName('');
      setFileUploaded(false);
    }
  };

  return (
    <Container>
      <Button component="label" variant="contained" startIcon={<CloudUploadIcon />} style={{ backgroundColor: fileUploaded ? 'green' : 'gray' }}>
        {fileUploaded ? fileName : 'Upload file'}
        <VisuallyHiddenInput type="file" onChange={handleFileChange} />
      </Button>
      <CustomizedSnackbars />
    </Container>
  );
}

export default App;
