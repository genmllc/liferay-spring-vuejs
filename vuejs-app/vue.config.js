module.exports = {
  // Liferay seems to preempt every css GET request, so keeping it into the js files is better for our use case.
  css: {
    extract: false,
  },
  // The web context must be the same as the same declared in spring-app/src/main/webapp/WEB-INF/liferay-plugin-package.properties
  publicPath: process.env.NODE_ENV === 'production' ? '/o/liferay-spring-vuejs/api/resources/' : '/'
}