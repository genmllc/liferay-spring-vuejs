module.exports = {
 // Liferay seems to preempt every css GET request, so keeping it into the js files is better for our use case.
  css: {
    extract: false,
  },
  // TODO : is there a way to inject that path at compile time ?
  publicPath: process.env.NODE_ENV === 'production'
    ? '/o/liferay-spring-vuejs/api/resources/'
    : '/'
}