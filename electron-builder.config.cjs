const config = {
  appId: 'com.zichan.desktop',
  productName: 'Zichan Desktop',
  directories: {
    output: 'release'
  },
  files: ['dist/**/*', 'dist-electron/**/*'],
  extraMetadata: {
    main: 'dist-electron/main/index.js'
  },
  mac: {
    target: ['dmg', 'zip']
  },
  win: {
    target: ['nsis', 'zip']
  },
  nsis: {
    oneClick: false,
    perMachine: false,
    allowToChangeInstallationDirectory: true
  }
};

module.exports = config;
