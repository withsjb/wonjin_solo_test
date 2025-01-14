Feature: [웅진 Daily] smartAll
#  * 있는 시나리오 >> 정기배포 시 확인 포함 할 시나리오

  Scenario: [웅진 스마트올] 구성 확인 (CheckSmartAll_001)
    Given 웅진 스마트올 버튼 클릭
    Then 웅진 스마트올 메뉴구성 확인
    And 웅진 스마트올 - "스타샵" 서브메뉴 클릭
    Then 웅진 스마트올 스타샵 실행 확인
    And 웅진 스마트올 - "올링고 번역" 서브메뉴 클릭
    Then 웅진 스마트올 올링고 번역 실행 확인
    And 북클럽 버튼 클릭
    Then 웅진 북클럽 확인


  Scenario Outline: 오늘의학습 초등 학습캘린더 학년 변경 확인 (CheckSmartAll_002)
    Given 웅진 스마트올 버튼 클릭
    When 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    And 스마트올 학습캘린더 클릭
    And 학습캘린더 학년 변경 > <year>
    Then 오늘의 학습 <year> 변경 확인

    Examples:
      |year|
      |"1학년"|
      |"2학년"|
      |"4학년"|
      |"5학년"|
      |"6학년"|
      |"3학년"|


  Scenario: [웅진 스마트올 - AI 학습센터] AI 학습센터 확인 (CheckSmartAll_003)
    Given 웅진 스마트올 버튼 클릭
    When 웅진 스마트올 - "AI 학습센터" 서브메뉴 클릭
    And 웅진 스마트올 AI 학습센터 "AI 학습센터" 서브메뉴 클릭
    Then 웅진 스마트올 AI 학습센터 - AI 학습센터 구성 확인
    And 웅진 스마트올 AI 학습센터 "영어" 서브메뉴 클릭
    Then 웅진 스마트올 AI 학습센터 영어 실행 확인
    And 웅진 스마트올 AI 학습센터 "AI 연산" 서브메뉴 클릭
    Then 웅진 스마트올 AI 학습센터 AI 연산 구성 확인
    Then 웅진 스마트올 AI 학습센터 AI 연산 실행 확인
    And 웅진 스마트올 AI 학습센터 "공부지원/게임" 서브메뉴 클릭
    Then 웅진 스마트올 AI 학습센터 - 공부지원_게임 구성 확인


  Scenario Outline: [웅진 스마트올 - AI 학습센터] 독서 메뉴 확인 (CheckSmartAll_004)*
    Given 웅진 스마트올 버튼 클릭
    And 웅진 스마트올 - "AI 학습센터" 서브메뉴 클릭
    And 웅진 스마트올 AI 학습센터 "독서" 서브메뉴 클릭
    And 웅진 스마트올 AI 학습센터 독서 "<menu>" 클릭
    Then 웅진 스마트올 AI 학습센터 독서 "<menu>" 확인

    Examples:
      | menu |
      | 초등 필독서 |
      | 초등 교양서 |
      | BEARPORT |
      | 수준별 영어 도서관 |
      | Disney |


  Scenario: [웅진 스마트올 - 초등 포털] 기능 확인 (CheckSmartAll_005)*
    Given 웅진 스마트올 버튼 클릭
    When 웅진 스마트올 - "초등 포털" 서브메뉴 클릭
    And 초등포털 인기 영역 클릭
    Then 초등포털 인기 영역 확인
    And 초등포털 퀴즈풀기 클릭
    Then 초틍포털 퀴즈풀기 확인
    And 초등포털 영어TV 클릭
    Then 초등포털 영어TV 영역 확인
    And 초등포털 3분_회화 클릭
    Then 초등포털 3분_회화 확인
    And 초등포털 우리끼리 투표 클릭
    Then 초등포털 우리끼리 투표 확인
    And 초등포털 체험의 발견 클릭
    Then 초등포털 체험의 발견 확인
    And 초등포털 그람책 클릭
    Then 초등포털 그람책 확인
    And 초등포털 직업영역 클릭
    Then 초등포털 직업영역 확인


#  전체과목 > 1. 임의의 카테고리 진입
#  학년 별로 있는 과목 선택

  Scenario: 스마트올 [기초학력다지기] 1학년 확인 (CheckSmartAll_006)
    Given 웅진 스마트올 버튼 클릭
    When 전체과목 버튼 클릭
    And 1학년 버튼 클릭
    And 전체과목 "원리 한글" 클릭
    Then 스마트올 전체과목 서브메뉴 "액티브북" "원리 한글" 확인


  Scenario: 스마트올 [AI학교공부] 확인 (CheckSmartAll_007)
    Given 웅진 스마트올 버튼 클릭
    When 전체과목 버튼 클릭
    And 2학년 버튼 클릭
    And 전체과목 "국어" 클릭
    Then 스마트올 전체과목 2학년 국어 진입 확인


  Scenario: 스마트올 [AI 학교시험] 실행 체크 (CheckSmartAll_008)
    Given 전체과목 버튼 클릭
    When 3학년 버튼 클릭
    And 전체과목 "실력완성문제" 클릭
    Then 스마트올 전체과목 3학년 실력완성문제 진입 확인


  Scenario: 스마트올 4학년 [AI 수학 마스터] 실행 체크 (CheckSmartAll_009)
    Given 웅진 스마트올 버튼 클릭
    When 전체과목 버튼 클릭
    And 4학년 버튼 클릭
    And 3학년 전체과목 분수 행성 클릭
    Then 전체과목 3학년 AI수학마스터 분수행성 확인


  Scenario: 스마트올 5학년 [대치TOP초등-탐구] 확인 (CheckSmartAll_010)
    Given 웅진 스마트올 버튼 클릭
    When 전체과목 버튼 클릭
    And 5학년 버튼 클릭
    And 전체과목 "미래탐구 과학" 클릭
    Then 5학년 대치TOP초등 미래탐구 과학 진입 확인


  Scenario: 스마트올 6학년 [AI 수학 마스터] 실행 체크 (CheckSmartAll_011)
    Given 웅진 스마트올 버튼 클릭
    When 전체과목 버튼 클릭
    And 6학년 버튼 클릭
    And 6학년 전체과목 분수 행성 클릭
    Then 전체과목 6학년 AI수학마스터 분수행성 확인

