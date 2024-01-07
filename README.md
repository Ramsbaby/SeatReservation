![Java_17](https://img.shields.io/badge/java-v1.17-red?logo=java)
![Spring_Boot](https://img.shields.io/badge/Spring_Boot-v3.2.1-green.svg?logo=spring)
![Spring_Boot](https://img.shields.io/badge/Gradle-v8.5-blue.svg?logo=gradle)

# 좌석예약 서비스

## Feature
* Java 17
* Sprint boot 3.2.1
* JPA, h2
* gradle 8.5

## 프로젝트 소개
좌석 예약 시스템

### 기능
1. 전 직원 근무형태 현황 조회 API
```
curl --location 'http://localhost:8080/api/staff/work-status?page=0&size=20'
```
2. 좌석 예약 API
```
curl --location 'http://localhost:8080/api/seats/reserve' \
--header 'Content-Type: application/json' \
--data '{
    "userId": 1,
    "seatId": 1
}'
```
3. 예약 취소 API
```
curl --location --request DELETE 'http://localhost:8080/api/seats/cancel?userId=1&seatId=1'
```

## Getting Started (실행방법)
* build
```cmd
./gradlew clean bootJar
```
* run(default port:8080)
```cmd
java -jar build/libs/SeatReservation-0.0.1-SNAPSHOT.jar
```

## 과제의 설계
1. 프로젝트 구동 시 기초 DATA들을 sql문을 통해 DB에 저장합니다.
2. 좌석과 유저 두 개의 객체를 기반으로 설계를 진행했습니다. 두 엔티티는 양방향 일대일 관계이입니다. 유저는 좌석이 할당되지 않을 수 있으나, 좌석은 생성될 경우 반드시 유저가 할당됩니다.
3. 좌석을 등록할 경우 비관적 락을 통해 다른 유저의 좌석예약을 막게 됩니다. 
4. 좌석을 예약할 경우 히스토리 테이블에 유저키와 좌석키를 기반으로 데이터가 저장됩니다. 
5. 좌석을 예약할 경우 히스토리 테이블에 저장된 날짜 데이터를 기반으로 같은 유저는 하루에 한번만 예약할 수 있는 제약조건을 추가했습니다.
6. 좌석을 예약할 경우 전체좌석수를 초과하는지 검사합니다.
7. 전체 현황 조회 시 좌석이 있는 유저는 좌석 id를 표현합니다.
```

