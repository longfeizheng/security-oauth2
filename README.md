## Spring Security OAuth2
> OAuth 是一个开放标准，允许用户让第三方应用访问该用户在某一网站上存储的私密的资源（如照片，视频，联系人列表），而不需要将用户名和密码提供给第三方应用。OAuth允许用户提供一个令牌，而不是用户名和密码来访问他们存放在特定服务提供者的数据。每一个令牌授权一个特定的网站在特定的时段内访问特定的资源。这样，OAuth让用户可以授权第三方网站访问他们存储在另外服务提供者的某些特定信息。更多`OAuth2`请参考[理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)

## 项目准备

1. 添加依赖
	```xml
	<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-security</artifactId>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
			</dependency>
			<dependency>
				<groupId>org.springframework.security.oauth</groupId>
				<artifactId>spring-security-oauth2</artifactId>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
			</dependency>
	```
2. 配置认证服务器
	```java
	@Configuration
	@EnableAuthorizationServer//是的，没做，就这么一个注解
	public class MerryyouAuthorizationServerConfig {

	}
	```
3. 配置资源服务器
	```java
	@Configuration
	@EnableResourceServer//咦，没错还是一个注解
	public class MerryyouResourceServerConfig {
	}
	```
4. 配置`application.yml`客户端信息（不配置的话，控制台会默认打印clientid和clietSecret）

	```shell
	security:
	  oauth2:
		client:
		  client-id: merryyou
		  client-secret: merryyou
	```
5. 定义`MyUserDetailsService`
	 ```java
	@Component
	public class MyUserDetailsService implements UserDetailsService {

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			return new User(username, "123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
		}
	}
	```
6. 添加测试类`SecurityOauth2Test`(用户名密码模式)
	```java
	@RunWith(SpringRunner.class)
	@SpringBootTest
	@Slf4j
	public class SecurityOauth2Test {
		//端口
		final static long PORT = 9090;
		//clientId
		final static String CLIENT_ID = "merryyou";
		//clientSecret
		final static String CLIENT_SECRET = "merryyou";
		//用户名
		final static String USERNAME = "admin";
		//密码
		final static String PASSWORD = "123456";
		//获取accessToken得URI
		final static String TOKEN_REQUEST_URI = "http://localhost:"+PORT+"/oauth/token?grant_type=password&username=" + USERNAME + "&password=" + PASSWORD+"&scope=all";
		//获取用户信息得URL
		final static String USER_INFO_URI = "http://localhost:"+PORT+"/user";

		@Test
		public void getUserInfo() throws Exception{
			RestTemplate rest = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add( "authorization", "Bearer " + getAccessToken() );
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			// pay attention, if using get with headers, should use exchange instead of getForEntity / getForObject
			ResponseEntity<String> result = rest.exchange( USER_INFO_URI, HttpMethod.GET, entity, String.class, new Object[]{ null } );
			log.info("用户信息返回的结果={}",JsonUtil.toJson(result));
		}

		/**
		 * 获取accessToken
		 * @return
		 */
		private String getAccessToken(){
			RestTemplate rest = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType( MediaType.TEXT_PLAIN );
			headers.add("authorization", getBasicAuthHeader());
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<OAuth2AccessToken> resp = rest.postForEntity( TOKEN_REQUEST_URI, entity, OAuth2AccessToken.class);
			if( !resp.getStatusCode().equals( HttpStatus.OK )){
				throw new RuntimeException( resp.toString() );
			}
			OAuth2AccessToken t = resp.getBody();
			log.info("accessToken={}",JsonUtil.toJson(t));
			log.info("the response, access_token: " + t.getValue() +"; token_type: " + t.getTokenType() +"; "
					+ "refresh_token: " + t.getRefreshToken() +"; expiration: " + t.getExpiresIn() +", expired when:" + t.getExpiration() );
			return t.getValue();

		}

		/**
		 * 构建header
		 * @return
		 */
		private String getBasicAuthHeader(){
			String auth = CLIENT_ID + ":" + CLIENT_SECRET;
			byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
			String authHeader = "Basic " + new String(encodedAuth);
			return authHeader;
		}
	}
	```

授权码模式效果如下：

授权链接：
[http://localhost:9090/oauth/authorize?response_type=code&client_id=merryyou&redirect_uri=http://merryyou.cn&scope=all](http://localhost:9090/oauth/authorize?response_type=code&client_id=merryyou&redirect_uri=http://merryyou.cn&scope=all "http://localhost:9090/oauth/authorize?response_type=code&client_id=merryyou&redirect_uri=http://merryyou.cn&scope=all")

[![https://raw.githubusercontent.com/longfeizheng/longfeizheng.github.io/master/images/security/spring-security-oauth201.gif](https://raw.githubusercontent.com/longfeizheng/longfeizheng.github.io/master/images/security/spring-security-oauth201.gif "https://raw.githubusercontent.com/longfeizheng/longfeizheng.github.io/master/images/security/spring-security-oauth201.gif")](https://raw.githubusercontent.com/longfeizheng/longfeizheng.github.io/master/images/security/spring-security-oauth201.gif "https://raw.githubusercontent.com/longfeizheng/longfeizheng.github.io/master/images/security/spring-security-oauth201.gif")

测试类打印`accessToken`信息

```shell
2018-01-20 18:16:49.900  INFO 16136 --- [           main] cn.merryyou.security.SecurityOauth2Test  : accessToken={
  "value": "8e5ea72c-d153-48f5-8ee7-9b5616fc43dc",
  "expiration": "Jan 21, 2018 6:10:25 AM",
  "tokenType": "bearer",
  "refreshToken": {
    "value": "7adfefec-c80c-4ff4-913c-4f161c47fbf1"
  },
  "scope": [
    "all"
  ],
  "additionalInformation": {}
}
```

[Spring Security系列](https://longfeizheng.github.io/categories/#Security)
