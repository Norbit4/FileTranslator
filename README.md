<h1 align="left"><b>File Translator</b></h1>

The File Translator project is a tool created to translate files with .yml and .yaml extensions. The main function of app is to translate the values contained in these files, leaving the keys unchanged. This means that the structure of the file remains unchanged, and only the content is modified.

<i>input:</i>

```yaml
key1: value1
key2: value2
```

<i>output:</i>

```yaml
key1: translated_value1
key2: translated_value2
```

<h5 align="left">Used Languages and Tools:</h5>

<p align="left">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,js,react,docker"/>
  </a>
</p>


<h2 align="left" id="content">Table of contents</h2>

- [Run](#run)
- [App view](#view)
- [Example usage](#usage)
- [Tests](#tests)

<h2 align="left" id="run">Run</h2>

1. Clone repository

```
git clone https://github.com/Norbit4/FileTranslator
```

2. Create account and get free [DeepL token](https://www.deepl.com/pl/your-account/keys)


3. Copy DeepL token and paste it to <b>docker-compose.yml </b>

4. Start app

```
docker-compose up
```

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="view">App view</h2>

![view-1](https://github.com/Norbit4/FileTranslator/assets/46154743/dbfbf44f-0273-4825-86f0-bd604b4f8870)

![view-2](https://github.com/Norbit4/FileTranslator/assets/46154743/e4ecc0fb-e6a0-4b1a-b09a-1b8e0fdf4b11)

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="usage">Example usage</h2>

<i>input (EN):</i>

```yaml
offer-command:
  no-permission: '&cYou do not have access to this command!'
  wrong-price: '&cThe price variable is invalid!'
  wrong-item: '&cYou can not offer this item!'
  success: '&aYou have successfully offered this item!'

market-gui:
  previous-page-icon:
    name: '&f&lPrevious Page'
    lore:
      - ''
      - '&eClick to browse!'
  next-page-icon:
    name: '&f&lNext Page'
    lore:
      - ''
      - '&eClick to browse!'
```
<i>output (PL):</i>

```yaml
offer-command:
  no-permission: '&cNie masz dostępu do tego polecenia!'
  wrong-price: '&cZmienna ceny jest nieprawidłowa!'
  wrong-item: '&cNie możesz zaoferować tego przedmiotu!'
  success: '&aPomyślnie zaoferowałeś ten przedmiot!'
market-gui:
  previous-page-icon:
    name: '&f&lPoprzednia strona'
    lore:
    - ''
    - '&eKliknij, aby przeglądać!'
  next-page-icon:
    name: '&f&lNastępna strona'
    lore:
    - ''
    - '&eKliknij, aby przeglądać!'
```

<div align="right"><a href="#content">Back to top</a></div>

<h2 align="left" id="tests">Tests</h2>

![tests](https://github.com/Norbit4/FileTranslator/assets/46154743/65a6b1ee-29b5-40e6-b8a8-75745ea49de8)


<div align="right"><a href="#content">Back to top</a></div>
