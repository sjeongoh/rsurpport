서버 port - 8080

개발 환경 세팅
- 개발 언이는 Java를 사용. (버전 8)
- Framework는 Spring-boot 모듈은 SDK1.8
- 빌드 도구는 Maven으로 설정

- persistence 는 JPA를 사용
- Db는 MariaDb를 사용
(접속정보 port 3306 , user rsurpport, password 1004 , database rsurpport)


[테이블 명세]

공지사항 테이블

|필드|설명|
|---|-----|
|id| 공지사항 id |
|content_txt|공지사항 내용텍스트|
|created_date|생성일자|
|created_user_id|생성 유저 아이디|
|modified_date|변경일자|
|modified_user_id|변경 유저 아이디|
|title|공지사항 제목|
|view_cnt|view 카운터 수|
|end_date|종료일자|



파일정보 테이블

|필드|설명|
|---|---|
|id|파일 id|
|content_type| 파일 타입|
|created_date|생성일자|
|file_name|파일 이름 (저장일자 붙여진 이름)|
|file_path|파일 저장 경로|
|file_size|파일 크기|
|parent_id|파일 상위글 id|
|target|저장 경로|
|original_file_name|원본 파일 이름|

사용자 테이블

|필드|설명|
|---|---|
|id|시퀀스 넘버|
|user_id|유저 아이디|
|user_name|유저 이름|



[인터페이스 정의]

모든 api는 RESTFul로 구현하였음

1.공지사항 등록 api 정의

||||
|---|---|---|
|URL|/api/v1/board/notices|
|Method|POST|
|||
|Request Parametar| |
|컬럼명|타입|필수여부|
|title|String|Y|
|contentTxt|String|Y|
|endDate|String|Y       (yyyy-MM-dd HH:mm:ss)|
|attachedFileList|MultipartFile|N|
|Response Parametar| |
|컬럼명|타입||
|code|int|
|message|String|
|result|object|

2.공지사항 수정 api 정의

||||
|---|---|---|
|URL|/api/v1/board/notices|
|Method|PUT|
|||
|Request Parametar| |
|컬럼명|타입|필수여부|
|id|long|Y|
|title|String|Y|
|contentTxt|String|Y|
|endDate|String|Y       (yyyy-MM-dd HH:mm:ss)|
|attachedFileList|MultipartFile|N|
|deletedFileIdList|long|N|
|Response Parametar| |
|컬럼명|타입||
|code|int|
|message|String|
|result|object|

3.공지사항 삭제 api 정의


||||
|---|---|---|
|URL|/api/v1/board/notices/{id}|
|Method|DELETTE|
|||
|Request Parametar| |
|컬럼명|타입|필수여부|
|id|long|Y|
|Response Parametar| |
|컬럼명|타입||
|code|int|
|message|String|
|result|object|


4.공지사항 전체 목록 조회 api


||||
|---|---|---|
|URL|/api/v1/board/notices|
|Method|GET|
|||
|Request Parametar| |
|컬럼명|타입|필수여부|
|page|int|Y|
|size|int|Y|
|searchText|String|N|
|Response Parametar| |
|컬럼명|타입||
|code|int|
|message|String|
|result|object|

5.공지사항 상세 조회 api


||||
|---|---|---|
|URL|/api/v1/board/notices/{id}|
|Method|GET|
|||
|Request Parametar| |
|컬럼명|타입|필수여부|
|||
|||
|||
|Response Parametar| |
|컬럼명|타입||
|code|int|
|message|String|
|result|object|