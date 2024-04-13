import * as React from 'react';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';

const languages = ['PL', 'EN', 'DE', 'FR', 'ES', 'IT', 'NL', 'RU']; 

const LanguageSelector = ({ setLanguage }) => {


  const handleChange = (lan) => {
    setLanguage(lan);
  };

  return (
    <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
          <InputLabel id="demo-select-small-label">Lan</InputLabel>
          <Select
            labelId="demo-select-small-label"
            id="demo-select-small"
            label="Age"
            defaultValue={"PL"}
            onChange={e => handleChange(e.target.value)}
          >
            {languages.map((lan) => ( <MenuItem value={lan}>{lan}</MenuItem>))}
          </Select>
        </FormControl>
  
  );
}

export default LanguageSelector;