<template>
  <div id="app">
    <img src="./assets/logo.png">
    <div>
      <Post/>
    </div>
  </div>
</template>

<script>
import Post from './components/Post.vue'

export default {
  name: 'app',
  components: {
    Post
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
