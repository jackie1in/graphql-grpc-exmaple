import Vue from 'vue'
import VueApollo from 'vue-apollo'
import {
  HttpLink
} from 'apollo-link-http'
import {
  InMemoryCache
} from 'apollo-cache-inmemory'
import ApolloClient from 'apollo-client'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

const httpLink = new HttpLink({
  uri: process.env.VUE_APP_GRAPHQL_HTTP || 'http://localhost:4000/graphql'
})

const cache = new InMemoryCache()

const apolloClient = new ApolloClient({
  link: httpLink,
  cache,
  connectToDevTools: true
})

Vue.use(VueApollo)

// NProgress Configuration
NProgress.configure({
  showSpinner: false
});

const apolloProvider = new VueApollo({
  defaultClient: apolloClient,
  // 查看所有查询的加载状态
  // 详见 '智能查询 > 选项 > watchLoading'
  watchLoading(isLoading, countModifier) {
    if (isLoading && countModifier === 1) {
      NProgress.start();
    } else {
      NProgress.done();
    }
  }
})

export default apolloProvider
