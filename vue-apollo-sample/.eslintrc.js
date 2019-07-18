module.exports = {
    root: true,
  
    env: {
      node: true,
    },
  
    'extends': [
      'plugin:vue/essential',
      '@vue/standard',
    ],
  
    rules: {
      'graphql/template-strings': [
        'error',
        {
          env: 'literal',
          projectName: 'app',
        },
      ],
      'space-before-function-paren': 0,
      'indent': 0,
      'comma-dangle': 'off',
      'semi': 0,
      'quotes': 'off',
      'no-tabs':'off'
    },
  
    parserOptions: {
      parser: 'babel-eslint',
    },
  
    plugins: [
      'graphql',
    ],
  }
  