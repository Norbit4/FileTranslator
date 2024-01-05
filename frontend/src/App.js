import React, { useState } from 'react';
import { Container } from '@mui/system';

import TranslateButton from './components/TranslateButton';
import DownloadButton from './components/DownloadButton';
import UploadButton from './components/UploadButton';

function App() {
  const [fileUploaded, setFileUploaded] = useState(false);
  const [fileName, setFileName] = useState('');
  const [fileContent, setFileContent] = useState('');
  const [translateFile, setTranslateFile] = useState(null);

  return (
    <Container>
          {/* <Stack spacing={2} > */}
       <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>

            <UploadButton fileName={fileName} fileUploaded={fileUploaded} setFileContent={setFileContent} setFileName={setFileName} setFileUploaded={setFileUploaded} setTranslateFile={setTranslateFile} />

            <DownloadButton fileName={fileName} translateFile={translateFile}/>

            <TranslateButton fileUploaded={fileUploaded} fileName={fileName} fileContent={fileContent} setTranslateFile={setTranslateFile}/>
          {/* </Stack> */}
        </div>
    </Container>
  );
}

export default App;
