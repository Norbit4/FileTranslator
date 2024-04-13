import React, { useState } from 'react';
import { Container } from '@mui/system';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';

import TranslateButton from './components/TranslateButton';
import DownloadButton from './components/DownloadButton';
import UploadButton from './components/UploadButton';
import LanguageSelector from './components/LanguageSelector';

function App() {
  const [fileUploaded, setFileUploaded] = useState(false);
  const [fileName, setFileName] = useState('');
  const [fileContent, setFileContent] = useState('');
  const [translateFile, setTranslateFile] = useState(null);
  const [language, setLanguage] = useState('PL');

  return (
    <Container>
        <Paper elevation={5} style={{ padding: '40px', marginTop: '30px', backgroundColor : '#f9f9f9',  display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
              <UploadButton fileName={fileName} fileUploaded={fileUploaded} setFileContent={setFileContent} setFileName={setFileName} setFileUploaded={setFileUploaded} setTranslateFile={setTranslateFile} />

              <DownloadButton fileName={fileName} translateFile={translateFile}/>
              
              <Grid container spacing={2} justifyContent="center" marginTop={2} alignItems="center">
                <Grid item>
                  <LanguageSelector language={language} setLanguage={setLanguage}/>
                </Grid>
                <Grid item>
                  <TranslateButton language={language} fileUploaded={fileUploaded} fileName={fileName} fileContent={fileContent} setTranslateFile={setTranslateFile}/>
              </Grid>
            </Grid>
        </Paper>
    </Container>
  );
}

export default App;
