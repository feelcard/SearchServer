## 외부 API 연동을 이용한 검색 API 서버 구현

### 실행 jar 다운로드 : https://github.com/feelcard/SearchServer/raw/master/build/libs/SearchServer-1.0-SNAPSHOT.jar

1. 검색 API

- 엔드포인트: `GET /api/search`

- 요청 파라미터:

  - `query` (필수): 검색하려는 쿼리 문자열입니다.
  - `sort` (선택, 기본값: "accuracy"): 결과 문서 정렬 방식입니다. "accuracy"는 정확도 순, "recency"는 최신 순입니다.
  - `page` (선택, 기본값: 1): 결과 페이지 번호로, 1과 50 사이의 값을 가져야 합니다.
  - `size` (선택, 기본값: 10): 한 페이지에 표시할 문서 수로, 1과 50 사이의 값을 가져야 합니다.

- 응답:

  - 정상

    - `state`: 요청의 성공 여부를 나타냅니다. (`"SUCCESS"` 또는 `"FAIL"`)
    - `origin`: 검색 결과의 출처를 나타냅니다. (`"kakao"` 또는 `"naver"`)
    - `meta` : 검색 결과에 대한 메타 정보를 포함합니다.
      - `total_count`: 검색 결과의 전체 개수입니다.
      - `pageable_count`: 현재 페이지에서 사용 가능한 검색 결과의 개수입니다.
      - `_end`: 검색 결과가 끝났는지 여부를 나타냅니다. (`true` 또는 `false`)
    - `documents`: 검색 결과에 대한 상세 정보를 포함하는 배열입니다.
      - `title`: 문서의 제목입니다.
      - `contents`: 문서의 내용 요약입니다.
      - `url`: 문서의 원본 URL입니다.
      - `blogname`: 블로그 이름입니다.
      - `thumbnail`: 문서의 썸네일 이미지 URL입니다.
      - `datetime`: 문서의 작성일시입니다.

    

- 예시

  - 정상 요청 예시 (로컬 서버 기준)
    http://localhost8080/api/search/v1.0/blog?query=test

  - 정상 응답 예시

    ```json
    {
        "state": "SUCCESS",
        "origin": "kakao",
        "meta": {
            "total_count": 1826162,
            "pageable_count": 800,
            "_end": false
        },
        "documents": [
            {
                "title": "NestJS — <b>Test</b> Driven Development (1)",
                "contents": "이전에 쓰던 To Do List를 폐기하고, NestJS MVC 환경에서 TDD를 수행하는 법을 작성하려 한다. 크게 Unit <b>Test</b>와 Integration <b>Test</b>로 나누어서 연재할 예정이다. 간략한 MVC 흔히 서비스의 프론트엔드에서 발생하는 요청을 처리하기 위해 우리는 백엔드의 시스템을 MVC 디자인 패턴을 이용해 설계하곤 한다. MVC 패턴을...",
                "url": "http://dev-whoan.xyz/102",
                "blogname": "짧은머리 개발자",
                "thumbnail": "https://search4.kakaocdn.net/argon/130x130_85_c/L9EJ0FzI9iO",
                "datetime": "2023-03-10T12:59:55+09:00"
            }
         ]
     }
    ```

    

  - 실패 요청 예시 (필수 값 입력 없음)

    - http://localhost:8080/api/search/v1.0/blog

  - 실패 응답 예시

    ```json
    {
        "state": "FAIL",
        "url": "/api/search/v1.0/blog",
        "message": "필수 값을 확인해주세요"
    }
    ```

    



2. 인기 검색어 API

- 엔드포인트: `GET /api/search/{keyword}/popular`

- 요청 파라미터:
  
  - `keyword`: 검색하려는 키워드 문자열입니다.
  
- 응답:
  
  - `id`: 검색어의 고유 ID입니다.
  - `keyword`: 검색어 텍스트입니다.
  - `count` : 검색 된 횟수 입니다.
  
- 예시

  - 요청 예시(로컬 서버 기준)

    - http://localhost:8080/api/keyword/v1.0/blog

  - 응답 예시

    ```json
    [
        {
            "id": 14,
            "keyword": "12124124",
            "count": 20
        },
        {
            "id": 13,
            "keyword": "10",
            "count": 14
        },
        {
            "id": 12,
            "keyword": "9",
            "count": 11
        },
        {
            "id": 11,
            "keyword": "8",
            "count": 9
        },
        {
            "id": 10,
            "keyword": "7",
            "count": 9
        },
        {
            "id": 9,
            "keyword": "6",
            "count": 6
        },
        {
            "id": 8,
            "keyword": "5",
            "count": 5
        },
        {
            "id": 1,
            "keyword": "321123123",
            "count": 3
        },
        {
            "id": 5,
            "keyword": "2",
            "count": 3
        },
        {
            "id": 7,
            "keyword": "4",
            "count": 3
        }
    ]
    ```

    