# ====================  예비초  ====================

  Scenario Outline: 스마트올 오늘의학습 키즈 학습캘린더 변경 확인 (CheckSmartAll_012)*
    Given 웅진 스마트올 버튼 클릭
    When 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    And 스마트올 학습캘린더 클릭
    And 학습캘린더 학년 변경 > <year>
    Then 오늘의 학습 <year> 변경 학습카드 확인

    Examples:
      |year|
      |"4세"|
      |"1단계"|
      |"2단계"|
      |"예비초"|

#  임의의 학습 카테고리 클릭
  Scenario: 키즈 전체과목 한글 체크 (CheckSmartAll_013)
    Given 웅진 스마트올 버튼 클릭
    When 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    And 스마트올 학습캘린더 클릭
    And 학습캘린더 학년 변경 > "예비초"
    When 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    When 키즈 과목 바로가기 클릭
    And 키즈 "한글" 바로가기 클릭
    And 키즈 바로가기 "이야기 한글" 클릭
    Then 스마트올 전체과목 서브메뉴 "예비초" "이야기 한글" 확인


  Scenario: 키즈 전체과목 국어 체크 (CheckSmartAll_014)
    Given 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    When 키즈 과목 바로가기 클릭
    When 키즈 "국어" 바로가기 클릭
    And 키즈 바로가기 "읽기" 클릭
    Then 스마트올 전체과목 서브메뉴 "예비초" "읽기" 확인


  Scenario: 키즈 전체과목 수학 체크 (CheckSmartAll_015)
    Given 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    When 키즈 과목 바로가기 클릭
    When 키즈 "수학" 바로가기 클릭
    And 키즈 바로가기 "수학 TV" 클릭
    Then 스마트올 전체과목 서브메뉴 "예비초" "수학 TV" 확인


  Scenario: 키즈 전체과목 영어 체크 (CheckSmartAll_016)
    Given 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    When 키즈 과목 바로가기 클릭
    When 키즈 "영어" 바로가기 클릭
    And 키즈 바로가기 "말하기&쓰기" 클릭
    Then 스마트올 전체과목 서브메뉴 "예비초" "말하기&쓰기" 확인


  Scenario: 키즈 전체과목 독서 체크 (CheckSmartAll_017)
    Given 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    When 키즈 과목 바로가기 클릭
    When 키즈 "독서" 바로가기 클릭
    And 키즈 바로가기 "독서사고력 기초" 클릭
    Then 스마트올 전체과목 서브메뉴 "액티브북" "독서사고력 기초" 확인


  Scenario: 키즈 전체과목 한자 체크 (CheckSmartAll_018)
    Given 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    When 키즈 과목 바로가기 클릭
    When 키즈 "한자" 바로가기 클릭
    And 키즈 바로가기 "한자 카드" 클릭
    Then 스마트올 전체과목 서브메뉴 "예비초" "한자 카드" 확인



  Scenario: [웅진 스마트올 - 키즈] 햄버거 버튼 기능 확인 (CheckSmartAll_019)*
    Given 스마트올 키즈 더보기 "스타샵" 클릭
    Then 스마트올 키즈 더보기 스타샵 확인
    And 스마트올 키즈 더보기 "스티커판" 클릭
    Then 스마트올 키즈 더보기 스티커판 확인
    And 스마트올 키즈 더보기 "학습 전체보기" 클릭
    Then 스마트올 키즈 더보기 학습 전체보기 확인
    And 스마트올 키즈 더보기 "스마드올 NEWS" 클릭
    Then 스마트올 키즈 더보기 스마트올 NEWS 확인
    And 스마트올 키즈 더보기 "올링고" 클릭
    Then 스마트올 키즈 더보기 올링고 확인
    And 스마트올 키즈 더보기 "선생님이랑" 클릭
    Then 스마트올 키즈 더보기 선생님이랑 확인
    And 스마트올 키즈 더보기 "독서앨범" 클릭
    Then 스마트올 키즈 더보기 독서앨범 확인
    And 독서앨범 나가기
    And 스마트올 키즈 더보기 "학교 공부 예습" 클릭
    Then 스마트올 키즈 더보기 학교 공부 예습 확인
    And 학교 공부 예습 나가기
    And 웅진 스마트올 - "오늘의 학습" 서브메뉴 클릭
    And 스마트올 학습캘린더 클릭
    And 학습캘린더 학년 변경 > "3학년"





