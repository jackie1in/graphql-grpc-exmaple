console.log(`当前graphql网关访问地址:${process.env.VUE_APP_GRAPHQL_HTTP}`)
module.exports = {
    pluginOptions: {
        graphqlMock: false,
        apolloEngine: false,
    },
    devServer: {
        port: 8081,
    }
}
