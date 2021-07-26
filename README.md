# Invest

## API스펙
### 전체 투자 상품 조회 API 
### /product (GET) 

#### 응답
|구분|파라미터|설명|
|---|---|---|
|응답(json)|code|응답코드|
|응답(json)|messege|응답메시지|
|응답(json)|productList|상품리스트|
|응답(json)|- productId|상품id|
|응답(json)|- title| 제목|
|응답(json)|- totalAmount| 총모집 금액|
|응답(json)|- startedAt| 모집시작일|
|응답(json)|- finishedAt|모집종료일|
|응답(json)|- invest_count|현재 투자수|
|응답(json)|- curr_invest_amount|현재 모집액|

### 투자하기 API 
### /invest (POST) 

#### 요청
|구분|파라미터|설명|
|---|---|---|
|HttpHeader|X-USER-ID|사용자식별값|
|요청(json)|productId|상품id|
|요청(json)|invest_amount|투자금액|
#### 응답
|구분|파라미터|설명|
|---|---|---|
|응답(json)|code|응답코드|
|응답(json)|messege|응답메시지|

### 나의 투자상품 조회 API 
### /myinvest (GET) 

#### 요청
|구분|파라미터|설명|
|---|---|---|
|HttpHeader|X-USER-ID|사용자식별값|
#### 응답
|구분|파라미터|설명|
|---|---|---|
|응답(json)|code|응답코드|
|응답(json)|messege|응답메시지|
|응답(json)|myinvest| 나의 투자리스트|
|응답(json)|- investId|투자id|
|응답(json)|- userId| 유저id|
|응답(json)|- productId| 상품id|
|응답(json)|- amount| 투자금액|
|응답(json)|- regDtm|투자일|
|응답(json)|- title|제목|
|응답(json)|- totalAmount|총모집 금액|


## 문제해결전략
1. 투자하기 API에 동시접속이 몰릴때 총모집금액을 넘지 않도록 동시성 제어가 필요
   - 배민 등의 재고관리로 사용하는 Redis를 사용해보자 : Redis의 opsForSet 자료구조를 사용하여 가능한가? 
   - opsForSet 은 중복을 허용하지 않고 size를 제공하기 때문에 1 transactiop 당 1개의 add로 사용할 경우 재고관리 등에 적합하나 금액컷에는 적절하지 않음. (이 검토에서 많은 시간 소요)
   - 그러면 단일 스레드에서 메시지큐를 사용하여 동시처리에 순서를 부여해주면 문제가 해결 가능한가?
   - 카프카 설치후 투자하기 service부분을 컨슈머로 지정하여 Jmeter로 대량테스트 : 성공 
   
2. X-USER-ID가 헤더로 넘어온다. 더 있을 다른 API도 이런식으로 넘어온다고 보고 AOP를 써서 공통으로 빼자

3. 전체상품API 와 나의투자상품보기API 는 product 와 invest를 entity에서 조인한다해도 count, sum은 구현하기 어려우므로 Formula를 써서 서브쿼리로 구현, native-query로 써서 List<Object[]> 타입 재가공보다는 나을 것 같다.

4. SpringBootTest를 써서 테스트코드를 돌려보는데 테스트가 완료되기 전에 DB커넥션이 종료되고 투자하기 commit이 실패가 난다. 
   - 아무래도 SpringBootTest를 controller 단위로 하기 때문에 KafkaListener로 들어와서 처리되는 부분까지 기다려주지 않는 것 같다. 
   - datasource url 부분에 DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE 를 추가하여 db_close를 지연시키니 해결.
   - 이렇게 해도 테스트코드에서는 controller 들이 먼저 실행, KafkaListener는 나중에 처리된다.  이부분은 시간이 없어서 해결불가, jmeter로 개별 테스트 하는게 확실할것 같다.

5. 카프카를 써서 순서를 부여함으로써 동시성 문제 해결은 했지만 사용자가 늘어나서 scale-out 을 해야 할 경우 서버나 컨테이너는 scale-out 가능하지만 카프카를 클러스터로 사용하고 리스너(컨슈머)를 늘려야 할 경우 순서는 무용지물이 된다.
   이 부분은 레디스 분산 락을 통해 한번 더 개선해야겠음.  이걸 할때는 왜 못찾았지?

## 프로젝트 빌드 및 실행 방법
### 개발 환경 
- JAVA 1.8
- Eclipse Java EE IDE for Web Developers Photon ( STS Plugin )
- Dependencies : spring-boot, JPA, H2, lombok ..
- kafka_2.13-2.8.0
- apache-jmeter-5.4.1

### 빌드 및 실행 방법
1. Git Repositoires 에 https://github.com/kwonpc/Invest.git 등록 후 Import Projects 로 내려받기
2. 내려받은 프로젝트를 Maven 빌드
3. 설치된 카프카를 아래와 같이 기동해준다
  - bin\windows\zookeeper-server-start.bat config/zookeeper.properties
  - bin\windows\kafka-server-start.bat config/server.properties
4. Run AS > Junit Test 진행하여 테스트 결과 확인

