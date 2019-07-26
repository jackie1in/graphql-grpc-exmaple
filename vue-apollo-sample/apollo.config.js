// apollo.config.js
module.exports = {
  client: {
    service: {
      name: 'vue-apollo-sample',
      // GraphQL API 的 URL
      url: 'http://localhost:4001/graphql',
    },
    // 通过扩展名选择需要处理的文件
    includes: [
      'src/**/*.vue',
      'src/**/*.js'
    ]
  }
}
