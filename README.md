# 쇼핑몰 카테고리

## 구현 스팩

- Java 17
- Spring Boot 2.7.8
- Spring Data JPA
- H2

## How to run?

### 1) 도커 설치

```
sudo apt-get upgrade
sudo apt-get update
sudo apt -y install docker.io
```

### 2) 이미지 다운로드

```
sudo docker pull ssok1217/mall-category
```

### 3) 컨테이너 실행

`5000`포트로 기본 설정하였습니다.

```
docker run -d --name mall-category -p 5000:8080 ssok1217/mall-category
```

## Swagger

컨테이너 실행 시 입력한 포트로 접속 가능합니다.

http://localhost:5000/swagger-ui.html#/