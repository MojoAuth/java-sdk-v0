 <p align="center">
  <a href="https://www.mojoauth.com">
    <img alt="MojoAuth" src="https://mojoauth.com/assets/images/logo.svg" width="200" />
  </a>
</p>

<h1 align="center">
  MojoAuth JAVA SDK
</h1>

MojoAuth provides you a robust and secure authentication mechanism for your web & mobile apps with a few lines of code and a delightful user experience.

# Installing
## Manual Installation
1.  Right click on your project in eclipse.
2.  Select **Build Path**.
3.  Click on **Configure Build Path**.
4.  Click on **Libraries** and select **Add External JARs**.
5.  Select the jar file from your computer.
6.  Hit and **Apply** button.

## Automatic Installation
[MojoAuth](https://www.mojoauth.com) is now using Maven. At present the jars **are** available from a public [maven]( ) repository.

Use the following dependency in your project:

```
<dependency>
  <groupId>com.mojoauth.sdk</groupId>
  <artifactId>java-sdk</artifactId>
  <version>1.1.0</version>
</dependency>

```

The jars are also available [here](). Select the directory for
the latest version and download the jar files.

## Documentation

Java Library
=====

-----

>**Disclaimer**
>This library is meant to help you with a quick implementation of the MojoAuth platform and also to serve as a reference point for the MojoAuth API. Keep in mind that it is an open source library, which means you are free to download and customize the library functions based on your specific application needs.



## Installation

This documentation presumes you have worked through java and eclipse libararies. API Details on this can be found in the [Document](https://mojoauth.com/docs).

Use the following dependency in your project:

You can also compile the source by running the following commands. This will generate the javadocs in java-sdk/target/apidocs


```
$ git clone https://github.com/MojoAuth/java-sdk.git
```
```
$ cd java-sdk
```
`$ mvn install` # Requires maven, download from http://maven.apache.org/download.html
  
`$ mvn dependency:copy-dependencies`   # This will generate all dependencies here: java-sdk/target/dependency
The jars are also available at [Maven](https://mvnrepository.com/artifact/io.mojoauth.sdk/java-sdk).

Select the directory for the latest version and download the jar files.

## Initialize SDK
Before using the SDK, you must initialize the SDK with the help of following code:
API Key and secret of your MojoAuth site. You can get one from [here](http://mojoauth.com/dashboard)

```
MojoAuthSDK.Initialize init = new MojoAuthSDK.Initialize();
init.setApiKey("___MOJOAUTH_APIKEY___");
```
## Quickstart Guide


### Verify Access Token


	  This function is used to verify jwt token.
	  ```
	public static void check() {
	String token = "<token>";
	Jwks jwks = new Jwks();
	jwks.verifyAccessToken(token, new AsyncHandler<VerifyTokenResponse>() {
		
		@Override
		public void onSuccess(VerifyTokenResponse data) {
			System.out.println(data.getAccessToken());
			System.out.println(data.getIsValid());
		}
		
		@Override
		public void onFailure(ErrorResponse errorcode) {
			System.out.println(errorcode.getMessage());
			System.out.println(errorcode.getDescription());
		}
	});
	}

	  ```
 
  	  
## Reference Manual

Please find the reference manual [here](http://mojoauth.com/docs).

## How to contribute

We appreciate all kinds of contributions from anyone, be it finding an issue or writing a blog.

Please check the [contributing guide](CONTRIBUTING.md) to become a contributor.

## License

For more information on licensing, please refer to [License](LICENSE)
