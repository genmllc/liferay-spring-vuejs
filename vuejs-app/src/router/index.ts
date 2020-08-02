import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  }
];

// DO NOT USE HISTORY MODE
// This app doesn't own the URL, Liferay does.
// Use either Hash ou Abstract MODE according to your needs.
// If your app is the only one on the page, you can use Hash mode
// Else if there are more than one SPA, use Abstract mode
const router = new VueRouter({
  mode: 'hash',
  routes
});

export default router;
