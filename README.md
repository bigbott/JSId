### JSId is a jQuery/Java user authentication framework (template). 
It supports all operations which would be expected from such a system: 
* register
* login
* verify email
* change password
* login with Facebook or Google
* logout

JSId consists of a jQuery plugin on the client side and a Java Servlet Filter on the server side. 

### Why?
As usual, I needed an authentication system for my project. I considered two options: 
* Amazon Cognito
* write something myself

Amazon Cognito was obviously a simpler option, but expensive.  $250 for the second 50,000 active users. 
Also, I have a DIY (RTW) attitude, so here we are. 

### [Demo](https://phoneparator.com/jsid/)
The demo is based on the [JRPC](https://github.com/bigbott/jrpc) demo and literally consists of two AJAX call examples from which one requires login. 

### Framework or template?
I tried to make it a framework, i.e. make it possible to use without any code changes. It kind of worked but given that system is complicated and the code was not so heavily tested, it should be considered a template anyway. 

### How is authentication performed? 
JSId uses custom encrypted JWT as access_token. By custom, I mean that we can generate any JSON we like, encrypt it with AES, and use the result string as access_token. 

Why encrypted and not signed? In the context of client-server authentication with no third party involved, signing has no sense. Symmetric AES encryption is more performant than signing and we got data secured for free. 

Why don't use the standard JWT library? Standardization is good, but standard JWT supposes signing, hence not so suitable. 

### Regular email/password authentication flow
1. After registration new user is created in DB with fields: email, password, id (among others).
2. After login server generates two tokens: 
    * access_token `{"id": 12345, "role": "regular_user" }` encrypted and Base64 encoded
    * refresh_token `12345` encrypted and Base64 encoded
    
    Those tokens are then sent to the client as cookies: access_token with short expiration, and refresh_token with a long expiration. 
    
    Also `user.isLoggedIn` flag is set to 1 (true) in DB. 
3. When a user tries to access login protected page (or AJAX route) JSId filter:

  * looks for access_token cookie. If present, the filter tries to decrypt it. In case of success, the filter renews the cookies and passes the 
         request to a servlet to process. 

  * If the access_token cookie is expired, the filter looks for refresh_token. If present, the filter tries to decrypt it, get id and find the related user. 
         If the user is found filter creates a new access_token cookie, renews the refresh_token cookie, and passes the request to the servlet (or another 
          filter if any). 

  *  If the refresh_token cookie is not there (expired) filter sends a response to the client with a `login_required` message.   
          Also, the `user.isLoggedIn` flag is set to false.  



### Facebook / Google authentication flow
1. When the user logged in for the first time with Facebook or Google new user is created in DB. The Password field is empty. The special field `user.isFacebookUser` or `user.isGoogleUser` is set to 1 (true). Access_token and refresh_token cookies are created and sent to the client.  
  Also `user.isLoggedIn` flag is set to 1 (true) in DB. 

 The rest of the authentication flow for Facebook/Google logged in user is actually the same as for the user logged in with email and password.

### Configuration
JSId Filter configured thru the filter's init parameters in `web.xml`. 
* `authStrategy` - possible values `all` or `none`. 
       `All` means that for all pages (routes, endpoints) login is required. 
       `None` means that login is not required for any page.
* `include` / 'exclude' 
        `Include` is used with `authStrategy:none`. For those pages (routes, endpoints) specified (comma separated) in `include` login is required. 
        `Exclude` is used with `authStrategy:all`. For those pages specified in `exclude` login is not required. 

For example, in demo application `authStrategy` is `none` and `include` is `EchoWords` which is pathInfo part of URL /jsid/demo/EchoWords. 

* `accessTokenMaxAge` specifies the expiration for access_token in seconds. 
* `refreshTokenMaxAge` specifies the expiration for refresh_token in seconds. This parameter actually specifies the session duration, i.e. time of user 
                       inactivity after which the user will be forced to re-login. 

* `userManagerClass` - full name of a class that implements the `IUserManager` `interface` and manages user basic operations: create, get, update. 
                       The current value in the demo application -  bigbott.demo.InMemoryUserManager. In a real application, it should be replaced with 
                       one of the following: MongoUserManager, OracleUserManager, or MySqlUserManager.  
* `mailClientClass`  -  full name of a class that implements the `IMailClient` `interface` and sends verify email and change passwords emails to the 
                       user. The current value in the demo application - bigbott.demo.GoogleMailClient. To make this class work with your google account 
                       you should enable two-step identification and generate Application Specific password. [https://stackoverflow.com/questions/16601405/how-to-access-gmail-with-2-step-verification](https://stackoverflow.com/questions/16601405/how-to-access-gmail-with-2-step-verification) 

* `tokenGeneratorClass` - full name of a class that implements the `ITokenGenerator` `interface` and generates access and refresh tokens. 

### Getting started
The best way to start with JSIdis to download the JSId-example project from github. If you don't use Netbeans your IDE should have 
"Create a project from existing sources" option. Otherwise, please, change IDE or just copy-paste the code manually. 
JSId-example project contains full demo code (client and server). 

### More simple Java frameworks
**[JPaS](https://github.com/bigbott/jpas)** - stands for Java Pages Simplified. It is a simple web mini framework intended to create multi-page applications, i.e. applications that know
       how to process GET requests, created by the user clicking a link or bookmark. It does not use any templates e.g. JSP, Velocity, Thymeleaf, etc.
       Instead, it parses plain HTML at server start time using JSoup and modifies them thru Java API at request time. 

**[JRPC](https://github.com/bigbott/jrpc)** - JRPC is a Java JSON-RPC over HTTP web(*) mini framework. 
           *It can be used for creating back-ends for SPA web apps, mobile apps, desktop apps - any app that sends/receives JSON over HTTP.  

### License
MIT license. 
 
