# VueJS - Spring MVC Liferay portlet

This is a Liferay Portlet based on PortletMVC4Spring as Backend and VuejS as Frontend.
Communication is done via REST Endpoint secured with Spring Security.

Tested on Liferay CE 7.3, VueJS 3 and PortletMVC4Spring 5.2.1

## Getting Started



### Prerequisites

You need a Liferay Server Up and Running. I tested it on Liferay CE 7.3.

### Installing

```
Step 1 : Checkout this project
Step 2 : In spring-app/build.gradle, update the path to Liferay's deploy folder (in Deploy Task).
Step 3 : Launch Deploy task
```

## Some important stuff

### Renaming the App

Search and replace "liferay-spring-vuejs" (without the quotes).

### Half-Portlet / Half-Servlet

This app relies on two servlets. The first one is the classic one, it handles all Portlet requests as any portlet would.
The second one is a Spring DispatcherServlet allowing us to declare REST Controllers as you would in a normal Spring MVC App.

We use the first one to serve the VueJS App. That app will next only request the second Servlet.

### WebContext

You can override your default servlet Webcontext path (which is your app name) by setting
```
Web-ContextPath = <YOUR_CONTEXT>
```
In spring-app/src/main/webapp/WEB-INF/liferay-plugin-package.properties

### How to request URLs

The base path is :
```
http(s)://<liferay_domain>/o/<WEB-CONTEXT>/api/services/<YOUR-ENDPOINT>
```
Of course you can change "/api/services" but you will have to report any change in Servlet and Spring Security Configuration.


### Spring Security

CORS and CSRF Protection are enabled by default.
The following rules are applied :
```
http.authorizeRequests()
		.antMatchers("/api/services/admin/**").hasRole("Administrator")
		.antMatchers("/api/services/**").hasRole("User")
		.antMatchers("/api/resources/**").authenticated()
		.antMatchers("/api/**").denyAll().and()
```
So by default you must be authenticated on the portal. You can change that if you wish.

By the portal, Authentication relies on Liferay's. The filter checks if Liferay is capable of retrieving a user from the HttpServletRequest.
If it can, then the user is considered "authenticated", his Liferay's Role are then transcripted as Spring's Authorities.
Liferay's User Interface is used as the Spring Principal so you can access it easily in your controllers.

IMPORTANT : if you plan to use CSRF, you must add an exception in Liferay's System properties : 
```
cookie.http.only.names.excludes=XSRF-TOKEN
```
Otherwise, your cookie will be httponly and you won't be able to access it with Javascript.
The file is located in portal-impl.jar so, if you're like me et don't want to rebuild Liferay, you can just overwrite an existing jar with the following command : 
```
jar uf portal-impl.jar system.properties
```
So get the original file on Github, add your property and override the shit out of it.

### CSS

You can user any CSS you want like you would with a VuejS vanilla App but your style sheets MUST BE scoped.
If not, your styles will conflict with Liferay's.

```
<style scoped lang="sass">
@import './node_modules/bootstrap/scss/bootstrap.scss'
@import '@/assets/settings.sass'

.btn-brand
  @include button-variant($primaryColor, darken($primaryColor, 7.5%), darken($primaryColor, 10%), lighten($primaryColor,5%), lighten($primaryColor, 10%), darken($primaryColor,30%))
  color: white
</style>
```

### Tests

TODO

## Built With

* [Liferay CE](https://github.com/liferay/liferay-portal)
* [VueJS](https://vuejs.org/) 
* [Thymeleaf](https://www.thymeleaf.org/) 
* [PortletMVC4Spring](https://github.com/liferay/portletmvc4spring)
* [Gradle](https://gradle.org/)
* [NPM](https://www.npmjs.com/)
* [Gradle-Node-Plugin](https://github.com/node-gradle/gradle-node-plugin)

## Authors

See the list of [contributors](https://github.com/genmllc/liferay-spring-vuejs/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

