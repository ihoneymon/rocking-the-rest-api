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
$ ./gradlew tomcatRun
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

#### 사용된 것들
* [gradle](http://www.gradle.org/) 1.10
* [Spring Framework](http://projects.spring.io/spring-framework/) 4.0.1.RELEASE
* [Hibernate ORM](http://hibernate.org/orm/) 4.3.1.Final
	* [Hibernate Validator](http://hibernate.org/validator/) 5.0.3.Final
* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/) 1.4.3.RELEASE
* [Spring Data Redis](http://projects.spring.io/spring-data-redis/) 1.1.1.RELEASE
* [H2Database](http://www.h2database.com/html/main.html), [BoneCP](http://jolbox.com/), [Lombok](http://projectlombok.org/), [Jackson](https://github.com/FasterXML/jackson), [Guava](http://code.google.com/p/guava-libraries/)