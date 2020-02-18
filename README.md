# spring-security-jwt

This is a demo application to show how JWT(Jason Web Token) authenticate requests.

# How To Use
Do a POST request to below url with AuthenticationRequest in body:
http://localhost:8080/authenticate

AuthenticationRequest:

{
	"userName": "user",
	"password": "user"
}

In response jwt token will be returned.

Do a GET to below url:
http://localhost:8080/hello
Below message will be returned:
 "message": "Access Denied",
  
Now post same request with Authorization: Bearer <jwt> in headers
http://localhost:8080/hello
If Hello World!! returned, jwt authenticated the request.
