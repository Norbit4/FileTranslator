import * as React from 'react';
import Button from '@mui/material/Button';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import { styled } from '@mui/material/styles';

export default function ({ fileUploaded, fileName, setFileContent, setFileName, setFileUploaded, setTranslateFile}) {

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

    const handleFileChange = (event) => {
        const file = event.target.files[0];
    
        if (file) {
          setTranslateFile(null);
          setFileName(file.name);
          setFileUploaded(true);
    
          const reader = new FileReader();
          reader.onload = (e) => {
            const content = e.target.result;
            setFileContent(content);
          };
    
         reader.readAsText(file);
         return;
        } 

        setFileName('');
        setFileUploaded(false);
        setTranslateFile(null);
    };

  return (
    <Button 
        component="label" 
        variant="contained" 
        startIcon={<CloudUploadIcon />} 
        style={{ 
          // backgroundColor: fileUploaded ? 'green' : 'gray', 
          marginBottom: '20px',
          marginTop: '20px' }}>
        
        {fileUploaded ? fileName : 'Upload file'}
        <VisuallyHiddenInput 
            type="file" 
            onChange={handleFileChange} 
        />
    </Button>
  );
}