REST API의 설계와 구축(A부터 I까지)
====================

#### 발표요약
* 발표일시: 2014/02/22(토) 12:30~13:15
* 발표장소: 세종대학교 컨벤션홀
* 발표제목: REST API의 설계와 구축(A부터 I까지)
* 예제프로젝트: [https://github.com/ihoneymon/rocking-the-rest-api](https://github.com/ihoneymon/rocking-the-rest-api)

    > REST API 에 대한 이야기(A부터 I까지). 모바일 시대에 들어서면서 다시한번 개발자들의 관심을 받기 시작한 REST API. 그 근간이 되는 리소스에 대한 이야기부터 REST API 설계, 스프링을 이용한 구현, 테스트와 문서화에 대해 이야기한다.

#### 실행방법
```
$ git clone https://github.com/ihoneymon/rocking-the-rest-api
$ cd rocking-the-rest-api
$ ./gradlew tomcatRun -Dspring.profiles.active=local
```

#### API 문서
* [I/O Docs](https://github.com/ihoneymon/iodocs)
	* nodejs 가 설치되어 있어야 함
* 실행방법
```
$ git clone https://github.com/ihoneymon/iodocs
$ npm install
$ node app &
```

#### REST API의 실행방법
> View가 전혀 사용되지 않았기 때문에, 실제로 내장 톰캣 실행후 안내되는 경로로 접근시에 볼 수 있는 내용이 없다. 정상동작하고 있는지 확인하려면 jQuery 를 이용해서 ajax 통신으로 확인해야 한다.

```javascript
$.ajax({
	url: "http://localhost:8080/rocking-the-rest-api/users",
    method: "get",
    dataType: "json",
    contentType: "application/json",
    success: function(data) {console.log(data)},
    error: function(data) {console.log(data)}
});
```
내가 REST API를 만든 후에 테스트의 목적으로 실행하는 스크립트의 기본적인 형태다. 자바 객체의 XML 처리는 JSON에 비해서 약간 더 귀찮은 것들이 많은 탓에 xml 데이터는 크게 사용하지 않는다. 프론트엔드쪽에서도 XML 데이터 처리가 더 효과적이지 않은 이상은 JSON이 더 편하지 않을까?

#### I/O docs를 이용해서 REST API 문서화를 할 수 있는지 살펴보자.
* [http://api.ihoney.pe.kr:3000](http://api.ihoney.pe.kr:3000)
	* 이번 발표에서 소개한 I/O docs 를 실제로 'Rocking the REST API'에 맞춰 작성봤다. 

#### 사용된 것들
* [gradle](http://www.gradle.org/) 1.10
* [Spring Framework](http://projects.spring.io/spring-framework/) 4.0.1.RELEASE
* [Hibernate ORM](http://hibernate.org/orm/) 4.3.1.Final
	* [Hibernate Validator](http://hibernate.org/validator/) 5.0.3.Final
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/) 1.4.3.RELEASE
* [Spring Data Redis](http://projects.spring.io/spring-data-redis/) 1.1.1.RELEASE
* [H2Database](http://www.h2database.com/html/main.html), [BoneCP](http://jolbox.com/), [Lombok](http://projectlombok.org/), [Jackson](https://github.com/FasterXML/jackson), [Guava](http://code.google.com/p/guava-libraries/)