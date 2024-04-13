import React from 'react';
import Button from '@mui/material/Button';
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';

const DownloadButton = ({ translateFile, fileName }) => {

  const handleDownload = async () => {
    if (!translateFile) return;
    
    const link = document.createElement('a');

    link.href = URL.createObjectURL(translateFile);
    link.download = 'translate-' + fileName;
    link.click();
  }

  return (
    <Button
      variant="contained"
      startIcon={<CloudDownloadIcon />} 
      onClick={handleDownload}
      disabled={!translateFile} 
      style={{ marginBottom: '20px' }}
    >
      Download
    </Button>
  );
}

export default DownloadButton;