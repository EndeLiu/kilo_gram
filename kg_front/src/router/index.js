import Vue from 'vue'
import Router from 'vue-router'
import Canvas from "../components/Canvas";

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'canvas',
      component: Canvas
    }
  ]
})
